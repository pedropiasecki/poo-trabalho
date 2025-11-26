package br.unicentro.appjogador.dao;

import br.unicentro.appjogador.database.Conexao;
import br.unicentro.appjogador.model.Estatistica;
import br.unicentro.appjogador.model.Jogador;
import br.unicentro.appjogador.model.Partida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstatisticaDAO {

    // usado para acessar entidades
    private JogadorDAO jogadorDAO = new JogadorDAO();
    private PartidaDAO partidaDAO = new PartidaDAO();

    // metodo para inserir nova estatistica ao banco de dados
    public void inserir(Estatistica e) {
        String sql = "INSERT INTO estatistica (jogador_id, partida_id, gols, assistencias) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, e.getJogador().getId());
            stmt.setInt(2, e.getPartida().getId());
            stmt.setInt(3, e.getGols());
            stmt.setInt(4, e.getAssistencias());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // lista estatisticas e informações de partida e jogador
    public List<Estatistica> listar() {
        List<Estatistica> lista = new ArrayList<>();
        String sql = """
                SELECT e.id, e.gols, e.assistencias,
                       j.id AS jogador_id, j.nome AS jogador_nome, j.posicao, j.idade,
                       p.id AS partida_id, p.data, p.adversario, p.local
                FROM estatistica e
                JOIN jogador j ON e.jogador_id = j.id
                JOIN partida p ON e.partida_id = p.id
                ORDER BY p.data DESC;
                """;

        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            /*
            4ª Refatoração
            Autor(a): Pedro
            Uso de Extract Method para tornar resulSet como objeto
            Objetivo: melhorar estrutura do código e mais legível */
            while (rs.next()) {
                lista.add(mapearEstatistica(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // atualiza uma estatistica no banco de dados
    public void atualizar(Estatistica e) {
        String sql = "UPDATE estatistica SET jogador_id=?, partida_id=?, gols=?, assistencias=? WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, e.getJogador().getId());
            stmt.setInt(2, e.getPartida().getId());
            stmt.setInt(3, e.getGols());
            stmt.setInt(4, e.getAssistencias());
            stmt.setInt(5, e.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // deleta uma estatistica no banco de dados
    public void deletar(int id) {
        String sql = "DELETE FROM estatistica WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    // métodos usados para mapeamento usado no listar()
    // usado para usar ResulSet como objetos
    private Jogador mapearJogador(ResultSet rs) throws SQLException {
        return new Jogador(
                rs.getInt("jogador_id"),
                rs.getString("jogador_nome"),
                rs.getString("posicao"),
                rs.getInt("idade")
        );
    }

    private Partida mapearPartida(ResultSet rs) throws SQLException {
        return new Partida(
                rs.getInt("partida_id"),
                rs.getDate("data").toLocalDate(),
                rs.getString("adversario"),
                rs.getString("local")
        );
    }

    private Estatistica mapearEstatistica(ResultSet rs) throws SQLException {
        return new Estatistica(
                rs.getInt("id"),
                mapearJogador(rs),
                mapearPartida(rs),
                rs.getInt("gols"),
                rs.getInt("assistencias")
        );
    }
}
