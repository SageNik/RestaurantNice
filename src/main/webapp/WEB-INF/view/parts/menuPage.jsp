<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="in-right-block">
    <h3>Edit menu</h3>
    <div id="right-block-content-order" class="right-block-content">
        <c:choose>
            <c:when test="${user.role.name()=='ADMIN'}">

                <p><a href="javascript:" onclick="addNewDish()" id="addNewDish">Add New Dish</a> </p>
                <p><a href="javascript:" onclick="addNewDishCategory()" id="addNewDishCategory">Add New Category</a> </p>

            </c:when>
            <c:otherwise>
                <p>You have not permission for edit.</p>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<div class="in-content" id="in-content">
    <h2>MENU</h2>
    <table class="order-table">
        <tr>
            <th class="td-table">Number</th>
            <th class="td-table">Name</th>
            <th class="td-table">Description</th>
            <th class="td-table">Price</th>
            <th style="width: 150px" class="td-table">Action</th>
        </tr>
        <c:choose>
            <c:when test="${dishes.size()>0}">

                <c:forEach items="${dishCategories}" var="category">
                    <tr>
                        <td class="td-table" style="color: darksalmon" colspan="4">${category.name}</td>
                        <c:if test="${user.role.name()=='ADMIN'}">
                                <td>
                                    <button class="button" id="editDishCategory"
                                            onclick="editDishCategory(${category.id})">Edit
                                    </button>
                                    <c:if test="${category.amountDish == 0}">
                                    <button class="button" id="deleteDishCategory"
                                            onclick="deleteDishCategory(${category.id})">Delete
                                    </button>
                        </c:if>
                                </td>
                        </c:if>
                    </tr>
                    <c:forEach items="${dishes}" var="dish">
                        <c:if test="${dish.dishCategory_id == category.id}">
                            <tr>
                                <td class="td-table">${dish.id}</td>
                                <td class="td-table">${dish.name}</td>
                                <td class="td-table">${dish.description}</td>
                                <td class="td-table">${dish.price}</td>
                                <td class="td-table">
                        <c:if test="${user.role.name()=='ADMIN'}">
                                    <button class="button" id="editDish"
                                            onclick="editDish(${dish.id})">Edit
                                    </button>
                                    <button class="button" id="deleteMenuDish"
                                            onclick="deleteMenuDish(${dish.id})">Delete
                                    </button>
                        </c:if>
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
