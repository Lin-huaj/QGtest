<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>欢迎来到二手交易平台</title>
</head>
<body>
<h1>欢迎您，${sessionScope.currentUser}！</h1>
<h3>平台功能：</h3>
<ul>
  <li><a href="product_list.jsp">浏览商品</a></li>
  <li><a href="publish_product.jsp">发布商品</a></li>
</ul>

<%-- 注销功能 --%>
<form action="logout" method="post">
  <input type="submit" value="安全退出">
</form>
</body>
</html>