<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="in-content">
    <h2>JOIN REQUESTS</h2>
    <table class="order-table">
        <tr>
            <th class="orders-td">Number</th>
            <th class="orders-td">User (from)</th>
            <th class="orders-td">Group (to)</th>
            <th class="orders-td">Action</th>
        </tr>
        <c:choose>
            <c:when test="${requests.size()>0}">
                <c:forEach items="${requests}" var="request">
                    <tr>
                        <td class="orders-td">${request.id}</td>
                        <td class="orders-td">${request.joinUser.username}</td>
                        <td class="orders-td">${request.group.name}</td>
                        <td class="orders-td">
                            <button class="button" id="acceptJoinRequest" onclick="acceptJoinRequest(${request.id})">Accept</button>
                            <button class="button" id="rejectJoinRequest" onclick="rejectJoinRequest(${request.id})">Reject</button>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr><td colspan="4">User have not no one order</td></tr>
            </c:otherwise>
        </c:choose>
    </table>
</div>
