package hello.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hello.spring.data.dto.MemberDTO;
import hello.spring.service.SignUpService;

@Controller
public class SignUpController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private final SignUpService signUpService;  // (private로 쓰는데 소스안변함 싱글톤 패턴 하나만씀)
	@Autowired
	public SignUpController(SignUpService signUpService) {
		this.signUpService = signUpService;
	}
	
	//회원가입 창 띄우기
	@GetMapping("/signUp")
	public String signUp() {
		logger.info("@@@@@@@signUp controller");
	    return "signUp";
	}
	
	//회원가입 실행
	@PostMapping("/doSignUp")
	public String doSignUp(MemberDTO memberDTO) {
		//유효성처리 시점 (앞단에서 하거나 앞단뒷단 다)
		signUpService.saveMember(memberDTO);
	    logger.info("doSignUp@@@@@@@{}", memberDTO.toString()); //@@@@@@@{} 중괄호 안에 뒷값이 표시됨
	    return "home";
	}
	
}
