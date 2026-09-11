/**
 * 
 */
package dao.jdbc;

import dao.CandidaturaDAO;
import dao.DaoException;
import votacao.Candidatura;
import tankDB.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public class CandidaturaJdbcDAO implements CandidaturaDAO {

    private final ConnectionFactory connectionFactory;

    public CandidaturaJdbcDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Candidatura inserir(Candidatura candidatura) {
        String sql = """
            INSERT INTO candidatura (
                eleitor_id,
                partido_id,
                eleicao_id,
                cargo_id,
                numero,
                ativa
            ) VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, candidatura.getEleitorId());
            ps.setLong(2, candidatura.getPartidoId());
            ps.setLong(3, candidatura.getEleicaoId());
            ps.setLong(4, candidatura.getCargoId());
            ps.setInt(5, candidatura.getNumero());
            ps.setInt(6, candidatura.isAtiva() ? 1 : 0);

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    candidatura.setId(keys.getLong(1));
                }
            }

            return candidatura;

        } catch (SQLException e) {
            throw new DaoException("Erro ao inserir candidatura.", e);
        }
    }

    @Override
    public Optional<Candidatura> buscarPorId(Long id) {
        String sql = """
            SELECT c.id,
                   c.eleitor_id,
                   c.partido_id,
                   c.eleicao_id,
                   c.cargo_id,
                   c.numero,
                   c.ativa,
                   e.nome AS nome_eleitor,
                   p.sigla AS sigla_partido,
                   el.nome AS nome_eleicao,
                   ca.nome AS nome_cargo,
                   ca.uf AS uf_cargo
            FROM candidatura c
            JOIN eleitor e ON e.id = c.eleitor_id
            JOIN partido p ON p.id = c.partido_id
            JOIN eleicao el ON el.id = c.eleicao_id
            JOIN cargo ca ON ca.id = c.cargo_id
            WHERE c.id = ?
        """;

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
            throw new DaoException("Erro ao buscar candidatura por ID.", e);
        }
    }

    @Override
    public List<Candidatura> buscarTodos() {
        String sql = """
            SELECT c.id,
                   c.eleitor_id,
                   c.partido_id,
                   c.eleicao_id,
                   c.cargo_id,
                   c.numero,
                   c.ativa,
                   e.nome AS nome_eleitor,
                   p.sigla AS sigla_partido,
                   el.nome AS nome_eleicao,
                   ca.nome AS nome_cargo,
                   ca.uf AS uf_cargo
            FROM candidatura c
            JOIN eleitor e ON e.id = c.eleitor_id
            JOIN partido p ON p.id = c.partido_id
            JOIN eleicao el ON el.id = c.eleicao_id
            JOIN cargo ca ON ca.id = c.cargo_id
            ORDER BY e.nome
        """;

        List<Candidatura> candidaturas = new ArrayList<>();

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                candidaturas.add(mapRow(rs));
            }

            return candidaturas;

        } catch (SQLException e) {
            throw new DaoException("Erro ao buscar candidaturas.", e);
        }
    }

    @Override
    public Candidatura atualizar(Candidatura candidatura) {
        String sql = """
            UPDATE candidatura
            SET eleitor_id = ?,
                partido_id = ?,
                eleicao_id = ?,
                cargo_id = ?,
                numero = ?,
                ativa = ?
            WHERE id = ?
        """;

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, candidatura.getEleitorId());
            ps.setLong(2, candidatura.getPartidoId());
            ps.setLong(3, candidatura.getEleicaoId());
            ps.setLong(4, candidatura.getCargoId());
            ps.setInt(5, candidatura.getNumero());
            ps.setInt(6, candidatura.isAtiva() ? 1 : 0);
            ps.setLong(7, candidatura.getId());

            int linhas = ps.executeUpdate();

            if (linhas == 0) {
                throw new DaoException("Candidatura não encontrada.");
            }

            return candidatura;

        } catch (SQLException e) {
            throw new DaoException("Erro ao atualizar candidatura.", e);
        }
    }

    @Override
    public boolean remover(Long id) {
        String sql = "DELETE FROM candidatura WHERE id = ?";

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException("Erro ao remover candidatura.", e);
        }
    }

    private Candidatura mapRow(ResultSet rs) throws SQLException {
        Candidatura candidatura = new Candidatura();

        candidatura.setId(rs.getLong("id"));
        candidatura.setEleitorId(rs.getLong("eleitor_id"));
        candidatura.setPartidoId(rs.getLong("partido_id"));
        candidatura.setEleicaoId(rs.getLong("eleicao_id"));
        candidatura.setCargoId(rs.getLong("cargo_id"));
        candidatura.setNumero(rs.getInt("numero"));
        candidatura.setAtiva(rs.getInt("ativa") == 1);

        candidatura.setNomeEleitor(rs.getString("nome_eleitor"));
        candidatura.setSiglaPartido(rs.getString("sigla_partido"));
        candidatura.setNomeEleicao(rs.getString("nome_eleicao"));
        candidatura.setNomeCargo(rs.getString("nome_cargo"));
        candidatura.setUfCargo(rs.getString("uf_cargo"));

        return candidatura;
    }
}
