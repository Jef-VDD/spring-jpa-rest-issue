package be.jvdd.springjparestissue.domain;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@MappedSuperclass
public abstract class AbstractSupplier {

    @NotBlank
    @Length(min=3, max=50)
    private String name;
}
