/**
 * 
 */
package votacao;

/**
 * 
 */
public class Partido extends EntidadeBase {
	/**
	 * 
	 */
	public Partido() {
		// TODO Auto-generated constructor stub
	}
	

	    private int numero;
	    private String sigla;
	    private String nome;
	    private boolean ativo = true;


	    public Partido(int numero, String sigla, String nome) {
	        this.numero = numero;
	        this.sigla = sigla;
	        this.nome = nome;
	    }

	    public int getNumero() {
	        return numero;
	    }

	    public void setNumero(int numero) {
	        this.numero = numero;
	    }

	    public String getSigla() {
	        return sigla;
	    }

	    public void setSigla(String sigla) {
	        this.sigla = sigla;
	    }

	    public String getNome() {
	        return nome;
	    }

	    public void setNome(String nome) {
	        this.nome = nome;
	    }

	    public boolean isAtivo() {
	        return ativo;
	    }

	    public void setAtivo(boolean ativo) {
	        this.ativo = ativo;
	    }
}
