package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public class RecordAssembler {

    public static Optional<MechanicRecord> toMechanicRecord(ResultSet m)
        throws SQLException {

        return m.next() ? Optional.of(resultSetToMechanicRecord(m))
            : Optional.ofNullable(null);
    }

    public static List<MechanicRecord> toMechanicRecordList(ResultSet rs)
        throws SQLException {
        List<MechanicRecord> res = new ArrayList<>();
        while (rs.next()) {
            res.add(resultSetToMechanicRecord(rs));
        }
        return res;
    }

    private static MechanicRecord resultSetToMechanicRecord(ResultSet rs)
        throws SQLException {
        MechanicRecord value = new MechanicRecord();
        value.id = rs.getString("id");
        value.version = rs.getLong("version");
        value.nif = rs.getString("nif");
        value.name = rs.getString("name");
        value.surname = rs.getString("surname");
        return value;
    }

}
