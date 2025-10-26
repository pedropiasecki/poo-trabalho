package br.unicentro.appjogador.model;

import java.time.LocalDate;

public class Partida {
    private int id;
    private LocalDate data;
    private String adversario;
    private String local;

    public Partida(int id, LocalDate data, String adversario, String local) {
        this.id = id;
        this.data = data;
        this.adversario = adversario;
        this.local = local;
    }

    public Partida(LocalDate data, String adversario, String local) {
        this.data = data;
        this.adversario = adversario;
        this.local = local;
    }

    public int getId() { return id; }
    public LocalDate getData() { return data; }
    public String getAdversario() { return adversario; }
    public String getLocal() { return local; }

    public void setId(int id) { this.id = id; }
    public void setData(LocalDate data) { this.data = data; }
    public void setAdversario(String adversario) { this.adversario = adversario; }
    public void setLocal(String local) { this.local = local; }

    @Override
    public String toString() { return adversario + " - " + data; }
}