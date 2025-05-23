package uo.ri.cws.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TCASHES")
public class Cash extends PaymentMean {

    Cash() {
    }

    public Cash(Client client) {
        ArgumentChecks.isNotNull(client);

        Associations.Hold.link(this, client);

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
        return true;
    }

    @Override
    public String toString() {
        return "Cash [toString()=" + super.toString() + "]";

    }

}