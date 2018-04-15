<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="login">
    <h3>DISH CATEGORY</h3>

    <p><input type="hidden" name="dishCategoryId" id="dishCategoryId" <c:if test="${dishCategory != null}">value="${dishCategory.id}" </c:if>></p>
    <p><input type="text" class="login-text-field" id="nameDishCategory" <c:if test="${dishCategory != null}">value="${dishCategory.name}" </c:if> name="name" maxlength="150" placeholder="Name"></p>
    <p class="submit"><input class="button" type="button" value="Cancel" onclick="moveToMenu()">
        <c:choose>
        <c:when test="${dishCategory == null}">
        <input type="submit" id="butSaveCategory" onclick="saveOrUpdateDishCategory()" class="button" value="Save"></p>
    </c:when>
    <c:otherwise>
        <input type="submit" id="butUpdateCategory" onclick="saveOrUpdateDishCategory()" class="button" value="Update"></p>
    </c:otherwise>
    </c:choose>
    </form>

</div>
