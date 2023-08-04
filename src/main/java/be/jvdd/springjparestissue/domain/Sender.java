package be.jvdd.springjparestissue.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sender {

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    
    
    @Column(name = "supplier_address_id")
    private Long supplierAddressId;
    
    @Column(name = "supplier_bankaccount_id")
    private Long supplierBankAccountId;
    
    public void setAddress(SupplierAddress address) {
        if(Objects.isNull(address)) return;
        this.supplierAddressId = ((SupplierAddress) address).getId();
    }
    
    public void setBankAccount(SupplierBankAccount bankAccount) {
        if(Objects.isNull(bankAccount)) return;
        this.supplierBankAccountId = ((SupplierBankAccount) bankAccount).getId();
    }
    
    @JsonIgnore
    public SupplierAddress getAddress(){
        if(Objects.isNull(this.supplier)) return null;
        return this.supplier.getAddresses().stream().filter(a -> Objects.equals(a.getId(), supplierAddressId)).findFirst().orElse(null);
    }
    
    @JsonIgnore
    public SupplierBankAccount getBankAccount(){
        if(Objects.isNull(this.supplier)) return null;
        return this.supplier.getBankAccounts().stream().filter(b -> Objects.equals(b.getId(), supplierBankAccountId)).findFirst().orElse(null);
    }
    
}
