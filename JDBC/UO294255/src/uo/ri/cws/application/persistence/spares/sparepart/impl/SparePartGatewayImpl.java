package uo.ri.cws.application.persistence.spares.sparepart.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.spares.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;

public class SparePartGatewayImpl implements SparePartGateway {

    @Override
    public void add(SparePartRecord t) throws PersistenceException {
        PreparedStatement pst = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TSPAREPARTS_ADD"));
            pst.setString(1, t.id);
            pst.setString(2, t.code);
            pst.setString(3, t.description);
            pst.setInt(4, t.maxStock);
            pst.setInt(5, t.minStock);
            pst.setDouble(6, t.price);
            pst.setInt(7, t.stock);
            pst.setLong(8, t.version);
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

    @Override
    public void remove(String id) throws PersistenceException {
        PreparedStatement pst = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TSPAREPARTS_REMOVE_BY_ID"));

            pst.setString(1, id);
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

    @Override
    public void update(SparePartRecord t) throws PersistenceException {
        PreparedStatement pst = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TSPAREPARTS_UPDATE_BY_ID"));

            pst.setString(1, t.description);
            pst.setInt(2, t.minStock);
            pst.setInt(3, t.maxStock);
            pst.setInt(4, t.stock);
            pst.setDouble(5, t.price);
            pst.setLong(6, t.version);
            pst.setString(7, t.id);

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

    @Override
    public Optional<SparePartRecord> findById(String id) throws PersistenceException {
        Optional<SparePartRecord> result = Optional.empty();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TSPAREPARTS_FIND_BY_ID"));
            pst.setString(1, id);
            rs = pst.executeQuery();
            result = RecordAssembler.toSparePartRecord(rs);
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
    public List<SparePartRecord> findAll() throws PersistenceException {

        List<SparePartRecord> result = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TSPAREPARTS_FIND_ALL"));
            rs = pst.executeQuery();

            result = RecordAssembler.toSparePartRecordList(rs);
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
    public Optional<SparePartRecord> findBySparePartCode(String code) {
        Optional<SparePartRecord> result = Optional.empty();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TSPAREPARTS_FIND_BY_CODE"));
            pst.setString(1, code);
            rs = pst.executeQuery();
            result = RecordAssembler.toSparePartRecord(rs);
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
    public void updateStockAndPrice(int quantity, Double precio, String code) {
        PreparedStatement pst = null;
        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TSPAREPARTS_UPDATE_BY_CODE"));
            pst.setDouble(1, precio);
            pst.setInt(2, quantity);
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

    @Override
    public List<SparePartRecord> findByDescription(String descriptionSparePart) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<SparePartRecord> result = new ArrayList<>();

        try {
            Connection c = Jdbc.getCurrentConnection();
            pst = c.prepareStatement(Queries.get("TSPAREPARTS_FIND_BY_DESCRIPTION"));
            pst.setString(1, "%" + descriptionSparePart + "%");
            rs = pst.executeQuery();

            result = RecordAssembler.toSparePartRecordList(rs);

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

}
