/**
 * 
 */
package dao.jdbc;


import dao.DaoException;
import dao.VotoDAO;
import tankDB.ConnectionFactory;
import votacao.ResultadoVotacao;
import votacao.Voto;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VotoJdbcDAO implements VotoDAO {

    private final ConnectionFactory connectionFactory;

    public VotoJdbcDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void inserir(Voto voto) {
        String sql = """
            INSERT INTO voto (candidatura_id, eleicao_id, turno, zona_id, secao_id)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, voto.getCandidaturaId());
            ps.setLong(2, voto.getEleicaoId());
            ps.setInt(3, voto.getTurno());
            ps.setLong(4, voto.getZonaId());
            ps.setLong(5, voto.getSecaoId());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    voto.setId(keys.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Erro ao inserir voto.", e);
        }
    }

    @Override
    public List<ResultadoVotacao> resultadoPorZona(Long zonaId, Long eleicaoId, int turno) {
        String sql = """
            SELECT c.numero,
                   e.nome AS nome_candidato,
                   p.sigla AS partido,
                   COUNT(v.id) AS total
            FROM voto v
            JOIN candidatura c ON c.id = v.candidatura_id
            JOIN eleitor e ON e.id = c.eleitor_id
            JOIN partido p ON p.id = c.partido_id
            WHERE v.zona_id = ?
              AND v.eleicao_id = ?
              AND v.turno = ?
            GROUP BY c.numero, e.nome, p.sigla
            ORDER BY total DESC
        """;

        List<ResultadoVotacao> resultado = new ArrayList<>();

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, zonaId);
            ps.setLong(2, eleicaoId);
            ps.setInt(3, turno);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ResultadoVotacao item = new ResultadoVotacao();
                    item.setNumeroCandidato(rs.getInt("numero"));
                    item.setNomeCandidato(rs.getString("nome_candidato"));
                    item.setPartido(rs.getString("partido"));
                    item.setTotal(rs.getLong("total"));
                    resultado.add(item);
                }
            }

            return resultado;

        } catch (SQLException e) {
            throw new DaoException("Erro ao apurar resultado por zona.", e);
        }
    }
}
