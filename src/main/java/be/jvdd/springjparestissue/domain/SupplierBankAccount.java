package be.jvdd.springjparestissue.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "supplier_bankaccount")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierBankAccount {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{2}\\d{2}[A-Z0-9]{9,30}$")
    private String iban;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{4}[A-Z]{2}[A-Z0-9]{2}([A-Z0-9]{3})?$")
    private String bic;
}
