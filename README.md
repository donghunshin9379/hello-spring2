# :pushpin: Spring Security를 이용하여 회원가입 및 로그인 구현
> 상세기능: 회원가입, 중복 아이디 처리, 비밀번호, 비밀번호 확인 체크 및 비밀번호 암호화, 로그인 + 로그인 비밀번호 암호화

</br>

## 1. 제작 기간 & 참여 인원 & 나의 역할
  - 2024년 3월 18일 ~ 5월 8일
  - 개인 프로젝트
  - 회원가입(중복처리), 로그인(비밀번호 암호화) 구현
  --추가-- (05/08)
  - 회원가입 이메일 인증
  - 카카오 로그인 api

</br>

## 2. 사용 기술

#### '언어 및 프레임워크'
- Java: Spring Boot (Spring MVC, Spring Data JPA), Spring Security, Gradle
- ORM(Object-Relational Mapping): Hibernate
- Servlet 컨테이너: Apache Tomcat
 
#### '데이터 베이스'
- 관계형 데이터베이스: MariaDB
- HeidiSQL을 통한 MariaDB 데이터베이스 관리 및 개발

#### '웹 개발 및 템플릿 엔진'
- JSP(JavaServer Pages) 및 JSTL(JavaServer Pages Standard Tag Library)

#### '의존성 주입 및 코드 간소화'
- Project Lombok: 자동 생성자, getter/setter 등을 위한 어노테이션을 사용하여 코드를 간소화
- Spring Boot Starter Mail : 메일 전송 기능을 제공합니다.
- Jackson Databind : JSON 데이터를 자바 객체로 매핑하는 데 사용됩니다.
- JSON : JSON 데이터를 처리하기 위한 라이브러리입니다.

#### '보안 및 인증'
- Spring Security를 사용한 인증 및 권한 부여
- AuthenticationProvider를 구현하여 사용자 인증을 커스터마이징
- Kakao Login: 카카오 로그인을 통한 사용자 인증 기능을 구현

#### '형상관리'
- Git을 사용하여 소스 코드의 버전 관리 
</br>



## 3. 핵심 기능
- 회원가입 기능

사용자가 회원가입 양식을 작성하여 제출합니다.
컨트롤러(Controller)는 회원가입 요청을 처리하고, 입력된 사용자 정보를 데이터베이스에 저장합니다.
비밀번호는 (raw)으로 저장되는 것이 아니라, Spring Security의 PasswordEncoder를 사용하여 암호화된 상태로 저장됩니다.
만약 동일한 아이디가 이미 데이터베이스에 존재한다면, 사용자에게 알림을 주는 기능이 포함됩니다. 
이를 위해 JavaScript를 사용하여 Alert 메시지를 표시합니다.
<details>
  <summary>상세설명 펼치기</summary>
  
  ![home](https://github.com/donghunshin9379/hello-spring2/assets/139945914/3702abb1-f63d-4d9d-a911-a81a17fc2730)
  
  상단 이미지는 홈 화면 입니다. 홈화면에서 회원가입으로 이동합니다.

  ![signup](https://github.com/donghunshin9379/hello-spring2/assets/139945914/d2351628-730b-4dcc-a76c-f7ec40436bff)
  
  상단 이미지는 회원가입 화면(signUp.jsp)입니다. 회원가입을 위한 데이터 입력 후 요청이 들어오게 됩니다.
  
  아래 코드는 SignUpController 클래스 내부의 doSignUp 메소드 비즈니스 로직입니다.
  
  ```

@PostMapping("/doSignUp")
public String doSignUp(MemberDTO memberDTO, Model model) {
String userId = memberDTO.getUserId();

boolean result = signUpService.isUserIdExists(userId);
  
if (result == true) {
model.addAttribute("errorMessage", "이미 사용 중인 아이디입니다. 다른 아이디를 선택해주세요.");
return "signUp"; // 다시 회원가입 페이지로 이동 (redirect:signUp)
	
} else {
signUpService.saveMember(memberDTO);
model.addAttribute("signUpSuccess", "회원가입이 완료 되었습니다.");
return "home";
}
}
```

위의 코드에서 중복되는 아이디가 없을 경우 signUpService.saveMember(memberDTO); 가 호출되며 home.jsp로 signUpSuccess 메세지가 전달됩니다.

다음은 signUpSerbice.saveMember 비즈니스 로직입니다.
아래 코드를 확인했을 때, 파라미터 값으로 받은 memberDTO의 비밀번호를 암호화 세팅시킨 이후
memberDataHandler.saveMemberEntity(memberDTO); 를 사용하여 memberDTO로 반환된 값을 기반으로 MemberEntity 객체를 생성합니다.
그리고 MemberEntity에 저장된 정보를 이용하여 새로운 memberDTO2를 반환하는데 이 객체에는 DB에 저장된 회원정보가 담겨 있습니다.
```
@Override
public MemberDTO saveMember(MemberDTO memberDTO) {
//비밀번호 암호화(passwordEncoder)
String encodedPassword = passwordEncoder.encode(memberDTO.getPassword());
String encodedPasswordCheck = passwordEncoder.encode(memberDTO.getPasswordCheck());

memberDTO.setPassword(encodedPassword);
memberDTO.setPasswordCheck(encodedPasswordCheck);

MemberEntity memberEntity = memberDataHandler.saveMemberEntity(memberDTO);
MemberDTO memberDTO2 = new MemberDTO(memberEntity.getUserId(),
memberEntity.getPassword(), memberEntity.getPasswordCheck(),
memberEntity.getUserName(), memberEntity.getBirthday(),
memberEntity.getEmail(), memberEntity.getPhone(),
memberEntity.getAddress(), memberEntity.getGender(), memberEntity.getRole());
return memberDTO2;
}
```
더 상세하게 보면 SignUpService > MemberDataHandler > MemberDAO > MemberRepository  순으로 진행되는데, 순서대로 코드를 확인하면 
아래와 같은 순서로 진행됩니다.

MemberDataHandler 코드
```
public MemberEntity saveMemberEntity(MemberDTO memberDTO) {
MemberEntity memberEntity = new MemberEntity(memberDTO.getUserId(),
memberDTO.getPassword(), memberDTO.getPasswordCheck(),
memberDTO.getUserName(), memberDTO.getBirthday(),
memberDTO.getEmail(), memberDTO.getPhone(),
memberDTO.getAddress(), memberDTO.getGender(), memberDTO.getRole());
return memberDAO.saveMember(memberEntity);
}
```
MemberDAO 코드
```
@Override
public MemberEntity saveMember(MemberEntity memberEntity) {
  memberRepository.save(memberEntity);
  return memberEntity;
}
```

MemberRepository 인터페이스
```
//레포짓터리가 사용할 Entity, primary key
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
}
```
위와 같은 방식으로
SignUpService는 MemberDataHandler를 호출하여 회원가입 프로세스를 시작하지만, 
동시에 MemberDataHandler는 SignUpService로부터 호출되어 회원가입 요청을 처리합니다. 
이렇게 각 계층은 서로의 메서드를 호출하고 서로의 기능을 사용하여 작업을 수행합니다.

이 과정은 중복되는 아이디가 없고, 비밀번호 확인이 일치하는 경우라고 가정했을 때 상황입니다. 

![signUpSuccess](https://github.com/donghunshin9379/hello-spring2/assets/139945914/fd2603ff-d9f3-4e91-9e10-840809c8e4a1)

최종적으로 회원가입이 완료됩니다.

하지만 만약 중복되는 아이디가 존재 하는 경우라면?
```
@PostMapping("/doSignUp")
public String doSignUp(MemberDTO memberDTO, Model model) {
String userId = memberDTO.getUserId();

boolean result = signUpService.isUserIdExists(userId);
  
if (result == true) {
model.addAttribute("errorMessage", "이미 사용 중인 아이디입니다. 다른 아이디를 선택해주세요.");
return "signUp"; // 다시 회원가입 페이지로 이동 (redirect:signUp)
}
}
	
```
위와 같이 앞단(signUp.jsp)에서 입력된 아이디값을 기반으로 signUpService.isUserIdExists(userId); 가 호출되며 return된 값이
boolean 타입 result에 저장됩니다.

이때 isUserIdExists(userId);는 최종적으로 existsByUserId(String userId); 를 호출하여
중복 아이디 존재 여부에 따라 true/false를 반환합니다.
아래는 회원 가입 프로세스의 마지막 단계인 데이터베이스 조회 및 확인 작업을 처리하는 코드입니다.
```
public interface MemberRepository extends JpaRepository<MemberEntity, String> {

boolean existsByUserId(String userId);
}
```

중복된 아이디가 있는 경우 사용자 화면입니다.
![joongbok](https://github.com/donghunshin9379/hello-spring2/assets/139945914/4b755697-8188-4362-9222-bd24aa6e5826)

</details>

### 3.1. 
- 비밀번호 일치 확인 기능

회원가입 양식에는 비밀번호와 비밀번호 확인란이 존재합니다.
사용자가 이 두 필드에 입력한 값이 서로 일치하는지 확인하는 기능이 구현됩니다.
비밀번호와 비밀번호 확인란이 일치하지 않을 경우, JavaScript와 jQuery를 사용하여 사용자에게 메시지를 표시하여 입력 오류를 알려줍니다.
<details>
  <summary>상세설명 펼치기</summary>
  
  ![passwordCheck](https://github.com/donghunshin9379/hello-spring2/assets/139945914/d8e653e5-f431-4091-b55f-eb89958d338a)
  
  상단 이미지는 !비밀먼호.equals(비밀번호확인) 일 경우 때 화면입니다. 
  
  실행과정 설명
  사용자 회원가입 화면(signUp.jsp)에서 입력한 비밀번호 값과 비밀번호 확인 일치여부에 따라 달라지는 로직입니다.
  
  (설명을 위해 잘라온 코드입니다)
  ```
$(document).ready(function() {
$("#passwordCheck").blur(function(){
if($("#passwordCheck").val() === $("#password").val()){
    $(".successPwChk").text("비밀번호가 일치합니다.").css("color", "green");
    $("#pwDoubleChk").val("true");
} else {
    $(".successPwChk").text("비밀번호가 일치하지 않습니다.").css("color", "red");
    $("#pwDoubleChk").val("false");
}
});
  ```
위와같이 비밀번호확인 값 입력후, 포커스를 벗어날 때(마우스 클릭 or 다음으로 넘어가는 상황) 실행되는데
이때 입력된 password값과 passwordCheck 값이 비교됩니다.
값이 일치하는 경우, 일치하지않는 경우 메세지가 css 컬러와 함께 설정됩니다.

![passwordCheck2](https://github.com/donghunshin9379/hello-spring2/assets/139945914/b8c63432-7f57-4475-b627-381047dab50a)
</details>

### 3.2. 
- 로그인 기능

사용자는 로그인 페이지에 접근하여 로그인 양식을 작성합니다.
컨트롤러는 사용자가 입력한 로그인 정보를 받아와 인증을 수행합니다.
Spring Security를 이용하여 사용자가 제공한 인증 정보와 데이터베이스에 저장된 암호화된 비밀번호를 비교하여 사용자를 인증합니다.
로그인에 성공하면 사용자의 인증 정보가 토큰에 저장되고, 보호된 페이지에 접근 할 수 있게 됩니다.
<details>
  <summary>상세 설명 펼치기</summary>
  아래 이미지는 로그인 사용자 화면입니다.
  
  ![login](https://github.com/donghunshin9379/hello-spring2/assets/139945914/e2f85ae6-7dba-4ce7-8f1c-b1664174fb33)

  상단 사용자 화면에서 아이디값과 비밀번호값을 입력한 후, Login 버튼을 누르면 아래의 SpringSeucirtyConfig 클래스를 통해
  로그인 절차가 진행됩니다.
  ```
//Security main
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

http
.csrf(AbstractHttpConfigurer::disable) 
.authorizeHttpRequests(authorizeRequest -> authorizeRequest 
.requestMatchers("/css/**","/js/**","/img/**","/fonts/**","/","/login", "/join/**", "/home").permitAll()
.requestMatchers("/error/**").permitAll()
.anyRequest().permitAll() 
)
.formLogin((formLogin) ->
formLogin
.loginPage("/login") // 로그인 url
.usernameParameter("userId")
.passwordParameter("password")
.loginProcessingUrl("/auth")// 인증절차(로그인 처리) url
.defaultSuccessUrl("/loginSuccess",true) // 로그인 성공 url
)
.logout((logoutConfig) ->
logoutConfig.logoutSuccessUrl("/logoutGo") // 로그아웃 시 url
);
return http.build();
}
```

위의 코드에서 로그인 인증절차인 .loginProcessingUrl("/auth")가 호출되면 SpringSecurity에서 자동으로 UserDetailsService 인터페이스 IOC를 찾게 되는데
기존에 존재하는 UserDetailsService의 loadUserByUsername을 함수를 실행해도 무방했지만, 회원가입을 관장하는 MemberDTO 객체와 MemberEntity 객체 사용을 위해
커스터마이징 한 PrincipalDetailsService 입니다.
 
```
public class PrincipalDetailsService implements UserDetailsService {
@Autowired
private MemberRepository memberRepository;
private PasswordEncoder passwordEncoder;

@Override
public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
MemberEntity memberEntity = memberRepository.findByUserId(userId);

MemberDTO member = new MemberDTO(memberEntity.getUserId(), passwordEncoder.encode(memberEntity.getPassword()),
passwordEncoder.encode(memberEntity.getPasswordCheck()), memberEntity.getUserName(), memberEntity.getBirthday(),
memberEntity.getEmail(), memberEntity.getPhone(), memberEntity.getAddress(), memberEntity.getGender(),
memberEntity.getRole());
if (member != null) {
	return new PrincipalDetails(member); // 권한을 부여함
}
return null;
}

}
```

위와 같이 MemberRepository를 사용하여 DB에서 사용자 정보를 조회 > 비밀번호 암호화(passwordEncoder) > loadUserByUsername 메소드로 가져온 사용자 정보를 기반으로
PrincipalDetails 객체가 반환(생성)됩니다. 동시에 Spring Security 내부에서 해당 객체의 사용자 정보를 기반으로 Authentication 객체가 만들어 집니다.
만들어진 Authentication 객체를 기반으로 AuthProvider 내의 Authentication authenticate을 실행합니다. 

</details>

### 3.3.
- 토큰 반환 방식

Spring Security 설정에서 사용자가 로그인한 후에는 인증된 사용자에게 토큰을 반환하는 방식으로 동작하도록 구성됩니다.
Spring Security의 설정 파일(SecurityConfig 클래스)에는 인증이 성공했을 때 토큰을 반환하는 필터가 포함됩니다.
이 필터는 인증이 완료되면 JWT(Json Web Token) 또는 다른 형식의 인증 토큰을 생성하여 사용자에게 반환합니다.
클라이언트는 이 토큰을 저장하고 보호된 엔드포인트에 요청할 때마다 이를 함께 전송하여 인증을 유지합니다.
<details>
  <summary>상세설명 펼치기</summary>
  아래 코드는 Authentication 객체를 통해 토큰을 return 하는 과정입니다.

```
@Component
public class AuthProvider implements AuthenticationProvider {
private static final Logger logger = LoggerFactory.getLogger(AuthProvider.class);

@Autowired
private SignUpService signUpService;

@Override
public Authentication authenticate(Authentication authentication) throws AuthenticationException {
String userId = (String) authentication.getPrincipal();
String password = (String) authentication.getCredentials(); 

PasswordEncoder passwordEncoder = signUpService.passwordEncoder();
UsernamePasswordAuthenticationToken token;

MemberDTO memberDTO = signUpService.getMemberByUserId(userId);

if (memberDTO != null && passwordEncoder.matches(password, memberDTO.getPassword())) { 
List<GrantedAuthority> roles = new ArrayList<>();
roles.add(new SimpleGrantedAuthority("ROLE_USER"));

token = new UsernamePasswordAuthenticationToken(memberDTO.getUserId(), null, roles);

if (memberDTO.getUserId().equals("admin")) {
	roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
}
return token;
}

throw new BadCredentialsException("No such user or wrong password.");
}

@Override
public boolean supports(Class<?> authentication) {
	return authentication.equals(UsernamePasswordAuthenticationToken.class);
}
}

```
위 코드와 같이 토큰이 return 되면 Spring Security는 로그인이 성공했음을 인식하고 .defaultSuccessUrl("/loginSuccess",true)에 지정했던
loginSuccess를 true로 설정하며 전달되며 로그인 처리가 완료됩니다.

로그인 성공 화면
![loginSuccess2](https://github.com/donghunshin9379/hello-spring2/assets/139945914/0a9850c2-8561-47e7-a869-74c902566315)

로그아웃은 Spring SecurityConfig의  .logout((logoutConfig)logoutConfig.logoutSuccessUrl("/logoutGo")을 통해 구현됩니다.

로그아웃 성공 화면
![logout](https://github.com/donghunshin9379/hello-spring2/assets/139945914/4e6d52c3-c061-4c28-976c-4460bca1f14e)

</details>
 
### 3.4. 
- 비밀번호 암호화
  
사용자의 비밀번호는 암호화하여 DB에 저장됩니다.
Spring Security의 PasswordEncoder 인터페이스를 구현한 BCryptPasswordEncoder 등을 사용하여 비밀번호를 해시화하고 저장합니다.
해시 함수를 통해 생성된 암호화된 비밀번호는 일방향으로만 변환되므로 원본 비밀번호는 복원할 수 없습니다. 따라서 보안성이 향상됩니다.
<details>
  <summary>상세설명 펼치기</summary>
  PasswordEncoder를 필요로 하는 계층에 다시 할당할 수 없도록 private final PasswordEncoder passwordEncoder; 선언 이후
  encode기능을 활용합니다. 예를 들어 회원가입 기능인 saveMember에서 적용시키면 아래와 같이 진행됩니다.

  ```
@Override
public MemberDTO saveMember(MemberDTO memberDTO) {
String encodedPassword = passwordEncoder.encode(memberDTO.getPassword());
String encodedPasswordCheck = passwordEncoder.encode(memberDTO.getPasswordCheck());
memberDTO.setPassword(encodedPassword);
memberDTO.setPasswordCheck(encodedPasswordCheck);

MemberEntity memberEntity = memberDataHandler.saveMemberEntity(memberDTO);
MemberDTO memberDTO2 = new MemberDTO(memberEntity.getUserId(),
memberEntity.getPassword(), memberEntity.getPasswordCheck(),
memberEntity.getUserName(), memberEntity.getBirthday(),
memberEntity.getEmail(), memberEntity.getPhone(),
memberEntity.getAddress(), memberEntity.getGender(), memberEntity.getRole());
return memberDTO2;
	  }
```
암호화된 비밀번호 확인 시점
![passwordEncoder](https://github.com/donghunshin9379/hello-spring2/assets/139945914/c20b9a0a-dc64-4c29-9046-970bbddb1e26)
</details>

### 4. 추가적인 방향, 계획
- 앞으로 프로젝트를 진행하면서 기본적으로 알아야 할 개념, 지식을 꾸준히 공부하고 짜임새 있게 프로젝트를 만들고 테스트해보며 실습 경험을 쌓아야겠습니다.

-------추가된 기능--------
- 카카오톡 API를 이용한 로그인
- 회원가입 이메일 인증
- 회원정보 수정

### 5. 추가된 기능
### 5.1. 카카오 로그인 api 
카카오 로그인 사용자 뷰
![kakaologin](https://github.com/donghunshin9379/hello-spring2/assets/139945914/37409392-4288-4c2c-aa33-34948631ff3c)
카카오 로그인 URL을 클릭하면 카카오 OAuth 2.0 인증 서버에 발급받았던 클라이언트 ID와 함께 리디렉션할 URL이 서비스 됩니다.

<details>
  <summary>상세설명 펼치기</summary>
	
  ```
  <div>
  <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=0c0bf7f540364c14e4b82afa45e09fe2&redirect_uri=http://localhost:8060/callback">카카오 로그인</a>
  </div>
  ```
</details>

리디렉션 이후 KakaoLoginController의 callback 이 호출됩니다. callback은 다음과 같이 동작됩니다

String clientId = "0c0bf7f540364c14e4b82afa45e09fe2";

클라이언트 아이디를 설정합니다. 이 값은 카카오 개발자 애플리케이션을 등록할 때 발급받은 것입니다.
String accessToken = kakaoService.getAccessTokenFromKakao(clientId, code);

kakaoService를 사용하여 인증 코드와 클라이언트 아이디를 통해 액세스 토큰을 받아옵니다. getAccessTokenFromKakao 메서드는 카카오 서버에 요청을 보내서
인증 코드를 액세스 토큰으로 교환합니다.

return "redirect:/userinfo?accessToken=" + accessToken;

액세스 토큰을 URL 매개변수로 포함하여 "/userinfo" 엔드포인트로 리디렉션합니다. 이 엔드포인트에서 사용자 정보를 처리할 수 있습니다.

<details>
  <summary>상세설명 펼치기</summary>
	
```
@GetMapping("/callback")
public String callback(@RequestParam("code") String code) throws IOException {
String clientId = "0c0bf7f540364c14e4b82afa45e09fe2";
String accessToken = kakaoService.getAccessTokenFromKakao(clientId, code);
return "redirect:/userinfo?accessToken=" + accessToken;
}
```
</details>

인증 코드 > 액세스 토큰 교환 과정 (KakaoService 클래스) 

getAccessTokenFromKakao 메소드는 매개변수로 clientId, code (인증코드)를 받습니다.
액세스 토큰을 요청할 URL을 만들고 생성한 URL로 POST 요청 > 카카오서버 응답 읽기 > 응답 데이터 JSON 파싱 (추출) > 추출한 액세스 토큰 반환

<details>
  <summary>상세설명 펼치기</summary>
	
```
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
return accessToken;
}

}

```
</details>

위의 과정으로 반환된 토큰이 userinfo로 함께 리다이렉트 요청 됩니다. 토큰을 기반으로 카카오 api를 이용하여 유저 정보를 불러올 수 있습니다.

![kakaoUserinfo](https://github.com/donghunshin9379/hello-spring2/assets/139945914/b11daea6-fe7e-4f01-b8da-b2d74fdb411a)

accessToken 파라미터를 받고 카카오api 요청 > 응답 후 JSON 형식의 사용자 정보 반환 > 반환된 정보를 Model 객체에 추가 후 userinfo.jsp로 전달됩니다.
<details>
  <summary>상세설명 펼치기</summary>
	
  ```
@GetMapping("/userinfo")
public String getUserInfo(@RequestParam("accessToken") String accessToken, Model model) {

String apiUrl = "https://kapi.kakao.com/v2/user/me";

RestTemplate restTemplate = new RestTemplate();

HttpHeaders headers = new HttpHeaders();
headers.set("Authorization", "Bearer " + accessToken);

ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);

if (responseEntity.getStatusCode() == HttpStatus.OK) {
String userInfoJson = responseEntity.getBody();
JSONObject jsonObject = new JSONObject(userInfoJson);

String userName = jsonObject.getJSONObject("properties").getString("nickname");
model.addAttribute("userName", userName);

if (jsonObject.getJSONObject("kakao_account").has("email")) {
    String email = jsonObject.getJSONObject("kakao_account").getString("email");
    model.addAttribute("email", email);
} else {
    model.addAttribute("email", "Email not provided");
}

String profileImageUrl = jsonObject.getJSONObject("properties").getString("profile_image");
model.addAttribute("profileImageUrl", profileImageUrl);

return "kakaouserInfo";
} else {
return "Failed to retrieve user information.";
}
}

  ```
</details>


