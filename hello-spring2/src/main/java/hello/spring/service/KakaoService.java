package hello.spring.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class KakaoService {
	private static final Logger logger = LoggerFactory.getLogger(KakaoService.class);
	
	  public String getAccessTokenFromKakao(String clientId, String code) throws IOException {
	        //------카카오 POST 요청------
	        String reqURL = "https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id="+clientId+"&code=" + code;
	        URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");


	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

	        String line = "";
	        String result = "";

	        while ((line = br.readLine()) != null) {
	            result += line;
	        }

	        ObjectMapper objectMapper = new ObjectMapper();
	        Map<String, Object> jsonMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
	        });

	        logger.info("Response Body : " + result);

	        String accessToken = (String) jsonMap.get("access_token");
	        String refreshToken = (String) jsonMap.get("refresh_token");
	        String scope = (String) jsonMap.get("scope");


	        logger.info("Access Token : " + accessToken);
	        logger.info("Refresh Token : " + refreshToken);
	        logger.info("Scope : " + scope);


	        return accessToken;
	    }
}
