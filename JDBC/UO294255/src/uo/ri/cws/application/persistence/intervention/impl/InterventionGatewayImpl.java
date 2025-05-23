package uo.ri.cws.application.persistence.intervention.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class InterventionGatewayImpl implements InterventionGateway {

    @Override
    public void add(InterventionRecord t) throws PersistenceException {

    }

    @Override
    public void remove(String id) throws PersistenceException {

    }

    @Override
    public void update(InterventionRecord t) throws PersistenceException {

    }

    @Override
    public Optional<InterventionRecord> findById(String id) {
        return null;
    }

    @Override
    public List<InterventionRecord> findAll() throws PersistenceException {
        return null;
    }

    @Override
    public List<InterventionRecord> findByMechanicId(String id)
            throws PersistenceException {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(
                Queries.get("TINTERVENTIONS_FIND_BY_MECHANIC_ID"));
            pst.setString(1, id);
            rs = pst.executeQuery();
            return RecordAssembler.toInterventionRecordList(rs);

        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ignored) {}
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ignored) {}
        }
    }
    
} 
