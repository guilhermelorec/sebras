/**
 * 
 */
package dao;
import votacao.Eleitor;
import java.util.Optional;

/**
 * 
 */
public interface EleitorDAO extends DAO<Eleitor, Long> {
    Optional<Eleitor> buscarPorTitulo(String titulo);

}
