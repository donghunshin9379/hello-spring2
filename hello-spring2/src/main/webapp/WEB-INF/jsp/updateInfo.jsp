<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 수정</title>
</head>
<body>
      <form action="updateInfoGo" method="post">
        <label for="id">아이디:</label>
        <input type="text" id="id" name="userId" value="${member.userId}" readonly><br><br>
        <label for="password">비밀번호:</label>
        <input type="password" id="password" name="password" autocomplete="off"><br><br>
        <label for="passwordCheck">비밀번호 확인:</label>
        <input type="password" id="passwordCheck" name="passwordCheck" placeholder="동일한 비밀번호 입력"><br><br>
        <label for="name">이름:</label>
        <input type="text" id="name" name="userName" value="${member.userName}"><br><br>
        <label for="birthdate">생년월일:</label>
        <input type="date" id="birthday" name="birthday" value="${member.birthday}"><br><br>
        <label for="email">이메일:</label>
        <input type="email" id="email" name="email" value="${member.email}">

		<label for="phone">휴대전화:</label>
		<input type="text" id="phone" name="phone" value="${member.phone}" pattern="^\d{3}-\d{3,4}-\d{4}$"
			placeholder="010-1234-5678"><br> <br> 
		<label for="address">주소:</label>
		<input type="text" id="address"	name="address"  value="${member.address}"><br><br>
		<label>성별 선택:</label><br>
		<input type="radio" id="male" name="gender" value="male"> 
		<label for="male">남</label><br> <input type="radio" id="female"	name="gender" value="female">
		<label for="female">여</label><br>
		<input type="radio" id="not_specified" name="gender" value="not_specified">
		<label for="not_specified">표시 안함</label><br>

		<button type="submit">회원정보 수정</button>
	</form>
</body>
</html>