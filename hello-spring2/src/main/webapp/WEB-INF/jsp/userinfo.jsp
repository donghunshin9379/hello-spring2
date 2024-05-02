<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카카오 User Information</title>
</head>
<body>
    <h1>카카오 User Information</h1>
    <img src="${profileImageUrl}" alt="Profile Image">
    <p>User Name: <%= request.getAttribute("userName") %></p>
    <p>Email: ${email}</p>
</body>
</html>
