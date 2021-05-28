<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<jsp:useBean id="loggedUser" class="com.JGSS.Projekt.Classes.User" scope="session"/>
<div id="header">
    <div id="banner">
        <h1><a href="index.jsp"><img src="Img/Book.png" alt="Book"> Baza danych o książkach</a></h1>
    </div>
    <% if(loggedUser.getPermissions() == -1) { %>
    <form class="login_outForm" method="post" action="GetPost?page=login&action=login">
        Login: <input type="text" name="Login">
        Hasło: <input type="password" name="Password">
        <input type="submit" value="Zaloguj">
    </form>
    <div style="clear:both"></div>
    <% } else { %>
    <h3 style="text-align: right">
        <a href="index.jsp?page=profile"> (${sessionScope.loggedUser.login})</a>
        <form class="loginForm" method="post" action="/GetPost?page=login&action=logout"><input type="submit" value="wyloguj"></form>
    </h3>
    <div style="clear:both"></div>
    <% } %>
</div>
