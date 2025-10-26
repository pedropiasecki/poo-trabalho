package br.unicentro.appjogador.controller;

import br.unicentro.appjogador.dao.PartidaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import br.unicentro.appjogador.model.Partida;

import java.time.LocalDate;

public class PartidaController {

    @FXML private DatePicker dataField;
    @FXML private TextField adversarioField;
    @FXML private TextField localField;
    @FXML private TableView<Partida> tabelaPartidas;
    @FXML private TableColumn<Partida, LocalDate> colData;
    @FXML private TableColumn<Partida, String> colAdversario;
    @FXML private TableColumn<Partida, String> colLocal;

    private PartidaDAO partidaDAO = new PartidaDAO();
    private ObservableList<Partida> dados = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colData.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getData()));
        colAdversario.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getAdversario()));
        colLocal.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getLocal()));

        atualizarTabela();

        tabelaPartidas.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                dataField.setValue(selecionado.getData());
                adversarioField.setText(selecionado.getAdversario());
                localField.setText(selecionado.getLocal());
            }
        });
    }

    @FXML
    private void adicionar() {
        Partida p = new Partida(dataField.getValue(), adversarioField.getText(), localField.getText());
        partidaDAO.inserir(p);
        atualizarTabela();
        limparCampos();
    }

    @FXML
    private void atualizar() {
        Partida selecionado = tabelaPartidas.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            selecionado.setData(dataField.getValue());
            selecionado.setAdversario(adversarioField.getText());
            selecionado.setLocal(localField.getText());
            partidaDAO.atualizar(selecionado);
            atualizarTabela();
        }
    }

    @FXML
    private void excluir() {
        Partida selecionado = tabelaPartidas.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            partidaDAO.deletar(selecionado.getId());
            atualizarTabela();
            limparCampos();
        }
    }

    private void atualizarTabela() {
        dados.setAll(partidaDAO.listar());
        tabelaPartidas.setItems(dados);
    }

    private void limparCampos() {
        dataField.setValue(null);
        adversarioField.clear();
        localField.clear();
    }
}
