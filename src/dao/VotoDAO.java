/**
 * 
 */
package dao;
import votacao.ResultadoVotacao;
import votacao.Voto;
import java.util.List;


public interface VotoDAO {
    void inserir(Voto voto);

    List<ResultadoVotacao> resultadoPorZona(Long zonaId, Long eleicaoId, int turno);
}
