package br.unicentro.appjogador.controller;

import br.unicentro.appjogador.dao.EstatisticaDAO;
import br.unicentro.appjogador.dao.JogadorDAO;
import br.unicentro.appjogador.dao.PartidaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import br.unicentro.appjogador.model.Estatistica;
import br.unicentro.appjogador.model.Jogador;
import br.unicentro.appjogador.model.Partida;

public class EstatisticaController {

    @FXML private ComboBox<Jogador> jogadorBox;
    @FXML private ComboBox<Partida> partidaBox;
    @FXML private TextField golsField;
    @FXML private TextField assistenciasField;
    @FXML private TableView<Estatistica> tabelaEstatisticas;
    @FXML private TableColumn<Estatistica, String> colJogador;
    @FXML private TableColumn<Estatistica, String> colPartida;
    @FXML private TableColumn<Estatistica, Integer> colGols;
    @FXML private TableColumn<Estatistica, Integer> colAssistencias;

    private EstatisticaDAO estatisticaDAO = new EstatisticaDAO();
    private JogadorDAO jogadorDAO = new JogadorDAO();
    private PartidaDAO partidaDAO = new PartidaDAO();

    private ObservableList<Estatistica> dados = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        jogadorBox.setItems(FXCollections.observableArrayList(jogadorDAO.listar()));
        partidaBox.setItems(FXCollections.observableArrayList(partidaDAO.listar()));

        colJogador.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getJogador().getNome()));
        colPartida.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPartida().getAdversario()));
        colGols.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getGols()).asObject());
        colAssistencias.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getAssistencias()).asObject());

        atualizarTabela();

        tabelaEstatisticas.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                jogadorBox.setValue(selecionado.getJogador());
                partidaBox.setValue(selecionado.getPartida());
                golsField.setText(String.valueOf(selecionado.getGols()));
                assistenciasField.setText(String.valueOf(selecionado.getAssistencias()));
            }
        });
    }

    @FXML
    private void adicionar() {
        Estatistica e = new Estatistica(
                jogadorBox.getValue(),
                partidaBox.getValue(),
                Integer.parseInt(golsField.getText()),
                Integer.parseInt(assistenciasField.getText())
        );
        estatisticaDAO.inserir(e);
        atualizarTabela();
        limparCampos();
    }

    @FXML
    private void atualizar() {
        Estatistica selecionada = tabelaEstatisticas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            selecionada.setJogador(jogadorBox.getValue());
            selecionada.setPartida(partidaBox.getValue());
            selecionada.setGols(Integer.parseInt(golsField.getText()));
            selecionada.setAssistencias(Integer.parseInt(assistenciasField.getText()));
            estatisticaDAO.atualizar(selecionada);
            atualizarTabela();
        }
    }

    @FXML
    private void excluir() {
        Estatistica selecionada = tabelaEstatisticas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            estatisticaDAO.deletar(selecionada.getId());
            atualizarTabela();
            limparCampos();
        }
    }

    private void atualizarTabela() {
        dados.setAll(estatisticaDAO.listar());
        tabelaEstatisticas.setItems(dados);
    }

    private void limparCampos() {
        jogadorBox.setValue(null);
        partidaBox.setValue(null);
        golsField.clear();
        assistenciasField.clear();
    }
}
