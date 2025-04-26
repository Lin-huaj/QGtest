<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>订单支付</title>
    <link href="https://cdn.bootcdn.net/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <div class="card shadow">
        <div class="card-header bg-info text-white">
            <h4>订单号：${order.id}</h4>
        </div>
        <div class="card-body">
            <p>总金额：¥${order.totalPrice}</p>
            <div class="border p-3 mb-3">
                <h5>选择支付方式</h5>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="paymentMethod" value="online" checked>
                    <label class="form-check-label">在线支付</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="paymentMethod" value="cod">
                    <label class="form-check-label">货到付款</label>
                </div>
            </div>
            <button class="btn btn-success w-100" onclick="submitPayment()">立即支付</button>
        </div>
    </div>
</div>

<script>
    function submitPayment() {
        // 调用支付接口
        fetch('/payment', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                orderId: ${order.id},
                paymentMethod: document.querySelector('input[name="paymentMethod"]:checked').value
            })
        }).then(response => {
            if (response.ok) {
                window.location.href = '/order_success.jsp';
            }
        });
    }
</script>
</body>
</html>