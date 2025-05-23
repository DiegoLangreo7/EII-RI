package uo.ri.cws.application.persistence.spares.supply.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.spares.supply.SupplyGateway.SupplyRecord;

public class RecordAssembler {

    public static Optional<SupplyRecord> toSuppliesRecord(ResultSet m) 
        throws SQLException {
        return m.next() ? 
            Optional.of(resultSetToSupplyRecord(m)) : Optional.ofNullable(null);
    }

    public static List<SupplyRecord> toSuppliesRecordList(ResultSet rs) 
        throws SQLException {
        List<SupplyRecord> res = new ArrayList<>();
        while (rs.next()) {
            res.add(resultSetToSupplyRecord(rs));
        }
        return res;
    }

    private static SupplyRecord resultSetToSupplyRecord(ResultSet rs) 
        throws SQLException {
        SupplyRecord s = new SupplyRecord();
        s.provider.id = rs.getString("provider_id");
        s.sparePart.id = rs.getString("sparepart_id");
        s.provider.nif = rs.getString("nif");
        s.sparePart.code = rs.getString("code");
        s.price = rs.getDouble("price");
        s.deliveryTerm = rs.getInt("deliveryTerm");
        s.id = rs.getString("id");
        s.version = rs.getLong("version");
        return s;
    }

}
