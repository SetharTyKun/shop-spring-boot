<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Products</title>
</head>
<body>
    <h1>Products</h1>

    <c:if test="${not empty message}">
        <p style="color: green;"><c:out value="${message}"/></p>
    </c:if>

    <form method="get" action="/products">
        <input type="text" name="q" value="<c:out value='${q}'/>" placeholder="Search by name">
        <button type="submit">Search</button>
    </form>

    <p><a href="/products/new">+ Add product</a></p>

    <table border="1" cellpadding="6">
        <tr>
            <th>ID</th><th>Name</th><th>Price</th><th>Stock</th>
        </tr>
        <c:forEach var="p" items="${products}">
            <tr>
                <td><c:out value="${p.id}"/></td>
                <td><c:out value="${p.name}"/></td>
                <td><fmt:formatNumber value="${p.price}" type="currency" currencySymbol="$"/></td>
                <td><c:out value="${p.stock}"/></td>
            </tr>
        </c:forEach>
        <c:if test="${empty products}">
            <tr><td colspan="4">No products found.</td></tr>
        </c:if>
    </table>
</body>
</html>