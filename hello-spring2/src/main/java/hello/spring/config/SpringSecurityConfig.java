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

    // private final static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	// 비밀번호 암호화 해결 안됌!!
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	

    //Security main
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable) //csrf 보안 관련 내용
            .authorizeHttpRequests(authorizeRequest ->
                authorizeRequest // 권한 부여 
                    //.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                    .requestMatchers("/css/**","/js/**","/img/**","/fonts/**","/","/login", "/join/**", "/getMember/").permitAll()  // 전체 권한 가능
                    .requestMatchers("/error/**").permitAll()       // 에러 권한
                    //.requestMatchers("/user/**").hasAnyRole(new String[]{"ADMIN","USER"})         // 해당 권한만 가능하게
                    //.requestMatchers("/admin/**").hasAnyRole(new String[]{"ADMIN"})
                    .anyRequest().permitAll()     // 권한있으면 가능
            )
            .formLogin((formLogin) ->
                formLogin
                    .loginPage("/login")                                      // 로그인 url
                    .usernameParameter("userId")
                    .passwordParameter("password")
                    .loginProcessingUrl("/auth")                                // 인증절차(로그인 처리) url
                    .defaultSuccessUrl("/home",true)       // 로그인 성공 url
                    //.failureHandler(authenticationFailureHandler)
            )
            .logout((logoutConfig) ->
                logoutConfig.logoutSuccessUrl("/")                              // 로그아웃 시 url
            )

        // Exception
            //.exceptionHandling(
               //httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                   //.authenticationEntryPoint(authenticationAuthenticationEntryPoint)   // 401
                    //.accessDeniedHandler(authenticationAccessDeniedHandler)             // 403
                   //.accessDeniedPage("/")                        // 403 - 메인으로
           // )
            ;

        return http.build();
    }
}
