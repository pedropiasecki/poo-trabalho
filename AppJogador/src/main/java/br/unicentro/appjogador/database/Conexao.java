package br.unicentro.appjogador.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* Classe que conecta com o banco de dados */
public class Conexao {
    private static final String URL = "jdbc:postgresql://localhost:5432/sistema_esportivo";
    private static final String USER = "postgres";
    private static final String PASSWORD = "garrafa";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}