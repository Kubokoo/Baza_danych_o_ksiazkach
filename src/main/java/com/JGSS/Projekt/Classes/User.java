package com.JGSS.Projekt.Classes;

import com.JGSS.Projekt.Controller.SQL;

public class User {
    private int id;
    private String login;
    private String password;
    private int permissions;

    public User(){
        super();
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(int id, String login, String password, int permissions) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.permissions = permissions;
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

    public void loginUser(String username, String password){
        SQL sql = new SQL();

        User sqlUser = sql.confirmLogin(username, password);
        if(sqlUser != null){
            this.id = sqlUser.id;
            this.login = sqlUser.login;
            this.password = sqlUser.password;
            this.permissions = sqlUser.permissions;
        }
    }
}
