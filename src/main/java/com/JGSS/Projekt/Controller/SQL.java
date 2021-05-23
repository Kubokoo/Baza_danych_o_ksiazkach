package com.JGSS.Projekt.Controller;

import com.JGSS.Projekt.Classes.User;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SQL {
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:";
    private Connection conn;
    private Statement stat;

    public SQL(){
        try {
            Class.forName(SQL.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("No JDBC driver");
            e.printStackTrace();
        }

        try {
            String path = this.getClass().getClassLoader().getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "UTF-8");
            fullPath = fullPath.replace("/WEB-INF/classes/", "");
            fullPath += "/DB/usersDB.db";
            conn = DriverManager.getConnection(DB_URL+fullPath);
            stat = conn.createStatement();
        } catch (SQLException | UnsupportedEncodingException e) {
            System.err.println("Connection problem");
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws SQLException {
        stat.close();
        conn.close();
    }

    public List<User> getUsers(String filer){
        List<User> users = new LinkedList<User>();

        try {
            ResultSet result = stat.executeQuery("SELECT * FROM users");
            int idDB;
            String userDB, passwordDB, premissionsDB;
            while(result.next()) {
                idDB = result.getInt("Id");
                userDB = result.getString("Username");
                passwordDB = result.getString("Password");
                premissionsDB = result.getString("Premissions");
                users.add(new User(idDB, userDB, passwordDB, Integer.parseInt(premissionsDB)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return users;
    }

    public User confirmLogin(String login, String password){
        User user = null;
        try {
            ResultSet result =
                    stat.executeQuery("SELECT * FROM 'Users' WHERE Username ='" + login + "' AND Password ='" + password + "'");
            int id;
            String userDB, passwordDB, premissionsDB;
            while(result.next()) {
                id = result.getInt("Id");
                userDB = result.getString("Username");
                passwordDB = result.getString("Password");
                premissionsDB = result.getString("Permissions");
                user = new User(id, userDB, passwordDB, Integer.parseInt(premissionsDB));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return user;
    }
}