package uo.ri.cws.application.persistence.intervention.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public class RecordAssembler {

    public static InterventionRecord toInterventionRecord(ResultSet rs)
        throws SQLException {

        InterventionRecord result = new InterventionRecord();

        result.id = rs.getString("id");
        result.mechanicId = rs.getString("mechanic_id");
        result.workorderId = rs.getString("workorder_id");
        result.date = rs.getDate("date");
        result.minutes = rs.getInt("minutes");

        return result;
    }

    public static List<InterventionRecord> toInterventionRecordList
    (ResultSet rs) throws SQLException {
        List<InterventionRecord> result = new ArrayList<>();
        while (rs.next()) {
            result.add(toInterventionRecord(rs));
        }
        return result;
    }
}
