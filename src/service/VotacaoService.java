/**
 * 
 */
package service;

import excecao.RegraNegocioException;
import tankDB.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VotacaoService {

    private final ConnectionFactory connectionFactory;

    public VotacaoService(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void registrarVoto(
            long eleitorId,
            long candidaturaId,
            long eleicaoId,
            int turno,
            long zonaId,
            long secaoId
    ) {
        Connection conn = null;

        try {
            conn = connectionFactory.getConnection();
            conn.setAutoCommit(false);

            validarEleicao(conn, eleicaoId, turno);
            validarSecaoNaZona(conn, secaoId, zonaId);
            validarEleitor(conn, eleitorId, zonaId, secaoId);
            validarCandidaturaParaEleitor(conn, eleitorId, candidaturaId, eleicaoId, turno);
            validarComparecimento(conn, eleitorId, eleicaoId, turno);

            inserirComparecimento(conn, eleitorId, eleicaoId, turno, zonaId, secaoId);
            inserirVoto(conn, candidaturaId, eleicaoId, turno, zonaId, secaoId);

            conn.commit();

        } catch (RegraNegocioException e) {
            rollback(conn);
            throw e;
        } catch (SQLException e) {
            rollback(conn);
            throw new RegraNegocioException("Erro ao registrar voto.", e);
        } finally {
            close(conn);
        }
    }

    private void validarEleicao(Connection conn, long eleicaoId, int turno) throws SQLException {
        String sql = """
            SELECT ativa,
                   turno_atual,
                   segundo_turno_habilitado
            FROM eleicao
            WHERE id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, eleicaoId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new RegraNegocioException("Eleição não encontrada.");
                }

                int ativa = rs.getInt("ativa");
                int turnoAtual = rs.getInt("turno_atual");
                int segundoTurnoHabilitado = rs.getInt("segundo_turno_habilitado");

                if (ativa != 1) {
                    throw new RegraNegocioException("Eleição inativa.");
                }

                if (turno != 1 && turno != 2) {
                    throw new RegraNegocioException("Turno inválido.");
                }

                if (turno != turnoAtual) {
                    throw new RegraNegocioException("O turno informado não é o turno atual da eleição.");
                }

                if (turno == 2 && segundoTurnoHabilitado != 1) {
                    throw new RegraNegocioException("Segundo turno não está habilitado.");
                }
            }
        }
    }

    private void validarSecaoNaZona(Connection conn, long secaoId, long zonaId) throws SQLException {
        String sql = "SELECT 1 FROM secao WHERE id = ? AND zona_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, secaoId);
            ps.setLong(2, zonaId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new RegraNegocioException("Seção não pertence à zona informada.");
                }
            }
        }
    }

    private void validarEleitor(Connection conn, long eleitorId, long zonaId, long secaoId) throws SQLException {
        String sql = """
            SELECT 1
            FROM eleitor
            WHERE id = ?
              AND zona_id = ?
              AND secao_id = ?
              AND ativo = 1
            FOR UPDATE
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, eleitorId);
            ps.setLong(2, zonaId);
            ps.setLong(3, secaoId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new RegraNegocioException("Eleitor não encontrado ou inativo para a zona/seção informada.");
                }
            }
        }
    }

    private void validarCandidaturaParaEleitor(
            Connection conn,
            long eleitorId,
            long candidaturaId,
            long eleicaoId,
            int turno
    ) throws SQLException {
        String sql = """
            SELECT 1
            FROM candidatura c
            JOIN cargo ca ON ca.id = c.cargo_id
            JOIN eleitor e ON e.id = ?
            JOIN zona z ON z.id = e.zona_id
            JOIN municipio m ON m.id = z.municipio_id
            WHERE c.id = ?
              AND c.eleicao_id = ?
              AND c.ativa = 1
              AND (ca.uf IS NULL OR ca.uf = m.uf)
              AND (? = 1 OR ca.permite_segundo_turno = 1)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, eleitorId);
            ps.setLong(2, candidaturaId);
            ps.setLong(3, eleicaoId);
            ps.setInt(4, turno);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new RegraNegocioException(
                        "Candidatura inválida para este eleitor, eleição, turno ou UF."
                    );
                }
            }
        }
    }

    private void validarComparecimento(Connection conn, long eleitorId, long eleicaoId, int turno) throws SQLException {
        String sql = """
            SELECT 1
            FROM comparecimento
            WHERE eleitor_id = ?
              AND eleicao_id = ?
              AND turno = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, eleitorId);
            ps.setLong(2, eleicaoId);
            ps.setInt(3, turno);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    throw new RegraNegocioException("Eleitor já votou nesta eleição/turno.");
                }
            }
        }
    }

    private void inserirComparecimento(
            Connection conn,
            long eleitorId,
            long eleicaoId,
            int turno,
            long zonaId,
            long secaoId
    ) throws SQLException {
        String sql = """
            INSERT INTO comparecimento (
                eleitor_id,
                eleicao_id,
                turno,
                zona_id,
                secao_id
            ) VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, eleitorId);
            ps.setLong(2, eleicaoId);
            ps.setInt(3, turno);
            ps.setLong(4, zonaId);
            ps.setLong(5, secaoId);
            ps.executeUpdate();
        }
    }

    private void inserirVoto(
            Connection conn,
            long candidaturaId,
            long eleicaoId,
            int turno,
            long zonaId,
            long secaoId
    ) throws SQLException {
        String sql = """
            INSERT INTO voto (
                candidatura_id,
                eleicao_id,
                turno,
                zona_id,
                secao_id
            ) VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, candidaturaId);
            ps.setLong(2, eleicaoId);
            ps.setInt(3, turno);
            ps.setLong(4, zonaId);
            ps.setLong(5, secaoId);
            ps.executeUpdate();
        }
    }

    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ignored) {
            }
        }
    }

    private void close(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
