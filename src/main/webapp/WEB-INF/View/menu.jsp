<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<form method="get" action="index.jsp?page=searchResult">
    Numer IBAN: <input type="text" name="IBAN" minlength="10" maxlength="13"/>
    Nazwa: <input type="text" name="Name">
    Data wydania: <input type="date" name="Release date">
    Autor: <input type="text" name="Author">
    Wydawnictwo: <input type="text" name="Publishing house">
    <input type="submit" value="Wyszukaj">
</form>