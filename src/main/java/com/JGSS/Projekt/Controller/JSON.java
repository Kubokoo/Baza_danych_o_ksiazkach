package com.JGSS.Projekt.Controller;

import com.JGSS.Projekt.Classes.SQL;
import com.JGSS.Projekt.Classes.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "JSON", value = "/JSON")
public class JSON extends HttpServlet {
    private User loggedUser;
    private SQL usersdb;
    private SQL booksDB;
    private HttpSession session;
    private PrintWriter out;
    private String action;

    private void hintHandler(JSONObject json, HttpServletResponse response
            , String ISBN, String title, String release_Date
            , String author, String publishing_House){
        LinkedList<LinkedList> books
                = booksDB.getSearchBooks(ISBN, title, release_Date, author, publishing_House, true);

        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        json.put("hint", books);

        out.write(json.toJSONString());
    }

    private void editUser(int id, String username, String password, int permissions
            , String firstName, String lastName){
        boolean result = usersdb.editUser(id, username, password, permissions, firstName, lastName);
        if(result){
            if(loggedUser.getId() == id)
                session.setAttribute("loggedUser", new User(id, username, permissions, firstName, lastName));
            String JSONResponse = "{\"Response\": \"" + "Pomyślnie zmodyfikowano użytkownika" + "\"}";
            out.write(JSONResponse);
        }
        else {
            String JSONResponse = "{\"Response\": \"" + "Niepowodzenie modyfikacji użytkownika" + "\"}";
            out.write(JSONResponse);
        }
    }

    private void deleteUser(int id){
        boolean result = usersdb.deleteUser(id);
        if (result) {
            if(id == loggedUser.getId())
                session.setAttribute("loggedUser", new User(-1));
            String JSONResponse = "{\"Id\": \"" + id
                    + "\", \"Action\": \"" + action
                    + "\",\"Response\": \"" + "Pomyślnie usunięto użytkownika z bazy" + "\"}";
            out.write(JSONResponse);
        } else {
            String JSONResponse = "{\"Response\": \"" + "Niepowodzenie usunięcia użytkownika z bazy" + "\"}";
            out.write(JSONResponse);
        }
    }

    private void addUser(int id, String username,String password,int permissions,String firstName,String lastName){
        boolean result = usersdb.addUser(username, password, permissions, firstName, lastName);
        String JSONResponse;
        if(result){
            JSONResponse = "{\"Id\": \"" + id
                    + "\", \"Action\": \"" + action
                    + "\",\"Response\": \"" + "Pomyślnie dodano użytkownika" + "\"}";
        }
        else {
            JSONResponse = "{\"Response\": \"" + "Niepowodzenie dodania użytkownika" + "\"}";
        }
        out.write(JSONResponse);
    }

    private void deleteBook(String ISBN) {
        boolean result = booksDB.deleteBook(ISBN);
        String JSONResponse;
        if (result) {
            JSONResponse = "{\"Id\": \"" + ISBN
                    + "\", \"Action\": \"" + action
                    + "\",\"Response\": \"" + "Pomyślnie usunięto książkę z bazy" + "\"}";
        } else {
            JSONResponse = "{\"Response\": \"" + "Niepowodzenie usunięcia książki z bazy" + "\"}";
        }
        out.write(JSONResponse);
    }

    private void editBook(String ISBN,String title,String release_Date
            ,String author,String publishing_House,int ownerID){
        boolean result = booksDB.editBook(ISBN, title, release_Date, author, publishing_House, ownerID);
        String JSONResponse;
        if(result){
            JSONResponse = "{\"Response\": \"" + "Pomyślnie zmodyfikowano książkę" + "\"}";
        }
        else {
            JSONResponse = "{\"Response\": \"" + "Niepowodzenie zmiany danych książki" + "\"}";
        }
        out.write(JSONResponse);
    }

    private void addBook(String ISBN,String title,String release_Date
            ,String author,String publishing_House,int ownerID){
        boolean result = booksDB.addBook(ISBN, title, release_Date, author, publishing_House, ownerID);
        String JSONResponse;
        if(result){
            JSONResponse = "{\"Id\": \"" + ISBN
                    + "\", \"Action\": \"" + action
                    + "\",\"Response\": \"" + "Pomyślnie dodano książkę" + "\"}";
        }
        else {
            JSONResponse = "{\"Response\": \"" + "Niepowodzenie dodania książki" + "\"}";
        }
        out.write(JSONResponse);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("aplication/json; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        session = request.getSession();
        ServletContext application = getServletContext();

        loggedUser = (User) session.getAttribute("loggedUser");
        usersdb = (SQL) application.getAttribute("usersDB");
        booksDB = (SQL) application.getAttribute("booksDB");

        String action = request.getParameter("action");
        if (action == null) action = "";

        this.action = action;
        String jsonText = null;

        try(BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
            jsonText = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if((jsonText != null) && !(jsonText.isEmpty())){
            try {
                json = (JSONObject) jsonParser.parse(jsonText);
            }
            catch (org.json.simple.parser.ParseException e){
                e.printStackTrace();
                json = new JSONObject();
            }
        }

        String ISBN,title,release_Date,author,publishing_House;
        int ownerID = 0;

        String username,password, firstName, lastName;
        int id = 0, permissions = 0;

        ISBN = (String) json.get("ISBN");
        title = (String) json.get("Title");
        release_Date = (String) json.get("Release_Date");
        author = (String) json.get("Author");
        publishing_House = (String) json.get("Publishing_House");
        username = (String) json.get("Username");
        password = (String) json.get("Password");
        firstName = (String) json.get("FirstName");
        lastName = (String) json.get("LastName");

        String tmp;

        tmp = (String) json.get("Permissions");
        if(tmp != null && !tmp.equals(""))
            permissions = Integer.parseInt((String) json.get("Permissions"));

        tmp = (String) json.get("Id");
        if(tmp != null && !tmp.equals(""))
            id = Integer.parseInt((String) json.get("Id"));

        tmp = (String) json.get("OwnerID");
        if(tmp != null && !tmp.equals(""))
            ownerID = Integer.parseInt((String) json.get("OwnerID"));

        int changedUser;
        boolean permisions = false;
        switch (action) {
            case "hintHandler":
                hintHandler(json, response,ISBN, title, release_Date, author, publishing_House);
                permisions = true;
                break;

            case "editUser":
                if(loggedUser.getPermissions() == 2
                        || (loggedUser.getPermissions() >= 0 && id == loggedUser.getId())){
                    editUser(id, username, password, permissions, firstName, lastName);
                    permisions = true;
                }
                break;

            case "deleteUser":
                changedUser = Integer.parseInt((String) json.get("Id"));
                if(loggedUser.getPermissions() == 2
                        || (loggedUser.getPermissions() >= 0 && changedUser == loggedUser.getId())){
                    deleteUser(id);
                    permisions = true;
                }
                break;

            case "addUser":
                if(loggedUser.getPermissions() == 2){
                    addUser(id, username, password, permissions, firstName, lastName);
                    permisions = true;
                }

                break;

            case "deleteBook":
                if(loggedUser.getPermissions() == 2
                        || (loggedUser.getPermissions() == 1 && loggedUser.getId() == ownerID)){
                    deleteBook(ISBN);
                    permisions = true;
                }
                break;

            case "editBook":
                if(loggedUser.getPermissions() == 2
                        || (loggedUser.getPermissions() == 1 && loggedUser.getId() == ownerID)){
                    editBook(ISBN, title, release_Date, author, publishing_House, ownerID);
                    permisions = true;
                }
                break;

            case "addBook":
                if(loggedUser.getPermissions() == 1){
                    ownerID = loggedUser.getId();
                    addBook(ISBN, title, release_Date, author, publishing_House, ownerID);
                    permisions = true;
                }
                else if (loggedUser.getPermissions() == 2){
                    addBook(ISBN, title, release_Date, author, publishing_House, ownerID);
                    permisions = true;
                }
                break;
        }
        if(!permisions){
            out.write("{\"Response\": \"" + "Niewystraczające uprawninia do wykonania tej operacji" + "\"}");
        }

        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        doGet(request, response);
    }

    public void destroy() {
    }
}