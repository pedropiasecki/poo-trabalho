package br.unicentro.appjogador.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuController {

    @FXML private Button btnJogadores;
    @FXML private Button btnPartidas;
    @FXML private Button btnEstatisticas;

    @FXML
    private void abrirJogadores() {
        abrirTela("/br/unicentro/appjogador/view/jogador.fxml", "Jogadores");
    }

    @FXML
    private void abrirPartidas() {
        abrirTela("/br/unicentro/appjogador/view/partida.fxml", "Partidas");
    }

    @FXML
    private void abrirEstatisticas() {
        abrirTela("/br/unicentro/appjogador/view/estatistica.fxml", "Estatísticas");
    }

    @FXML
    private void sair() {
        System.exit(0);
    }

    private void abrirTela(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
