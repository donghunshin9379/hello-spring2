package hello.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hello.spring.data.dto.MemberDTO;
import hello.spring.service.SignUpService;

@Controller
public class SignUpController {
	private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);
	private final SignUpService signUpService;  // (private로 쓰는데 소스안변함 싱글톤 패턴 하나만씀)
//	@Autowired
//	private PasswordEncoder pwEncoder;
	
	
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
	public String doSignUp(MemberDTO memberDTO, Model model) {
		// 중복확인을 위해 아이디값 받아옴
		String userId = memberDTO.getUserId();
		
		// 가져온 아이디값 중복 확인
		boolean result = signUpService.isUserIdExists(userId);
		
		// 중복 확인용 검색 아이디
//		MemberDTO member = signUpService.getMemberByUserId(userId); 
//		logger.info("@@@@@@@겟멤버 {}", member.toString()); 
		
		if (result == true) {
	        // 중복된 아이디가 있을 경우
	        model.addAttribute("errorMessage", "이미 사용 중인 아이디입니다. 다른 아이디를 선택해주세요.");
	        logger.info("@@@@@@@아이디 중복 {}", memberDTO.toString()); 
	        return "signUp"; // 다시 회원가입 페이지로 이동 (redirect:signUp)
	        
	    } else {
		
//		// 비밀번호 일치 확인
//		String password = memberDTO.getPassword();
//		String passwordCheck = memberDTO.getPasswordCheck();
//		
//		if (!password.equals(passwordCheck)) {
//			model.addAttribute("passwordCheckError", "비밀번호가 일치하지않습니다.");
//			logger.info("@@@@@@@비밀번호 불일치 {}", memberDTO.getPassword()); 
//			logger.info("@@@@@@@비밀번호확인 불일치~ {}", memberDTO.getPasswordCheck()); 
//			return "signUp"; // 다시 회원가입 페이지로 이동 (redirect:signUp)
//		}
		signUpService.saveMember(memberDTO);
	    logger.info("doSignUp@@@@@@@{}", memberDTO.toString()); //@@@@@@@{} 중괄호 안에 뒷값이 표시됨
	    return "redirect:home?signUpSuccess=success"; //return 하면 URL 안바뀌고 화면만 바뀜 redirect:는 URL 바뀜
	    }
	}
	
	@GetMapping("/getMember")	 
	public String getMemberByUserId (@RequestParam("userId") String userId, Model model) { //String으로 반환해야 jsp로 감
		MemberDTO member = signUpService.getMemberByUserId(userId); 
		model.addAttribute("member",member); // (내가담을 명칭, 뒤는 데이터 담김)
		logger.info("getMemberByUserId@@@@@@@{}", member.toString()); //@@@@@@@{} 중괄호 안에 뒷값이 표시됨
		return "info";
	}
	
	
	
}
