package com.JGSS.Projekt.Controller;

import com.JGSS.Projekt.Controller.SQL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.JGSS.Projekt.Classes.User;
import com.JGSS.Projekt.Classes.Book;

@WebServlet(name = "GetPost", value = "/GetPost")
public class GetPost extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        ServletContext appContext = getServletContext();

        String action = request.getParameter("action");
        if (action == null) action = "";

        if (action.equals("login")){
            String login = request.getParameter("Login");
            String password = request.getParameter("Password");

            if(login == null || password == null)
                session.setAttribute("loggedUser", null);
            else{
                User user = new User();
                user.loginUser(login, password);
                if(user != null){
                    session.setAttribute("loggedUser", user);
                }
                else{
                    session.setAttribute("loggedUser", new User(-1));
                }
            }
            response.sendRedirect(request.getContextPath() + "/index.jsp?page=login&action=login");
        }

//        out.println(json);
//        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void destroy() {
    }
}