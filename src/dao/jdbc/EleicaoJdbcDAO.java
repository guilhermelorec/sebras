/**
 * 
 */
package dao.jdbc;

import dao.EleicaoDAO;
import appVoto.Eleicao;
import tankDB.ConnectionFactory;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class EleicaoJdbcDAO extends JdbcDAO<Eleicao> implements EleicaoDAO {

    public EleicaoJdbcDAO(ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    protected String insertSql() {
        return """
            INSERT INTO eleicao (
                nome,
                ano,
                data_turno1,
                data_turno2,
                turno_atual,
                segundo_turno_habilitado,
                ativa
            ) VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
    }

    @Override
    protected String selectByIdSql() {
        return """
            SELECT id,
                   nome,
                   ano,
                   data_turno1,
                   data_turno2,
                   turno_atual,
                   segundo_turno_habilitado,
                   ativa
            FROM eleicao
            WHERE id = ?
        """;
    }

    @Override
    protected String selectAllSql() {
        return """
            SELECT id,
                   nome,
                   ano,
                   data_turno1,
                   data_turno2,
                   turno_atual,
                   segundo_turno_habilitado,
                   ativa
            FROM eleicao
            ORDER BY ano DESC, nome
        """;
    }

    @Override
    protected String updateSql() {
        return """
            UPDATE eleicao
            SET nome = ?,
                ano = ?,
                data_turno1 = ?,
                data_turno2 = ?,
                turno_atual = ?,
                segundo_turno_habilitado = ?,
                ativa = ?
            WHERE id = ?
        """;
    }

    @Override
    protected String deleteSql() {
        return "DELETE FROM eleicao WHERE id = ?";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Eleicao eleicao) throws SQLException {
        ps.setString(1, eleicao.getNome());
        ps.setInt(2, eleicao.getAno());
        ps.setDate(3, Date.valueOf(eleicao.getDataTurno1()));
        ps.setDate(4, eleicao.getDataTurno2() == null ? null : Date.valueOf(eleicao.getDataTurno2()));
        ps.setInt(5, eleicao.getTurnoAtual());
        ps.setInt(6, eleicao.isSegundoTurnoHabilitado() ? 1 : 0);
        ps.setInt(7, eleicao.isAtiva() ? 1 : 0);
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Eleicao eleicao) throws SQLException {
        ps.setString(1, eleicao.getNome());
        ps.setInt(2, eleicao.getAno());
        ps.setDate(3, Date.valueOf(eleicao.getDataTurno1()));
        ps.setDate(4, eleicao.getDataTurno2() == null ? null : Date.valueOf(eleicao.getDataTurno2()));
        ps.setInt(5, eleicao.getTurnoAtual());
        ps.setInt(6, eleicao.isSegundoTurnoHabilitado() ? 1 : 0);
        ps.setInt(7, eleicao.isAtiva() ? 1 : 0);
        ps.setLong(8, eleicao.getId());
    }

    @Override
    protected Eleicao mapRow(ResultSet rs) throws SQLException {
        Eleicao eleicao = new Eleicao();
        eleicao.setId(rs.getLong("id"));
        eleicao.setNome(rs.getString("nome"));
        eleicao.setAno(rs.getInt("ano"));
        eleicao.setDataTurno1(toLocalDate(rs.getDate("data_turno1")));
        eleicao.setDataTurno2(toLocalDate(rs.getDate("data_turno2")));
        eleicao.setTurnoAtual(rs.getInt("turno_atual"));
        eleicao.setSegundoTurnoHabilitado(rs.getInt("segundo_turno_habilitado") == 1);
        eleicao.setAtiva(rs.getInt("ativa") == 1);
        return eleicao;
    }

    @Override
    protected void setId(Eleicao entidade, Long id) {
        entidade.setId(id);
    }

    private LocalDate toLocalDate(Date date) {
        return date == null ? null : date.toLocalDate();
    }
}
