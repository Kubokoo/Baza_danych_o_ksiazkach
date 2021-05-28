<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<jsp:useBean id="loggedUser" class="com.JGSS.Projekt.Classes.User" scope="session"/>
<%
    String action = "" + request.getParameter("action");
    if(action.equals("login") && loggedUser.getPermissions() != -1){ %>
        <h3>Zostałeś zalogowany jako ${sessionScope.loggedUser.login} o uprawnieniach ${sessionScope.loggedUser.permissions}.</h3>
<% } else if(action.equals("login") && loggedUser.getPermissions() == -1) { %>
        <h3>Błąd logowania</h3>
<% } else if(action.equals("logout")) { %>
        <h3>Zostałeś pomyślnie wylogowany</h3>
<%}%>