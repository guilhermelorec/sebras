/**
 * 
 */
package dao.jdbc;

import  dao.DaoException;
import  dao.SecaoDAO;
import  votacao.Secao;
import  tankDB.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SecaoJdbcDAO extends JdbcDAO<Secao> implements SecaoDAO {

    public SecaoJdbcDAO(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected String insertSql() {
        return "INSERT INTO secao (numero, zona_id, local) VALUES (?, ?, ?)";
    }

    @Override
    protected String selectByIdSql() {
        return """
            SELECT s.id, s.numero, s.zona_id, s.local, z.numero AS numero_zona
            FROM secao s
            JOIN zona z ON z.id = s.zona_id
            WHERE s.id = ?
        """;
    }


    @Override
    protected String selectAllSql() {
        return """
            SELECT s.id, s.numero, s.zona_id, s.local, z.numero AS numero_zona
            FROM secao s
            JOIN zona z ON z.id = s.zona_id
            ORDER BY z.numero, s.numero
        """;
    }


    @Override
    protected String updateSql() {
        return "UPDATE secao SET numero = ?, zona_id = ?, local = ? WHERE id = ?";
    }

    @Override
    protected String deleteSql() {
        return "DELETE FROM secao WHERE id = ?";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Secao secao) throws SQLException {
        ps.setInt(1, secao.getNumero());
        ps.setLong(2, secao.getZonaId());
        ps.setString(3, secao.getLocal());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Secao secao) throws SQLException {
        ps.setInt(1, secao.getNumero());
        ps.setLong(2, secao.getZonaId());
        ps.setString(3, secao.getLocal());
        ps.setLong(4, secao.getId());
    }

    @Override
    protected Secao mapRow(ResultSet rs) throws SQLException {
        Secao secao = new Secao();
        secao.setId(rs.getLong("id"));
        secao.setNumero(rs.getInt("numero"));
        secao.setZonaId(rs.getLong("zona_id"));
        secao.setLocal(rs.getString("local"));
        secao.setNumeroZona(rs.getInt("numero_zona"));
        return secao;
    }


    @Override
    protected void setId(Secao entidade, Long id) {
        entidade.setId(id);
    }

    @Override
    public List<Secao> buscarPorZona(Long zonaId) {
    	String sql = """
    		    SELECT s.id, s.numero, s.zona_id, s.local, z.numero AS numero_zona
    		    FROM secao s
    		    JOIN zona z ON z.id = s.zona_id
    		    WHERE s.zona_id = ?
    		    ORDER BY s.numero
    		""";
        List<Secao> secoes = new ArrayList<>();

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, zonaId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    secoes.add(mapRow(rs));
                }
            }

            return secoes;

        } catch (SQLException e) {
            throw new DaoException("Erro ao buscar seções por zona.", e);
        }
    }
}

