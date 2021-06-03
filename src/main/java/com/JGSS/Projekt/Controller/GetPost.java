package com.JGSS.Projekt.Controller;

import com.JGSS.Projekt.Controller.SQL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
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

        SQL usersdb = (SQL) appContext.getAttribute("usersDB");

        String action = request.getParameter("action");
        if (action == null) action = "";

        if (action.equals("login")){
            String login = request.getParameter("Login");
            String password = request.getParameter("Password");

            if(login == null || password == null)
                session.setAttribute("loggedUser", null);
            else{
                User user = new User(-1);
                user.loginUser(login, password, usersdb);
                if(user != null){
                    session.setAttribute("loggedUser", user);
                }
                else{
                    session.setAttribute("loggedUser", new User(-1));
                }
            }
            response.sendRedirect(request.getContextPath() + "/index.jsp?page=login&action=login");
        }

        if (action.equals("logout")){
            User user = new User(-1);
            session.setAttribute("loggedUser", user);

            response.sendRedirect(request.getContextPath() + "/index.jsp?page=logout&action=logout");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void destroy() {
    }
}