<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="in-right-block">
    <p><a href="javascript:" onclick="moveNewOrder()" id="newOrder">NEW Order</a> </p>
    <div id="right-block-content" class="right-block-content"></div>
</div>
<div class="in-content">
    <h2>ORDERS</h2>
<table class="order-table">
    <tr>
    <th class="orders-td">Number</th>
    <th class="orders-td">Sum</th>
    <th class="orders-td">Action</th>
    </tr>
    <c:choose>
        <c:when test="${orders.size()>0}">
    <c:forEach items="${orders}" var="order">
    <tr>
        <td class="orders-td">${order.id}</td>
        <td class="orders-td">${order.sum}</td>
        <td class="orders-td"><button class="button" id="showOrder" onclick="showOrder(${order.id})">Show</button> </td>
    </tr>
    </c:forEach>
        </c:when>
        <c:otherwise>
            <tr><td colspan="3">User have not no one order</td></tr>
        </c:otherwise>
    </c:choose>
</table>
</div>