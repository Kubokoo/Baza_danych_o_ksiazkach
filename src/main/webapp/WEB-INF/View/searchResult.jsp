<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="com.JGSS.Projekt.Classes.SQL" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="java.io.IOException" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="org.json.simple.parser.JSONParser" %>
<jsp:useBean id="loggedUser" class="com.JGSS.Projekt.Classes.User" scope="session"/>
<jsp:useBean id="columns" class="java.util.LinkedList" scope="application"/>
<jsp:useBean id="booksDB" class="com.JGSS.Projekt.Classes.SQL" scope="application"/>
<jsp:useBean id="i" class="com.JGSS.Projekt.Classes.Counter"/>
<h3>Wyniki wyszukiwania:</h3>
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

        String ISBN = (String) request.getParameter("ISBN");
        String title = (String) request.getParameter("Title");
        String release_Date = (String) request.getParameter("Release_Date");
        String author = (String) request.getParameter("Author");
        String publishing_House = (String) request.getParameter("Publishing_House");

        LinkedList allBooks = booksDB.getSearchBooks(ISBN, title, release_Date, author, publishing_House, false);
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