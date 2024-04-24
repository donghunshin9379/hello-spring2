<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 성공</title>
</head>
<body>
        <div>
        <h2>로그인 성공화면</h2>
        <h3>${member.userName}님 환영합니다</h3>
        </div>
       	<div>
       		<h2>유저정보</h2>
       		<p>로그인 아이디 : ${member.userId}</p>
       		<p>로그인 이름 : ${member.userName}</p>
       		<p>로그인 권한 : ${authorities}</p>
       		
       	</div> 
       
        <a href="logout">로그아웃</a>
        <a href="home">홈 돌아가기</a>
        <a href="getMember">회원정보 수정</a>
</body>
</html>