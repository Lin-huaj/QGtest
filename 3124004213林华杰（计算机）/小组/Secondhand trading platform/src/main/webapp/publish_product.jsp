<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>发布商品</title>
  <link href="https://cdn.bootcdn.net/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
  <h2 class="mb-4">发布新商品</h2>
  <form action="/seller/product" method="post" enctype="multipart/form-data">
    <!-- 商品名称 -->
    <div class="mb-3">
      <label class="form-label">商品标题</label>
      <input type="text" name="title" class="form-control" required>
    </div>
    <!-- 商品描述 -->
    <div class="mb-3">
      <label class="form-label">详细描述</label>
      <textarea name="description" class="form-control" rows="3"></textarea>
    </div>
    <!-- 上传图片 -->
    <div class="mb-3">
      <label class="form-label">商品图片</label>
      <input type="file" name="image" class="form-control" accept="image/*">
    </div>
    <button type="submit" class="btn btn-success">立即发布</button>
  </form>
</div>
</body>
</html>