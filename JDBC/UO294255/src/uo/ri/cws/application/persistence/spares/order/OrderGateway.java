package uo.ri.cws.application.persistence.spares.order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.spares.order.OrderGateway.OrderRecord;

public interface OrderGateway extends Gateway<OrderRecord> {

    public static class OrderRecord {
        public String id;
        public long version;

        public String code;
        public LocalDate orderedDate;
        public LocalDate receptionDate;
        public double amount;
        public String state;

        public String providerId;
        public String providerNif;
        public String providerName;
    }

    List<OrderRecord> findByProviderNif(String nifProvider);

    Optional<OrderRecord> findByCode(String code);

    void receive(String code, LocalDate receptionDate, String state);
}
