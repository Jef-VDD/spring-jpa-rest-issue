package be.jvdd.springjparestissue.repository;


import be.jvdd.springjparestissue.domain.Sender;
import be.jvdd.springjparestissue.domain.Supplier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface SupplierRestResource extends PagingAndSortingRepository<Supplier, Long>, CrudRepository<Supplier, Long> {

    @Override
    <S extends Supplier> S save(S Supplier);

    @Override
    void delete(Supplier supplier);

    @Override
    Optional<Supplier> findById(Long supplierId);
}

