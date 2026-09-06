/**
 * 
 */
package tankDB;
import java.sql.Connection;
import java.sql.SQLException;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

/**
 * 
 */
public final class UCPConnectionFactory implements ConnectionFactory {

    private static volatile UCPConnectionFactory instance;

    private final PoolDataSource pool;

    private UCPConnectionFactory() {
        try {
            pool = PoolDataSourceFactory.getPoolDataSource();

            pool.setConnectionFactoryClassName("oracle.jdbc.datasource.impl.OracleDataSource");

            pool.setURL(getEnv("VOTACAO_DB_URL", "jdbc:oracle:thin:@//localhost:1521/FREEPDB1"));
            pool.setUser(getEnv("VOTACAO_DB_USER", "votacao"));
            pool.setPassword(getEnv("VOTACAO_DB_PASSWORD", "votacao"));

            pool.setInitialPoolSize(5);
            pool.setMinPoolSize(5);
            pool.setMaxPoolSize(50);
            pool.setValidateConnectionOnBorrow(true);

        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static UCPConnectionFactory getInstance() {
        if (instance == null) {
            synchronized (UCPConnectionFactory.class) {
                if (instance == null) {
                    instance = new UCPConnectionFactory();
                }
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    private static String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
