package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceRecord;

public class InvoiceRecordAssembler {

    private static InvoiceRecord resultSetToInvoiceRecord(ResultSet rs)
        throws SQLException {
        InvoiceRecord record = new InvoiceRecord();
        record.id = rs.getString("id");
        record.version = rs.getLong("version");

        record.date = rs.getDate("date").toLocalDate();
        record.amount = rs.getDouble("amount");
        record.number = rs.getLong("number");
        record.vat = rs.getDouble("vat");
        record.state = rs.getString("state");
        return record;
    }

    public static List<InvoiceRecord> toInvoiceList(ResultSet rs)
        throws SQLException {
        List<InvoiceRecord> result = new ArrayList<InvoiceRecord>();
        while (rs.next()) {
            result.add(resultSetToInvoiceRecord(rs));
        }
        return result;
    }

    public static Optional<InvoiceRecord> toInvoiceRecord(ResultSet rs)
        throws SQLException {
        Optional<InvoiceRecord> oir = Optional.empty();
        if (rs.next())
            oir = Optional.of(resultSetToInvoiceRecord(rs));
        return oir;
    }

}
