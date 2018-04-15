<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="dish-form">
    <h3>GROUP</h3>

    <p><input type="hidden" name="dishId" id="idGroup" <c:if test="${group != null}">value="${group.id}" </c:if>></p>
    <p><input type="text" class="dish-form-text-field" id="nameGroup" <c:if test="${group != null}">value="${group.name}" </c:if> name="name" maxlength="50" placeholder="Name"></p>
    <p><input type="text" class="dish-form-text-field" id="descriptionGroup" <c:if test="${group != null}">value="${group.description}" </c:if> name="description" maxlength="200" placeholder="Description"></p>
    <p class="submit"><input class="button" type="button" value="Cancel" onclick="moveToGroups()">
        <c:choose>
        <c:when test="${group == null}">
        <input type="submit" id="butSaveGroup" onclick="saveOrUpdateGroup()" class="button" value="Save"></p>
    </c:when>
    <c:otherwise>
        <input type="submit" id="butUpdateGroup" onclick="saveOrUpdateGroup()" class="button" value="Update"></p>
    </c:otherwise>
    </c:choose>
    </form>

</div>
