<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <table class="new-order-table">
        <tr>
            <th class="td-new-order-table" >Name</th>
            <th class="td-new-order-table">Amount</th>
            <th class="td-new-order-table" style="width: 60px"> Price </th>
            <th></th>
        </tr>
    <c:forEach items="${orderDishes}" var="dish">
        <tr class="order-content-row">
            <td class="td-new-order-table">${dish.name}</td>
            <td class="td-new-order-table">${dish.amount}</td>
            <td class="td-new-order-table">${dish.price}</td>
            <td><button class="in-button" id="fromOrder" onclick="fromOrder(${dish.id})">Delete</button> </td>
        </tr>
    </c:forEach>
    <tr>
        <td class="total-order-label" colspan="2">Total:</td>
        <td class="total-order-sum">${total}</td>
    </tr>
        <tr><td colspan="4"></td></tr>
        <tr><td colspan="4"></td></tr>
        <tr>
            <td colspan="4">
                <button class="in-submit" id="saveOrder" onclick="saveOrder()">Save</button>
                <button class="in-submit" id="saveAndSendOrder" onclick="saveAndSendOrder()">Save&Send</button>
            </td>
        </tr>
    </table>
