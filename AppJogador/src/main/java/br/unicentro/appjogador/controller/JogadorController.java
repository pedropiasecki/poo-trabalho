package br.unicentro.appjogador.controller;

import br.unicentro.appjogador.dao.JogadorDAO;
import br.unicentro.appjogador.model.Jogador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class JogadorController {

    @FXML private TableView<Jogador> tabelaJogadores;
    @FXML private TableColumn<Jogador, String> colNome;
    @FXML private TableColumn<Jogador, String> colPosicao;
    @FXML private TableColumn<Jogador, Integer> colIdade;

    @FXML private TextField nomeField;
    @FXML private TextField posicaoField;
    @FXML private TextField idadeField;

    private JogadorDAO jogadorDAO = new JogadorDAO();
    private ObservableList<Jogador> listaJogadores;

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPosicao.setCellValueFactory(new PropertyValueFactory<>("posicao"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));

        listaJogadores = FXCollections.observableArrayList(jogadorDAO.listar());
        tabelaJogadores.setItems(listaJogadores);
    }

    @FXML
    private void adicionar() {
        if (nomeField.getText().isBlank() || idadeField.getText().isBlank()) return;
        Jogador j = new Jogador(nomeField.getText(), posicaoField.getText(), Integer.parseInt(idadeField.getText()));
        jogadorDAO.inserir(j);
        atualizarTabela();
        limparCampos();
    }

    @FXML
    private void atualizar() {
        Jogador selecionado = tabelaJogadores.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            selecionado.setNome(nomeField.getText());
            selecionado.setPosicao(posicaoField.getText());
            selecionado.setIdade(Integer.parseInt(idadeField.getText()));
            jogadorDAO.atualizar(selecionado);
            atualizarTabela();
        }
    }

    @FXML
    private void excluir() {
        Jogador selecionado = tabelaJogadores.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            jogadorDAO.deletar(selecionado.getId());
            atualizarTabela();
            limparCampos();
        }
    }

    private void atualizarTabela() {
        listaJogadores.setAll(jogadorDAO.listar());
    }

    private void limparCampos() {
        nomeField.clear();
        posicaoField.clear();
        idadeField.clear();
    }
}
