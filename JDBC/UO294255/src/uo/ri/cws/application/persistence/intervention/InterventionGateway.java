package uo.ri.cws.application.persistence.intervention;

import java.util.Date;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public interface InterventionGateway extends Gateway<InterventionRecord> {

    /**
     * @param id, refers to the mechanic id
     * @return a list with all interventions done by the mechanic or an empty
     *         list if there are none
     * @throws PersistenceException
     */
    List<InterventionRecord> findByMechanicId(String id)
        throws PersistenceException;

    public static class InterventionRecord {
        public String id;
        public long version;

        public String workorderId;
        public String mechanicId;
        public Date date;
        public int minutes;
    }

}
