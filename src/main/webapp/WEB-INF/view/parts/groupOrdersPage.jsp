<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="in-content" class="in-content">
    <h2>GROUP ORDERS</h2>
    <table class="order-table">
        <tr>
            <th class="orders-td">Number</th>
            <th class="orders-td">Group</th>
            <th class="orders-td">Sum</th>
            <th class="orders-td">Action</th>
        </tr>
        <c:choose>
        <c:when test="${orders.size()>0}">
        <c:forEach items="${orders}" var="order">
        <c:if test="${order.myState eq 'ADMIN' or order.myState eq 'Member'}">
        <tr>
            <td class="orders-td">${order.number}</td>
            <td class="orders-td">${order.groupName}</td>
            <td class="orders-td">${order.sum}</td>
            <td class="orders-td"><button class="button" id="showOrder" onclick="showGroupOrder(${order.number},${order.myOrderId})">Show</button> </td>
        </tr>
        </c:if>
        </c:forEach>
        </c:when>
        <c:otherwise>
        <tr><td colspan="4">User have not no one group order</td></tr>
        </c:otherwise>
        </c:choose>
</div>
