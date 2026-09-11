/**
 * 
 */
package dao.jdbc;

import dao.CargoDAO;
import dao.DaoException;
import votacao.Cargo;
import tankDB.ConnectionFactory ;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CargoJdbcDAO extends JdbcDAO<Cargo> implements CargoDAO {

    public CargoJdbcDAO(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected String insertSql() {
        return "INSERT INTO cargo (nome, uf, permite_segundo_turno) VALUES (?, ?, ?)";
    }

    @Override
    protected String selectByIdSql() {
        return "SELECT id, nome, uf, permite_segundo_turno FROM cargo WHERE id = ?";
    }

    @Override
    protected String selectAllSql() {
        return """
            SELECT id, nome, uf, permite_segundo_turno
            FROM cargo
            ORDER BY nome, uf
        """;
    }

    @Override
    protected String updateSql() {
        return "UPDATE cargo SET nome = ?, uf = ?, permite_segundo_turno = ? WHERE id = ?";
    }

    @Override
    protected String deleteSql() {
        return "DELETE FROM cargo WHERE id = ?";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Cargo cargo) throws SQLException {
        ps.setString(1, cargo.getNome());
        ps.setString(2, cargo.getUf());
        ps.setInt(3, cargo.isPermiteSegundoTurno() ? 1 : 0);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Cargo cargo) throws SQLException {
        ps.setString(1, cargo.getNome());
        ps.setString(2, cargo.getUf());
        ps.setInt(3, cargo.isPermiteSegundoTurno() ? 1 : 0);
        ps.setLong(4, cargo.getId());
    }

    @Override
    protected Cargo mapRow(ResultSet rs) throws SQLException {
        Cargo cargo = new Cargo();
        cargo.setId(rs.getLong("id"));
        cargo.setNome(rs.getString("nome"));
        cargo.setUf(rs.getString("uf"));
        cargo.setPermiteSegundoTurno(rs.getInt("permite_segundo_turno") == 1);
        return cargo;
    }

    @Override
    protected void setId(Cargo entidade, Long id) {
        entidade.setId(id);
    }

    @Override
    public List<Cargo> buscarPorUf(String uf) {
        String sql = """
            SELECT id, nome, uf, permite_segundo_turno
            FROM cargo
            WHERE uf = ?
            ORDER BY nome
        """;

        List<Cargo> cargos = new ArrayList<>();

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, uf);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cargos.add(mapRow(rs));
                }
            }

            return cargos;

        } catch (SQLException e) {
            throw new DaoException("Erro ao buscar cargos por UF.", e);
        }
    }
}
