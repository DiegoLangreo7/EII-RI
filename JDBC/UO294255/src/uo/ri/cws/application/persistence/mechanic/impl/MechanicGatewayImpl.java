package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class MechanicGatewayImpl implements MechanicGateway {

    @Override
    public void add(MechanicRecord t) throws PersistenceException {
        PreparedStatement pst = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TMECHANICS_ADD"));

            pst.setString(1, t.id);
            pst.setString(2, t.nif);
            pst.setString(3, t.name);
            pst.setString(4, t.surname);
            pst.setLong(5, t.version);

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
        PreparedStatement pst = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TMECHANICS_REMOVE_BY_ID"));

            pst.setString(1, id);
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
    public void update(MechanicRecord t) throws PersistenceException {
        PreparedStatement pst = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TMECHANICS_UPDATE_BY_ID"));

            pst.setString(1, t.name);
            pst.setString(2, t.surname);
            pst.setString(3, t.id);

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
    public Optional<MechanicRecord> findById(String id)
        throws PersistenceException {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TMECHANICS_FIND_BY_ID"));

            pst.setString(1, id);
            rs = pst.executeQuery();
            return RecordAssembler.toMechanicRecord(rs);

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

    @Override
    public List<MechanicRecord> findAll() throws PersistenceException {
        List<MechanicRecord> result = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TMECHANICS_FIND_ALL"));
            rs = pst.executeQuery();

            result = RecordAssembler.toMechanicRecordList(rs);
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
        return result;
    }

    @Override
    public Optional<MechanicRecord> findByNif(String nif)
        throws PersistenceException {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TMECHANICS_FIND_BY_NIF"));

            pst.setString(1, nif);
            rs = pst.executeQuery();
            return RecordAssembler.toMechanicRecord(rs);

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
