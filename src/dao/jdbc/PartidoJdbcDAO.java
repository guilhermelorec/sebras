/**
 * 
 */
package dao.jdbc;
import  dao.PartidoDAO;
import  votacao.Partido;
import  tankDB.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PartidoJdbcDAO extends JdbcDAO<Partido> implements PartidoDAO {

    public PartidoJdbcDAO(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected String insertSql() {
        return "INSERT INTO partido (numero, sigla, nome, ativo) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String selectByIdSql() {
        return "SELECT id, numero, sigla, nome, ativo FROM partido WHERE id = ?";
    }

    @Override
    protected String selectAllSql() {
        return "SELECT id, numero, sigla, nome, ativo FROM partido ORDER BY sigla";
    }

    @Override
    protected String updateSql() {
        return "UPDATE partido SET numero = ?, sigla = ?, nome = ?, ativo = ? WHERE id = ?";
    }

    @Override
    protected String deleteSql() {
        return "DELETE FROM partido WHERE id = ?";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Partido partido) throws SQLException {
        ps.setInt(1, partido.getNumero());
        ps.setString(2, partido.getSigla());
        ps.setString(3, partido.getNome());
        ps.setInt(4, partido.isAtivo() ? 1 : 0);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Partido partido) throws SQLException {
        ps.setInt(1, partido.getNumero());
        ps.setString(2, partido.getSigla());
        ps.setString(3, partido.getNome());
        ps.setInt(4, partido.isAtivo() ? 1 : 0);
        ps.setLong(5, partido.getId());
    }

    @Override
    protected Partido mapRow(ResultSet rs) throws SQLException {
        Partido partido = new Partido();
        partido.setId(rs.getLong("id"));
        partido.setNumero(rs.getInt("numero"));
        partido.setSigla(rs.getString("sigla"));
        partido.setNome(rs.getString("nome"));
        partido.setAtivo(rs.getInt("ativo") == 1);
        return partido;
    }

    @Override
    protected void setId(Partido entidade, Long id) {
        entidade.setId(id);
    }
}

