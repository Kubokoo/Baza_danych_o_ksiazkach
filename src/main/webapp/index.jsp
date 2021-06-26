<%@ page import="com.JGSS.Projekt.Classes.User" %>
<%@ page import="com.JGSS.Projekt.Classes.SQL" %>
<%@ page import="com.JGSS.Projekt.Classes.Narzedzia" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<jsp:useBean id="loggedUser" class="com.JGSS.Projekt.Classes.User" scope="session"/>
<jsp:useBean id="usersdb" class="com.JGSS.Projekt.Classes.SQL" scope="application"/>
<jsp:useBean id="booksDB" class="com.JGSS.Projekt.Classes.SQL" scope="application"/>
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
    loggedUser = (User) session.getAttribute("loggedUser");
    if(loggedUser.getPermissions() == -1){
        loggedUser = new User(-1);
        session.setAttribute("loggedUser", loggedUser);
    }

    usersdb = (SQL) application.getAttribute("usersDB");
    if(usersdb == null || usersdb.getConn() == null){
        usersdb = new SQL("usersDB.db");
        application.setAttribute("usersDB", usersdb);
    }

    booksDB = (SQL) application.getAttribute("booksDB");
    if(booksDB == null || booksDB.getConn() == null){
        booksDB = new SQL("booksDB.db");
        application.setAttribute("booksDB", booksDB);
    }

    String pageString = request.getParameter("page");
    if (request.getParameter("ISBN") != null) pageString = "searchResult";
    if (pageString == null) pageString = "main";

    switch (loggedUser.getPermissions()){
        case 0:
            pageString = Narzedzia
                    .filtrujStrone(pageString, "profile;login;logout;searchResult;main;");
            break;
        case 1:
            pageString = Narzedzia
                    .filtrujStrone(pageString, "profile;login;logout;searchResult;main;browseBooks;");
            break;
        case 2:
            pageString = Narzedzia
                    .filtrujStrone(pageString, "profile;login;logout;searchResult;main;browseBooks;admin;");
            break;
        default:
            pageString = Narzedzia
                    .filtrujStrone(pageString, "main;searchResult;logout;");
            break;
    }
%>
<body>
    <div id="container">
        <jsp:include page="/WEB-INF/View/header.jsp"/>
        <jsp:include page="/WEB-INF/View/menu.jsp" />
        <div id="srodek">
            <div id="tresc">
                <jsp:include page="/WEB-INF/View/content.jsp">
                    <jsp:param name="web_page" value="<%=pageString%>"/>
                </jsp:include>
            </div>
        </div>
        <div id="footer">
            <jsp:include page="/WEB-INF/View/footer.jsp" />
        </div>
    </div>
</body>
</html>