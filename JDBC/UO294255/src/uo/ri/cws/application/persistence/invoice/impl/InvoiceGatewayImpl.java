package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class InvoiceGatewayImpl implements InvoiceGateway {

    @Override
    public void add(InvoiceRecord t) throws PersistenceException {
    	Connection c = null;
    	PreparedStatement pst = null;
        try {
            c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TINVOICES_ADD"));

            pst.setString(1, t.id);
            pst.setDouble(2, t.amount);
            pst.setDate(3, Date.valueOf(t.date));
            pst.setLong(4, t.number);
            pst.setString(5, t.state);
            pst.setDouble(6, t.vat);
            pst.setLong(7, t.version);

            pst.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ignored) {}
        }

    }

    @Override
    public void remove(String id) throws PersistenceException {

    }

    @Override
    public void update(InvoiceRecord t) throws PersistenceException {

    }

    @Override
    public Optional<InvoiceRecord> findById(String id)
        throws PersistenceException {
        return null;
    }

    @Override
    public List<InvoiceRecord> findAll() throws PersistenceException {
        return null;
    }

    @Override
    public Optional<InvoiceRecord> findByNumber(Long number) {
        return null;
    }

    @Override
    public Long getNextInvoiceNumber() throws PersistenceException {
        return null;
    }

}
