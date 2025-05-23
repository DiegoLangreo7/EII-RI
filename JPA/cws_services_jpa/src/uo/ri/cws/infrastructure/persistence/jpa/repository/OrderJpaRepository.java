package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.OrderRepository;
import uo.ri.cws.domain.Order;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class OrderJpaRepository 
extends BaseJpaRepository<Order> 
implements OrderRepository {


    @Override
    public Optional<Order> findByCode(String code) {
        return Jpa.getManager()
            .createNamedQuery("Order.findByCode", Order.class)
            .setParameter(1, code) 
            .getResultStream()
            .findFirst();
    }

    @Override
    public List<Order> findByProviderNif(String nif) {
        return Jpa.getManager()
            .createNamedQuery("Order.findByProviderNif", Order.class)
            .setParameter(1, nif)
            .getResultList(); 
    }

    @Override
    public List<Order> findBySparePartCode(String code) {
        return Jpa.getManager()
            .createNamedQuery("Order.findBySparePartCode", Order.class)
            .setParameter(1, code)
            .getResultList(); 
    }


}
