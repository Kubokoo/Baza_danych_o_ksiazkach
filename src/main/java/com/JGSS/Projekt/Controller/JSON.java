package com.JGSS.Projekt.Controller;

import com.JGSS.Projekt.Classes.SQL;
import com.JGSS.Projekt.Classes.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.nio.charset.StandardCharsets;
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
    private JSONObject json;

    String ISBN,title,release_Date,author,publishing_House;
    int ownerID = 0;

    String username,password, firstName, lastName;
    int id = 0, permissions = 0;

    private void editUser(){
        boolean result = usersdb.editUser(id, username, password, permissions, firstName, lastName);
        if(result){
            if(loggedUser.getId() == id)
                session.setAttribute("loggedUser", new User(id, username, permissions, firstName, lastName));
            out.write("Pomyślnie zmodyfikowano użytkownika");
        }
        else {
            out.write("Niepowodzenie modyfikacji użytkownika");
        }
    }

    private void deleteUser(){
        boolean result = usersdb.deleteUser(id);
        if (result) {
            if(id == loggedUser.getId())
                session.setAttribute("loggedUser", new User(id));
            out.write("Pomyślnie usunięto użytkownika z bazy");
        } else {
            out.write("Niepowodzenie usunięcia użytkownika z bazy");
        }
    }

    private void addUser(){
        boolean result = usersdb.addUser(username, password, permissions, firstName, lastName);
        if(result){
            out.write("Pomyślnie dodano użytkownika");
        }
        else {
            out.write("Niepowodzenie dodania użytkownika");
        }
    }

    private void deleteBook() {
        boolean result = booksDB.deleteBook(ISBN);
        if (result) {
            out.write("Pomyślnie usunięto książkę z bazy");
        } else {
            out.write("Niepowodzenie usunięcia książki z bazy");
        }
    }

    private void editBook(){
        boolean result = booksDB.editBook(ISBN, title, release_Date, author, publishing_House, ownerID);
        if(result){
            out.write("Pomyślnie zmodyfikowano książkę");
        }
        else {
            out.write("Niepowodzenie zmiany danych książki");
        }
    }

    private void addBook(){
        boolean result = booksDB.addBook(ISBN, title, release_Date, author, publishing_House, ownerID);
        if(result){
            out.write("Pomyślnie dodano książkę");
        }
        else {
            out.write("Niepowodzenie dodania książki");
        }
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
        String jsonText = null;

        try(BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
            jsonText = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        json = new JSONObject();
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

        String changedBook;
        int changedUser;
        switch (action) {
            case "editUser":
                changedUser = Integer.parseInt((String) json.get("Id"));
                if(loggedUser.getPermissions() == 2
                        || (loggedUser.getPermissions() >= 0 &&  changedUser == loggedUser.getId()))
                    editUser();
                break;

            case "sugestions":
                String query = (String) json.get("wartosc");
//        query = query.toUpperCase();

                String data = "";

                String akcja = request.getParameter("akcja");
                if (akcja == null) akcja = "";

                if (akcja.equals("zaloguj"))

//        ArrayList<String> sugestie = new ArrayList<>();
//        for (String samochod: lista){
//            if (samochod.startsWith(query)) {
//                sugestie.add(samochod);
//            }
//        }

                json.put("data", data);

//        out.println(json);
                break;

            case "deleteUser":
                changedUser = Integer.parseInt((String) json.get("Id"));
                if(loggedUser.getPermissions() == 2
                        || (loggedUser.getPermissions() >= 0 &&  changedUser == loggedUser.getId()))
                    deleteUser();
                break;

            case "addUser":
                if(loggedUser.getPermissions() == 2)
                    addUser();
                break;

            case "deleteBook":
                changedBook = (String) json.get("ISBN");
                if(loggedUser.getPermissions() == 2
                        || (loggedUser.getPermissions() >= 1)) /* TODO dodać weryfikcaję kto jest autorem &&
                        changedBook.getAuthor == loggedUser.getId() */
                    deleteBook();
                break;

            case "editBook":
                changedBook = (String) json.get("ISBN");
                if(loggedUser.getPermissions() == 2
                        || (loggedUser.getPermissions() >= 1)) /* TODO dodać weryfikcaję kto jest autorem &&
                        changedBook.getAuthor == loggedUser.getId() */
                    editBook();
                break;

            case "addBook":
                if(loggedUser.getPermissions() >= 1)
                    addBook();
                break;
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