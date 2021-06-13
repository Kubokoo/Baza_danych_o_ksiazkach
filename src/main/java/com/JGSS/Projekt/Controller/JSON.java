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

    private void editUser(){
        int id = Integer.parseInt((String) json.get("Id"));
        String username = (String) json.get("Username");
        String password = (String) json.get("Password");
        int permissions = Integer.parseInt((String) json.get("Permissions"));
        String firstName = (String) json.get("FirstName");
        String lastName = (String) json.get("LastName");

        boolean result = usersdb.editUser(id, username, password, permissions, firstName, lastName);
        if(result){
            session.setAttribute("loggedUser", new User(id, username, permissions, firstName, lastName));
            out.write("Pomyślnie zmodyfikowano rekord");
        }
        else {
            out.write("Niepowodzenie zmiany rekordu");
        }
    }

    private void deleteUser(){
        int id = Integer.parseInt((String) json.get("Id"));

        boolean result = usersdb.deleteUser(id);
        if (result) {
            if(id == loggedUser.getId())
                session.setAttribute("loggedUser", new User(id));
            out.write("Pomyślnie usunięto użytkownika z bazy");
        } else {
            out.write("Niepowodzenie usunięcia użytkownika z bazy");
        }
    }

    private void deleteBook() {
        String ISBN = (String) json.get("ISBN");

        boolean result = booksDB.deleteBook(ISBN);
        if (result) {
//            session.setAttribute("loggedUser", new User(ISBN));
            out.write("Pomyślnie usunięto książkę z bazy");
        } else {
            out.write("Niepowodzenie usunięcia książki z bazy");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("aplication/json; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        session = request.getSession();
        ServletContext appContext = getServletContext();

        loggedUser = (User) session.getAttribute("loggedUser");
        if( loggedUser.getPermissions() == -1){
            loggedUser = new User(-1);
            session.setAttribute("loggedUser", loggedUser);
        }

        usersdb = (SQL) appContext.getAttribute("usersDB");
        if(usersdb.getConn() == null){
            usersdb = new SQL("usersDB.db");
            appContext.setAttribute("usersDB", usersdb);
        }

        booksDB = (SQL) appContext.getAttribute("booksDB");
        if(booksDB.getConn() == null){
            booksDB = new SQL("booksDB.db");
            appContext.setAttribute("booksDB", booksDB);
        }

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

        String changedBook = "";
        int changedUser = -1;
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

            case "deleteBook":
                changedBook = (String) json.get("ISBN");
                if(loggedUser.getPermissions() == 2
                        || (loggedUser.getPermissions() >= 1)) /* TODO dodać weryfikcaję kto jest autorem &&
                        changedBook.getAuthor == loggedUser.getId() */
                    deleteBook();
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