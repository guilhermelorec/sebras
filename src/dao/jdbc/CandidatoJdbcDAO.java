/**
 * 
 */
package dao.jdbc;

import  dao.CandidatoDAO;
import  dao.DaoException;
import  tankDB.ConnectionFactory;
import  votacao.Candidato;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CandidatoJdbcDAO implements CandidatoDAO {

    private final ConnectionFactory connectionFactory;

    public CandidatoJdbcDAO(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Candidato inserir(Candidato candidato) {
        String sqlEleitor = """
            INSERT INTO eleitor (titulo, cpf, nome, zona_id, secao_id, ativo, votou)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        String sqlCandidato = """
            INSERT INTO candidato (eleitor_id, partido_id, numero, cargo, ativo)
            VALUES (?, ?, ?, ?, ?)
        """;

        Connection conn = null;

        try {
            conn = connectionFactory.getConnection();
            conn.setAutoCommit(false);

            long eleitorId;

            try (PreparedStatement ps = conn.prepareStatement(sqlEleitor, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, candidato.getTitulo());
                ps.setString(2, candidato.getCpf());
                ps.setString(3, candidato.getNome());
                ps.setLong(4, candidato.getZonaId());
                ps.setLong(5, candidato.getSecaoId());
                ps.setInt(6, candidato.isAtivo() ? 1 : 0);
                ps.setInt(7, candidato.isVotou() ? 1 : 0);
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (!keys.next()) {
                        throw new DaoException("Falha ao gerar ID do eleitor.");
                    }
                    eleitorId = keys.getLong(1);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlCandidato)) {
                ps.setLong(1, eleitorId);
                ps.setLong(2, candidato.getPartidoId());
                ps.setInt(3, candidato.getNumero());
                ps.setString(4, candidato.getCargo());
                ps.setInt(5, candidato.isCandidatoAtivo() ? 1 : 0);
                ps.executeUpdate();
            }

            conn.commit();
            candidato.setId(eleitorId);
            return candidato;

        } catch (SQLException e) {
            rollback(conn);
            throw new DaoException("Erro ao inserir candidato.", e);
        } finally {
            close(conn);
        }
    }

    @Override
    public Optional<Candidato> buscarPorId(Long id) {
        String sql = """
            SELECT e.id,
                   e.titulo,
                   e.cpf,
                   e.nome,
                   e.zona_id,
                   e.secao_id,
                   e.ativo AS eleitor_ativo,
                   e.votou,
                   c.partido_id,
                   c.numero,
                   c.cargo,
                   c.ativo AS candidato_ativo
            FROM eleitor e
            JOIN candidato c ON c.eleitor_id = e.id
            WHERE e.id = ?
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
            throw new DaoException("Erro ao buscar candidato por ID.", e);
        }
    }

    @Override
    public List<Candidato> buscarTodos() {
        String sql = """
            SELECT e.id,
                   e.titulo,
                   e.cpf,
                   e.nome,
                   e.zona_id,
                   e.secao_id,
                   e.ativo AS eleitor_ativo,
                   e.votou,
                   c.partido_id,
                   c.numero,
                   c.cargo,
                   c.ativo AS candidato_ativo
            FROM eleitor e
            JOIN candidato c ON c.eleitor_id = e.id
            ORDER BY e.nome
        """;

        List<Candidato> candidatos = new ArrayList<>();

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                candidatos.add(mapRow(rs));
            }

            return candidatos;

        } catch (SQLException e) {
            throw new DaoException("Erro ao buscar todos os candidatos.", e);
        }
    }

    @Override
    public Candidato atualizar(Candidato candidato) {
        String sqlEleitor = """
            UPDATE eleitor
            SET titulo = ?, cpf = ?, nome = ?, zona_id = ?, secao_id = ?, ativo = ?, votou = ?
            WHERE id = ?
        """;

        String sqlCandidato = """
            UPDATE candidato
            SET partido_id = ?, numero = ?, cargo = ?, ativo = ?
            WHERE eleitor_id = ?
        """;

        Connection conn = null;

        try {
            conn = connectionFactory.getConnection();
            conn.setAutoCommit(false);

            int linhasEleitor;
            try (PreparedStatement ps = conn.prepareStatement(sqlEleitor)) {
                ps.setString(1, candidato.getTitulo());
                ps.setString(2, candidato.getCpf());
                ps.setString(3, candidato.getNome());
                ps.setLong(4, candidato.getZonaId());
                ps.setLong(5, candidato.getSecaoId());
                ps.setInt(6, candidato.isAtivo() ? 1 : 0);
                ps.setInt(7, candidato.isVotou() ? 1 : 0);
                ps.setLong(8, candidato.getId());
                linhasEleitor = ps.executeUpdate();
            }

            int linhasCandidato;
            try (PreparedStatement ps = conn.prepareStatement(sqlCandidato)) {
                ps.setLong(1, candidato.getPartidoId());
                ps.setInt(2, candidato.getNumero());
                ps.setString(3, candidato.getCargo());
                ps.setInt(4, candidato.isCandidatoAtivo() ? 1 : 0);
                ps.setLong(5, candidato.getId());
                linhasCandidato = ps.executeUpdate();
            }

            if (linhasEleitor == 0 || linhasCandidato == 0) {
                throw new DaoException("Candidato não encontrado para atualização.");
            }

            conn.commit();
            return candidato;

        } catch (SQLException e) {
            rollback(conn);
            throw new DaoException("Erro ao atualizar candidato.", e);
        } finally {
            close(conn);
        }
    }

    @Override
    public boolean remover(Long id) {
        Connection conn = null;

        try {
            conn = connectionFactory.getConnection();
            conn.setAutoCommit(false);

            int linhasCandidato;
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM candidato WHERE eleitor_id = ?")) {
                ps.setLong(1, id);
                linhasCandidato = ps.executeUpdate();
            }

            int linhasEleitor;
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM eleitor WHERE id = ?")) {
                ps.setLong(1, id);
                linhasEleitor = ps.executeUpdate();
            }

            if (linhasCandidato == 0 || linhasEleitor == 0) {
                return false;
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            rollback(conn);
            throw new DaoException("Erro ao remover candidato.", e);
        } finally {
            close(conn);
        }
    }

    private Candidato mapRow(ResultSet rs) throws SQLException {
        Candidato candidato = new Candidato();

        candidato.setId(rs.getLong("id"));
        candidato.setTitulo(rs.getString("titulo"));
        candidato.setCpf(rs.getString("cpf"));
        candidato.setNome(rs.getString("nome"));
        candidato.setZonaId(rs.getLong("zona_id"));
        candidato.setSecaoId(rs.getLong("secao_id"));
        candidato.setAtivo(rs.getInt("eleitor_ativo") == 1);
        candidato.setVotou(rs.getInt("votou") == 1);

        candidato.setPartidoId(rs.getLong("partido_id"));
        candidato.setNumero(rs.getInt("numero"));
        candidato.setCargo(rs.getString("cargo"));
        candidato.setCandidatoAtivo(rs.getInt("candidato_ativo") == 1);

        return candidato;
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
