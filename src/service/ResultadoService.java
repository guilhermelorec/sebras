/**
 * 
 */
package service;


import dao.VotoDAO;
import votacao.ResultadoVotacao;
import java.util.List;


public class ResultadoService {


    private final VotoDAO votoDAO;

    public ResultadoService(VotoDAO votoDAO) {
        this.votoDAO = votoDAO;
    }

    public List<ResultadoVotacao> resultadoPorZona(Long zonaId, Long eleicaoId, int turno) {
        if (zonaId == null || eleicaoId == null) {
            throw new IllegalArgumentException("Zona e eleição são obrigatórios.");
        }

        if (turno != 1 && turno != 2) {
            throw new IllegalArgumentException("Turno deve ser 1 ou 2.");
        }

        return votoDAO.resultadoPorZona(zonaId, eleicaoId, turno);
    }
}
