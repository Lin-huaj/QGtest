<%--
  Created by IntelliJ IDEA.
  User: 24834
  Date: 2025/4/26
  Time: 1:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 卖家商品列表 -->
<c:forEach items="${products}" var="product">
    <div class="product-item">
        <h3>${product.title}</h3>
        <p>状态：${product.status}</p>
        <button onclick="showEditForm(${product.productId})">修改</button>
        <button onclick="deactivateProduct(${product.productId})">下架</button>
    </div>
</c:forEach>

<!-- 修改商品表单（动态弹出） -->
<div id="editForm" style="display:none;">
    <form onsubmit="return submitEditForm()">
        <input type="hidden" name="productId" id="editProductId">
        <input type="text" name="title" id="editTitle" required>
        <textarea name="description" id="editDescription"></textarea>
        <input type="number" name="price" id="editPrice" step="0.01" required>
        <input type="number" name="stock" id="editStock" required>
        <button type="submit">保存修改</button>
    </form>
</div>

<script>
    function showEditForm(productId) {
        // 通过AJAX获取商品详情并填充表单
        fetch(`/seller/product?productId=${productId}`)
            .then(response => response.json())
            .then(data => {
                document.getElementById('editProductId').value = data.productId;
                document.getElementById('editTitle').value = data.title;
                document.getElementById('editDescription').value = data.description;
                document.getElementById('editPrice').value = data.price;
                document.getElementById('editStock').value = data.stock;
                document.getElementById('editForm').style.display = 'block';
            });
    }

    function submitEditForm() {
        const formData = new FormData(document.getElementById('editForm'));
        fetch('/seller/product', {
            method: 'PUT',
            body: formData
        }).then(/* 处理响应 */);
        return false;
    }

    function deactivateProduct(productId) {
        if (confirm('确定要下架该商品吗？')) {
            fetch(`/seller/product?productId=${productId}`, {
                method: 'DELETE'
            }).then(/* 处理响应 */);
        }
    }
</script>
