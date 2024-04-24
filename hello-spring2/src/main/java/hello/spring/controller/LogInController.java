package hello.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import hello.spring.data.dto.MemberDTO;
import hello.spring.service.SignUpService;


@Controller
public class LogInController {
	private static final Logger logger = LoggerFactory.getLogger(LogInController.class);
	
	@Autowired
	private SignUpService signUpService;
	
	@GetMapping("/login")
	public String logIn() {
		logger.info("@@@@@@@logIn controller");
		return "logingo";
	}

	@GetMapping("/loginSuccess")
	public String loginSuccess(Model model) {
	    logger.info("@@@@@@@loginSuccess controller");
	    
	    // Authentication 객체 가져오기
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    // Principal 가져오기
	    String principal = authentication.getPrincipal().toString();
	    logger.info("@@@@@@@principal : {}",principal);
	    
	    // 가져온 Principal 토대로 사용자정보 가져오기
	    MemberDTO member = signUpService.getMemberByUserId(principal);
	    logger.info("@@@@@@@member : {}",member);
	    
	    // 앞단으로 전달
	    model.addAttribute("member", member);
	    
	    // 권한정보 가져오기
	    String authorities = authentication.getAuthorities().toString();
	    
	    logger.info("@@@@@권한{}", authorities);
	    if (authorities.equals("[ROLE_ADMIN]")) {
	        return "redirect:/admin";
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
