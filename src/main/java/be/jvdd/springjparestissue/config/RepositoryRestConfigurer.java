package be.jvdd.springjparestissue.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED;

@Configuration
public class RepositoryRestConfigurer implements org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.setRepositoryDetectionStrategy(ANNOTATED);
        config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream().map(Type::getJavaType).toArray(Class[]::new));
    }
}
