/**
 * 
 */
package dao.jdbc;


import  dao.DaoException;
import  dao.ZonaDAO;
import  votacao.Zona;
import  tankDB.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ZonaJdbcDAO extends JdbcDAO<Zona> implements ZonaDAO {

    public ZonaJdbcDAO(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected String insertSql() {
        return "INSERT INTO zona (numero, municipio_id) VALUES (?, ?)";
    }

    @Override
    protected String selectByIdSql() {
        return """
            SELECT z.id, z.numero, z.municipio_id, m.nome AS nome_municipio
            FROM zona z
            JOIN municipio m ON m.id = z.municipio_id
            WHERE z.id = ?
        """;
    }

    @Override
    protected String selectAllSql() {
        return """
            SELECT z.id, z.numero, z.municipio_id, m.nome AS nome_municipio
            FROM zona z
            JOIN municipio m ON m.id = z.municipio_id
            ORDER BY m.nome, z.numero
        """;
    }


    @Override
    protected String updateSql() {
        return "UPDATE zona SET numero = ?, municipio_id = ? WHERE id = ?";
    }


    @Override
    protected String deleteSql() {
        return "DELETE FROM zona WHERE id = ?";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Zona zona) throws SQLException {
        ps.setInt(1, zona.getNumero());
        ps.setLong(2, zona.getMunicipioId());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Zona zona) throws SQLException {
        ps.setInt(1, zona.getNumero());
        ps.setLong(2, zona.getMunicipioId());
        ps.setLong(3, zona.getId());
    }

    @Override
    protected Zona mapRow(ResultSet rs) throws SQLException {
        Zona zona = new Zona();
        zona.setId(rs.getLong("id"));
        zona.setNumero(rs.getInt("numero"));
        zona.setMunicipioId(rs.getLong("municipio_id"));
        zona.setNomeMunicipio(rs.getString("nome_municipio"));
        return zona;
    }


    @Override
    protected void setId(Zona entidade, Long id) {
        entidade.setId(id);
    }

    @Override
    public List<Zona> buscarPorMunicipio(Long municipioId) {
    	String sql = """
    		    SELECT z.id, z.numero, z.municipio_id, m.nome AS nome_municipio
    		    FROM zona z
    		    JOIN municipio m ON m.id = z.municipio_id
    		    WHERE z.municipio_id = ?
    		    ORDER BY z.numero
    		""";
        List<Zona> zonas = new ArrayList<>();

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, municipioId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    zonas.add(mapRow(rs));
                }
            }

            return zonas;

        } catch (SQLException e) {
            throw new DaoException("Erro ao buscar zonas por município.", e);
        }
    }

    @Override
    public int contarPorMunicipio(Long municipioId) {
        String sql = "SELECT COUNT(*) FROM zona WHERE municipio_id = ?";

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, municipioId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }

        } catch (SQLException e) {
            throw new DaoException("Erro ao contar zonas por município.", e);
        }
    }
}

