package com.JGSS.Projekt;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String login;
    private String password;
//    private Map<Integer, String> permissons = new HashMap<>();

    public User(){
//        permissons.put(0, "non-user");
//        permissons.put(1, "user");
//        permissons.put(2, "admin");
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

//    public Map<Integer, String> getPermissons() {
//        return permissons;
//    }
//
//    public void setPermissons(Map<Integer, String> permissons) {
//        this.permissons = permissons;
//    }
}
