<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input type="hidden" id="groupOrderIdOverride" value="${groupOrder.group_id}">
<input type="hidden" id="orderIdOverride" value="${orderId}">
<div class="in-right-block">
    <c:choose>
    <c:when test="${groupOrder.state.name() eq 'SENT'}"><h3>Sent Group order</h3></c:when>
    <c:otherwise>

    <c:if test="${orderId == null }">
        <p>
            <button class="button_dark" id="addOrder" onclick="addOrder(${groupOrder.id})">addOrder</button>
        </p>
    </c:if>
    <c:if test="${orderId != null}">
        <p>
            <button class="button_dark" id="editOrder" onclick="editOrder(${orderId},${groupOrder.id})">editOrder
            </button>
        </p>
        <p>
            <button class="button_dark" id="deleteOrder" onclick="deleteOrder(${orderId},${groupOrder.id})">
                deleteOrder
            </button>
        </p>
    </c:if>
    <c:if test="${groupOrder.owner_id eq userId}">
        <p>
            <button class="button_dark" id="deleteGroupOrder" onclick="deleteGroupOrder(${groupOrder.id})">DELETE GROUP
                ORDER
            </button>
        </p>
        <p>
            <button class="button_dark" id="sendGroupOrder" onclick="sendGroupOrder(${groupOrder.id})">SEND GROUP
                ORDER
            </button>
        </p>
</c:if>
    </c:otherwise>
    </c:choose>
    <div id="right-block-content" class="right-block-content"></div>
    </div>

<div class="in-content">
    <h2>GROUP ORDER "${groupOrder.id}"</h2>
    <table class="order-table">
        <tr>
            <th class="orders-td">User</th>
            <th class="orders-td" style="width: 500px">Order</th>
            <th class="orders-td">Total by user</th>
        </tr>
        <c:choose>
            <c:when test="${groupOrder.orders.size()>0}">
                <c:forEach items="${groupOrder.orders}" var="order">
                    <tr>
                        <td class="orders-td">${order.user.username}</td>
                        <td class="orders-td">
                            <table class="new-order-table" style="width: 480px">
                                <tr>
                                    <th class="td-new-order-table">Name</th>
                                    <th class="td-new-order-table">Amount</th>
                                    <th class="td-new-order-table" style="width: 60px"> Price</th>
                                    <th></th>
                                </tr>
                                <c:forEach items="${order.dishes}" var="dish">
                                    <tr class="order-content-row">
                                        <td class="td-new-order-table">${dish.name}</td>
                                        <td class="td-new-order-table">${dish.amount}</td>
                                        <td class="td-new-order-table">${dish.price}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                        <td class="orders-td">${order.sum}</td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="3">Group order have not no one order</td>
                </tr>
            </c:otherwise>
        </c:choose>
        <tr>
            <td class="orders-td" colspan="2" style="font-weight: bold; color: green">Group Order TOTAL:</td>
            <td class="orders-td" style="font-weight: bold; color: green">${groupOrder.sum}</td>
        </tr>
    </table>
</div>
