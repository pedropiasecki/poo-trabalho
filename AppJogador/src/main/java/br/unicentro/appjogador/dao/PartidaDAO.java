package br.unicentro.appjogador.dao;

import br.unicentro.appjogador.database.Conexao;
import br.unicentro.appjogador.model.Partida;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO {

    // metodo para inserir partida no banco de dados
    public void inserir(Partida p) {
        String sql = "INSERT INTO partida (data, adversario, local) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(p.getData()));
            stmt.setString(2, p.getAdversario());
            stmt.setString(3, p.getLocal());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // metodo para listar partidas
    public List<Partida> listar() {
        List<Partida> lista = new ArrayList<>();
        String sql = "SELECT * FROM partida ORDER BY data DESC";
        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Partida(
                        rs.getInt("id"),
                        rs.getDate("data").toLocalDate(),
                        rs.getString("adversario"),
                        rs.getString("local")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // metodo que chama inserir com uma partida p
    public void adicionarPartida(LocalDate data, String adversario, String local) {
        Partida p = new Partida(data, adversario, local);
        inserir(p);
    }

    // atualiza uma partida ja existente
    public void atualizar(Partida p) {
        String sql = "UPDATE partida SET data=?, adversario=?, local=? WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(p.getData()));
            stmt.setString(2, p.getAdversario());
            stmt.setString(3, p.getLocal());
            stmt.setInt(4, p.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // deleta uma partida do banco de dados
    public void deletar(int id) {
        String sql = "DELETE FROM partida WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}