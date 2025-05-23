package uo.ri.cws.application.persistence.substitution.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.substitution.SubstitutionGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;
import uo.ri.util.exception.NotYetImplementedException;

public class SubstitutionGatewayImpl implements SubstitutionGateway {

    @Override
    public void add(SubstitutionRecord t) throws PersistenceException {
        throw new NotYetImplementedException();
    }

    @Override
    public void remove(String id) throws PersistenceException {
        throw new NotYetImplementedException();
    }

    @Override
    public void update(SubstitutionRecord t) throws PersistenceException {
        throw new NotYetImplementedException();
    }

    @Override
    public Optional<SubstitutionRecord> findById(String id) throws PersistenceException {
        throw new NotYetImplementedException();
    }

    @Override
    public List<SubstitutionRecord> findAll() throws PersistenceException {
        throw new NotYetImplementedException();
    }

    @Override
    public List<SubstitutionRecord> findBySparePartId(String id) throws PersistenceException {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TSUBSTITUTIONS_FIND_BY_SPAREPART_ID"));
            pst.setString(1, id);
            rs = pst.executeQuery();
            return RecordAssembler.toSubstitutionRecordList(rs);
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
