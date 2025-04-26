<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>用户注册</title>
</head>
<body>
<h2>用户注册</h2>
<form action="register" method="post">
  <table>
    <tr>
      <td>用户名:</td>
      <td><input type="text" name="nickname" required></td>
    </tr>
    <tr>
      <td>密码:</td>
      <td><input type="password" name="password" required></td>
    </tr>
    <tr>
      <td>手机号:</td>
      <td><input type="text" name="phone" required></td>
    </tr>
    <tr>
      <td>邮箱:</td>
      <td><input type="text" name="email" required></td>
    </tr>
    <tr>
      <td>学号:</td>
      <td><input type="text" name="studentId" required></td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" value="注册"></td>
    </tr>
  </table>
</form>
${error}
</body>
</html>