/**
 * 
 */
package votacao;

/**
 * 
 */
public class Eleitor extends EntidadeBase {

	
	    private String titulo;
	    private String cpf;
	    private String nome;
	    private Long zonaId;
	    private Long secaoId;
	    private boolean ativo = true;
	    private boolean votou = false;

	    public String getTitulo() {
	        return titulo;
	    }

	    public void setTitulo(String titulo) {
	        this.titulo = titulo;
	    }

	    public String getCpf() {
	        return cpf;
	    }

	    public void setCpf(String cpf) {
	        this.cpf = cpf;
	    }

	    public String getNome() {
	        return nome;
	    }

	    public void setNome(String nome) {
	        this.nome = nome;
	    }

	    public Long getZonaId() {
	        return zonaId;
	    }

	    public void setZonaId(Long zonaId) {
	        this.zonaId = zonaId;
	    }

	    public Long getSecaoId() {
	        return secaoId;
	    }

	    public void setSecaoId(Long secaoId) {
	        this.secaoId = secaoId;
	    }

	    public boolean isAtivo() {
	        return ativo;
	    }

	    public void setAtivo(boolean ativo) {
	        this.ativo = ativo;
	    }

	    public boolean isVotou() {
	        return votou;
	    }

	    public void setVotou(boolean votou) {
	        this.votou = votou;
	    }
}
