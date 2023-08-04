package be.jvdd.springjparestissue.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "supplier_address")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierAddress {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String streetAndNumber;

    private String addressLine2;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String city;
}
