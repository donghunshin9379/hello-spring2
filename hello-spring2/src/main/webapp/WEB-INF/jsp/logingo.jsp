<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인 화면</title>
</head>
<body>
    <h2>Login Form</h2>
    <form action="auth" method="post">
        <div>
            <label for="userId">UserID:</label>
            <input type="text" id="userId" name="userId" required>
        </div>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div>
            <input type="submit" value="Login">
        </div>
        <br><br>
        <div>
        <!--  카카오 로그인 이동 -->
        <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=0c0bf7f540364c14e4b82afa45e09fe2&redirect_uri=http://localhost:8060/callback">카카오 로그인</a>
        </div>
    </form>
</body>
</html>
