package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.math.Round;

@Entity
@Table(name = "TINVOICES")
public class Invoice extends BaseEntity {

    public enum InvoiceState {
        NOT_YET_PAID, PAID
    }

    @Column(unique = true)
    private Long number;
    private LocalDate date;
    private double amount;
    private double vat;
    @Enumerated(EnumType.STRING)
    private InvoiceState state = InvoiceState.NOT_YET_PAID;

    @OneToMany(mappedBy = "invoice")
    private Set<WorkOrder> workOrders = new HashSet<>();
    @OneToMany(mappedBy = "invoice")
    private Set<Charge> charges = new HashSet<>();

    Invoice() {
    }

    public Invoice(Long number) {
        this(number, LocalDate.now(), new ArrayList<WorkOrder>());
    }

    public Invoice(Long number, LocalDate date) {
        this(number, date, new ArrayList<WorkOrder>());
    }

    public Invoice(Long number, List<WorkOrder> workOrders) {
        this(number, LocalDate.now(), workOrders);
    }

    public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) {

        ArgumentChecks.isTrue(number >= 0);
        ArgumentChecks.isNotNull(date);
        ArgumentChecks.isNotNull(workOrders);
        this.number = number;
        this.date = date;
        for (WorkOrder o : workOrders) {
            this.addWorkOrder(o);
        }

    }

    /**
     * Computes amount and vat (vat depends on the date)
     */
    private void computeAmount() {
        this.amount = workOrders.stream().mapToDouble(w -> w.getAmount()).sum();
        LocalDate vatChange = LocalDate.of(2012, 7, 1);
        double vatRate = (date.isBefore(vatChange)) ? 0.18 : 0.21;
        this.vat = this.amount * vatRate;
        this.vat = Round.twoCents(vat);
        this.amount += vat;
        this.amount = Round.twoCents(amount);
    }

    /**
     * Adds (double links) the workOrder to the invoice and updates the amount
     * and vat
     * 
     * @param workOrder
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
     */
    public void addWorkOrder(WorkOrder workOrder) {
        this.workOrders.add(workOrder);
        Associations.Bill.link(this, workOrder);
        computeAmount();
        workOrder.markAsInvoiced();
    }

    /**
     * Removes a work order from the invoice and recomputes amount and vat
     * 
     * @param workOrder
     * @see UML_State diagrams on the problem statement document
     * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
     */
    public void removeWorkOrder(WorkOrder workOrder) {
        this.workOrders.remove(workOrder);
        Associations.Bill.unlink(this, workOrder);
        computeAmount();
        workOrder.markBackToFinished();
    }

    /**
     * Marks the invoice as PAID, but
     * 
     * @throws IllegalStateException if - Is already settled - Or the amounts
     *                               paid with charges to payment means do not
     *                               cover the total of the invoice
     */
    public void settle() {
        if (this.state.equals(InvoiceState.PAID)) {
            throw new IllegalStateException("Invoice is already settled");
        }

        double totalPaid = charges.stream().mapToDouble(c -> c.getAmount())
            .sum();
        if (totalPaid < this.getAmount()) {
            throw new IllegalStateException(
                "Total paid does not cover the invoice amount");
        }

        this.state = InvoiceState.PAID;
    }

    public Set<WorkOrder> getWorkOrders() {
        return new HashSet<>(workOrders);
    }

    Set<WorkOrder> _getWorkOrders() {
        return workOrders;
    }

    public Set<Charge> getCharges() {
        return new HashSet<>(charges);
    }

    Set<Charge> _getCharges() {
        return charges;
    }

    public InvoiceState getState() {
        return this.state;
    }

    public boolean isNotSettled() {
        return !this.state.equals(InvoiceState.PAID);
    }

    public Double getAmount() {
        return Round.twoCents(amount);
    }

    public Long getNumber() {
        return number;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getVat() {
        return Round.twoCents(vat);
    }

    @Override
    public String toString() {
        return "Invoice [number=" + number + ", date=" + date + ", amount="
            + amount + ", vat=" + vat + ", state=" + state + "]";
    }

}
