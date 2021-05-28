package com.JGSS.Projekt.Classes;

import com.JGSS.Projekt.Controller.SQL;

import java.sql.SQLException;

public class User {
    private int id;
    private String login;
    private String password;
    private int permissions;
    private String firstName;
    private String lastName;

    public User(){
        id = -1;
        permissions = -1;
    }

    public User(int permissions) {
        this.permissions = permissions;
    }

    public User(int id, String login, int permissions) {
        this.id = id;
        this.login = login;
        this.permissions = permissions;
    }

    public User(int id, String login, int permissions, String firstName, String lastName) {
        this.id = id;
        this.login = login;
        this.permissions = permissions;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(int id, String login, String password, int permissions, String firstName, String lastName) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.permissions = permissions;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPermissions() {
        return permissions;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void loginUser(String username, String password){
        SQL sql = new SQL("usersDB.db");

        User sqlUser = sql.confirmLogin(username, password);
        if(sqlUser.getPermissions() >= 0){
            this.id = sqlUser.id;
            this.login = sqlUser.login;
            this.permissions = sqlUser.permissions;
        }

        try {
            sql.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
