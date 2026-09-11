/**
 * 
 */
package votacao;

/**
 * 
 */
public class Secao extends EntidadeBase {

	
	    private int numero;
	    private Long zonaId;
	    private String local;
	    private int numeroZona;

	    public int getNumeroZona() {
	        return numeroZona;
	    }

	    public void setNumeroZona(int numeroZona) {
	        this.numeroZona = numeroZona;
	    }

	    

	    public int getNumero() {
	        return numero;
	    }

	    public void setNumero(int numero) {
	        this.numero = numero;
	    }

	    public Long getZonaId() {
	        return zonaId;
	    }

	    public void setZonaId(Long zonaId) {
	        this.zonaId = zonaId;
	    }

	    public String getLocal() {
	        return local;
	    }

	    public void setLocal(String local) {
	        this.local = local;
	    }
}
