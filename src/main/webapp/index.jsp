<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="icon" type="image/png" href="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABABAMAAABYR2ztAAAABGdBTUEAALGPC/xhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAACXBIWXMAAA7EAAAOxAGVKw4bAAAAB3RJTUUH4QgMCyQZ8rwweQAAACV0RVh0ZGF0ZTpjcmVhdGUAMjAxNy0wOC0xMlQxMTozNjoyNCswMDowMHQIn/EAAAAldEVYdGRhdGU6bW9kaWZ5ADIwMTctMDgtMTJUMTE6MzY6MjQrMDA6MDAFVSdNAAAAJFBMVEUySl6IxcyJxcyKxs2Mx86Nx86Pyc/Rs1zs8PH+/v7/0Fv///+ez9hHAAAAAWJLR0QLH9fEwAAAAPxJREFUSMfN1TsOwyAMBmBvfUz86sjReoSeolvXjhmzslF143IdMAkkNpAqquoRPtkKBJtCHgAAFEu02l4QkvZzMQGPIuwKYBFLACiCxAJZEdL2kyClwFSE1AScgvQEMQVxAkPkikgpEqAaAACiwxogAh/BWQC2D0AEI9cgPgQF2F0A4jHJADpwCfg6sL8AqAPogD90L0BfAbcVuD8GXgfjfJsdYBBi/h/MadBBAGAGFYQKeDLwNcAvyzykuDN418A1Pn8ZPPj5B6+D1GHMsQqCfpJTGzTyXeR9Uu3FXa243cyb46A9UJojqT3U2mMxvMr92/bR3DHcE7kUSx/KXzjAZcPhIgAAAABJRU5ErkJggg=="/>
    <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="author" content="Jakub Gwiazda, Szymon Stuglik">
    <title>Projekt - Gwiazda, Stuglik</title>
    <link rel="stylesheet" type="text/css" href="CSS/style.css"/>
    <script type="text/javascript" src="JS/main.js"></script>
</head>
<%
    String pageString = request.getParameter("page");
    if (request.getParameter("IBAN") != null) pageString = "searchResult";
    if (pageString == null) pageString = "main";
%>
<body>
<jsp:useBean id="user" class="com.JGSS.Projekt.User" scope="session"/>
<div id="kontener">
    <div id="header">
        <jsp:include page="/WEB-INF/View/header.jsp"/>
    </div>
    <div id="left">
        <div id="menu">
            <jsp:include page="/WEB-INF/View/menu.jsp" />
        </div>
        <div id="newsy">
            <ul>
                <li><a href="index.jsp?page=browse">Zarządzaj swoimi książkami</a></li>
                <li><a href="index.jsp?page=admin">Zarządzaj użytkownikami</a></li>
            </ul>
        </div>
    </div>
    <div id="srodek">
        <div id="tresc">
            <jsp:include page="/WEB-INF/View/content.jsp">
                <jsp:param name="web_page" value="<%=pageString%>"/>
            </jsp:include>
        </div>
    </div>
    <div id="stopka">
        <jsp:include page="/WEB-INF/View/footer.jsp" />
    </div>
</div>
</body>
</html>