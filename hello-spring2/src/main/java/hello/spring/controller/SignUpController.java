package hello.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hello.spring.data.dto.MemberDTO;
import hello.spring.service.SignUpService;

@Controller
public class SignUpController {
	private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);
	private final SignUpService signUpService; // (private로 쓰는데 소스안변함 싱글톤 패턴 하나만씀)

	@Autowired
	public SignUpController(SignUpService signUpService) {
		this.signUpService = signUpService;
	}

	// 회원가입 창 띄우기
	@GetMapping("/signUp")
	public String signUp() {
		logger.info("@@@@@@@signUp controller");
		return "signUp";
	}

	// 회원가입 실행
	@PostMapping("/doSignUp")
	public String doSignUp(MemberDTO memberDTO, Model model) {
		// 중복확인을 위해 아이디값 받아옴
		String userId = memberDTO.getUserId();

		// 가져온 아이디값 중복 확인
		boolean result = signUpService.isUserIdExists(userId);

		if (result == true) {
			// 중복된 아이디가 있을 경우
			model.addAttribute("errorMessage", "이미 사용 중인 아이디입니다. 다른 아이디를 선택해주세요.");
			logger.info("@@@@@@@아이디 중복 {}", memberDTO.toString());
			return "signUp"; // 다시 회원가입 페이지로 이동 (redirect:signUp)

		} else {
			signUpService.saveMember(memberDTO);
			logger.info("doSignUp@@@@@@@{}", memberDTO.toString());
			model.addAttribute("signUpSuccess", "회원가입이 완료 되었습니다.");
			return "home";
		}
	}

	// 회원정보 가져오기
	@GetMapping("/getMember")
	public String getMemberByUserId(Model model) { // userId 파라미터 제거
		// 인증된 사용자의 아이디 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loggedInUserId = authentication.getName();
		// 이제 loggedInUserId를 사용하여 회원 정보를 조회하고 필요한 작업을 수행합니다.

		MemberDTO member = signUpService.getMemberByUserId(loggedInUserId);
		model.addAttribute("member", member); // (내가담을 명칭, 뒤는 데이터 담김)
		logger.info("getMemberByUserId@@@@@@@{}", member.toString());
		return "updateInfo";
	}

	// 회원정보 수정실행
	@PostMapping("/updateInfoGo")
	public String updateInfoGo(MemberDTO memberDTO, Model model) {
		
		signUpService.saveMember(memberDTO);
		
		logger.info("updateInfoGo@@@@@@@{}", memberDTO.toString());
		model.addAttribute("updateInfoGoSuccess", "회원정보 수정이 완료 되었습니다.");
		return "updateInfoSuccess";
	}

}
