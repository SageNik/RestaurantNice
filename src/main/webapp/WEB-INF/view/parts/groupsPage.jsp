<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="in-right-block">
    <p><a href="javascript:" onclick="moveNewGroup()" id="newGroup">NEW GROUP</a> </p>
    <div id="right-block-group-content" class="right-block-content"></div>
</div>
<div id="in-content" class="in-content">
    <h2>GROUPS</h2>
    <table class="order-table">
        <tr>
            <th class="orders-td">Number</th>
            <th class="orders-td" style="width: 200px">Name</th>
            <th class="orders-td"style="width: 300px">Description</th>
            <th class="orders-td">My status</th>
            <th class="orders-td" style="width: 150px">Action</th>
        </tr>
        <c:choose>
            <c:when test="${groups.size()>0}">
                <c:forEach items="${groups}" var="group">
                    <tr>
                        <td class="orders-td">${group.groupId}</td>
                        <td class="orders-td">${group.groupName}</td>
                        <td class="orders-td">${group.groupDescription}</td>
                        <td class="orders-td">${group.myStatus}</td>
                        <td class="orders-td">
                        <c:if test="${group.myStatus eq 'Owner'}">
                            <button class="button" id="editGroup" onclick="editGroup(${group.groupId})">Edit</button>
                            <button class="button" id="deleteGroup" onclick="deleteGroup(${group.groupId})">Delete</button>
                        </c:if>
                            <c:if test="${group.myStatus eq 'Member'}"><button class="button" id="quitGroup" onclick="quitGroup(${group.groupId})">Quit</button> </c:if>

                            <c:if test="${group.myStatus eq ''}"><button class="button" id="joinToGroup" onclick="joinToGroup(${group.groupId})">Join</button> </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr><td colspan="5">There is no one group is present</td></tr>
            </c:otherwise>
        </c:choose>
    </table>
</div>
