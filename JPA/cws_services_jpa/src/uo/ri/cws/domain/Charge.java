package uo.ri.cws.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import uo.ri.cws.domain.Associations.Settle;
import uo.ri.cws.domain.Invoice.InvoiceState;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TCHARGES")
public class Charge extends BaseEntity {

    private double amount = 0.0;

    @ManyToOne
    private Invoice invoice;
    @ManyToOne
    private PaymentMean paymentMean;

    Charge() {
    }

    public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
        ArgumentChecks.isNotNull(invoice);
        ArgumentChecks.isNotNull(paymentMean);
        ArgumentChecks.isTrue(amount >= 0);

        this.amount = amount;
        this.invoice = invoice;
        this.paymentMean = paymentMean;

        paymentMean.pay(amount);
        Settle.link(invoice, this, paymentMean);
    }

    /**
     * Unlinks this charge and restores the accumulated to the payment mean.
     *
     * @throws IllegalStateException if the invoice is already settled.
     */
    public void rewind() {
        ArgumentChecks
            .isTrue(invoice.getState().equals(InvoiceState.NOT_YET_PAID));

        paymentMean.pay(amount);
        Settle.unlink(this);
    }

    @Override
    public String toString() {
        return "Charge [amount=" + amount + ", invoice=" + invoice
            + ", paymentMean=" + paymentMean + "]";
    }

    public double getAmount() {
        return amount;
    }

    void _setAmount(double amount) {
        this.amount = amount;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    void _setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public PaymentMean getPaymentMean() {
        return paymentMean;
    }

    void _setPaymentMean(PaymentMean paymentMean) {
        this.paymentMean = paymentMean;
    }
}
