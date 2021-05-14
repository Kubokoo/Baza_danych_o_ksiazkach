package com.JGSS.Projekt;

import java.io.*;
import java.util.LinkedList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class Servlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

//        LinkedList<JGuzytkownik> uzytkownicy;
//        ServletContext aplikacja = getServletContext();
//        HttpSession sesja = request.getSession();
//
//        if(aplikacja.getAttribute("uzytkownicy") == null){
//            uzytkownicy = new LinkedList<JGuzytkownik>();
//            uzytkownicy.add(new JGuzytkownik("user","","",-1, 1));
//            uzytkownicy.add(new JGuzytkownik("user0","","",-1, 0));
//            uzytkownicy.add(new JGuzytkownik("user1","","",-1, 0));
//            uzytkownicy.add(new JGuzytkownik("admin","","",-1, 2));
//            aplikacja.setAttribute("uzytkownicy", uzytkownicy);
//        }
//        else
//            uzytkownicy = (LinkedList<JGuzytkownik>) aplikacja.getAttribute("uzytkownicy");
//
//        JGuzytkownik uzytkownik = (JGuzytkownik) sesja.getAttribute("uzytkownik");
//        if(uzytkownik == null){
//            uzytkownik = new JGuzytkownik();
//            sesja.setAttribute("uzytkownik", uzytkownik);
//        }
//        else {
//            for (int i = 0; i < uzytkownicy.size(); i++){
//                if (uzytkownicy.get(i).getLogin().equals(uzytkownik.getLogin())){
//                    uzytkownik = uzytkownicy.get(i);
//                    break;
//                }
//            }
//        }
//
//        String komunikat = "Niepoprawny login lub hasło";
//
//        String kolorTla = (String) aplikacja.getAttribute("kolorTla");
//        if (kolorTla == null) kolorTla = "white";
//
//        String akcja = request.getParameter("akcja");
//        if (akcja == null) akcja = "";
//
//        if (akcja.equals("zaloguj")){
//            String login = request.getParameter("login");
//            String haslo = request.getParameter("haslo");
//
//            if (login == null) login = "";
//            if (haslo == null) haslo = "";
//
//            if((login.equals("user0")) & (haslo.equals("user0"))){
//                uzytkownik.setLogin(login);
//            }
//
//            if((login.equals("user1")) & (haslo.equals("user1"))){
//                uzytkownik.setLogin(login);
//            }
//
//            if((login.equals("user")) & (haslo.equals("user"))){
//                uzytkownik.setLogin(login);
//            }
//
//            if((login.equals("admin")) & (haslo.equals("admin"))){
//                uzytkownik.setLogin(login);
//            }
//
//            if(uzytkownik.getLogin() != ""){
//                for (int i = 0; i < uzytkownicy.size(); i++){
//                    if (uzytkownicy.get(i).getLogin().equals(uzytkownik.getLogin())){
//                        uzytkownik = uzytkownicy.get(i);
//                        sesja.setAttribute("uzytkownik", uzytkownik);
//                        break;
//                    }
//                }
//
//                if(uzytkownik.getUprawnienia() == 0)
//                    komunikat = "Zaostałeś zalogowany jako nieaktywny <b>" + login + "</b>";
//                else if(uzytkownik.getUprawnienia() == 1)
//                    komunikat = "Zaostałeś zalogowany jako aktywny <b>" + login + "</b>";
//                else if(uzytkownik.getUprawnienia() == 2)
//                    komunikat = "Zaostałeś zalogowany jako administrator <b>" + login + "</b>";
//            }
//        }
//        else if (akcja.equals("wyloguj")){
//            uzytkownik = new JGuzytkownik();
//            sesja.setAttribute("uzytkownik", uzytkownik);
//
//            komunikat = "Zostałeś prawidłowo wylogowany";
//        }
//        else if (akcja.equals("ustawienia")){
//            String imie = request.getParameter("imie");
//            String nazwisko = request.getParameter("nazwisko");
//            if (imie == null) imie = "";
//            if (nazwisko == null) nazwisko = "";
//            int wiek = Narzedzia.parsujInteger(request.getParameter("wiek"), -1);
//
//            uzytkownik.setImie(imie);
//            uzytkownik.setNazwisko(nazwisko);
//            uzytkownik.setWiek(wiek);
//
//            for (int i = 0; i < uzytkownicy.size(); i++){
//                if (uzytkownicy.get(i).getLogin().equals(uzytkownik.getLogin())){
//                    uzytkownicy.set(i, uzytkownik);
//                    sesja.setAttribute("uzytkownik", uzytkownik);
//                    break;
//                }
//            }
//            komunikat = "Ustawienia zostały prawidłowo zapisane";
//        }
//        else if (akcja.equals("administracja")){
//            if(uzytkownik.getUprawnienia() == 2){
//                String kolor = request.getParameter("kolor");
//                if (kolor == null){
//                    kolor = "white";
//                }
//
//                aplikacja.setAttribute("kolorTla", kolor);
//                kolorTla = kolor;
//                komunikat = "Kolor tła został ustawiony";
//            }
//            else
//                komunikat = "Brak uprawnień do zmiany koloru tła";
//        }
//        else if (akcja.equals("administracjaUrzytkownicy")){
//            if(uzytkownik.getUprawnienia() == 2){
//                uzytkownicy = (LinkedList<JGuzytkownik>) aplikacja.getAttribute("uzytkownicy");
//                for (int i = 0; i < uzytkownicy.size(); i++){
//                    int tmp = Narzedzia.parsujInteger(request
//                            .getParameter(uzytkownicy
//                                    .get(i)
//                                    .getLogin()),0);
//                    uzytkownicy
//                            .get(i)
//                            .setUprawnienia(tmp);
//                }
//                aplikacja.setAttribute("uzytkownicy", uzytkownicy);
//                komunikat = "Pomyślnie zmieniono uprawnienia";
//            }
//            else
//                komunikat = "Brak uprawnień do zarządzania urzytkownikami";
//        }
//        else
//            komunikat = "Nieprawidłowe wywołanie";
//
//        String szablon = Narzedzia.pobierzSzablon("komunikat.html", aplikacja);
//
//
//        out.println(szablon);
//        out.close();
    }

    public void destroy() {
    }
}