package uo.ri.cws.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TCREDITCARDS")
public class CreditCard extends PaymentMean {

    @Column(unique = true)
    private String number;
    private String type;
    private LocalDate validThru;

    CreditCard() {
    }

    public CreditCard(String number, String type, LocalDate validThru) {
        ArgumentChecks.isNotBlank(number);
        ArgumentChecks.isNotBlank(type);
        ArgumentChecks.isNotNull(validThru);

        this.number = number;
        this.type = type;
        this.validThru = validThru;
    }

    @Override
    public void pay(double amount) {
        if (!canPay(amount)) {
            throw new IllegalStateException("Not enough available to pay");
        }
        super.pay(amount);
    }

    @Override
    public boolean canPay(Double amount) {
        return isValid();
    }

    private boolean isValid() {
        return validThru.isAfter(LocalDate.now());
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    @Override
    public String toString() {
        return "CreditCard [number=" + number + ", type=" + type
            + ", validThru=" + validThru + "]";
    }
}
