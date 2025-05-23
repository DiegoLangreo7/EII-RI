package uo.ri.cws.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TSUPPLIES")
public class Supply extends BaseEntity {

    private int deliveryTerm;
    private double price;

    @ManyToOne
    private Provider provider;

    @ManyToOne
    private SparePart sparePart;

    Supply() {

    }

    public Supply(Provider provider, SparePart sparePart, double price,
        int deliveryTerm) {
        this.provider = provider;
        this.sparePart = sparePart;
        this.price = price;
        this.deliveryTerm = deliveryTerm;
    }

    public int getDeliveryTerm() {
        return deliveryTerm;
    }

    public double getPrice() {
        return price;
    }

    public Provider getProvider() {
        return provider;
    }

    public SparePart getSparePart() {
        return sparePart;
    }

    public void setDeliveryTerm(int deliveryTerm) {
        this.deliveryTerm = deliveryTerm;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
