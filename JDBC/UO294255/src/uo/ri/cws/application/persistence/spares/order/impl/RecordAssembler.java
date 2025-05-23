package uo.ri.cws.application.persistence.spares.order.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.spares.order.OrderGateway.OrderRecord;

public class RecordAssembler {

    public static Optional<OrderRecord> toOrderRecord(ResultSet m)
        throws SQLException {
        return m.next() ? Optional.of(resultSetToOrderRecord(m)) : Optional.empty();
    }

    public static List<OrderRecord> toOrderRecordList(ResultSet rs)
        throws SQLException {
        List<OrderRecord> res = new ArrayList<>();
        while (rs.next()) {
            res.add(resultSetToOrderRecord(rs));
        }
        return res;
    }

    private static OrderRecord resultSetToOrderRecord(ResultSet rs)
        throws SQLException {
        OrderRecord order = new OrderRecord();
        order.id = rs.getString("id");
        order.amount = rs.getDouble("amount");
        order.code = rs.getString("code");
        order.orderedDate = rs.getDate("orderedDate").toLocalDate();

        if (rs.getDate("receptionDate") != null) {
            order.receptionDate = rs.getDate("receptionDate").toLocalDate();
        }

        order.state = rs.getString("state");
        order.version = rs.getLong("version");
        order.providerId = rs.getString("provider_id");
        order.providerName = rs.getString("provider_name");
        order.providerNif = rs.getString("provider_nif");

        return order;
    }
}
