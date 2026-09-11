/**
 * 
 */
package votacao;

public class Cargo extends EntidadeBase {

    private String nome;
    private String uf;
    private boolean permiteSegundoTurno = false;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public boolean isPermiteSegundoTurno() {
        return permiteSegundoTurno;
    }

    public void setPermiteSegundoTurno(boolean permiteSegundoTurno) {
        this.permiteSegundoTurno = permiteSegundoTurno;
    }
}
