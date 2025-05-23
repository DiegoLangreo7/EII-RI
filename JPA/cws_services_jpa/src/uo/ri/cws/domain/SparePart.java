package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TSPAREPARTS")
public class SparePart extends BaseEntity {

    @Column(unique = true)
    private String code;
    private String description;
    private int maxStock;
    private int minStock;
    private double price;
    private int stock;

    @OneToMany(mappedBy = "sparePart")
    private Set<Substitution> substitutions = new HashSet<>();

    SparePart() {
    }

    public SparePart(String code, String description, double price, int stock,
        int minStock, int maxStock) {
        ArgumentChecks.isNotBlank(code);
        ArgumentChecks.isNotBlank(description);
        ArgumentChecks.isTrue(price >= 0);
        ArgumentChecks.isTrue(stock >= 0);
        ArgumentChecks.isTrue(minStock >= 0);
        ArgumentChecks.isTrue(maxStock >= 0);
        this.code = code;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.maxStock = maxStock;
        this.minStock = minStock;
    }

    public SparePart(String code, String description, double price) {
        this(code, description, price, 0, 0, 0);
    }

    public SparePart(String code) {
        this(code, "no-description", 0.00, 0, 0, 0);
    }

    public Set<Substitution> getSubstitutions() {
        return new HashSet<>(substitutions);
    }

    Set<Substitution> _getSubstitutions() {
        return substitutions;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "SparePart [code=" + code + ", description=" + description
            + ", price=" + price + "]";
    }

    public int getQuantityToOrder() {
        if (stock < minStock)
            return maxStock - stock;
        else
            return 0;
    }

    public int getTotalUnitsSold() {
        return this.substitutions.stream().mapToInt(s -> s.getQuantity()).sum();
    }

    public void setStock(int stock) {
        ArgumentChecks.isTrue(stock >= 0);
        this.stock = stock;
    }

    public int getStock() {
        return this.stock;
    }

    public void setMinStock(int minStock) {
        ArgumentChecks.isTrue(minStock >= 0);
        this.minStock = minStock;
    }

    public void setMaxStock(int maxStock) {
        ArgumentChecks.isTrue(maxStock >= 0);
        this.maxStock = maxStock;
    }

    public int getMinStock() {
        return this.minStock;
    }

    public int getMaxStock() {
        return this.maxStock;
    }

    public void updatePriceAndStock(double purchasePrice, int newQuantity) {
        ArgumentChecks.isTrue(purchasePrice > 0);
        ArgumentChecks.isTrue(newQuantity > 0);
        int updatedStock = this.stock + newQuantity;
        double updatedPrice = (this.stock * this.price
            + newQuantity * purchasePrice * 1.2) / updatedStock;

        this.stock = updatedStock;
        this.price = updatedPrice;
    }

    public boolean isUnderStock() {
        return this.stock < this.minStock;
    }

}
