package dao;

import java.util.List;
import votacao.Secao;

public interface SecaoDAO extends DAO<Secao, Long> {
	List<Secao> buscarPorZona(Long zonaId);

}
