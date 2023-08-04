
package be.jvdd.springjparestissue.repository;

import be.jvdd.springjparestissue.domain.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface InvoiceRestResource extends PagingAndSortingRepository<Invoice, Long>, CrudRepository<Invoice, Long> {

    @Override
    Invoice save(Invoice invoice);

    @Override
    Optional<Invoice> findById(Long invoiceId);

    @Override
    void deleteById(Long invoiceId);

    @Override
    void delete(Invoice invoice);
}

