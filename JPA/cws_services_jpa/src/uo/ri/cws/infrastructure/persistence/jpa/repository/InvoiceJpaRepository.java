package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.Optional;

import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class InvoiceJpaRepository
		extends BaseJpaRepository<Invoice>
		implements InvoiceRepository {


	@Override
	public Long getNextInvoiceNumber() {
		return Jpa.getManager()
				.createNamedQuery("Invoice.getNextInvoiceNumber", long.class)
				.getSingleResult(); 
	}

    @Override
    public Optional<Invoice> findByNumber(Long numero) {
        // CASO NO IMPLEMENTADO
        return null;
    }

}
