/**
 * 
 */
package votacao;

/**
 * 
 */
public class Municipio extends EntidadeBase {
		
	    private String nome;
	    private UnidadeFederativa uf;

	    public String getNome() {
	        return nome;
	    }

	    public void setNome(String nome) {
	        this.nome = nome;
	    }

	    public UnidadeFederativa getUf() {
	        return uf;
	    }

	    public void setUf(UnidadeFederativa uf) {
	        this.uf = uf;
	    }
}
