/**
 * 
 */
package dao;


import votacao.Cargo;
import java.util.List;

public interface CargoDAO extends DAO<Cargo, Long> {
    List<Cargo> buscarPorUf(String uf);
}
