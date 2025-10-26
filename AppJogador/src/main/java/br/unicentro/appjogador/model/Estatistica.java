package br.unicentro.appjogador.model;

public class Estatistica {
    private int id;
    private Jogador jogador;
    private Partida partida;
    private int gols;
    private int assistencias;

    public Estatistica(int id, Jogador jogador, Partida partida, int gols, int assistencias) {
        this.id = id;
        this.jogador = jogador;
        this.partida = partida;
        this.gols = gols;
        this.assistencias = assistencias;
    }

    public Estatistica(Jogador jogador, Partida partida, int gols, int assistencias) {
        this.jogador = jogador;
        this.partida = partida;
        this.gols = gols;
        this.assistencias = assistencias;
    }

    public int getId() { return id; }
    public Jogador getJogador() { return jogador; }
    public Partida getPartida() { return partida; }
    public int getGols() { return gols; }
    public int getAssistencias() { return assistencias; }

    public void setId(int id) { this.id = id; }
    public void setJogador(Jogador jogador) { this.jogador = jogador; }
    public void setPartida(Partida partida) { this.partida = partida; }
    public void setGols(int gols) { this.gols = gols; }
    public void setAssistencias(int assistencias) { this.assistencias = assistencias; }
}