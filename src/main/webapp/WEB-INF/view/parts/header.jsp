<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="block-header" >
    <h1>Restaurant NICE</h1>
    <h4>Have a nice dinner!</h4>
    <c:if test = "${sessionScope.get('CURRENT_SESSION_USER') != null }"> <p class="logout"><a href="logout">LogOUT</a></p></c:if>
</div>
