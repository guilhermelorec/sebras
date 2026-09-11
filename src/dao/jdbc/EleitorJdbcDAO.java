/**
 * 
 */
package dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import dao.DaoException;
import dao.EleitorDAO;
import tankDB.ConnectionFactory;
import votacao.Eleitor;

public class EleitorJdbcDAO extends JdbcDAO<Eleitor> implements EleitorDAO {

    public EleitorJdbcDAO(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected String insertSql() {
        return """
            INSERT INTO eleitor (titulo, cpf, nome, zona_id, secao_id, ativo, votou)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
    }

    @Override
    protected String selectByIdSql() {
        return """
            SELECT id, titulo, cpf, nome, zona_id, secao_id, ativo, votou
            FROM eleitor
            WHERE id = ?
        """;
    }

    @Override
    protected String selectAllSql() {
        return """
            SELECT id, titulo, cpf, nome, zona_id, secao_id, ativo, votou
            FROM eleitor
            ORDER BY nome
        """;
    }

    @Override
    protected String updateSql() {
        return """
            UPDATE eleitor
            SET titulo = ?, cpf = ?, nome = ?, zona_id = ?, secao_id = ?, ativo = ?, votou = ?
            WHERE id = ?
        """;
    }

    @Override
    protected String deleteSql() {
        return "DELETE FROM eleitor WHERE id = ?";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Eleitor eleitor) throws SQLException {
        ps.setString(1, eleitor.getTitulo());
        ps.setString(2, eleitor.getCpf());
        ps.setString(3, eleitor.getNome());
        ps.setLong(4, eleitor.getZonaId());
        ps.setLong(5, eleitor.getSecaoId());
        ps.setInt(6, eleitor.isAtivo() ? 1 : 0);
        ps.setInt(7, eleitor.isVotou() ? 1 : 0);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Eleitor eleitor) throws SQLException {
        ps.setString(1, eleitor.getTitulo());
        ps.setString(2, eleitor.getCpf());
        ps.setString(3, eleitor.getNome());
        ps.setLong(4, eleitor.getZonaId());
        ps.setLong(5, eleitor.getSecaoId());
        ps.setInt(6, eleitor.isAtivo() ? 1 : 0);
        ps.setInt(7, eleitor.isVotou() ? 1 : 0);
        ps.setLong(8, eleitor.getId());
    }

    @Override
    protected Eleitor mapRow(ResultSet rs) throws SQLException {
        Eleitor eleitor = new Eleitor();
        eleitor.setId(rs.getLong("id"));
        eleitor.setTitulo(rs.getString("titulo"));
        eleitor.setCpf(rs.getString("cpf"));
        eleitor.setNome(rs.getString("nome"));
        eleitor.setZonaId(rs.getLong("zona_id"));
        eleitor.setSecaoId(rs.getLong("secao_id"));
        eleitor.setAtivo(rs.getInt("ativo") == 1);
        eleitor.setVotou(rs.getInt("votou") == 1);
        return eleitor;
    }

    @Override
    protected void setId(Eleitor entidade, Long id) {
        entidade.setId(id);
    }

    @Override
    public Optional<Eleitor> buscarPorTitulo(String titulo) {
        String sql = """
            SELECT id, titulo, cpf, nome, zona_id, secao_id, ativo, votou
            FROM eleitor
            WHERE titulo = ?
        """;

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, titulo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new DaoException("Erro ao buscar eleitor por título.", e);
        }
    }
}

