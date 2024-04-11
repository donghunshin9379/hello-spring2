package hello.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
	    return "loginSuccess";
	}
	
	@GetMapping("/logoutGo")
	public String logOut() {
	    logger.info("@@@@@@@logOut controller");
	    return "logoutGo";
	}
}
