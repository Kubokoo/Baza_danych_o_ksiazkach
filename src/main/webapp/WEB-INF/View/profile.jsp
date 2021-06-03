<%@ page import="com.JGSS.Projekt.Controller.SQL" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<jsp:useBean id="loggedUser" class="com.JGSS.Projekt.Classes.User" scope="session"/>
<jsp:useBean id="columns" class="java.util.LinkedList" scope="application"/>
<jsp:useBean id="usersDB" class="com.JGSS.Projekt.Controller.SQL" scope="application"/>
<jsp:useBean id="booksDB" class="com.JGSS.Projekt.Controller.SQL" scope="application"/>
<jsp:useBean id="i" class="com.JGSS.Projekt.Classes.Counter"/>
<h3>Zarządzanie profilem:</h3>
<table id="tableUsersBody">
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
    <tbody>
    <tr id="userID_${sessionScope.loggedUser.id}">
        <%
            String val;
            columns = usersDB.getCurrUser(loggedUser.getLogin());
            for(i.setI(0); i.getI() < columns.size(); i.setI(i.getI()+1)){
                val = (String) columns.get(i.getI());
                if(val == null || val.equals("null") || val.equals("****")) {
                    val = "";
                }
        %>
        <td><input type="text" value="<%= val %>"></td>
        <% } %>
        <td>
            <label
                    for="changeUser_${sessionScope.loggedUser.id}"><img class="icon" alt="edit" src="Img/Pencil.png"></label>
            <button id="changeUser_${sessionScope.loggedUser.id}" class="deleteButton" onclick="changeUser(changeUser_${sessionScope.loggedUser.id})">
                Zmień
            </button>
            <label for="deleteUser_${sessionScope.loggedUser.id}">
                <img class="icon" alt="remove" src="Img/Trashcan.png">
            </label>
            <button id="deleteUser_${sessionScope.loggedUser.id}" class="deleteButton" onclick="deleteUser(${sessionScope.loggedUser.id})">
                Usuń
            </button>
        </td>
    </tr>
    </tbody>
</table>