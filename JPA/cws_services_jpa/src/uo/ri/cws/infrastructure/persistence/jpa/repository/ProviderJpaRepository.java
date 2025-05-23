package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.ProviderRepository;
import uo.ri.cws.domain.Provider;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class ProviderJpaRepository extends BaseJpaRepository<Provider>
    implements ProviderRepository {

    @Override
    public Optional<Provider> findByNif(String nif) {
        return Jpa.getManager()
            .createNamedQuery("Provider.findByNif", Provider.class)
            .setParameter(1, nif)
            .getResultStream()
            .findFirst();
    }

    @Override
    public List<Provider> findByNameMailPhone(String name, String email,
        String phone) {
        return Jpa.getManager()
            .createNamedQuery("Provider.findByNameMailPhone", Provider.class)
            .setParameter(1, name)
            .setParameter(2, email)
            .setParameter(3, phone)
            .getResultList(); 
    }

    @Override
    public List<Provider> findByName(String name) {
        return Jpa.getManager()
            .createNamedQuery("Provider.findByName", Provider.class)
            .setParameter(1, name)
            .getResultList(); 
    }

    @Override
    public List<Provider> findBySparePartCode(String code) {
        return Jpa.getManager()
            .createNamedQuery("Provider.findBySparePartCode", Provider.class)
            .setParameter(1, code)
            .getResultList(); 
    }

}
