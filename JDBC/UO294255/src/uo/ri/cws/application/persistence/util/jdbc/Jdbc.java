package uo.ri.cws.application.persistence.util.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import uo.ri.conf.Conf;

public class Jdbc {

    private static ThreadLocal<Connection> threadConnection = new ThreadLocal<>();

    private static String URL = Conf.getProperty("DB_URL");
    private static String USER = Conf.getProperty("DB_USER");
    private static String PASS = Conf.getProperty("DB_PASS");

    public static Connection createThreadConnection() throws SQLException {
        Connection con = DriverManager.getConnection(URL, USER, PASS);
        threadConnection.set(con);
        return con;
    }

    public static Connection getCurrentConnection() throws SQLException {
        Connection con = threadConnection.get();
        if (con == null || con.isClosed()) {
            con = createThreadConnection();
        }
        return con;
    }

    public static void close(Connection c) {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException e) {

            }
        }
    }

    public static void closeConnection() {
        Connection con = threadConnection.get();
        if (con != null) {
            close(con);
            threadConnection.remove();
        }
    }
}
