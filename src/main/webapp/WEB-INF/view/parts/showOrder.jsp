<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table class="new-order-table">
    <tr><td colspan="3">Order № ${currentOrder.id}</td> </tr>
    <tr>
        <th class="td-new-order-table" >Name</th>
        <th class="td-new-order-table">Amount</th>
        <th class="td-new-order-table" style="width: 60px"> Price </th>
    </tr>
    <c:forEach items="${orderDishes}" var="dish">
        <tr class="order-content-row">
            <td class="td-new-order-table">${dish.name}</td>
            <td class="td-new-order-table">${dish.amount}</td>
            <td class="td-new-order-table">${dish.price}</td>
        </tr>
    </c:forEach>
    <tr>
        <td class="total-order-label" colspan="2">Total:</td>
        <td class="total-order-sum">${currentOrder.sum}</td>
    </tr>
    <tr><td colspan="3"></td></tr>
    <tr><td colspan="3"></td></tr>

    <tr>
        <td colspan="3">
            <button class="in-submit" id="deleteOrder" onclick="deleteOrder(${currentOrder.id})">Delete</button>
            <c:choose>
            <c:when test="${currentOrder.orderState.name() == 'NOT_SENT'}">
            <button class="in-submit" id="editOrder" onclick="editOrder(${currentOrder.id})">Edit</button>
            <button class="in-submit" id="sendOrder" onclick="sendOrder(${currentOrder.id})">Send</button>
            </c:when>
            </c:choose>
        </td>
    </tr>

</table>
