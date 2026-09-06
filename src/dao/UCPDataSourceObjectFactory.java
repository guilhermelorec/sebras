/**
 * 
 */
package dao;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

public class UCPDataSourceObjectFactory implements ObjectFactory {

    private static volatile PoolDataSource pool;

    @Override
    public Object getObjectInstance(
            Object obj,
            Name name,
            Context nameCtx,
            Hashtable<?, ?> environment
    ) throws Exception {

        if (pool == null) {
            synchronized (UCPDataSourceObjectFactory.class) {
                if (pool == null) {
                    pool = createPool();
                }
            }
        }

        return pool;
    }

    private PoolDataSource createPool() throws Exception {
        PoolDataSource p = PoolDataSourceFactory.getPoolDataSource();

        p.setConnectionFactoryClassName("oracle.jdbc.datasource.impl.OracleDataSource");

        p.setURL(property("votacao.db.url", "jdbc:oracle:thin:@//localhost:1521/FREEPDB1"));
        p.setUser(property("votacao.db.user", "votacao"));
        p.setPassword(property("votacao.db.password", "votacao"));

        p.setInitialPoolSize(intProperty("votacao.pool.initial", 5));
        p.setMinPoolSize(intProperty("votacao.pool.min", 5));
        p.setMaxPoolSize(intProperty("votacao.pool.max", 20));

        p.setValidateConnectionOnBorrow(true);

        return p;
    }

    private String property(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (value == null || value.isBlank()) {
            value = System.getenv(key);
        }
        return value == null || value.isBlank() ? defaultValue : value;
    }

    private int intProperty(String key, int defaultValue) {
        String value = property(key, null);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
