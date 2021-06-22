<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<jsp:useBean id="loggedUser" class="JGSS.Models.User" scope="session"/>
<%
  String action = "" + request.getParameter("action");
  if(action.equals("logout") && loggedUser.getPermissions() == -1) {
%>
    <h3>Zostałeś pomyślnie wylogowany</h3>
<% } else { %>
    <h3>Wylogowywanie niepomyślne</h3>
<% } %>
