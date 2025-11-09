package br.unicentro.appjogador.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JogadorTest {
    @Test
    void testeConstrutores() {
        Jogador j = new Jogador(1, "Messi", "Atacante", 37);

        assertEquals(1, j.getId());
        assertEquals("Messi", j.getNome());
        assertEquals("Atacante", j.getPosicao());
        assertEquals(37, j.getIdade());
    }

    @Test
    void testeToString() {
        Jogador j = new Jogador("Vinicius Jr", "Atacante", 25);
        assertEquals("Vinicius Jr", j.toString());
    }
}