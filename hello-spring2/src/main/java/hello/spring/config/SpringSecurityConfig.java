package hello.spring.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SpringSecurityConfig {

	// private final static Logger logger =
	// LoggerFactory.getLogger(SecurityConfig.class);
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Security main(권한에 따른 HTTP 요청!!!!!!!!)
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.csrf(AbstractHttpConfigurer::disable) // CSRF 보안 관련 내용
	        .authorizeHttpRequests(authorizeRequest -> authorizeRequest // 권한 부여
	            .requestMatchers("/admin").hasRole("ADMIN") // ROLE_ADMIN 권한을 가진 사용자에게만 admin.jsp에 접근 허용
	            .requestMatchers("/admin/**", "/js/**", "/img/**", "/fonts/**", "/", "/login", "/join/**", "/home").permitAll() // 전체 권한 가능
	            .requestMatchers("/error/**").permitAll() // 에러 페이지 권한 설정
	            .anyRequest().permitAll() // 나머지 요청은 모두 허용
	        )
	        .formLogin(formLogin -> formLogin.loginPage("/login") // 로그인 페이지 URL
	            .usernameParameter("userId").passwordParameter("password").loginProcessingUrl("/auth") // 로그인 처리 URL
	            .defaultSuccessUrl("/loginSuccess", true) // 로그인 성공 시 URL
	        )
	        .logout(logoutConfig -> logoutConfig.logoutSuccessUrl("/logoutGo")); // 로그아웃 시 URL

	    return http.build();
	}
}
//Exception //만들어야댐 (핸들러)
//.exceptionHandling(
//		httpSecurityExceptionHandlingConfigurer ->
//		httpSecurityExceptionHandlingConfigurer
//		.authenticationEntryPoint(authenticationAuthenticationEntryPoint) // 401
//		.accessDeniedHandler(authenticationAccessDeniedHandler) // 403
//		.accessDeniedPage("/home") // 403 - 메인으로
//		)
