package hello.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
}
