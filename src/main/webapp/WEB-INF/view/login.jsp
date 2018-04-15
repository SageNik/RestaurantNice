
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <h4>Come in please.</h4>
    <form method="post" action="authorization">
        <p class="errorLogin">${loginError}</p>
        <p><input type="text" class="login-text-field" id="username" name="username" maxlength="30" placeholder="Username"></p>
        <p><input type="text" class="login-text-field" id="password" name="password" maxlength="30" placeholder="Password"></p>
        <p class="login-help"> <a href="registration">Registration</a></p>
        <p class="submit"><input class="button" type="submit" value="Login"></p>
    </form>

    </div>
</div>
</body>
</html>
