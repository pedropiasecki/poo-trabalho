package br.unicentro.appjogador.dao;

import br.unicentro.appjogador.database.Conexao;
import br.unicentro.appjogador.model.Jogador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogadorDAO {

    // metodo para inserir jogador ao banco de dados
    public void inserir(Jogador j) {
        String sql = "INSERT INTO jogador (nome, posicao, idade) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, j.getNome());
            stmt.setString(2, j.getPosicao());
            stmt.setInt(3, j.getIdade());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // metodo para listar jogadores presentes no banco de dados
    public List<Jogador> listar() {
        List<Jogador> lista = new ArrayList<>();
        String sql = "SELECT * FROM jogador ORDER BY id";
        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Jogador(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("posicao"),
                        rs.getInt("idade")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // metodo para atualizar jogador existente no banco de dados
    public void atualizar(Jogador j) {
        String sql = "UPDATE jogador SET nome=?, posicao=?, idade=? WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, j.getNome());
            stmt.setString(2, j.getPosicao());
            stmt.setInt(3, j.getIdade());
            stmt.setInt(4, j.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // metodo para deletar jogador do banco de dados
    public void deletar(int id) {
        String sql = "DELETE FROM jogador WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
