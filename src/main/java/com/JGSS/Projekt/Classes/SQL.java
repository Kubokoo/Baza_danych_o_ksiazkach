package com.JGSS.Projekt.Classes;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Objects;

public class SQL{
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:";

    private Connection conn;
    private PreparedStatement stat;
    private ResultSet result;
    private String DBName;

    public Connection getConn() {
        return conn;
    }

    public SQL(){
        super();
    }

    public SQL(String DBName){
        this.DBName = DBName;

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

    public LinkedList<LinkedList> getAllUsers(){
        LinkedList<LinkedList> allUsers = new LinkedList<>();

        try {
            String SQLQuery = "SELECT * FROM 'Users';";
            stat = conn.prepareStatement(SQLQuery);
            ResultSet result =
                    stat.executeQuery();
            while(result.next()) {
                LinkedList<String> user = new LinkedList<>();
                user.add(result.getString("Id"));
                user.add(result.getString("Username"));
                user.add("****");
                user.add(result.getString("Permissions"));
                user.add(result.getString("FirstName"));
                user.add(result.getString("LastName"));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return allUsers;
        } finally {
            try {
                stat.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return allUsers;
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
        String SQLQuery = "DELETE FROM Users\n" +
                "WHERE Id = ?;";
        try {
            stat = conn.prepareStatement(SQLQuery);
            stat.setInt(1, id);
            int resultInt =
                    stat.executeUpdate();
            if(resultInt > 0) return true;
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

        return false;
    }

    public boolean addUser(String userName, String password,
                            int permisions, String firstName, String lastName){
        int resultInt = -1;
        String SQLQuery = "INSERT INTO Users\n" +
                "(Username, Password, Permissions, FirstName, LastName)" +
                "VALUES (?, ?, ?, ?, ?);";
        try {
            stat = conn.prepareStatement(SQLQuery);
            stat.setString(1, userName);
            stat.setString(2, password);
            stat.setInt(3, permisions);
            stat.setString(4, firstName);
            stat.setString(5, lastName);
            resultInt =
                    stat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                stat.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


        return resultInt >= 0;
    }

    public LinkedList<LinkedList> getAllBooks(){
        LinkedList<LinkedList> allBooks = new LinkedList<>();

        try {
            String SQLQuery = "SELECT * FROM 'Books';";
            stat = conn.prepareStatement(SQLQuery);
            ResultSet result =
                    stat.executeQuery();
            while(result.next()) {
                LinkedList<String> book = new LinkedList<>();
                book.add(result.getString("ISBN"));
                book.add(result.getString("Title"));
                book.add(result.getString("Release_Date"));
                book.add(result.getString("Author"));
                book.add(result.getString("Publishing_House"));
                book.add(result.getString("OwnerID"));
                allBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return allBooks;
        } finally {
            try {
                stat.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return allBooks;
    }

    public LinkedList<LinkedList> getSearchBooks(String ISBN, String title, String release_Date,
                                                 String author, String publishing_House, boolean hint) {
        LinkedList<LinkedList> allBooks = new LinkedList<>();

        String column = "";
        if (Objects.equals(ISBN, "")) ISBN = null;
        else if(!Objects.equals(ISBN, null) && hint){
            ISBN = ISBN + "%";
            column = "ISBN";
        }

        if (Objects.equals(title, "")) title = null;
        else if(!Objects.equals(title, null)  &&  hint){
            title = title + "%";
            column = "Title";
        }

        if (Objects.equals(release_Date, "")) release_Date = null;
        else if(!Objects.equals(release_Date, null) &&  hint) column = "Release_Date";

        if (Objects.equals(author, "")) author = null;
        else if(!Objects.equals(author, null)  &&  hint){
            author = author + "%";
            column = "Author";
        }

        if (Objects.equals(publishing_House, "")) publishing_House = null;
        else if(!Objects.equals(publishing_House, null) && hint){
            publishing_House = publishing_House + "%";
            column = "Publishing_House";
        }

        try {
            String SQLQuery;

            SQLQuery = "SELECT * FROM 'Books' WHERE" +
                    " ISBN LIKE IFNULL(?,ISBN) AND" +
                    " Title  LIKE IFNULL(?,Title) AND" +
                    " Release_Date  LIKE IFNULL(?,Release_Date) AND" +
                    " Author  LIKE IFNULL(?,Author) AND" +
                    " Publishing_House  LIKE IFNULL(?,Publishing_House) ;";

            stat = conn.prepareStatement(SQLQuery);

            stat.setString(1, ISBN);
            stat.setString(2, title);
            stat.setString(3, release_Date);
            stat.setString(4, author);
            stat.setString(5, publishing_House);

            ResultSet result =
                    stat.executeQuery();
            while (result.next()) {
                LinkedList<String> book = new LinkedList<>();
                if(hint)
                    book.add(result.getString(column));
                else{
                    book.add(result.getString("ISBN"));
                    book.add(result.getString("Title"));
                    book.add(result.getString("Release_Date"));
                    book.add(result.getString("Author"));
                    book.add(result.getString("Publishing_House"));
                    book.add(result.getString("OwnerID"));
                }
                allBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return allBooks;
        } finally {
            try {
                stat.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return allBooks;
    }

    public LinkedList<LinkedList> getUserBooks(User loggedUser){
        LinkedList<LinkedList> allBooks = new LinkedList<>();

        try {
            String SQLQuery = "SELECT * FROM 'Books' WHERE OwnerID = ?;";
            stat = conn.prepareStatement(SQLQuery);
            stat.setInt(1, loggedUser.getId());
            ResultSet result =
                    stat.executeQuery();
            while(result.next()) {
                LinkedList<String> book = new LinkedList<>();
                book.add(result.getString("ISBN"));
                book.add(result.getString("Title"));
                book.add(result.getString("Release_Date"));
                book.add(result.getString("Author"));
                book.add(result.getString("Publishing_House"));
                book.add(result.getString("OwnerID"));
                allBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return allBooks;
        } finally {
            try {
                stat.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return allBooks;
    }

    public boolean addBook(String ISBN, String title, String release_Date,
                           String author, String publishing_House, int ownerID){
        int resultInt = 0;
        String SQLQuery = "INSERT INTO Books\n" +
                "(ISBN, Title, Release_Date, Author, Publishing_House, OwnerID)" +
                "VALUES (?, ?, ?, ?, ?, ?);";
        try {
            stat = conn.prepareStatement(SQLQuery);
            stat.setString(1, ISBN);
            stat.setString(2, title);
            stat.setString(3, release_Date);
            stat.setString(4, author);
            stat.setString(5, publishing_House);
            stat.setInt(6, ownerID);
            resultInt =
                    stat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                stat.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return resultInt == 1;
    }

    public boolean deleteBook(String ISBN){
        String SQLQuery = "DELETE FROM Books\n" +
                "WHERE ISBN = ?;";
        try {
            stat = conn.prepareStatement(SQLQuery);
            stat.setString(1, ISBN);
            int resultInt =
                    stat.executeUpdate();
            if(resultInt > 0) return true;
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

        return false;
    }

    public boolean editBook(String ISBN, String title, String release_Date,
                            String author, String publishing_House, int ownerID){
        int resultInt = 0;
        String SQLQuery = "UPDATE Books\n" +
                "SET ISBN    = ?,\n" +
                "    Title    = ?,\n" +
                "    Release_Date = ?,\n" +
                "    Author   = ?,\n" +
                "    Publishing_House    = ?,\n" +
                "    OwnerID    = ?\n" +
                "WHERE ISBN = ?;";
        try {
            stat = conn.prepareStatement(SQLQuery);
            stat.setString(1, ISBN);
            stat.setString(2, title);
            stat.setString(3, release_Date);
            stat.setString(4, author);
            stat.setString(5, publishing_House);
            stat.setInt(6, ownerID);
            stat.setString(7, ISBN);
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

        return resultInt == 1;
    }

    public LinkedList<String> columnsLabels(){
        LinkedList<String> columns = new LinkedList<>();

        try {
            String SQLQuery = null;
            if(Objects.equals(DBName, "usersDB.db"))
                SQLQuery = "SELECT * FROM 'Users' WHERE 0=1";
            else if (Objects.equals(DBName, "booksDB.db"))
                SQLQuery = "SELECT * FROM 'Books' WHERE 0=1";
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