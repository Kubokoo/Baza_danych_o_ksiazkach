<%@ page import="JGSS.Services.SQL" %>
<%@ page import="java.util.LinkedList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<jsp:useBean id="columns" class="java.util.LinkedList" scope="application"/>
<jsp:useBean id="usersDB" class="JGSS.Services.SQL" scope="application"/>
<jsp:useBean id="booksDB" class="JGSS.Services.SQL" scope="application"/>

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
            <button id="addBook" class="deleteButton" onclick="bookUserButton(addBook, 'addBook')">
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
            for(int i = 0; i < columns.size(); ++i){
        %>
        <td><%=columns.get(i)%></td>
        <% } %>
        <td>Akcje</td>
    </tr>

    </thead>
    <tbody>
    <%
        String valBook;
        LinkedList allBooks = booksDB.getAllBooks();
        for(int i = 0; i < allBooks.size(); ++i){
            LinkedList book = (LinkedList) allBooks.get(i);
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
                    onclick="bookUserButton(changeBook_<%=book.get(0)%>, 'editBook')">
                Zmień
            </button>
            <label for="deleteBook_<%=book.get(0)%>">
                <img class="icon" alt="remove" src="Img/Trashcan.png">
            </label>
            <button id="deleteBook_<%=book.get(0)%>" class="deleteButton"
                    onclick="bookUserButton('<%=book.get(0)%>', 'deleteBook')">
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
            <td><input type="number" min="0" max="3"/></td>
            <td><input type="text"/></td>
            <td><input type="text"/></td>
            <td>
                <button id="addUser" class="deleteButton" onclick="bookUserButton(addUser, 'addUser')">
                    Dodaj
                </button>
            </td>
        </tr>
    </tbody>
</table>
<br/><br/>

<jsp:useBean id="loggedUser" class="JGSS.Models.User" scope="session"/>
<table id="tableUsersBody">
    <thead>
    <tr>
        <%
            usersDB = (SQL) application.getAttribute("usersDB");
            columns = usersDB.columnsLabels();
            for(int i = 0; i < columns.size(); ++i){
        %>
        <td><%=columns.get(i)%></td>
        <% } %>
        <td>Akcje</td>
    </tr>

    </thead>
    <tbody>
    <%
        String valUser = "";
        LinkedList allUsers = usersDB.getAllUsers();
            for(int i = 0; i < allUsers.size(); ++i){
            LinkedList user = (LinkedList) allUsers.get(i);
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
                <button id="changeUser_<%=user.get(0)%>" class="deleteButton" onclick="bookUserButton(changeUser_<%=user.get(0)%>, 'editUser')">
                    Zmień
                </button>
                <label for="deleteUser_<%=user.get(0)%>">
                    <img class="icon" alt="remove" src="Img/Trashcan.png">
                </label>
                <button id="deleteUser_<%=user.get(0)%>" class="deleteButton"
                        onclick="bookUserButton(<%=user.get(0)%>, 'deleteUser')">
                    Usuń
                </button>
            </td>
    <% } %>
    </tr>
    </tbody>
</table>