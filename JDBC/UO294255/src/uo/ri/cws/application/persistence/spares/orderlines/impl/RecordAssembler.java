package uo.ri.cws.application.persistence.spares.orderlines.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.spares.orderlines.OrderLineGateway.OrderLineRecord;

public class RecordAssembler {

    public static List<OrderLineRecord> toOrderLinesRecordList(ResultSet rs)
        throws SQLException {
        List<OrderLineRecord> res = new ArrayList<>();
        while (rs.next()) {
            res.add(resultSetToOrderLineRecord(rs));
        }
        return res;
    }

    private static OrderLineRecord resultSetToOrderLineRecord(ResultSet rs)
        throws SQLException {
        OrderLineRecord o = new OrderLineRecord();
        o.sparePartId = rs.getString("sparepart_id");
        o.sparePartCode = rs.getString("code");
        o.sparePartDescription = rs.getString("description");
        o.price = rs.getDouble("price");
        o.quantity = rs.getInt("quantity");
        return o;
    }
}
