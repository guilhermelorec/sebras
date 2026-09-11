/**
 * 
 */
package votacao;

/**
 * 
 */
public class ResultadoVotacao {


	    private int numeroCandidato;
	    private String nomeCandidato;
	    private String partido;
	    private long total;

	    public int getNumeroCandidato() {
	        return numeroCandidato;
	    }

	    public void setNumeroCandidato(int numeroCandidato) {
	        this.numeroCandidato = numeroCandidato;
	    }

	    public String getNomeCandidato() {
	        return nomeCandidato;
	    }

	    public void setNomeCandidato(String nomeCandidato) {
	        this.nomeCandidato = nomeCandidato;
	    }

	    public String getPartido() {
	        return partido;
	    }

	    public void setPartido(String partido) {
	        this.partido = partido;
	    }

	    public long getTotal() {
	        return total;
	    }

	    public void setTotal(long total) {
	        this.total = total;
	    }
}
