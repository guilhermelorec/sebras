/**
 * 
 */
package votacao;

/**
 * 
 */
public class Zona extends EntidadeBase {

	    private int numero;
	    private Long municipioId;
	    private String nomeMunicipio;

	    public String getNomeMunicipio() {
	        return nomeMunicipio;
	    }

	    public void setNomeMunicipio(String nomeMunicipio) {
	        this.nomeMunicipio = nomeMunicipio;
	    }

	    
	    
	    public int getNumero() {
	        return numero;
	    }

	    public void setNumero(int numero) {
	        this.numero = numero;
	    }

	    public Long getMunicipioId() {
	        return municipioId;
	    }

	    public void setMunicipioId(Long municipioId) {
	        this.municipioId = municipioId;
	    }
}
