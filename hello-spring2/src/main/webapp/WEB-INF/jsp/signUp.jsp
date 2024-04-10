<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 화면</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js">
</script>
<script>
$(document).ready(function() {
    // 비밀번호 확인
    $("#passwordCheck").blur(function(){
        if($("#passwordCheck").val() === $("#password").val()){
            $(".successPwChk").text("비밀번호가 일치합니다.").css("color", "green");
            $("#pwDoubleChk").val("true");
        } else {
            $(".successPwChk").text("비밀번호가 일치하지 않습니다.").css("color", "red");
            $("#pwDoubleChk").val("false");
        }
    });
    
	//-------------------------------------------------------

    // 폼 제출 시 비밀번호 일치 여부 확인
    $("form").submit(function(event) {
        if ($("#pwDoubleChk").val() === "false") {
            alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            event.preventDefault(); // 폼 제출을 막습니다.
        }
    });
});


</script>
<body>
	<form action="/doSignUp" method="post">
		<label for="id">아이디:</label>
	    <input type="text" id="id" name="userId"><br><br>
	    
	    <label for="password">비밀번호:</label>
		<input type="password" id="password" name="password" autocomplete="off"><br><br>
		
		<label for="passwordCheck">비밀번호 확인:</label>
		<input type="password" id="passwordCheck" name="passwordCheck" placeholder="동일한 비밀번호 입력">
		<span class="point successPwChk"></span>
		<input type="hidden" id="pwDoubleChk"><br><br>
	    
		<label for="name">이름:</label>
	    <input type="text" id="name" name="userName"><br><br>
	    
		<label for="birthdate">생년월일:</label>
		<input type="date" id="birthday" name="birthday"><br><br>

		<label for="email">이메일:</label>
		<input type="email" id="email" name="email"><br><br>

		<label for="phone">휴대전화:</label>
		<input type="text" id="phone" name="phone" 
		pattern="^\d{3}-\d{3,4}-\d{4}$" placeholder="010-1234-5678"><br><br>
	    
		<label for="address">주소:</label>
		<input type="text" id="address" name="address"><br><br>
		
		 <label>성별 선택:</label><br>
		<input type="radio" id="male" name="gender" value="male">
		<label for="male">남</label><br>
		<input type="radio" id="female" name="gender" value="female">
		<label for="female">여</label><br>
		<input type="radio" id="not_specified" name="gender" value="not_specified">
		<label for="not_specified">표시 안 함</label><br>

	    <button type="submit">회원가입</button>
	</form>
	<!-- 에러 메시지 표시 -->
	<div id="error" data-yourobject = "${errorMessage}"></div>
	<div id="passwordCheckError" data-yourobject = "${passwordCheckError}"></div>
	
	
	
</body>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    String passwordCheckError = (String) request.getAttribute("passwordCheckError");
    if (errorMessage != null && !errorMessage.isEmpty()) {
%>
<script>
    alert('<%= errorMessage %>'); // errorMessage를 alert 창으로 띄웁니다.
</script>
<%
    }
    if (passwordCheckError != null && !passwordCheckError.isEmpty()) {
%>
<script>
    alert('<%= passwordCheckError %>'); // passwordCheckError를 alert 창으로 띄웁니다.
</script>
<%
    }																				
%>


</html>