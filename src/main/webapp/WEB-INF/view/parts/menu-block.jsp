<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="in-content">
    <h2>MENU</h2>
    <table class="order-table">
        <tr>
            <th class="td-table">Number</th>
            <th class="td-table">Name</th>
            <th class="td-table">Description</th>
            <th class="td-table">Price</th>
            <th class="td-table">Amount</th>
            <th class="td-table">Action</th>
        </tr>
        <c:choose>
            <c:when test="${dishes.size()>0}">

                <c:forEach items="${dishCategories}" var="category">
                    <tr>
                        <td class="td-table" style="color: darksalmon" colspan="6">${category.name}</td>
                    </tr>
                    <c:forEach items="${dishes}" var="dish">
                        <c:if test="${dish.dishCategory_id == category.id}">
                            <tr>
                                <td class="td-table">${dish.id}</td>
                                <td class="td-table">${dish.name}</td>
                                <td class="td-table">${dish.description}</td>
                                <td class="td-table">${dish.price}</td>
                                <td class="td-table"><input type="text" value="1" class="field-dish-amount"
                                                            onkeypress="return onlyNumber(event)" name="Amount"
                                                            id="dishAmount" max="12" min="0" maxlength="1"></td>
                                <td class="td-table">
                                    <button class="button" id="toOrder"
                                            onclick="toOrder(${dish.id},$('#dishAmount').val())">toOrder
                                    </button>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td class="td-table" colspan="5">Sorry menu is empty.</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>
</div>
