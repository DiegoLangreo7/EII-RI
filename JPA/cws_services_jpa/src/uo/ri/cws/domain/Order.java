package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

@Entity
@Table(name = "TORDERS")
public class Order extends BaseEntity {

    public enum OrderState {
        PENDING, RECEIVED
    }

    @Column(unique = true)
    private String code;

    @Column(nullable = false)
    private LocalDate orderedDate;

    private int amount;
    private LocalDate receptionDate;

    @Enumerated(EnumType.STRING)
    private OrderState state = OrderState.PENDING;

    @ManyToOne
    private Provider provider;

    @ElementCollection
    @CollectionTable(name = "TORDERLINES", joinColumns = @JoinColumn(name = "Order_ID"))
    private Set<OrderLine> orderLines = new HashSet<>();

    Order() {
    }

    public Order(String code, LocalDate date) {
        ArgumentChecks.isNotBlank(code);
        ArgumentChecks.isNotNull(date);
        this.code = code;
        this.orderedDate = date;
    }

    public Order(String code) {
        this(code, LocalDate.now());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(LocalDate orderedDate) {
        this.orderedDate = orderedDate;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getReceptionDate() {
        return receptionDate;
    }

    public OrderState getState() {
        return state;
    }

    protected void _setProvider(Provider p) {
        this.provider = p;
    }

    public Provider getProvider() {
        return provider;
    }

    public Set<OrderLine> getOrderLines() {
        return new HashSet<>(orderLines);
    }

    protected Set<OrderLine> _getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Set<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public void addSparePartFromSupply(Supply supply) {
        ArgumentChecks.isNotNull(supply);
        SparePart sp = supply.getSparePart();
        if (sp.isUnderStock()) {
            for (OrderLine o : orderLines) {
                StateChecks.isFalse(o.getSparePart().equals(sp));
            }
            orderLines
                .add(new OrderLine(supply.getSparePart(), supply.getPrice()));
            this.amount += supply.getPrice() * sp.getQuantityToOrder();
        }
    }

    public void removeSparePart(SparePart sparePart) {
        ArgumentChecks.isNotNull(sparePart);
        for (OrderLine o : orderLines) {
            if (o.getSparePart().equals(sparePart)) {
                orderLines.remove(o);
                break;
            }
        }
    }

    public void receive() {
        StateChecks.isTrue(isPending());
        this.state = OrderState.RECEIVED;
        this.receptionDate = LocalDate.now();
        for (OrderLine o : orderLines) {
            o.receive();
        }
    }

    public boolean isReceived() {
        return this.state.equals(OrderState.RECEIVED);
    }

    public boolean isPending() {
        return this.state.equals(OrderState.PENDING);
    }

}
