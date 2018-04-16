<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="parts/head.jsp"/>
    <title>${title}</title>
</head>
<header>
    <jsp:include page="parts/header.jsp"/>
</header>
<body>
<div class="info-block">
    <div>
        <h4>Your information:</h4>
        <p>Login name: ${user.username}</p>
        <p>Login role: ${user.role}</p>
    </div>
    <ul >
        <li><a href="javascript:" id="orders"> Orders</a> </li>
        <li><a href="javascript:" id="groupOrders"> Group orders</a> </li>
        <li><a href="javascript:" id="groups"> Groups</a> </li>
        <li><a href="javascript:" id="menu"> Menu</a> </li>
    </ul>
    <div>
        <h4>Messages:</h4>
        <p><a href="javascript:"onclick="toJoinRequests()">Incoming Requests(${user.requestsSize})</a> </p>
    </div>

</div>
<div class="content" id="content">
    <h3>Welcome in restaurant Nice.</h3>
</div>
</body>
</html>
