package br.unicentro.appjogador.model;

public class Jogador {
    private int id;
    private String nome;
    private String posicao;
    private int idade;

    public Jogador(int id, String nome, String posicao, int idade) {
        this.id = id;
        this.nome = nome;
        this.posicao = posicao;
        this.idade = idade;
    }

    public Jogador(String nome, String posicao, int idade) {
        this.nome = nome;
        this.posicao = posicao;
        this.idade = idade;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getPosicao() { return posicao; }
    public int getIdade() { return idade; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setPosicao(String posicao) { this.posicao = posicao; }
    public void setIdade(int idade) { this.idade = idade; }

    @Override
    public String toString() { return nome; }
}