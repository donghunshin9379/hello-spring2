package hello.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@GetMapping("/home")	 //@RequestParam(required = false) 요구가 필수 아님X // @RequestParam(required = false)
	public String home(String signUpSuccess, Model model) {
	    logger.info("@@@@@@@home controller");
	    
	    if (signUpSuccess.equals("success")) {
	    	model.addAttribute("signUpSuccess", "회원가입이 완료 되었습니다.");
	    }
	    return "home";
	}
	
}
