<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<jsp:useBean id="book" class="JGSS.Models.Book" scope="page"/>
<h3>Wyniki wyszukiwania:</h3>
<table>
    <thead>
    <tr>
        <td>Kolumny z bazy danych</td>
        <td>Akcje</td>
    </tr>
    </thead>
    <tbody id="tableBrowseBody">
    <tr id="bookID_X">
        <form method="post" action="index.jsp?page=browseBooks&id=x&action=editBook">
            <td>
                Dane z bazy danych <%-- <input type="text" value="Dane z bazy danych"> Dla usera z uprawnieniami --%>
            </td>
            <td><%-- Jeśli user ma uprawnienia ponieważ książka jest jego lub jest adminem to może nimi zarządzać z tego menu --%>
                <label for="changeBook_x"><img class="icon" src="Img/Pencil.png"></label>
                <input type="submit" id="changeBook_x" value="Zmień">,
        </form>
                <a href="index.jsp?page=browseBooks&id=x&action=deleteBook">
                    <img class="icon" src="Img/Trashcan.png"><button class="deleteButton">Usuń</button>
                </a>
            </td>
        </form>
    </tr>
    </tbody>
</table>