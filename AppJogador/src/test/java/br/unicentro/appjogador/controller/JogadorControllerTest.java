package br.unicentro.appjogador.controller;

import br.unicentro.appjogador.model.Jogador;
import br.unicentro.appjogador.dao.JogadorDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class JogadorControllerTest {

    private JogadorController controller;
    private FakeJogadorDAO fakeDAO;
    private static boolean javafxStarted = false;

    @BeforeAll
    static void initToolkit() throws Exception {
        if (!javafxStarted) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            latch.await();
            javafxStarted = true;
        }
    }

    @BeforeEach
    void setup() {
        controller = new JogadorController();
        fakeDAO = new FakeJogadorDAO();
        setPrivateField(controller, "jogadorDAO", fakeDAO);

        TextField nome = new TextField();
        TextField posicao = new TextField();
        TextField idade = new TextField();
        setPrivateField(controller, "nomeField", nome);
        setPrivateField(controller, "posicaoField", posicao);
        setPrivateField(controller, "idadeField", idade);

        TableView<Jogador> tabela = new TableView<>();
        tabela.setItems(FXCollections.observableArrayList());
        setPrivateField(controller, "tabelaJogadores", tabela);
        setPrivateField(controller, "listaJogadores", FXCollections.observableArrayList());
    }

    @Test
    void testeJogadorValido() {
        getTextField(controller, "nomeField").setText("Messi");
        getTextField(controller, "posicaoField").setText("Atacante");
        getTextField(controller, "idadeField").setText("37");

        invokeMethod(controller, "adicionar");

        assertEquals(1, fakeDAO.listar().size(), "Deve haver exatamente um jogador inserido.");
    }

    @Test
    void testeCampoVazio() {
        getTextField(controller, "nomeField").setText("");
        getTextField(controller, "posicaoField").setText("Atacante");
        getTextField(controller, "idadeField").setText("30");

        invokeMethod(controller, "adicionar");

        assertTrue(fakeDAO.listar().isEmpty(), "Não deve inserir jogador com nome em branco.");
    }

    static class FakeJogadorDAO extends JogadorDAO {
        List<Jogador> jogadores = new ArrayList<>();

        @Override
        public List<Jogador> listar() {
            return new ArrayList<>(jogadores);
        }

        @Override
        public void inserir(Jogador j) {
            j.setId(jogadores.size() + 1);
            jogadores.add(j);
        }

        @Override
        public void atualizar(Jogador j) {
            for (int i = 0; i < jogadores.size(); i++) {
                if (jogadores.get(i).getId() == j.getId()) {
                    jogadores.set(i, j);
                    return;
                }
            }
        }

        @Override
        public void deletar(int id) {
            jogadores.removeIf(j -> j.getId() == id);
        }
    }

    private static void setPrivateField(Object obj, String fieldName, Object value) {
        try {
            var field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getPrivateField(Object obj, String fieldName) {
        try {
            var field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static TextField getTextField(Object obj, String fieldName) {
        try {
            var field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (TextField) field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void invokeMethod(Object obj, String methodName) {
        try {
            var method = obj.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            method.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
