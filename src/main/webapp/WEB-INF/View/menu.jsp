<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<jsp:useBean id="loggedUser" class="JGSS.Models.User" scope="session"/>
<div id="left">
    <div id="menu">
        <form method="get" action="index.jsp?page=searchResult">
            Numer ISBN: <input type="text" placeholder="000-00-0000-000-0" pattern="(?:(?=.{17}$)97[89][ -](?:[0-9]+[ -]){2}[0-9]+[ -][0-9]|97[89][0-9]{10}|(?=.{13}$)(?:[0-9]+[ -]){2}[0-9]+[ -][0-9Xx]|[0-9]{9}[0-9Xx])" name="IBAN" id="IBAN" minlength="14" maxlength="17" onkeyup="getSugestions('ISBN');">
            <div id="ISBN_Result"></div>
            Nazwa: <input type="text" name="Title" id="Title" onkeyup="getSugestions('Title')">
            <div id="Title_Result"></div>
            Data wydania: <input type="date" name="Release date" id="Release_Date">
            Autor: <input type="text" name="Author" id="Author" onkeyup="getSugestions('Author')">
            <div id="Author_Result"></div>
            Wydawnictwo: <input type="text" name="Publishing house" id="Publisihng_House" onkeyup="getSugestions('Publisihng_House')">
            <div id="Publisihng_House_Result"></div>
            <input type="submit" value="Wyszukaj">
        </form>
    </div>
    <div id="newsy">
        <ul>
            <% if(loggedUser.getPermissions() == 1 || loggedUser.getPermissions() == 2) { %>
                <li><a href="index.jsp?page=browseBooks">Zarządzaj swoimi książkami</a></li>
            <% } if(loggedUser.getPermissions() == 2) { %>
                <li><a href="index.jsp?page=admin">Administracja</a></li>
            <% } %>
        </ul>
    </div>
</div>