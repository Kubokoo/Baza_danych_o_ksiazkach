package com.JGSS.Projekt.Controller;

import com.JGSS.Projekt.Classes.User;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SQL {
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:";

    private Connection conn;
    private Statement stat;

    public Connection getConn() {
        return conn;
    }

    public SQL(){
        super();
    }

    public SQL(String DBName){
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
            fullPath += "/DB/" + DBName;
            conn = DriverManager.getConnection(DB_URL+fullPath);
            stat = conn.createStatement();
        } catch (SQLException | UnsupportedEncodingException e) {
            System.err.println("Connection problem or wrong DB name.");
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws SQLException {
        stat.close();
        conn.close();
    }

    public void close() throws SQLException {
        stat.close();
        conn.close();
    }

    public List<User> getUsers(String filer){
        List<User> users = new LinkedList<User>();

        try {
            ResultSet result = stat.executeQuery("SELECT * FROM users" + filer);
            int idDB, premissionsDB;
            String userDB, passwordDB, firstName, lastName;
            while(result.next()) {
                idDB = result.getInt("Id");
                userDB = result.getString("Username");
                passwordDB = result.getString("Password");
                premissionsDB = result.getInt("Permissions");
                firstName = result.getString("FirsName");
                lastName = result.getString("LastName");
                users.add(new User(idDB, userDB, passwordDB, premissionsDB, firstName, lastName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return users;
    }

    public User confirmLogin(String login, String password){
        User user = new User(-1);
        try {
            ResultSet result =
                    stat.executeQuery("SELECT * FROM 'Users' WHERE Username ='" + login + "' AND Password ='" + password + "'");
            int idDB, premissionsDB;
            String userDB, firstName, lastName;
            while(result.next()) {
                idDB = result.getInt("Id");
                userDB = result.getString("Username");
                premissionsDB = result.getInt("Permissions");
                firstName = result.getString("FirstName");
                lastName = result.getString("LastName");
                user = new User(idDB, userDB, premissionsDB, firstName, lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return user;
        }

        return user;
    }
    public LinkedList<String> getCurrUser(String login){
        LinkedList<String> user = new LinkedList<>();
        try {
            ResultSet result =
                    stat.executeQuery("SELECT * FROM 'Users' WHERE Username ='" + login + "'");
            int idDB, premissionsDB;
            String userDB, firstName, lastName;
            while(result.next()) {
                user.add(result.getString("Id"));
                user.add(result.getString("Username"));
                user.add("****");
                user.add(result.getString("Permissions"));
                user.add(result.getString("FirstName"));
                user.add(result.getString("LastName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return user;
        }

        return user;
    }

    public LinkedList<String> columnsLabels(){
        LinkedList<String> columns = new LinkedList<>();

        try {
            ResultSet result =
                    stat.executeQuery("SELECT * FROM 'Users' WHERE 0=1");
            ResultSetMetaData rsmd = result.getMetaData();
            for(int i = 1; i <= rsmd.getColumnCount(); i++){
                columns.add(rsmd.getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return columns;
        }

        return columns;
    }
}