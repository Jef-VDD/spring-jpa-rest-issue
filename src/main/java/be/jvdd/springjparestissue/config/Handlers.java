package be.jvdd.springjparestissue.config;

import be.jvdd.springjparestissue.SpringJpaRestIssueApplication;
import be.jvdd.springjparestissue.domain.Invoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.data.rest.webmvc.RepositoryRestExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = { SpringJpaRestIssueApplication.class, RepositoryRestExceptionHandler.class })
@RepositoryEventHandler
@Slf4j
public class Handlers {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException e) {
        log.warn("Returning HTTP 400 Bad Request", e);
    }
    
    @HandleBeforeSave
    public void handleInvoice(Invoice invoice) {
        log.info("saving invoice");
    }
    
}
