/**
 * 
 */
package votacao;

/**
 * 
 */
public class Candidato extends Eleitor {


	    private Long partidoId;
	    private int numero;
	    private String cargo;
	    private boolean candidatoAtivo = true;

	    public Long getPartidoId() {
	        return partidoId;
	    }

	    public void setPartidoId(Long partidoId) {
	        this.partidoId = partidoId;
	    }

	    public int getNumero() {
	        return numero;
	    }

	    public void setNumero(int numero) {
	        this.numero = numero;
	    }

	    public String getCargo() {
	        return cargo;
	    }

	    public void setCargo(String cargo) {
	        this.cargo = cargo;
	    }

	    public boolean isCandidatoAtivo() {
	        return candidatoAtivo;
	    }

	    public void setCandidatoAtivo(boolean candidatoAtivo) {
	        this.candidatoAtivo = candidatoAtivo;
	    }
}
