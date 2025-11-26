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

    // componentes da interface
    @FXML private TableView<Jogador> tabelaJogadores;
    @FXML private TableColumn<Jogador, String> colNome;
    @FXML private TableColumn<Jogador, String> colPosicao;
    @FXML private TableColumn<Jogador, Integer> colIdade;

    @FXML private TextField nomeField;
    @FXML private TextField posicaoField;
    @FXML private TextField idadeField;

    // obj DAO para acessar banco de dados
    private JogadorDAO jogadorDAO = new JogadorDAO();

    // lista para tabela
    private ObservableList<Jogador> listaJogadores;

    @FXML
    public void initialize() {
        // colunas para adicionar valores
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPosicao.setCellValueFactory(new PropertyValueFactory<>("posicao"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));

        // carrega jogadores do banco de dados para a lista
        listaJogadores = FXCollections.observableArrayList(jogadorDAO.listar());
        tabelaJogadores.setItems(listaJogadores);

        // preenchimento dos campos a partir da tabela
        tabelaJogadores.getSelectionModel().selectedItemProperty().addListener(
                (obs, antigo, novo) -> {
                    if (novo != null) {
                        nomeField.setText(novo.getNome());
                        posicaoField.setText(novo.getPosicao());
                        idadeField.setText(String.valueOf(novo.getIdade()));
                    }
                }
        );
    }


    /*
        2ª Refatoração
        Autor(a): Pedro
        Uso de Introduce Parameter Object para transformar campos em um Obj jogador
        Objetivo: deixar o código claro e melhorar manuteniblidade, vários parametros em um único objeto */
    private Jogador obterDadosDosCampos() {
        return new Jogador(
                nomeField.getText(),
                posicaoField.getText(),
                Integer.parseInt(idadeField.getText())
        );
    }

    // pega os dados e insere no banco
    @FXML
    private void adicionar() {
        if (nomeField.getText().isBlank() || idadeField.getText().isBlank()) return;

        Jogador j = obterDadosDosCampos();
        jogadorDAO.inserir(j);

        atualizarTabela();
        limparCampos();
    }

    // pega jogador da tabela e atualiza dados
    @FXML
    private void atualizar() {
        Jogador selecionado = tabelaJogadores.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            Jogador dados = obterDadosDosCampos();
            selecionado.setNome(dados.getNome());
            selecionado.setPosicao(dados.getPosicao());
            selecionado.setIdade(dados.getIdade());

            jogadorDAO.atualizar(selecionado);
            atualizarTabela();
        }
    }


    // exclui um jogador da tabela
    @FXML
    private void excluir() {
        Jogador selecionado = tabelaJogadores.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            jogadorDAO.deletar(selecionado.getId());
            atualizarTabela();
            limparCampos();
        }
    }

    // usa banco para atualizar a tabela
    private void atualizarTabela() {
        listaJogadores.setAll(jogadorDAO.listar());
    }

    // limpa campos
    private void limparCampos() {
        nomeField.clear();
        posicaoField.clear();
        idadeField.clear();
    }
}
