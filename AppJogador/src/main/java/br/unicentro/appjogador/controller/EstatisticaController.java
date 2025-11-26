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

    // componentes da interface
    @FXML private ComboBox<Jogador> jogadorBox;
    @FXML private ComboBox<Partida> partidaBox;
    @FXML private TextField golsField;
    @FXML private TextField assistenciasField;
    @FXML private TableView<Estatistica> tabelaEstatisticas;
    @FXML private TableColumn<Estatistica, String> colJogador;
    @FXML private TableColumn<Estatistica, String> colPartida;
    @FXML private TableColumn<Estatistica, Integer> colGols;
    @FXML private TableColumn<Estatistica, Integer> colAssistencias;

    // Obj DAO para acessar banco de dados
    private EstatisticaDAO estatisticaDAO = new EstatisticaDAO();
    private JogadorDAO jogadorDAO = new JogadorDAO();
    private PartidaDAO partidaDAO = new PartidaDAO();

    // lista observavel para atualização automatica da tabela
    private ObservableList<Estatistica> dados = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        // carregar jogadores e partidas nos ComboBoxes
        jogadorBox.setItems(FXCollections.observableArrayList(jogadorDAO.listar()));
        partidaBox.setItems(FXCollections.observableArrayList(partidaDAO.listar()));

        // colunas para adicionar valores, objetos
        colJogador.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getJogador().getNome()));
        colPartida.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPartida().getAdversario()));
        colGols.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getGols()).asObject());
        colAssistencias.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getAssistencias()).asObject());

        // carrega dados de inicio na tabela
        atualizarTabela();

        /*
        1ª Refatoração
        Autor(a): Pedro
        Uso de Extract Method para criar um metodo separado preencherCampos()
        Objetivo: deixar o código claro e melhorar manuteniblidade */

        tabelaEstatisticas.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                preencherCampos(selecionado);
            }
        });
    }

    // função de botão adicionar
    // adiciona nova estatistica e salva no banco de dados
    // atualiza a tabela e apaga os campos para nova isercao
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

    // funcao para atualizar
    // atualiza uma estatistica selecionada na tabela de exibicao
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

    // funcao para excluir estatistica
    // remove do banco de dados e atualiza tabela de exibicao
    @FXML
    private void excluir() {
        Estatistica selecionada = tabelaEstatisticas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            estatisticaDAO.deletar(selecionada.getId());
            atualizarTabela();
            limparCampos();
        }
    }

    // recarrega dados da tabela de exibicao usando banco de dados
    private void atualizarTabela() {
        dados.setAll(estatisticaDAO.listar());
        tabelaEstatisticas.setItems(dados);
    }

    // limpa os campos de texto da interface
    private void limparCampos() {
        jogadorBox.setValue(null);
        partidaBox.setValue(null);
        golsField.clear();
        assistenciasField.clear();
    }

    // preenche os campos ao selecionar uma linha da tabela de exibicao
    private void preencherCampos(Estatistica e) {
        jogadorBox.setValue(e.getJogador());
        partidaBox.setValue(e.getPartida());
        golsField.setText(String.valueOf(e.getGols()));
        assistenciasField.setText(String.valueOf(e.getAssistencias()));
    }

}
