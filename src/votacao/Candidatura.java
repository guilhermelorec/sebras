/**
 * 
 */
package votacao;


public class Candidatura extends EntidadeBase {

    private Long eleitorId;
    private Long partidoId;
    private Long eleicaoId;
    private Long cargoId;
    private int numero;
    private boolean ativa = true;

    // Campos auxiliares para listagem
    private String nomeEleitor;
    private String siglaPartido;
    private String nomeEleicao;
    private String nomeCargo;
    private String ufCargo;

    public Long getEleitorId() {
        return eleitorId;
    }

    public void setEleitorId(Long eleitorId) {
        this.eleitorId = eleitorId;
    }

    public Long getPartidoId() {
        return partidoId;
    }

    public void setPartidoId(Long partidoId) {
        this.partidoId = partidoId;
    }

    public Long getEleicaoId() {
        return eleicaoId;
    }

    public void setEleicaoId(Long eleicaoId) {
        this.eleicaoId = eleicaoId;
    }

    public Long getCargoId() {
        return cargoId;
    }

    public void setCargoId(Long cargoId) {
        this.cargoId = cargoId;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public String getNomeEleitor() {
        return nomeEleitor;
    }

    public void setNomeEleitor(String nomeEleitor) {
        this.nomeEleitor = nomeEleitor;
    }

    public String getSiglaPartido() {
        return siglaPartido;
    }

    public void setSiglaPartido(String siglaPartido) {
        this.siglaPartido = siglaPartido;
    }

    public String getNomeEleicao() {
        return nomeEleicao;
    }

    public void setNomeEleicao(String nomeEleicao) {
        this.nomeEleicao = nomeEleicao;
    }

    public String getNomeCargo() {
        return nomeCargo;
    }

    public void setNomeCargo(String nomeCargo) {
        this.nomeCargo = nomeCargo;
    }

    public String getUfCargo() {
        return ufCargo;
    }

    public void setUfCargo(String ufCargo) {
        this.ufCargo = ufCargo;
    }
}
