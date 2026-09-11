/**
 * 
 */
package dao;
import java.util.List;
import java.util.Optional;

/**
 * 
 */
public interface DAO<T, ID> {

	T inserir(T entidade);
	Optional<T> buscarPorId(ID id);
	List<T> buscarTodos();
	T atualizar(T entidade);
	boolean remover(ID id);
}
