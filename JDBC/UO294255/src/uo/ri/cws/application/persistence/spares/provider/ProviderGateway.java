package uo.ri.cws.application.persistence.spares.provider;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.spares.provider.ProviderGateway.ProviderRecord;

public interface ProviderGateway extends Gateway<ProviderRecord> {

    public static class ProviderRecord {
        public String id;
        public long version;

        public String nif;
        public String name;
        public String email;
        public String phone;
    }

    public Optional<ProviderRecord> findByNif(String nif);

    public Optional<ProviderRecord> findByValues(String name, String email,
        String phone);

    public List<ProviderRecord> findByName(String providerName);

    public List<ProviderRecord> findBySparePartCode(String code);
}
