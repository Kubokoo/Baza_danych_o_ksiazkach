<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<jsp:useBean id="loggedUser" class="com.JGSS.Projekt.Classes.User" scope="session"/>
<%
    String pageString = "" + request.getParameter("action");
    if(pageString.equals("login")){ %>
        <h3>Zostałeś zalogowany jako ${sessionScope.loggedUser.login} o uprawnieniach ${sessionScope.loggedUser.permissions}.</h3>
    <%
    } else { %>
        <h3>Zostałeś pomyślnie wylogowany</h3>
    <%}%>