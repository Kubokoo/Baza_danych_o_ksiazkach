<%@ page import="com.JGSS.Projekt.Controller.SQL" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<jsp:useBean id="loggedUser" class="com.JGSS.Projekt.Classes.User" scope="session"/>
<jsp:useBean id="columns" class="java.util.LinkedList" scope="application"/>
<jsp:useBean id="usersDB" class="com.JGSS.Projekt.Controller.SQL" scope="application"/>
<jsp:useBean id="booksDB" class="com.JGSS.Projekt.Controller.SQL" scope="application"/>
<jsp:useBean id="i" class="com.JGSS.Projekt.Classes.Counter" scope="page"/>
<h3>Zarządzanie profilem:</h3>
<table>
    <thead>
    <tr>
        <%
            usersDB = (SQL) application.getAttribute("usersDB");
            columns = usersDB.columnsLabels();
            for(i.setI(0); i.getI() < columns.size(); i.setI(i.getI()+1)){
        %>
        <td><%=columns.get(i.getI())%></td>
        <% } %>
        <td>Akcje</td>
    </tr>

    </thead>
    <tbody id="tableUsersBody">
    <tr id="userID_${sessionScope.loggedUser.id}">
        <%
            columns = usersDB.getCurrUser(loggedUser.getLogin());
            for(i.setI(0); i.getI() < columns.size(); i.setI(i.getI()+1)){
        %>
        <td><input type="text" value="<%=columns.get(i.getI())%>"></td>
        <% } %>
        <td>
            <label for="changeUser_${sessionScope.loggedUser.id}"><img class="icon" src="Img/Pencil.png"></label>
            <button id="changeUser_${sessionScope.loggedUser.id}" class="deleteButton" onclick="changeUser(changeUser_${sessionScope.loggedUser.id})">
                Zmień
            </button>
            <a href="GetPost?page=profile&id=${sessionScope.loggedUser.id}&action=deleteUser">
                <img class="icon" src="Img/Trashcan.png"><button class="deleteButton">Usuń</button>
            </a>
        </td>
    </tr>
    </tbody>
</table>