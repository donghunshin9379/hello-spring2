<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>홈 화면</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>

<body>
	<a href="/login">로그인</a>
	<a href="/signUp">회원가입</a>
	<div id="signUpSuccess" data-yourobject = "${signUpSuccess}"></div>
	
<script>
//회원가입 성공 메시지 확인
var signUpSuccess = $("#signUpSuccess").data("yourobject");
if (signUpSuccess) {
    alert(signUpSuccess); // signUpSuccess를 alert 창으로 띄웁니다.
}
</script>
</body>
</html>