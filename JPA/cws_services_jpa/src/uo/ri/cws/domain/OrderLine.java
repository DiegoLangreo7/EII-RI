package uo.ri.cws.domain;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import uo.ri.util.assertion.ArgumentChecks;

@Embeddable
public class OrderLine {

    private SparePart sparePart;
    private double price;
    private int quantity;

    OrderLine() {

    }

    public OrderLine(SparePart sparePart, double price, int quantity) {
        ArgumentChecks.isTrue(price > 0);
        ArgumentChecks.isTrue(quantity > 0);
        ArgumentChecks.isNotNull(sparePart);
        this.sparePart = sparePart;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderLine(SparePart sparePart, double price) {
        this(sparePart, price, sparePart.getQuantityToOrder());
    }

    public SparePart getSparePart() {
        return sparePart;
    }

    public void setSparePart(SparePart sparePart) {
        this.sparePart = sparePart;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return this.quantity * this.price;
    }

    public void receive() {
        sparePart.updatePriceAndStock(price, quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, quantity, sparePart);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderLine other = (OrderLine) obj;
        return Double.doubleToLongBits(price) == Double
            .doubleToLongBits(other.price) && quantity == other.quantity
            && Objects.equals(sparePart, other.sparePart);
    }

}
