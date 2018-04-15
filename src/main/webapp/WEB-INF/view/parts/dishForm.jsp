<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="dish-form">
    <h3>DISH</h3>

        <p><input type="hidden" name="dishId" id="idDish" <c:if test="${dish != null}">value="${dish.id}" </c:if>></p>
        <p><input type="text" class="dish-form-text-field" id="nameDish" <c:if test="${dish != null}">value="${dish.name}" </c:if> name="name" maxlength="150" placeholder="Name"></p>
        <p><input type="text" class="dish-form-text-area-field" id="descriptionDish" <c:if test="${dish != null}">value="${dish.description}" </c:if> name="description" maxlength="500" placeholder="Description"></p>
        <p><input type="text" class="dish-form-text-field" id="priceDish" <c:if test="${dish != null}">value="${dish.price}" </c:if> name="price" maxlength="10" placeholder="Price" onkeypress="return onlyNumber(event)"></p>
        <select class="dish-form-text-field" name="dishCategory_id" id="selectDishCategory" <c:if test="${dish != null}">autofocus="${dish.dishCategory_id}" </c:if>>
            <c:forEach items="${dishCategories}" var="category">
            <option value="${category.id}"><c:out value="${category.name}"></c:out></option>
            </c:forEach>
        </select>
        <p class="submit"><input class="button" type="button" value="Cancel" onclick="moveToMenu()">
            <c:choose>
            <c:when test="${dish == null}">
            <input type="submit" id="butSaveDish" onclick="saveOrUpdateDish()" class="button" value="Save"></p>
        </c:when>
        <c:otherwise>
            <input type="submit" id="butUpdateDish" onclick="saveOrUpdateDish()" class="button" value="Update"></p>
        </c:otherwise>
        </c:choose>
    </form>

</div>
