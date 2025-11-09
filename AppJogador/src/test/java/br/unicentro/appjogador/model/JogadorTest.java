package br.unicentro.appjogador.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JogadorTest {
    @Test
    void testeConstrutores() {
        Jogador j = new Jogador(1, "Messi", "Atacante", 37);

        assertEquals(1, j.getId(), "ID diferente do esperado");
        assertEquals("Messi", j.getNome(), "Nome diferente do esperado");
        assertEquals("Atacante", j.getPosicao(), "Posição diferente do esperado");
        assertEquals(37, j.getIdade(), "Idade diferente do esperado");
    }

    @Test
    void testeToString() {
        Jogador j = new Jogador("Lopes Jr", "Atacante", 25);
        assertEquals("Vinicius Jr", j.toString(), "O nome do Jogador deve ser o adicionado pelo usuário");
    }
}
