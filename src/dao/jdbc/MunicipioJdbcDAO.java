/**
 * 
 */
package dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import dao.MunicipioDAO;
import votacao.Municipio;
import votacao.UnidadeFederativa;
import tankDB.ConnectionFactory;


public class MunicipioJdbcDAO extends JdbcDAO<Municipio> implements MunicipioDAO {

    public MunicipioJdbcDAO(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected String insertSql() {
        return "INSERT INTO municipio (nome, uf) VALUES (?, ?)";
    }

    @Override
    protected String selectByIdSql() {
        return "SELECT id, nome, uf FROM municipio WHERE id = ?";
    }

    @Override
    protected String selectAllSql() {
        return "SELECT id, nome, uf FROM municipio ORDER BY uf, nome";
    }

    @Override
    protected String updateSql() {
        return "UPDATE municipio SET nome = ?, uf = ? WHERE id = ?";
    }

    @Override
    protected String deleteSql() {
        return "DELETE FROM municipio WHERE id = ?";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Municipio municipio) throws SQLException {
        ps.setString(1, municipio.getNome());
        ps.setString(2, municipio.getUf().name());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Municipio municipio) throws SQLException {
        ps.setString(1, municipio.getNome());
        ps.setString(2, municipio.getUf().name());
        ps.setLong(3, municipio.getId());

    }

    @Override
    protected Municipio mapRow(ResultSet rs) throws SQLException {
        Municipio municipio = new Municipio();
        municipio.setId(rs.getLong("id"));
        municipio.setNome(rs.getString("nome"));
        municipio.setUf(UnidadeFederativa.fromSigla(rs.getString("uf")));
        return municipio;
    }

    @Override
    protected void setId(Municipio entidade, Long id) {
        entidade.setId(id);
    }
}

