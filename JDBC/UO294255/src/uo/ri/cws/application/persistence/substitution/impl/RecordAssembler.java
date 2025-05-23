package uo.ri.cws.application.persistence.substitution.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.substitution.SubstitutionGateway.SubstitutionRecord;

public class RecordAssembler {

    public static SubstitutionRecord toSubstitutionRecord(ResultSet rs) throws SQLException {
        SubstitutionRecord r = new SubstitutionRecord();
        r.id = rs.getString("id");
        r.quantity = rs.getInt("quantity");
        r.version = rs.getLong("version");
        r.interventionId = rs.getString("intervention_id");
        r.sparePartId = rs.getString("sparepart_id");
        return r;
    }

    public static List<SubstitutionRecord> toSubstitutionRecordList(ResultSet rs) throws SQLException {
        List<SubstitutionRecord> result = new ArrayList<>();
        while (rs.next()) {
            result.add(toSubstitutionRecord(rs));
        }
        return result;
    }
}
