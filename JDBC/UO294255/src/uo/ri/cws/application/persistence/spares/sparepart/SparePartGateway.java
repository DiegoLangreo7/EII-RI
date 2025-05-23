package uo.ri.cws.application.persistence.spares.sparepart;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway.SparePartRecord;

public interface SparePartGateway extends Gateway<SparePartRecord> {

    public static class SparePartRecord {
        public String id;
        public long version;

        public String code;
        public String description;
        public int stock;
        public int minStock;
        public int maxStock;
        public double price;
    }

    public Optional<SparePartRecord> findBySparePartCode(String code);
    
    public List<SparePartRecord> findByDescription(String descriptionSparePart);

    public void updateStockAndPrice(int quantity, Double precio, String code);

}
