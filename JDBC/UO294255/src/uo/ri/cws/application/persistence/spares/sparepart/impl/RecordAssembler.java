package uo.ri.cws.application.persistence.spares.sparepart.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway.SparePartRecord;

public class RecordAssembler {

    public static Optional<SparePartRecord> toSparePartRecord(ResultSet m)
        throws SQLException {

        return m.next() ? Optional.of(resultSetToSparePartRecord(m))
            : Optional.ofNullable(null);
    }

    public static List<SparePartRecord> toSparePartRecordList(ResultSet rs)
        throws SQLException {
        List<SparePartRecord> res = new ArrayList<>();
        while (rs.next()) {
            res.add(resultSetToSparePartRecord(rs));
        }
        return res;
    }

    private static SparePartRecord resultSetToSparePartRecord(ResultSet rs)
        throws SQLException {
        SparePartRecord s = new SparePartRecord();
        s.code = rs.getString("code");
        s.price = rs.getDouble("price");
        s.description = rs.getString("description");
        s.stock = rs.getInt("stock");
        s.minStock = rs.getInt("minStock");
        s.maxStock = rs.getInt("maxStock");
        s.id = rs.getString("id");
        s.version = rs.getLong("version");
        return s;
    }

}
