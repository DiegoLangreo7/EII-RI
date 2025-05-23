package uo.ri.cws.application.service.util.command.executor;

import java.sql.Connection;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.exception.BusinessException;

public class JdbcCommandExecutor {

    public <T> T execute(Command<T> cmd) throws BusinessException {
        try {
            Connection c = Jdbc.createThreadConnection();

            try {
                c.setAutoCommit(false);

                T res = cmd.execute();

                c.commit();
                return res;

            } catch (Exception e) {
                c.rollback();
                throw e;

            } finally {
                Jdbc.closeConnection();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistenceException(e);
        }
    }

}
