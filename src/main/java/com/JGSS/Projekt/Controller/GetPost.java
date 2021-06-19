package com.JGSS.Projekt.Controller;

import java.io.*;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.JGSS.Projekt.Classes.SQL;
import com.JGSS.Projekt.Classes.User;

@WebServlet(name = "GetPost", value = "/GetPost")
public class GetPost extends HttpServlet {
    private User loggedUser;
    private SQL usersdb;
    private SQL booksDB;
    private HttpSession session;
    private PrintWriter out;
    private ServletContext appContext;
    private HttpServletRequest request;
    private HttpServletResponse response;

    private void login(){
        String login = request.getParameter("Login");
        String password = request.getParameter("Password");

        if(login == null || password == null)
            session.setAttribute("loggedUser", new User(-1));
        else{
            User user = new User(-1);
            boolean result = user.loginUser(login, password, usersdb);
            if(result){
                session.setAttribute("loggedUser", user);
            }
            else{
                session.setAttribute("loggedUser", new User(-1));
            }
        }
        try {
            response.sendRedirect(request.getContextPath() + "/index.jsp?page=login&action=login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logout(){
        User user = new User(-1);
        session.setAttribute("loggedUser", user);

        try {
            response.sendRedirect(request.getContextPath() + "/index.jsp?page=logout&action=logout");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        session = request.getSession();
        appContext = getServletContext();
        this.request = request;
        this.response = response;


        loggedUser = (User) session.getAttribute("loggedUser");
        usersdb = (SQL) appContext.getAttribute("usersDB");
        booksDB = (SQL) appContext.getAttribute("booksDB");

        String action = "" + request.getParameter("action");

        switch (action) {
            case "login":
                login();
                break;

            case "logout":
                logout();
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }

    public void destroy() {
    }
}