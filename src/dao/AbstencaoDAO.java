/**
 * 
 */
package dao;


import votacao.AbstencaoZona;
import java.util.List;

public interface AbstencaoDAO {
    List<AbstencaoZona> buscarPorEleicaoETurno(Long eleicaoId, int turno);
}
