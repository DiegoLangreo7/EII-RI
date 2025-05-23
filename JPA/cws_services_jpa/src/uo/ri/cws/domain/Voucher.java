package uo.ri.cws.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TVOUCHERS")
public class Voucher extends PaymentMean {
    @Column(unique = true)
    private String code;
    private double available = 0.0;
    private String description;

    Voucher() {
    }

    public Voucher(String code, double available, String description) {
        ArgumentChecks.isNotBlank(code);
        ArgumentChecks.isNotBlank(description);
        ArgumentChecks.isTrue(available >= 0);

        this.code = code;
        this.available = available;
        this.description = description;
    }

    public Voucher(String code, String description, double available) {
        this(code, available, description);
    }

    /**
     * Augments the accumulated (super.pay(amount)) and decrements the available
     * 
     * @throws IllegalStateException if not enough available to pay
     */
    @Override
    public void pay(double amount) {
        if (!canPay(amount)) {
            throw new IllegalStateException("Not enough available to pay");
        }
        this.available -= amount;
        super.pay(amount);
    }

    @Override
    public boolean canPay(Double amount) {
        return available - amount >= 0;
    }

    public Double getAvailable() {
        return available;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
