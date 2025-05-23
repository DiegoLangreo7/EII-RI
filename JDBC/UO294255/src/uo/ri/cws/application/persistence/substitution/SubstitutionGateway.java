package uo.ri.cws.application.persistence.substitution;

import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.substitution.SubstitutionGateway.SubstitutionRecord;

public interface SubstitutionGateway extends Gateway<SubstitutionRecord>{
    
    List<SubstitutionRecord> findBySparePartId(String id)
        throws PersistenceException;
    
    public class SubstitutionRecord {
        public String id;
        public int quantity;
        public long version;
        public String interventionId;
        public String sparePartId;
    }
    
}
