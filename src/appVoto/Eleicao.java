/**
 * 
 */
package appVoto;

import java.time.LocalDate;

import votacao.EntidadeBase;

public class Eleicao extends EntidadeBase {

    private String nome;
    private int ano;
    private LocalDate dataTurno1;
    private LocalDate dataTurno2;
    private int turnoAtual = 1;
    private boolean segundoTurnoHabilitado = false;
    private boolean ativa = true;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public LocalDate getDataTurno1() {
        return dataTurno1;
    }

    public void setDataTurno1(LocalDate dataTurno1) {
        this.dataTurno1 = dataTurno1;
    }

    public LocalDate getDataTurno2() {
        return dataTurno2;
    }

    public void setDataTurno2(LocalDate dataTurno2) {
        this.dataTurno2 = dataTurno2;
    }

    public int getTurnoAtual() {
        return turnoAtual;
    }

    public void setTurnoAtual(int turnoAtual) {
        this.turnoAtual = turnoAtual;
    }

    public boolean isSegundoTurnoHabilitado() {
        return segundoTurnoHabilitado;
    }

    public void setSegundoTurnoHabilitado(boolean segundoTurnoHabilitado) {
        this.segundoTurnoHabilitado = segundoTurnoHabilitado;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
}

