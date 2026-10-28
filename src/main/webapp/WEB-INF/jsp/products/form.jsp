<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>New product</title>
</head>
<body>
    <h1>New product</h1>

    <form:form method="post" action="/products" modelAttribute="productForm">
        <p>
            Name:
            <form:input path="name"/>
            <form:errors path="name" cssStyle="color:red"/>
        </p>
        <p>
            Price:
            <form:input path="price"/>
            <form:errors path="price" cssStyle="color:red"/>
        </p>
        <p>
            Stock:
            <form:input path="stock"/>
            <form:errors path="stock" cssStyle="color:red"/>
        </p>
        <button type="submit">Save</button>
        <a href="/products">Cancel</a>
    </form:form>
</body>
</html>