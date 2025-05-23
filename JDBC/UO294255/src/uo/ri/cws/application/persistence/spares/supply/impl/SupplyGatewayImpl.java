package uo.ri.cws.application.persistence.spares.supply.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.spares.supply.SupplyGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class SupplyGatewayImpl implements SupplyGateway {

    @Override
    public void add(SupplyRecord t) throws PersistenceException {
        PreparedStatement pst = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TSUPPLIES_ADD"));

            pst.setString(1, t.id);
            pst.setInt(2, t.deliveryTerm);
            pst.setDouble(3, t.price);
            pst.setLong(4, t.version);
            pst.setString(5, t.provider.id);
            pst.setString(6, t.sparePart.id);

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
            pst = c.prepareStatement(Queries.get("TSUPPLIES_REMOVE_BY_ID"));

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
    public void update(SupplyRecord t) throws PersistenceException {
        PreparedStatement pst = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TSUPPLIES_UPDATE_BY_PROVIDER_NIF_AND_SPAREPART_CODE"));

            pst.setDouble(1, t.price);
            pst.setInt(2, t.deliveryTerm);
            pst.setString(3, t.provider.nif);
            pst.setString(4, t.sparePart.code);

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
    public Optional<SupplyRecord> findById(String id) {
        Optional<SupplyRecord> result = Optional.empty();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TSUPPLIES_FIND_BY_ID"));

            pst.setString(1, id);
            rs = pst.executeQuery();

            result = RecordAssembler.toSuppliesRecord(rs);
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
    public List<SupplyRecord> findAll() throws PersistenceException {
        List<SupplyRecord> result = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TSUPPLIES_FIND_ALL"));
            rs = pst.executeQuery();

            result = RecordAssembler.toSuppliesRecordList(rs);
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
    public Optional<SupplyRecord> findByNifAndCode(String nif, String code) {
        Optional<SupplyRecord> result = Optional.empty();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TSUPPLIES_FIND_BY_PROVIDER_NIF_AND_SPAREPART_CODE"));
            pst.setString(1, nif);
            pst.setString(2, code);
            rs = pst.executeQuery();

            result = RecordAssembler.toSuppliesRecord(rs);
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
    public List<SupplyRecord> findByProviderNif(String nifProvider) {
        List<SupplyRecord> result = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TSUPPLIES_FIND_BY_PROVIDER_NIF"));

            pst.setString(1, nifProvider);
            rs = pst.executeQuery();

            result = RecordAssembler.toSuppliesRecordList(rs);
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
    public List<SupplyRecord> findBySparePartCode(String codeSparePart) {
        List<SupplyRecord> result = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TSUPPLIES_FIND_BY_SPAREPART_CODE"));

            pst.setString(1, codeSparePart);
            rs = pst.executeQuery();

            result = RecordAssembler.toSuppliesRecordList(rs);
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

}
