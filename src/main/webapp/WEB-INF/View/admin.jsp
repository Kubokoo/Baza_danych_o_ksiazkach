<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>

<jsp:useBean id="book" class="com.JGSS.Projekt.Classes.Book" scope="page"/>
<h3>Zarządzanie książkami: <a href="index.jsp?page=admin&action=addBook"><img class="icon" src="Img/Add.png">Dodaj nową kiążkę</a></h3>
<table>
    <thead>
    <tr>
        <td>Kolumny z bazy danych</td>
        <td>Akcje</td>
    </tr>
    </thead>
    <tbody id="tableBrowseBody">
    <tr id="bookID_X">
        <form method="post" action="index.jsp?page=admin&id=x&action=editBook">
            <td>
                <input type="text" value="Dane z bazy danych">
            </td>
            <td>
                <label for="changeBook_x"><img class="icon" src="Img/Pencil.png"></label>
                <input type="submit" id="changeBook_x" value="Zmień">,
        </form>
                <a href="index.jsp?page=admin&id=x&action=deleteBook">
                    <img class="icon" src="Img/Trashcan.png"><button class="deleteButton">Usuń</button>
                </a>
            </td>
        </form>
    </tr>
    </tbody>
</table>

<jsp:useBean id="loggedUser" class="com.JGSS.Projekt.Classes.User" scope="session"/>
<h3>Zarządzanie użytkownikami: <a href="index.jsp?page=admin&action=addUser"><img class="icon" src="Img/Add.png">Dodaj
    nowego
    użytkownika</a></h3>
<table>
    <thead>
    <tr>
        <td>Kolumny z bazy danych</td>
        <td>Akcje</td>
    </tr>
    </thead>
    <tbody id="tableUsersBody">
    <tr id="userID_X">
        <form method="post" action="index.jsp?page=admin&id=x&action=editUser">
            <td>
                <input type="text" value="Dane z bazy danych">
            </td>
            <td>
                <label for="changeUser_x"><img class="icon" src="Img/Pencil.png"></label>
                <input type="submit" id="changeUser_x" value="Zmień">
        </form>
                <a href="index.jsp?page=admin&id=x&action=deleteUser">
                    <img class="icon" src="Img/Trashcan.png"><button class="deleteButton">Usuń</button>
                </a>
            </td>

    </tr>
    </tbody>
</table>


