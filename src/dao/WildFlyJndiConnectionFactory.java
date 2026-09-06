/**
 * 
 */
package dao;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import tankDB.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public final class WildFlyJndiConnectionFactory implements ConnectionFactory {

    private static volatile WildFlyJndiConnectionFactory instance;

    private final DataSource dataSource;

    private WildFlyJndiConnectionFactory() {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:global/jdbc/VotacaoUCP");
        } catch (NamingException e) {
            throw new IllegalStateException("Falha ao obter DataSource JNDI do WildFly.", e);
        }
    }

    public static WildFlyJndiConnectionFactory getInstance() {
        if (instance == null) {
            synchronized (WildFlyJndiConnectionFactory.class) {
                if (instance == null) {
                    instance = new WildFlyJndiConnectionFactory();
                }
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
