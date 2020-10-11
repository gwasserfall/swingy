package controllers;

import helpers.Character;
import models.GameState;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Database {

    private static final Connection conn = null;

    private Database(){}

    private static Connection createConnection() throws ClassNotFoundException {
        Connection conn = null;
        String uri = "jdbc:sqlite:swingy.db";
        Class.forName("org.sqlite.JDBC");
        try {
            conn = DriverManager.getConnection(uri);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static Connection getInstance() throws ClassNotFoundException {
        if (conn == null) {
            return createConnection();
        }
        return conn;
    }

    private static void CloseConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Could not close database connection.");
            }

        }
    }

    public static void CheckDatabaseSchema() throws SQLException {
        Connection connection = null;
        String sql = new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS saved_games (\n ")
                .append("id integer PRIMARY KEY,\n ")
                .append("name TEXT UNIQUE,\n ")
                .append("class TEXT,\n ")
                .append("level INTEGER,\n ")
                .append("state TEXT\n )")
                .toString();
        try {
            connection = Database.getInstance();
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            CloseConnection(connection);
        }
    }

    public static String LoadGame(String name) {
        Connection connection = null;
        String sql = new StringBuilder()
                .append("SELECT * FROM saved_games \n")
                .append("WHERE name = ? \n ")
                .toString();
        try {
            connection = Database.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs  = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("state");
            }
            return "";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            CloseConnection(connection);
        }
        return "";
    }


    public static void SaveGame(GameState state) {
        Connection connection = null;
        String sql = new StringBuilder()
                .append("REPLACE INTO saved_games \n")
                .append("(name, class, level, state) \n ")
                .append("VALUES\n ")
                .append("(?, ?, ?, ?)")
                .toString();

        try {
            connection = Database.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, state.player.name);
            pstmt.setString(2, state.player.cls);
            pstmt.setInt(3, state.player.level);
            pstmt.setString(4, state.stringify());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            CloseConnection(connection);
        }
    }

    public static ArrayList<Character> LoadSavedCharacters() {
        Connection connection = null;
        ArrayList<Character> characters = new ArrayList<Character>();

        String sql = "SELECT name, class, level FROM saved_games";

        try {
            connection = Database.getInstance();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString("name");
                String cls = rs.getString("class");
                Integer level = rs.getInt("level");
                characters.add(new Character(name, cls, level));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            CloseConnection(connection);
        }
        return characters;
    }
}
