package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public class RecordAssembler {

    private static WorkOrderRecord resultSetToWorkOrderRecord(ResultSet rs)
        throws SQLException {
        WorkOrderRecord result = new WorkOrderRecord();

        result.id = rs.getString("id");
        result.version = rs.getLong("version");

        result.vehicleId = rs.getString("vehicle_Id");
        result.description = rs.getString("description");
        result.date = rs.getTimestamp("date").toLocalDateTime();
        result.amount = rs.getDouble("amount");
        result.state = rs.getString("state");

        result.mechanicId = rs.getString("mechanic_Id");
        result.invoiceId = rs.getString("invoice_Id");

        return result;

    }

    public static Optional<WorkOrderRecord> toWorkOrderRecord(ResultSet m)
        throws SQLException {

        return m.next() ? Optional.of(resultSetToWorkOrderRecord(m))
            : Optional.ofNullable(null);
    }

    public static List<WorkOrderRecord> toWorkOrderRecordList(ResultSet rs)
        throws SQLException {
        List<WorkOrderRecord> res = new ArrayList<>();
        while (rs.next()) {
            res.add(resultSetToWorkOrderRecord(rs));
        }
        return res;
    }

}
