package be.jvdd.springjparestissue.domain;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice {
    @Id
    @GeneratedValue
    private Long id;

    @Nonnull
    private String name;

    @Embedded
    @Valid
    private Sender sender;
}
