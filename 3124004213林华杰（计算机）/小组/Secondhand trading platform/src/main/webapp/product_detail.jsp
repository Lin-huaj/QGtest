<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>商品详情</title>
</head>
<body>
<h2>商品详情</h2>
<table border="1">
    <tr>
        <th>名称</th>
        <th>价格</th>
        <th>库存</th>
        <th>描述</th>
    </tr>
    <tr>
        <td>${product.title}</td>
        <td>${product.price}</td>
        <td>${product.stock}</td>
        <td>${product.description}</td>
    </tr>
</table>
<form action="order" method="post">
    <input type="hidden" name="productId" value="${product.productId}">
    <input type="number" name="quantity" min="1" value="1">
    <select name="paymentMethod">
        <option value="online">在线支付</option>
        <option value="cod">货到付款</option>
    </select>
    <button type="submit">提交订单</button>
</form>
${error}
</body>
</html>