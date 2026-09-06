/**
 * 
 */
package dao.jdbc;

import dao.DAO;
import dao.DaoException;
import tankDB.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 
 */
public abstract class JdbcDAO<T> implements DAO<T, Long> {

    protected final ConnectionFactory connectionFactory;

    protected JdbcDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    protected abstract String insertSql();
    protected abstract String selectByIdSql();
    protected abstract String selectAllSql();
    protected abstract String updateSql();
    protected abstract String deleteSql();

    protected abstract void setInsertParameters(PreparedStatement ps, T entidade) throws SQLException;
    protected abstract void setUpdateParameters(PreparedStatement ps, T entidade) throws SQLException;
    protected abstract T mapRow(ResultSet rs) throws SQLException;
    protected abstract void setId(T entidade, Long id);

    @Override
    public T inserir(T entidade) {
        String sql = insertSql();

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setInsertParameters(ps, entidade);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    setId(entidade, keys.getLong(1));
                }
            }

            return entidade;

        } catch (SQLException e) {
            throw new DaoException("Erro ao inserir registro.", e);
        }
    }

    @Override
    public Optional<T> buscarPorId(Long id) {
        String sql = selectByIdSql();

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new DaoException("Erro ao buscar registro por ID.", e);
        }
    }

    @Override
    public List<T> buscarTodos() {
        String sql = selectAllSql();
        List<T> lista = new ArrayList<>();

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }

            return lista;

        } catch (SQLException e) {
            throw new DaoException("Erro ao buscar todos os registros.", e);
        }
    }

    @Override
    public T atualizar(T entidade) {
        String sql = updateSql();

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setUpdateParameters(ps, entidade);
            int linhas = ps.executeUpdate();

            if (linhas == 0) {
                throw new DaoException("Registro não encontrado para atualização.");
            }

            return entidade;

        } catch (SQLException e) {
            throw new DaoException("Erro ao atualizar registro.", e);
        }
    }

    @Override
    public boolean remover(Long id) {
        String sql = deleteSql();

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException("Erro ao remover registro.", e);
        }
    }
}

