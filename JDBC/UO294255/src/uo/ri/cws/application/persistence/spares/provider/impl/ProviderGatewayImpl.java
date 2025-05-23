package uo.ri.cws.application.persistence.spares.provider.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.spares.provider.ProviderGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class ProviderGatewayImpl implements ProviderGateway {

    @Override
    public void add(ProviderRecord t) throws PersistenceException {
        PreparedStatement pst = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TPROVIDERS_ADD"));

            pst.setString(1, t.id);
            pst.setString(2, t.email);
            pst.setString(3, t.name);
            pst.setString(4, t.nif);
            pst.setString(5, t.phone);
            pst.setLong(6, t.version);

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
            pst = c.prepareStatement(Queries.get("TPROVIDERS_REMOVE_BY_NIF"));
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
    public void update(ProviderRecord t) throws PersistenceException {
        PreparedStatement pst = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TPROVIDERS_UPDATE_BY_NIF"));

            pst.setString(1, t.name);
            pst.setString(2, t.email);
            pst.setString(3, t.phone);
            pst.setString(4, t.nif);

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
    public Optional<ProviderRecord> findById(String id) throws PersistenceException {
        return null;
    }

    @Override
    public List<ProviderRecord> findAll() throws PersistenceException {
        return null;
    }

    @Override
    public Optional<ProviderRecord> findByNif(String nif) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TPROVIDERS_FIND_BY_NIF"));
            pst.setString(1, nif);
            rs = pst.executeQuery();
            return RecordAssembler.toProviderRecord(rs);
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
    public Optional<ProviderRecord> findByValues(String name, String email, String phone) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TPROVIDERS_FIND_BY_NAME_EMAIL_PHONE"));
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            rs = pst.executeQuery();
            return RecordAssembler.toProviderRecord(rs);
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
    public List<ProviderRecord> findByName(String name) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TPROVIDERS_FIND_BY_NAME"));
            pst.setString(1, name);
            rs = pst.executeQuery();
            return RecordAssembler.toProviderRecordList(rs);
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
    public List<ProviderRecord> findBySparePartCode(String code) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TPROVIDERS_FIND_BY_SPAREPART_CODE"));
            pst.setString(1, code);
            rs = pst.executeQuery();
            return RecordAssembler.toProviderRecordList(rs);
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
