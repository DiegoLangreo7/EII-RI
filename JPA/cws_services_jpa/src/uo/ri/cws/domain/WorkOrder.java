package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TWORKORDERS", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "date", "vehicle_id" }) })
public class WorkOrder extends BaseEntity {

    public enum WorkOrderState {
        OPEN, ASSIGNED, FINISHED, INVOICED
    }

    private LocalDateTime date;
    private String description;
    private double amount = 0.0;

    @Enumerated(EnumType.STRING)
    private WorkOrderState state = WorkOrderState.OPEN;

    @ManyToOne
    private Vehicle vehicle;

    @ManyToOne
    private Mechanic mechanic;

    @ManyToOne
    private Invoice invoice;

    @OneToMany(mappedBy = "workOrder")
    private Set<Intervention> interventions = new HashSet<>();

    WorkOrder() {
    }

    public WorkOrder(Vehicle vehicle, String description) {
        this(LocalDateTime.now(), description, vehicle);
    }

    public WorkOrder(LocalDateTime date, String description, Vehicle vehicle) {
        ArgumentChecks.isNotNull(date);
        ArgumentChecks.isNotBlank(description);
        ArgumentChecks.isNotNull(vehicle);

        this.date = date;
        this.description = description;
        Associations.Fix.link(vehicle, this);

    }

    public WorkOrder(Vehicle vehicle) {
        this(LocalDateTime.now(), "no-description", vehicle);
    }

    public WorkOrder(Vehicle vehicle, LocalDateTime date) {
        this(date, "no-description", vehicle);
    }

    public WorkOrder(Vehicle vehicle, LocalDateTime date, String string) {
        this(date, string, vehicle);
    }

    /**
     * Changes it to INVOICED state given the right conditions This method is
     * called from Invoice.addWorkOrder(...)
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not FINISHED, or -
     *                               The work order is not linked with the
     *                               invoice
     */
    public void markAsInvoiced() {
        if (!isFinished() || this.invoice == null) {
            throw new IllegalStateException(
                "Work order is not FINISHED or not linked with the invoice");
        }
        this.state = WorkOrderState.INVOICED;
    }

    /**
     * Changes it to FINISHED state given the right conditions and computes the
     * amount
     *
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in ASSIGNED
     *                               state, or - The work order is not linked
     *                               with a mechanic
     */
    public void markAsFinished() {
        if (!isAssigned() || this.mechanic == null) {
            throw new IllegalStateException(
                "The work order is not in ASSIGNED state, or is not linked with a mechanic");
        }

        this.amount = interventions.stream().mapToDouble(i -> i.getAmount())
            .sum();
        this.state = WorkOrderState.FINISHED;
    }

    /**
     * Changes it back to FINISHED state given the right conditions This method
     * is called from Invoice.removeWorkOrder(...)
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not INVOICED, or -
     *                               The work order is still linked with the
     *                               invoice
     */
    public void markBackToFinished() {
        if (!isInvoiced()) {
            throw new IllegalStateException("The work order is not INVOICED");
        }
        if (this.invoice != null) {
            throw new IllegalStateException(
                "The work order is still linked with the invoice");
        }

        this.amount = interventions.stream()
            .mapToDouble(Intervention::getAmount).sum();

        this.state = WorkOrderState.FINISHED;
    }

    /**
     * Links (assigns) the work order to a mechanic and then changes its state
     * to ASSIGNED
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in OPEN state,
     *                               or - The work order is already linked with
     *                               another mechanic
     */
    public void assignTo(Mechanic mechanic) {
        if (!state.equals(WorkOrderState.OPEN)) {
            throw new IllegalStateException(
                "The work order is not in OPEN state");
        }
        if (this.mechanic != null) {
            throw new IllegalStateException(
                "The work order is already linked with another mechanic");
        }
        this.mechanic = mechanic;
        this.state = WorkOrderState.ASSIGNED;
    }

    /**
     * Unlinks (deassigns) the work order and the mechanic and then changes its
     * state back to OPEN
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in ASSIGNED
     *                               state
     */
    public void desassign() {
        if (!isAssigned()) {
            throw new IllegalStateException(
                "The work order is not in ASSIGNED state");
        }
        Associations.Assign.unlink(mechanic, this);
        this.state = WorkOrderState.OPEN;
    }

    /**
     * In order to assign a work order to another mechanic is first have to be
     * moved back to OPEN state and unlinked from the previous mechanic.
     * 
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if - The work order is not in FINISHED
     *                               state
     */
    public void reopen() {
        if (!isFinished()) {
            throw new IllegalStateException(
                "The work order is not in FINISHED state");
        }
        Associations.Assign.unlink(mechanic, this);
        this.state = WorkOrderState.OPEN;
    }

    public Set<Intervention> getInterventions() {
        return new HashSet<>(interventions);
    }

    Set<Intervention> _getInterventions() {
        return interventions;
    }

    void _setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    void _setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    void _setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return "WorkOrder [date=" + date.format(formatter) + ", description="
        + description + ", amount=" + amount + ", state=" + state
        + ", vehicle=" + vehicle + "]";
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public Mechanic getMechanic() {
        return this.mechanic;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return Math.round(amount * 100.0) / 100.0;
    }

    public WorkOrderState getState() {
        return state;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public boolean isFinished() {
        return this.state.equals(WorkOrderState.FINISHED);
    }

    public boolean isInvoiced() {
        return this.state.equals(WorkOrderState.INVOICED);
    }

    public boolean isAssigned() {
        return this.state.equals(WorkOrderState.ASSIGNED);
    }
}
