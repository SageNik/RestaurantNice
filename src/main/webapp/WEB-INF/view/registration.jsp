
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

<div class="content">

    <div class="login">
        <h4>User registration.</h4>
        <form method="post" action="registration">
            <p class="errorLogin">${loginError}</p>
            <p><input type="text" class="login-text-field" id="usernameReg" name="username" maxlength="30" placeholder="Username"></p>
            <p><input type="text" class="login-text-field" id="passwordReg" name="password" maxlength="30" placeholder="Password"></p>
            <select class="regSelect" name="selectRole" id="selectRole">
                <option value="0">admin</option>
                <option value="1">user</option>
            </select>
            <p class="submit"><input class="button" type="button" value="Cancel" id="cancelRegistration">
            <input type="submit" class="button" value="Registration"></p>
        </form>

    </div>
</div>
</body>
</html>

