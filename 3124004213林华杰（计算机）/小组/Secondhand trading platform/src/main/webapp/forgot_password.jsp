<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>找回密码</title>
    <link href="https://cdn.bootcdn.net/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5" style="max-width: 500px;">
    <div class="card shadow">
        <div class="card-header bg-warning">
            <h4 class="mb-0">找回密码</h4>
        </div>
        <div class="card-body">
            <form id="forgotForm" onsubmit="return sendResetLink(event)">
                <div class="mb-3">
                    <label class="form-label">注册邮箱</label>
                    <input type="email" name="email" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-warning w-100">发送重置链接</button>
            </form>
        </div>
    </div>
</div>

<script>
    function sendResetLink(event) {
        event.preventDefault();
        const email = document.querySelector('input[name="email"]').value;
        fetch('/forgotPassword', {
            method: 'POST',
            body: new URLSearchParams({email: email})
        }).then(response => {
            alert('重置链接已发送至您的邮箱！');
        });
    }
</script>
</body>
</html>