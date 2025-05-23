package uo.ri.cws.application.persistence.spares.orderlines.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.spares.orderlines.OrderLineGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class OrderLineGatewayImpl implements OrderLineGateway {

    @Override
    public void add(OrderLineRecord line) throws PersistenceException {
        PreparedStatement pst = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TORDERLINES_ADD"));
            pst.setString(1, line.orderId);
            pst.setString(2, line.sparePartId);
            pst.setDouble(3, line.price);
            pst.setInt(4, line.quantity);

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
    public List<OrderLineRecord> findByOrderCode(String code) {
        List<OrderLineRecord> result = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TORDERLINES_FIND_BY_ORDER_CODE"));
            pst.setString(1, code);
            rs = pst.executeQuery();
            result = RecordAssembler.toOrderLinesRecordList(rs);
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
    public List<OrderLineRecord> findBySparePartId(String sparePartId) {
        List<OrderLineRecord> result = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TORDERLINES_FIND_BY_SPAREPART_ID"));
            pst.setString(1, sparePartId);
            rs = pst.executeQuery();
            result = RecordAssembler.toOrderLinesRecordList(rs);
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
    public void remove(String id) throws PersistenceException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(OrderLineRecord t) throws PersistenceException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Optional<OrderLineRecord> findById(String id) throws PersistenceException {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public List<OrderLineRecord> findAll() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }
}
