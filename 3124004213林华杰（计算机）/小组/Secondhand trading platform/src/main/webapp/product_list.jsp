<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>商品列表</title>
</head>
<body>
<h2>商品列表</h2>
<form action="Browse/product" method="post">
    <input type="text" name="keyword" placeholder="搜索商品">
    <select name="categoryId">
        <option value="">所有分类</option>
        <option value="1">电子产品</option>
        <option value="2">书籍</option>
        <option value="3">家具</option>
    </select>
    <button type="submit">搜索</button>
</form>
<table border="1">
    <tr>
        <th>ID</th>
        <th>名称</th>
        <th>价格</th>
        <th>库存</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${products}" var="product">
        <tr>
            <td>${product.productId}</td>
            <td>${product.title}</td>
            <td>${product.price}</td>
            <td>${product.stock}</td>
            <td><a href="order?productId=${product.productId}">购买</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>