package hello.spring.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import hello.spring.data.dto.MemberDTO;
import hello.spring.service.SignUpService;

@Component
public class AuthProvider implements AuthenticationProvider {
	 private static final Logger logger = LoggerFactory.getLogger(AuthProvider.class);
	 
	    @Autowired
	    private SignUpService signUpService;

	    @Override
	    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	        String userId = (String) authentication.getPrincipal();   // 로그인 창에 입력한 userId
	        String password = (String) authentication.getCredentials(); // 로그인 창에 입력한 password

	        PasswordEncoder passwordEncoder = signUpService.passwordEncoder();
	        UsernamePasswordAuthenticationToken token;
	        MemberDTO memberDTO = signUpService.getMemberByUserId(userId);

	        if (memberDTO != null && passwordEncoder.matches(password, memberDTO.getPassword())) { // 일치하는 user 정보가 있는지 확인
	        	List<GrantedAuthority> roles = new ArrayList<>();
	        	roles.add(new SimpleGrantedAuthority("ROLE_USER")); // 권한 부여
	        	
	        	logger.info("roles : {}",roles);
	            token = new UsernamePasswordAuthenticationToken(memberDTO.getUserId(), null, roles);
	            // 인증된 user 정보를 담아 SecurityContextHolder에 저장되는 token
	            
	            logger.info("memberDTO 정보 : {} ", memberDTO.toString());
	            if(memberDTO.getUserId().equals("admin")){
	                roles.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // 권한 부여
	            }
	            return token;
	            }
	        
	       // ----------------------------------------------------------
//	            
//	            if(memberVo.getAuthCd().equals("2")){
//	                roles.add(new SimpleGrantedAuthority("ROLE_USER")); // 권한 부여
//	            }
	        
	            
	        	// if 반대 방향 던짐
	        throw new BadCredentialsException("No such user or wrong password.");
	        // Exception을 던지지 않고 다른 값을 반환하면 authenticate() 메서드는 정상적으로 실행된 것이므로 인증되지 않았다면 Exception을 throw 해야 한다.
	    }

	    @Override
	    public boolean supports(Class<?> authentication) {
	        return authentication.equals(UsernamePasswordAuthenticationToken.class);
	    }
	}
