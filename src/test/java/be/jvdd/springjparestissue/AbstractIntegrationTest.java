package be.jvdd.springjparestissue;

import be.jvdd.springjparestissue.repository.InvoiceRestResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.config;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;
import static wiremock.com.google.common.net.HttpHeaders.X_FORWARDED_HOST;
import static wiremock.com.google.common.net.HttpHeaders.X_FORWARDED_PORT;
import static wiremock.com.google.common.net.HttpHeaders.X_FORWARDED_PROTO;

@SpringBootTest(classes = SpringJpaRestIssueApplication.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureRestDocs
public abstract class AbstractIntegrationTest {
    @Autowired
    public ObjectMapper objectMapper;

    @LocalServerPort
    protected int port;

    @Value("${server.servlet.context-path}")
    protected String contextPath;

    @RegisterExtension
    final RestDocumentationExtension restDocumentationExtension = new RestDocumentationExtension("target/generated-snippets");

    @BeforeEach
    public void setupRestAssuredAndRestDocSpec(RestDocumentationContextProvider restDocumentation) {

        RestAssured.port = port;
        RestAssured.basePath = contextPath;
        config = config()
                .objectMapperConfig(objectMapperConfig().jackson2ObjectMapperFactory((cls, charset) -> objectMapper))
                .redirect(redirectConfig().followRedirects(false));

        RestAssured.requestSpecification =
                new RequestSpecBuilder()
                        .addFilter(documentationConfiguration(restDocumentation))
                        .addHeader(X_FORWARDED_PROTO, "https")
                        .addHeader(X_FORWARDED_HOST, "www.jvdd.com")
                        .addHeader(X_FORWARDED_PORT, "")
                        .build();
    }

    @Inject
    protected InvoiceRestResource invoiceRestResource;
}
