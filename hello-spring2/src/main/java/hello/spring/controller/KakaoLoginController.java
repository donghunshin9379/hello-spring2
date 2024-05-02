package hello.spring.controller;

import java.io.IOException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import hello.spring.service.KakaoService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("")
public class KakaoLoginController {

	private static final Logger logger = LoggerFactory.getLogger(KakaoLoginController.class);
	private final KakaoService kakaoService; // (private로 쓰는데 소스안변함 싱글톤 패턴 하나만씀)

	public KakaoLoginController(KakaoService kakaoService) {
		this.kakaoService = kakaoService;
	}

	@GetMapping("/callback")
	public String callback(@RequestParam("code") String code) throws IOException {
	    // clientId는 개발자 애플리케이션을 등록할 때 발급받은 클라이언트 아이디
	    String clientId = "0c0bf7f540364c14e4b82afa45e09fe2";
	    String accessToken = kakaoService.getAccessTokenFromKakao(clientId, code);
	    logger.info("Received access token: " + accessToken);
	    // userinfo 엔드포인트로 리다이렉트하면서 accessToken을 함께 전달
	    return "redirect:/userinfo?accessToken=" + accessToken;
	}


	
	@GetMapping("/userinfo")
	public String getUserInfo(@RequestParam("accessToken") String accessToken, Model model) {
	    // 카카오 API 호출을 위한 엔드포인트 URL
	    String apiUrl = "https://kapi.kakao.com/v2/user/me";

	    // RestTemplate 객체 생성
	    RestTemplate restTemplate = new RestTemplate();

	    // 카카오 API 호출을 위한 요청 헤더 설정
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", "Bearer " + accessToken);

	    // API 호출 및 응답 수신
	    ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);

	    // 응답 결과 확인
	    if (responseEntity.getStatusCode() == HttpStatus.OK) {
	        String userInfoJson = responseEntity.getBody();
	        // JSON 형식의 응답에서 필요한 사용자 정보 추출하여 처리
	        JSONObject jsonObject = new JSONObject(userInfoJson);
	        
	        // 사용자 이름 추출
	        String userName = jsonObject.getJSONObject("properties").getString("nickname");
	        model.addAttribute("userName", userName);
	        
	        // 이메일 주소가 있는지 확인
	        if (jsonObject.getJSONObject("kakao_account").has("email")) {
	            // 이메일 주소 추출
	            String email = jsonObject.getJSONObject("kakao_account").getString("email");
	            model.addAttribute("email", email);
	        } else {
	            model.addAttribute("email", "Email not provided");
	        }
	        
	        // 프로필 사진 URL 추출
	        String profileImageUrl = jsonObject.getJSONObject("properties").getString("profile_image");
	        model.addAttribute("profileImageUrl", profileImageUrl);

	        // userinfo.jsp로 포워딩
	        return "userinfo";
	    } else {
	        // API 호출에 실패한 경우에 대한 예외 처리
	        return "Failed to retrieve user information.";
	    }
	}


	
}