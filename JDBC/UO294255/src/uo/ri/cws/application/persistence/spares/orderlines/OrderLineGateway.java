package uo.ri.cws.application.persistence.spares.orderlines;

import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.spares.orderlines.OrderLineGateway.OrderLineRecord;

public interface OrderLineGateway extends Gateway<OrderLineRecord> {

    public static class OrderLineRecord {
        public String id;
        public long version;

        public String orderId;
        public String sparePartId;
        public String sparePartCode;
        public String sparePartDescription;
        public double price;
        public int quantity;
    }

    List<OrderLineRecord> findByOrderCode(String code);

    List<OrderLineRecord> findBySparePartId(String sparePartId);
}
