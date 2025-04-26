<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户管理</title>
    <link href="https://cdn.bootcdn.net/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4">用户列表</h2>
    <table class="table table-hover">
        <thead class="table-dark">
        <tr>
            <th>用户ID</th>
            <th>昵称</th>
            <th>邮箱</th>
            <th>信用分</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.userId}</td>
                <td>${user.nickname}</td>
                <td>${user.email}</td>
                <td>${user.creditScore}</td>
                <td>
                    <button class="btn btn-sm btn-danger"
                            onclick="banUser(${user.userId})">封禁</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script>
    function banUser(userId) {
        if (confirm('确定要封禁此用户吗？')) {
            fetch('/admin?action=banUser&userId=' + userId)
                .then(response => location.reload());
        }
    }
</script>
</body>
</html>