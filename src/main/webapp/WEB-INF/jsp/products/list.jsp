<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Products</title>
    <link rel="stylesheet" href="/css/site.css">
</head>
<body>
    <h1>Products (<c:out value="${currency}"/>)</h1>

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
            <th>ID</th>
            <th>Name</th>
            <th>Net (<c:out value="${currency}"/>)</th>
            <th>Gross (<c:out value="${currency}"/>)</th>
            <th>Stock</th>
        </tr>
        <c:forEach var="p" items="${products}">
            <tr class="${p.low ? 'low' : ''}">
                <td><c:out value="${p.id}"/></td>
                <td><c:out value="${p.name}"/></td>
                <td><fmt:formatNumber value="${p.price}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td><fmt:formatNumber value="${p.gross}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td><c:out value="${p.stock}"/></td>
            </tr>
        </c:forEach>
        <c:if test="${empty products}">
            <tr><td colspan="5">No products found.</td></tr>
        </c:if>
    </table>

    <p class="hint">Rows in red have fewer than <c:out value="${threshold}"/> units in stock.</p>
</body>
</html>