package com.JGSS.Projekt.Controller;

import com.JGSS.Projekt.Classes.User;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SQL{
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:";

    private Connection conn;
    private PreparedStatement stat;
    private ResultSet result;

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
        } catch (SQLException | UnsupportedEncodingException e) {
            System.err.println("Connection problem or wrong DB name.");
            e.printStackTrace();
        }
    }

//    @Override
//    protected void finalize() throws SQLException {
//        stat.close();
//        conn.close();
//    }

    public List<User> getUsers(String filer){
        List<User> users = new LinkedList<>();

        try {
            ResultSet result = stat.executeQuery("SELECT * FROM 'users'" + filer + ";");
            int idDB, permissionsDB;
            String userDB, passwordDB, firstName, lastName;
            while(result.next()) {
                idDB = result.getInt("Id");
                userDB = result.getString("Username");
                passwordDB = result.getString("Password");
                permissionsDB = result.getInt("Permissions");
                firstName = result.getString("FirsName");
                lastName = result.getString("LastName");
                users.add(new User(idDB, userDB, passwordDB, permissionsDB, firstName, lastName));
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
            String SQLQuery = "SELECT * FROM 'Users' WHERE Username = ? AND Password = ?";
            stat = conn.prepareStatement(SQLQuery);
            stat.setString(1, login);
            stat.setString(2, password);
            ResultSet result =
                    stat.executeQuery();
            int idDB, permissionsDB;
            String userDB, firstName, lastName;
            while(result.next()) {
                idDB = result.getInt("Id");
                userDB = result.getString("Username");
                permissionsDB = result.getInt("Permissions");
                firstName = result.getString("FirstName");
                lastName = result.getString("LastName");
                user = new User(idDB, userDB, permissionsDB, firstName, lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return user;
        } finally {
            try {
                stat.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return user;
    }
    public LinkedList<String> getCurrUser(String login){
        LinkedList<String> user = new LinkedList<>();
        try {
            String SQLQuery = "SELECT * FROM 'Users' WHERE Username = ? ;";
            stat = conn.prepareStatement(SQLQuery);
            stat.setString(1, login);
            ResultSet result =
                    stat.executeQuery();
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
        } finally {
            try {
                stat.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return user;
    }

    public boolean editUser(int id, String userName, String password,
                            int permisions, String firstName, String lastName){
        int resultInt = 0;
        if(password != null && !password.equals("")){
            String SQLQuery = "UPDATE Users\n" +
                    "SET Username    = ?,\n" +
                    "    Password    = ?,\n" +
                    "    Permissions = ?,\n" +
                    "    FirstName   = ?,\n" +
                    "    LastName    = ?\n" +
                    "WHERE Id = ?;";
            try {
                stat = conn.prepareStatement(SQLQuery);
                stat.setString(1, userName);
                stat.setString(2, password);
                stat.setInt(3, permisions);
                stat.setString(4, firstName);
                stat.setString(5, lastName);
                stat.setInt(6, id);
                resultInt =
                        stat.executeUpdate();
                stat.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    stat.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        else {
            String SQLQuery = "UPDATE Users\n" +
                    "SET Username    = ?,\n" +
                    "    Permissions = ?,\n" +
                    "    FirstName   = ?,\n" +
                    "    LastName    = ?\n" +
                    "WHERE Id = ?;";
            try {
                stat = conn.prepareStatement(SQLQuery);
                stat.setString(1, userName);
                stat.setInt(2, permisions);
                stat.setString(3, firstName);
                stat.setString(4, lastName);
                stat.setInt(5, id);
                resultInt =
                        stat.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }
            finally {
                try {
                    stat.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        return resultInt == 1;
    }

    public boolean deleteUser(int id){
        boolean result = false;
        String SQLQuery = "DELETE FROM Users\n" +
                "WHERE Id = ?;";
        try {
            stat = conn.prepareStatement(SQLQuery);
            stat.setInt(1, id);
            result =
                    stat.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        finally {
            try {
                stat.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


        return result;
    }

    public LinkedList<String> columnsLabels(){
        LinkedList<String> columns = new LinkedList<>();

        try {
            String SQLQuery = "SELECT * FROM 'Users' WHERE 0=1";
            stat = conn.prepareStatement(SQLQuery);
            result =
                    stat.executeQuery();
            ResultSetMetaData rsmd = result.getMetaData();
            for(int i = 1; i <= rsmd.getColumnCount(); i++){
                columns.add(rsmd.getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return columns;
        }
        finally {
            try {
                result.close();
                stat.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return columns;
    }

    private String sanitazeInput(String input){


        return input;
    }
}