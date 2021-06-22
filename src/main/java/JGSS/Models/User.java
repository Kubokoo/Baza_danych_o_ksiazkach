package JGSS.Models;

import JGSS.Services.SQL;

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
        if(firstName != null)
            return firstName;
        else
            return "";
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        if(lastName != null)
            return lastName;
        else
            return "";
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean loginUser(String username, String password, SQL usersDB){
        User sqlUser = usersDB.confirmLogin(username, password);
        if(sqlUser.getPermissions() >= 0){
            this.id = sqlUser.id;
            this.login = sqlUser.login;
            this.permissions = sqlUser.permissions;
            this.firstName = sqlUser.firstName;
            this.lastName = sqlUser.lastName;
            return true;
        }
        return false;
    }
}
