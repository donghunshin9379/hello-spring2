package hello.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogInController {
	private static final Logger logger = LoggerFactory.getLogger(LogInController.class);

	@GetMapping("/login")
	public String logIn() {
		logger.info("@@@@@@@logIn controller");
		return "logingo";
	}

	@GetMapping("/loginSuccess")
	public String loginSuccess() {
		logger.info("@@@@@@@loginSuccess controller");
		String authentication = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
		
		logger.info("@@@@@권한{}",authentication);
		if (authentication.equals("[ROLE_ADMIN]")) {
			return "redirect:admin";
		}
		return "loginSuccess";
	}

	@GetMapping("/logoutGo")
	public String logOut() {
		logger.info("@@@@@@@logOut controller");
		return "logoutGo";
	}
	
	@GetMapping("/admin")
	public String admin() {
		logger.info("@@@@@@@admin controller");
		return "admin";
	}
	
}
