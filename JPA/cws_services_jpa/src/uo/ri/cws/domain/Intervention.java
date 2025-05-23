package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TINTERVENTIONS", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "workorder_id", "mechanic_id",
        "date" }) })
public class Intervention extends BaseEntity {
    // natural attributes
    private LocalDateTime date;
    private int minutes;

    // accidental attributes
    @ManyToOne
    @JoinColumn(name = "workorder_id")
    private WorkOrder workOrder;

    @ManyToOne
    @JoinColumn(name = "mechanic_id")
    private Mechanic mechanic;

    @OneToMany(mappedBy = "intervention")
    private Set<Substitution> substitutions = new HashSet<>();

    Intervention() {
    }

    public Intervention(Mechanic mechanic, WorkOrder workOrder,
        LocalDateTime date, int minutes) {
        ArgumentChecks.isNotNull(mechanic);
        ArgumentChecks.isNotNull(workOrder);
        ArgumentChecks.isNotNull(date);
        ArgumentChecks.isTrue(minutes >= 0);

        this.date = date;
        this.minutes = minutes;
        Associations.Intervene.link(workOrder, this, mechanic);
    }

    public Intervention(Mechanic mechanic, WorkOrder workOrder, int minutes) {
        this(mechanic, workOrder, LocalDateTime.now(), minutes);
    }

    void _setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    void _setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public Set<Substitution> getSubstitutions() {
        return new HashSet<>(substitutions);
    }

    Set<Substitution> _getSubstitutions() {
        return substitutions;
    }

    @Override
    public String toString() {
        return "Intervention [date=" + date + ", minutes=" + minutes
            + ", workOrder=" + workOrder + ", mechanic=" + mechanic + "]";
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getMinutes() {
        return minutes;
    }

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public Double getAmount() {
        double pricePerHour = this.workOrder.getVehicle().getVehicleType()
            .getPricePerHour();
        double priceWork = this.minutes * (pricePerHour / 60);
        double priceParts = this.substitutions.stream()
            .mapToDouble(s -> s.getAmount()).sum();
        return priceWork + priceParts;
    }

}
