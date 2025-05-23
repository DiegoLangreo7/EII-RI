package uo.ri.cws.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TSUBSTITUTIONS", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "SPAREPART_ID", "INTERVENTION_ID" }) })
public class Substitution extends BaseEntity {

    private int quantity;

    @ManyToOne
    private SparePart sparePart;

    @ManyToOne
    private Intervention intervention;

    Substitution() {
    }

    public Substitution(SparePart sparePart, Intervention intervention,
        int quantity) {
        ArgumentChecks.isTrue(quantity > 0);
        ArgumentChecks.isNotNull(intervention);
        ArgumentChecks.isNotNull(sparePart);

        this.quantity = quantity;
        Associations.Substitute.link(sparePart, this, intervention);
    }

    void _setSparePart(SparePart sparePart) {
        this.sparePart = sparePart;
    }

    void _setIntervention(Intervention intervention) {
        this.intervention = intervention;
    }

    public int getQuantity() {
        return quantity;
    }

    public SparePart getSparePart() {
        return sparePart;
    }

    public Intervention getIntervention() {
        return intervention;
    }

    public double getAmount() {
        return this.quantity * this.sparePart.getPrice();
    }

    @Override
    public String toString() {
        return "Substitution [quantity=" + quantity + ", sparePart=" + sparePart
            + ", intervention=" + intervention + "]";
    }

}
