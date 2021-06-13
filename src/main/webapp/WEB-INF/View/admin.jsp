<%@ page import="com.JGSS.Projekt.Classes.SQL" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="com.JGSS.Projekt.Classes.Book" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<jsp:useBean id="columns" class="java.util.LinkedList" scope="application"/>
<jsp:useBean id="usersDB" class="com.JGSS.Projekt.Classes.SQL" scope="application"/>
<jsp:useBean id="booksDB" class="com.JGSS.Projekt.Classes.SQL" scope="application"/>
<jsp:useBean id="i" class="com.JGSS.Projekt.Classes.Counter"/>
<h3>Zarządzanie książkami: <a href="index.jsp?page=admin&action=addBook"><img class="icon" src="Img/Add.png">Dodaj nową kiążkę</a></h3>
<table id="tableBooksBody">
    <thead>
    <tr>
        <%
            booksDB = (SQL) application.getAttribute("booksDB");
            columns = booksDB.columnsLabels();
            for(i.setI(0); i.getI() < columns.size(); i.setI(i.getI()+1)){
        %>
        <td><%=columns.get(i.getI())%></td>
        <% } %>
        <td>Akcje</td>
    </tr>

    </thead>
    <tbody>
    <%
        String valBook = "";
        LinkedList allBooks = booksDB.getAllBooks();
        for(i.setI(0); i.getI() < allBooks.size(); i.setI(i.getI()+1)){
            LinkedList book = (LinkedList) allBooks.get(i.getI());
    %>
    <tr id="bookID_<%=book.get(0)%>">
        <%

            for (int j = 0; j < book.size(); j++ ){
                valBook = (String) book.get(j);
                if(valBook == null || valBook.equals("null") || valBook.equals("****")) {
                    valBook = "";
                }

        %>
        <td><input type="text" value="<%= valBook %>"></td>
        <% } %>
        <td>
            <label
                    for="changeBook_<%=book.get(0)%>"><img class="icon" alt="edit" src="Img/Pencil.png"></label>
            <button id="changeBook_<%=book.get(0)%>" class="deleteButton" onclick="changeUser(changeUser_<%=book.get(0)%>)">
                Zmień
            </button>
            <label for="deleteBook_<%=book.get(0)%>">
                <img class="icon" alt="remove" src="Img/Trashcan.png">
            </label>
            <button id="deleteBook_<%=book.get(0)%>" class="deleteButton" onclick="deleteBook('<%=book.get(0)%>')">
                Usuń
            </button>
        </td>
        <% } %>
    </tr>
    </tbody>
</table>

<br/><br/>

<h3>Zarządzanie użytkownikami:
    <a href="index.jsp?page=admin&action=addUser"><img class="icon" src="Img/Add.png">Dodaj nowego użytkownika</a>
</h3>

<jsp:useBean id="loggedUser" class="com.JGSS.Projekt.Classes.User" scope="session"/>
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
    <%
        String valUser = "";
        LinkedList allUsers = usersDB.getAllUsers();
        for(i.setI(0); i.getI() < allUsers.size(); i.setI(i.getI()+1)){
            LinkedList user = (LinkedList) allUsers.get(i.getI());
    %>
    <tr id="userID_<%=user.get(0)%>">
        <%

                for (int j = 0; j < user.size(); j++ ){
                    valUser = (String) user.get(j);
                    if(valUser == null || valUser.equals("null") || valUser.equals("****")) {
                        valUser = "";
                    }

        %>
        <td><input type="text" value="<%= valUser %>"></td>
        <% } %>
            <td>
                <label
                        for="changeUser_<%=user.get(0)%>"><img class="icon" alt="edit" src="Img/Pencil.png"></label>
                <button id="changeUser_<%=user.get(0)%>" class="deleteButton" onclick="changeUser(changeUser_<%=user.get(0)%>)">
                    Zmień
                </button>
                <label for="deleteUser_<%=user.get(0)%>">
                    <img class="icon" alt="remove" src="Img/Trashcan.png">
                </label>
                <button id="deleteUser_<%=user.get(0)%>" class="deleteButton" onclick="deleteUser(<%=user.get(0)%>)">
                    Usuń
                </button>
            </td>
    <% } %>
    </tr>
    </tbody>
</table>