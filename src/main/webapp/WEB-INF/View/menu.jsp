<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<form method="get" action="index.jsp?page=searchResult">
    Numer IBAN: <input type="text" name="IBAN" id="IBAN" minlength="10" maxlength="13" onkeyup="getSugestions('IBAN')"/>
    <div id="IBAN_Result"></div>
    Nazwa: <input type="text" name="Name" id="Name" onkeyup="getSugestions('Name')">
    <div id="Name_Result"></div>
    Data wydania: <input type="date" name="Release date" id="Release_Date">
    Autor: <input type="text" name="Author" id="Author" onkeyup="getSugestions('Author')">
    <div id="Author_Result"></div>
    Wydawnictwo: <input type="text" name="Publishing house" id="Publisihng_House" onkeyup="getSugestions('Publisihng_House')">
    <div id="Publisihng_House_Result"></div>
    <input type="submit" value="Wyszukaj">
</form>