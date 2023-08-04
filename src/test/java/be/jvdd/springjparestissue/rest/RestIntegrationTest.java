package be.jvdd.springjparestissue.rest;

import be.jvdd.springjparestissue.AbstractIntegrationTest;
import be.jvdd.springjparestissue.domain.Invoice;
import be.jvdd.springjparestissue.domain.Sender;
import be.jvdd.springjparestissue.domain.Supplier;
import be.jvdd.springjparestissue.domain.SupplierAddress;
import be.jvdd.springjparestissue.domain.SupplierBankAccount;
import be.jvdd.springjparestissue.repository.InvoiceRestResource;
import be.jvdd.springjparestissue.repository.SupplierRestResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class RestIntegrationTest extends AbstractIntegrationTest {
    
    public static final String IBAN_1 = "BE92957656218823";
    public static final String IBAN_2 = "BE00000000000003";
    
    @Autowired
    public SupplierRestResource supplierRestResource;
    
    @Autowired
    public InvoiceRestResource invoiceRestResource;

    @Test
    public void invoicePutTest() throws JsonProcessingException {
        Supplier supplier1 = saveSupplierWithName("supplier1", IBAN_1);
        Supplier supplier2 = saveSupplierWithName("supplier2", IBAN_2);

        Invoice invoice = saveInvoiceWithSupplier(supplier1);
        
        String invoicePutJson = getUIInvoicePutJson(invoice.getId().toString(), supplier2.getId().toString(),
                                           supplier2.getFirstAddress().getId().toString(), "null");
        
        Invoice invoiceResource = invoiceRestResource.findById(invoice.getId()).orElseThrow();
        //Initial supplier is supplier 1
        assertThat(invoiceResource.getSender().getSupplier().getId()).isEqualTo(supplier1.getId());
    
        given()
                .contentType(JSON)
                .body(invoicePutJson)
                .when()
                .pathParam("invoiceId", invoice.getId())
                .put("/invoices/{invoiceId}")
                .then()
                .statusCode(SC_OK);
    
        invoiceResource = invoiceRestResource.findById(invoice.getId()).orElseThrow();
        //Name will be updated via json put
        assertThat(invoiceResource.getName()).isEqualTo("invoice updated via UI");
        //Supplier will not be updated and the test will fail
        assertThat(objectMapper.writeValueAsString(invoiceResource.getSender().getSupplier())).isEqualTo(objectMapper.writeValueAsString(supplier2));
        assertThat(invoiceResource.getSender().getAddress()).isEqualTo(supplier2.getFirstAddress());
    }
    
    @Test
    public void invoicePatchTest() throws JsonProcessingException {
        Supplier supplier1 = saveSupplierWithName("supplier1", IBAN_1);
        Supplier supplier2 = saveSupplierWithName("supplier2", IBAN_2);
        
        Invoice invoice = saveInvoiceWithSupplier(supplier1);
        
        String invoicePutJson = getUIInvoicePutJson(invoice.getId().toString(), supplier2.getId().toString(),
                                                    supplier2.getFirstAddress().getId().toString(), "null");
        given()
                .contentType(JSON)
                .body(invoicePutJson)
                .when()
                .pathParam("invoiceId", invoice.getId())
                .patch("/invoices/{invoiceId}")
                .then()
                .statusCode(SC_OK);
        
        Invoice invoiceResource = invoiceRestResource.findById(invoice.getId()).orElseThrow();
        assertThat(invoiceResource.getName()).isEqualTo("invoice updated via UI");
        assertThat(objectMapper.writeValueAsString(invoiceResource.getSender().getSupplier())).isEqualTo(objectMapper.writeValueAsString(supplier2));
        assertThat(invoiceResource.getSender().getAddress()).isEqualTo(supplier2.getFirstAddress());
    }

    private Invoice saveInvoiceWithSupplier(Supplier supplier) throws JsonProcessingException {
        Invoice invoice = Invoice.builder()
                .name("invoice")
                .sender(
                        Sender.builder()
                                .supplier(supplier)
                                .supplierAddressId(supplier.getFirstAddress().getId())
                                .supplierBankAccountId(supplier.getFirstBankAccount().getId())
                                .build()
                )
                .build();
        return invoiceRestResource.save(invoice);
    }

    private Supplier saveSupplierWithName(String supplierName, String iban) throws JsonProcessingException {
        SupplierAddress supplierAddress = new SupplierAddress();
        supplierAddress.setCity(supplierName + "city");
        supplierAddress.setPostalCode("1000");
        supplierAddress.setStreetAndNumber("street 1");
    
        SupplierBankAccount supplierBankAccount = new SupplierBankAccount();
        supplierBankAccount.setBic("GEBABEBB");
        supplierBankAccount.setIban(iban);
        
        Supplier supplier = Supplier.builder()
                .addresses(List.of(supplierAddress))
                .bankAccounts(List.of(supplierBankAccount))
                .build();
        supplier.setName(supplierName);
        
        return given()
                .contentType(JSON)
                .body(objectMapper.writeValueAsString(supplier))
                .post("/suppliers")
                .then()
                .statusCode(SC_CREATED)
                .extract().as(Supplier.class);
    }
    
    private String getUIInvoicePutJson(String invoiceId, String supplier2Id, String address2Id, String bankAccount2Id){
        return """
                {
                  "id": "%s",
                  "name": "invoice updated via UI",
                  "sender": {
                    "supplier": "/api/suppliers/%s",
                    "supplierAddressId": %s,
                    "supplierBankAccountId": %s
                  }
                }
                """.formatted(invoiceId, supplier2Id, address2Id, bankAccount2Id);
    }
}
