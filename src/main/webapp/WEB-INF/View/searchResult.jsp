<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="com.JGSS.Projekt.Classes.SQL" %>
<%@ page import="java.util.LinkedList" %>
<jsp:useBean id="loggedUser" class="com.JGSS.Projekt.Classes.User" scope="session"/>
<jsp:useBean id="columns" class="java.util.LinkedList" scope="application"/>
<jsp:useBean id="booksDB" class="com.JGSS.Projekt.Classes.SQL" scope="application"/>
<h3>Wyniki wyszukiwania:</h3>
<table id="tableBooksBody">
    <thead>
    <tr>
        <%
            booksDB = (SQL) application.getAttribute("booksDB");
            columns = booksDB.columnsLabels();
            for(int i = 0; i < columns.size(); i++){
        %>
        <td><%=columns.get(i)%></td>
        <% } %>
        <td>Akcje</td>
    </tr>

    </thead>
    <tbody>
    <%
        String valBook;

        String ISBN = request.getParameter("ISBN");
        String title = request.getParameter("Title");
        String release_Date = request.getParameter("Release_Date");
        String author = request.getParameter("Author");
        String publishing_House = request.getParameter("Publishing_House");

        LinkedList allBooks = booksDB.getSearchBooks(ISBN, title, release_Date, author, publishing_House, false);
        for(int i = 0; i < allBooks.size(); i++){
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