package hello.spring.controller;

import java.util.HashMap;

import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hello.spring.service.EmailService;

@RestController
public class EmailAuthController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EmailService emailService;

	private Map<String, String> emailVerificationCodes = new HashMap<>();

	@PostMapping("/emailAuth")
	public ResponseEntity<String> emailAuth(@RequestParam("email") String email) {
		String verificationCode = generateVerificationCode();
		emailVerificationCodes.put(email, verificationCode);
		emailService.sendEmail(email, verificationCode);
		return ResponseEntity.ok("이메일 인증이 요청되었습니다.");
	}

	// 난수 생성기
	private String generateVerificationCode() {
		// 4자리의 난수 생성
		Random random = new Random();
		int verificationCodeInt = random.nextInt(9000) + 1000; // 1000 이상 9999 이하의 난수 생성
		String verificationCode = String.valueOf(verificationCodeInt);
		return verificationCode;
	}

	@PostMapping("/verifyEmail")
	public ResponseEntity<String> verifyEmail(@RequestParam("email") String email, @RequestParam("code") String code) {
		String savedCode = emailVerificationCodes.get(email);
		logger.info("인증코드 {} : ", savedCode);
		logger.info("인증코드2 {} : ", code);
		if (savedCode != null && savedCode.equals(code)) {
			// 인증 코드가 일치하면
			emailVerificationCodes.remove(email); // 사용한 인증 코드 삭제
			logger.info("하하@@@@ {} : ");
			return ResponseEntity.ok("이메일이 성공적으로 인증되었습니다.");
		} else {
			// 인증 코드가 일치하지 않거나 이메일이 없는 경우
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 코드가 올바르지 않습니다.");
		}
	}
}
