/**
 * 
 */
package votacao;

public class AbstencaoZona {

    private Long zonaId;
    private int numeroZona;
    private String municipio;
    private long totalEleitores;
    private long presentes;
    private long abstencao;
    private double taxaAbstencao;

    public Long getZonaId() {
        return zonaId;
    }

    public void setZonaId(Long zonaId) {
        this.zonaId = zonaId;
    }

    public int getNumeroZona() {
        return numeroZona;
    }

    public void setNumeroZona(int numeroZona) {
        this.numeroZona = numeroZona;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public long getTotalEleitores() {
        return totalEleitores;
    }

    public void setTotalEleitores(long totalEleitores) {
        this.totalEleitores = totalEleitores;
    }

    public long getPresentes() {
        return presentes;
    }

    public void setPresentes(long presentes) {
        this.presentes = presentes;
    }

    public long getAbstencao() {
        return abstencao;
    }

    public void setAbstencao(long abstencao) {
        this.abstencao = abstencao;
    }

    public double getTaxaAbstencao() {
        return taxaAbstencao;
    }

    public void setTaxaAbstencao(double taxaAbstencao) {
        this.taxaAbstencao = taxaAbstencao;
    }
}
