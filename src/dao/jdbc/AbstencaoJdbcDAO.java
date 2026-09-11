/**
 * 
 */
package dao.jdbc;
import dao.AbstencaoDAO;
import dao.DaoException;
import votacao.AbstencaoZona;
import tankDB.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AbstencaoJdbcDAO implements AbstencaoDAO {

    private final ConnectionFactory connectionFactory;

    public AbstencaoJdbcDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public List<AbstencaoZona> buscarPorEleicaoETurno(Long eleicaoId, int turno) {
        String sql = """
            SELECT z.id AS zona_id,
                   z.numero AS zona_numero,
                   m.nome AS municipio,
                   (
                       SELECT COUNT(*)
                       FROM eleitor e
                       WHERE e.zona_id = z.id
                         AND e.ativo = 1
                   ) AS total_eleitores,
                   (
                       SELECT COUNT(*)
                       FROM comparecimento c
                       WHERE c.zona_id = z.id
                         AND c.eleicao_id = ?
                         AND c.turno = ?
                   ) AS presentes
            FROM zona z
            JOIN municipio m ON m.id = z.municipio_id
            ORDER BY m.nome, z.numero
        """;

        List<AbstencaoZona> lista = new ArrayList<>();

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, eleicaoId);
            ps.setInt(2, turno);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AbstencaoZona item = new AbstencaoZona();

                    item.setZonaId(rs.getLong("zona_id"));
                    item.setNumeroZona(rs.getInt("zona_numero"));
                    item.setMunicipio(rs.getString("municipio"));

                    long total = rs.getLong("total_eleitores");
                    long presentes = rs.getLong("presentes");
                    long abstencao = Math.max(0, total - presentes);

                    item.setTotalEleitores(total);
                    item.setPresentes(presentes);
                    item.setAbstencao(abstencao);

                    if (total > 0) {
                        item.setTaxaAbstencao((abstencao * 100.0) / total);
                    } else {
                        item.setTaxaAbstencao(0.0);
                    }

                    lista.add(item);
                }
            }

            return lista;

        } catch (SQLException e) {
            throw new DaoException("Erro ao buscar abstenção por zona.", e);
        }
    }
}

