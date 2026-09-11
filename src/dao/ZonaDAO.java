/**
 * 
 */
package dao;
import votacao.Zona;

/**
 * 
 */
import java.util.List;

public interface ZonaDAO extends DAO<Zona, Long> {
    List<Zona> buscarPorMunicipio(Long municipioId);
    int contarPorMunicipio(Long municipioId);

}
