<%@ page import="com.JGSS.Projekt.Classes.SQL" %>
<%@ page import="java.util.LinkedList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<jsp:useBean id="columns" class="java.util.LinkedList" scope="application"/>
<jsp:useBean id="usersDB" class="com.JGSS.Projekt.Classes.SQL" scope="application"/>
<jsp:useBean id="booksDB" class="com.JGSS.Projekt.Classes.SQL" scope="application"/>
<jsp:useBean id="i" class="com.JGSS.Projekt.Classes.Counter"/>

<h3>Zarządzanie książkami:
    <a onclick="showTable(tableBookAdd)" style="text-decoration: underline">
        <img class="icon" src="Img/Add.png" alt="add">Dodaj nową kiążkę
    </a>
</h3>

<table id="tableBookAdd" style="display: none">
    <thead>
    <tr>
        <td>ISBN</td>
        <td>Title</td>
        <td>Release_Date</td>
        <td>Author</td>
        <td>Publishing_House</td>
        <td>OwnerID</td>
        <td>Akcje</td>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><input type="text"></td>
        <td><input type="text"/></td>
        <td><input type="date"/></td>
        <td><input type="text"/></td>
        <td><input type="text"/></td>
        <td><input type="number" min="0"/></td>
        <td>
            <button id="addBook" class="deleteButton" onclick="bookUserButton(this, 'addBook')">
                Dodaj
            </button>
        </td>
    </tr>
    </tbody>
</table>
<br/><br/>

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
        String valBook;
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
            <button id="changeBook_<%=book.get(0)%>" class="deleteButton"
                    onclick="bookUserButton(this, 'editBook')">
                Zmień
            </button>
            <label for="deleteBook_<%=book.get(0)%>">
                <img class="icon" alt="remove" src="Img/Trashcan.png">
            </label>
            <button id="deleteBook_<%=book.get(0)%>" class="deleteButton"
                    onclick="bookUserButton(this, 'deleteBook')">
                Usuń
            </button>
        </td>
        <% } %>
    </tr>
    </tbody>
</table>

<br/><br/>

<h3>Zarządzanie użytkownikami:
    <a onclick="showTable(tableUserAdd)" style="text-decoration: underline">
        <img class="icon" src="Img/Add.png" alt="Add"> Dodaj nowego użytkownika
    </a>
</h3>
<table id="tableUserAdd" style="display: none">
    <thead>
        <tr>
            <td style="display:none;">ID</td>
            <td>Username</td>
            <td>Password</td>
            <td>Permissions</td>
            <td>FirstName</td>
            <td>LastName</td>
            <td>Akcje</td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td style="display: none"><input type="number"></td>
            <td><input type="text"/></td>
            <td><input type="password"/></td>
            <td><input type="number" min="0" max="2"/></td>
            <td><input type="text"/></td>
            <td><input type="text"/></td>
            <td>
                <button type="button" id="addUser" class="deleteButton" onclick="bookUserButton(this, 'addUser')">
                    Dodaj
                </button>
            </td>
        </tr>
    </tbody>
</table>
<br/><br/>

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
                <button id="changeUser_<%=user.get(0)%>" class="deleteButton" onclick="bookUserButton(this, 'editUser')">
                    Zmień
                </button>
                <label for="deleteUser_<%=user.get(0)%>">
                    <img class="icon" alt="remove" src="Img/Trashcan.png">
                </label>
                <button id="deleteUser_<%=user.get(0)%>" class="deleteButton"
                        onclick="bookUserButton(this, 'deleteUser')">
                    Usuń
                </button>
            </td>
    <% } %>
    </tr>
    </tbody>
</table>