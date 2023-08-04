package be.jvdd.springjparestissue.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Supplier extends AbstractSupplier {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "supplier_id")
    @Fetch(FetchMode.SUBSELECT)
    @Valid
    private List<SupplierAddress> addresses = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "supplier_id", nullable = false)
    @Fetch(FetchMode.SUBSELECT)
    @Valid
    private List<SupplierBankAccount> bankAccounts = new ArrayList<>();
    
    public SupplierAddress getFirstAddress() {
        if (addresses.isEmpty()) return null;
        return addresses.get(0);
    }
    
    public SupplierBankAccount getFirstBankAccount() {
        if (bankAccounts.isEmpty()) return null;
        return bankAccounts.get(0);
    }
}
