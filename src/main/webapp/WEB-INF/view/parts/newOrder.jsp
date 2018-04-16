<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="in-right-block">
    <h3>New Order</h3>
    <c:if test="${groupOrderId != null}"><p><h4> for Group Order "${groupOrderId}"</h4></p></c:if>
    <div id="right-block-content-order" class="right-block-content"></div>
</div>
<jsp:include page="menu-block.jsp"/>
