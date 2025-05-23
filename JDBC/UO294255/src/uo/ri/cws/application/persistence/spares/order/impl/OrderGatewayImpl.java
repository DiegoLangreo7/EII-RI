package uo.ri.cws.application.persistence.spares.order.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.spares.order.OrderGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class OrderGatewayImpl implements OrderGateway {

    @Override
    public void add(OrderRecord order) throws PersistenceException {
        PreparedStatement pstOrder = null;
        try {
            Connection c = Jdbc.getCurrentConnection();

            pstOrder = c.prepareStatement(Queries.get("TORDERS_ADD"));
            pstOrder.setString(1, order.id);
            pstOrder.setString(2, order.code);
            pstOrder.setDate(3, Date.valueOf(order.orderedDate));
            pstOrder.setDate(4,
                order.receptionDate != null ? Date.valueOf(order.receptionDate) : null);
            pstOrder.setDouble(5, order.amount);
            pstOrder.setString(6, order.state);
            pstOrder.setLong(7, order.version);
            pstOrder.setString(8, order.providerId);

            pstOrder.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            try {
                if (pstOrder != null) {
                    pstOrder.close();
                }
            } catch (SQLException ignored) {
            }
        }
    }

    @Override
    public void remove(String id) throws PersistenceException {
    }

    @Override
    public void update(OrderRecord order) throws PersistenceException {
    }

    @Override
    public Optional<OrderRecord> findById(String id) throws PersistenceException {
        return Optional.empty();
    }

    @Override
    public List<OrderRecord> findAll() throws PersistenceException {
        return new ArrayList<>();
    }

    @Override
    public List<OrderRecord> findByProviderNif(String nifProvider) {
        List<OrderRecord> result = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TORDERS_FIND_BY_PROVIDER_NIF"));
            pst.setString(1, nifProvider);
            rs = pst.executeQuery();
            result = RecordAssembler.toOrderRecordList(rs);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ignored) {
            }
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ignored) {
            }
        }
        return result;
    }

    @Override
    public Optional<OrderRecord> findByCode(String code) {
        Optional<OrderRecord> result = Optional.empty();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TORDERS_FIND_BY_CODE"));
            pst.setString(1, code);
            rs = pst.executeQuery();
            result = RecordAssembler.toOrderRecord(rs);
        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ignored) {
            }
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ignored) {
            }
        }
        return result;
    }

    @Override
    public void receive(String code, LocalDate receptionDate, String state) {
        PreparedStatement pst = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TORDERS_UPDATE_BY_CODE"));
            pst.setDate(1, Date.valueOf(receptionDate));
            pst.setString(2, state);
            pst.setString(3, code);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ignored) {
            }
        }
    }
}