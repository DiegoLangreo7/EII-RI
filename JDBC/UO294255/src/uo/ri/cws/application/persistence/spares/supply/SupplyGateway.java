package uo.ri.cws.application.persistence.spares.supply;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.spares.supply.SupplyGateway.SupplyRecord;

public interface SupplyGateway extends Gateway<SupplyRecord> {

    public static class SupplyRecord {
        public String id;
        public long version;

        public SupplierProviderRecord provider = new SupplierProviderRecord();
        public SuppliedSparePartRecord sparePart = new SuppliedSparePartRecord();

        public double price;
        public int deliveryTerm;

        public static class SupplierProviderRecord {
            public String id;
            public String nif;
            public String name;
        }

        public static class SuppliedSparePartRecord {
            public String id;
            public String code;
            public String description;
        }
    }

    public Optional<SupplyRecord> findByNifAndCode(String nif, String code);

    public List<SupplyRecord> findByProviderNif(String nifProvider);

    public List<SupplyRecord> findBySparePartCode(String codeSparePart);
}
