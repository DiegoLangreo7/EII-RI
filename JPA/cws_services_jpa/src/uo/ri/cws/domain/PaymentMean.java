package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TPAYMENTMEANS")
public abstract class PaymentMean extends BaseEntity {

    private double accumulated = 0.0;

    @ManyToOne
    private Client client;
    @OneToMany(mappedBy = "paymentMean")
    private Set<Charge> charges = new HashSet<>();

    public abstract boolean canPay(Double amount);

    public void pay(double amount) {
        this.accumulated += amount;
    }

    void _setClient(Client client) {
        this.client = client;
    }

    public Set<Charge> getCharges() {
        return new HashSet<>(charges);
    }

    Set<Charge> _getCharges() {
        return charges;
    }

    public double getAccumulated() {
        return accumulated;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public String toString() {
        return "PaymentMean [client=" + client + "]";
    }
}