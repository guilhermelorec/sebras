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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ZonaService {

    private final ConnectionFactory connectionFactory;

    public ZonaService(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void garantirMinimoDeZonasPorMunicipio(Long municipioId, int minimo) {
        if (minimo < 1) {
            throw new RegraNegocioException("O mínimo de zonas deve ser maior que zero.");
        }

        String selectSql = "SELECT numero FROM zona WHERE municipio_id = ?";
        String insertSql = "INSERT INTO zona (numero, municipio_id) VALUES (?, ?)";

        Connection conn = null;

        try {
            conn = connectionFactory.getConnection();
            conn.setAutoCommit(false);

            Set<Integer> numerosExistentes = new HashSet<>();

            try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
                ps.setLong(1, municipioId);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        numerosExistentes.add(rs.getInt("numero"));
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                for (int numero = 1; numero <= minimo; numero++) {
                    if (!numerosExistentes.contains(numero)) {
                        ps.setInt(1, numero);
                        ps.setLong(2, municipioId);
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
            }

            conn.commit();

        } catch (SQLException e) {
            rollback(conn);
            throw new RegraNegocioException("Erro ao garantir mínimo de zonas.", e);
        } finally {
            close(conn);
        }
    }

    public void garantirMinimoParaTodosMunicipios(int minimo) {
        List<Long> municipios = new ArrayList<>();

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id FROM municipio");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                municipios.add(rs.getLong(1));
            }

        } catch (SQLException e) {
            throw new RegraNegocioException("Erro ao carregar municípios.", e);
        }

        for (Long municipioId : municipios) {
            garantirMinimoDeZonasPorMunicipio(municipioId, minimo);
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

