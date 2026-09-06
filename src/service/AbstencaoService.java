/**
 * 
 */
package service;
import dao.AbstencaoDAO;
import votacao.AbstencaoZona;

import java.util.List;

/**
 * 
 */
public class AbstencaoService {


    private final AbstencaoDAO abstencaoDAO;

    public AbstencaoService(AbstencaoDAO abstencaoDAO) {
        this.abstencaoDAO = abstencaoDAO;
    }

    public List<AbstencaoZona> buscarPorEleicaoETurno(Long eleicaoId, int turno) {
        if (eleicaoId == null) {
            throw new IllegalArgumentException("Eleição é obrigatória.");
        }

        if (turno != 1 && turno != 2) {
            throw new IllegalArgumentException("Turno deve ser 1 ou 2.");
        }

        return abstencaoDAO.buscarPorEleicaoETurno(eleicaoId, turno);
    }
}
