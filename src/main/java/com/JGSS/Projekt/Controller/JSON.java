package com.JGSS.Projekt.Controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.JGSS.Projekt.Classes.Book;

@WebServlet(name = "JSON", value = "/JSON")
public class JSON extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("aplication/json; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        ServletContext appContext = getServletContext();
        String action = request.getParameter("action");
        if (action == null) action = "";

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String jsonText = "";
        if(br != null){
            jsonText = br.readLine();
        }

        JSONObject json = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        PrintWriter out = response.getWriter();

        if((jsonText != null) & !(jsonText.isEmpty())){
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

            SQL sql = new SQL("usersDB.db");
            boolean result = sql.editUser(id, username, password, permissions, firstName, lastName);
            out.write("Test1234");

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


        }

        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void destroy() {
    }
}