package com.JGSS.Projekt.Controller;

import com.JGSS.Projekt.Classes.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.JGSS.Projekt.Classes.Book;

@WebServlet(name = "JSON", value = "/JSON")
public class JSON extends HttpServlet {
    private void editUser(){

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("aplication/json; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        ServletContext appContext = getServletContext();
        String action = request.getParameter("action");
        if (action == null) action = "";
        String jsonText;

        SQL usersdb = (SQL) appContext.getAttribute("usersDB");

        try(BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
            jsonText = br.readLine();
        }

        JSONObject json = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        PrintWriter out = response.getWriter();

        if((jsonText != null) && !(jsonText.isEmpty())){
            try {
                json = (JSONObject) jsonParser.parse(jsonText);
            }
            catch (org.json.simple.parser.ParseException e){
                e.printStackTrace();
                json = new JSONObject();
            }
        }

        if(action.equals("editUser")){
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
        else if(action.equals("sugestions")){
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


        } else if(action.equals("deleteUser")){
            int id = Integer.parseInt((String) json.get("Id"));

            boolean result = usersdb.deleteUser(id);
            if(result){
                session.setAttribute("loggedUser", new User(id));
                out.write("Pomyślnie usunięto rekord");
            }
            else {
                out.write("Niepowodzenie usunięcia rekordu");
            }

        }

        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    public void destroy() {
    }
}