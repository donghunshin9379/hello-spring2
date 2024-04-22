package hello.spring.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor // 생성자 자동생성(필드값넣어서)
@NoArgsConstructor // 생성자 자동생성 X
public class MemberDTO {
	private String userId;

	private String password;

	private String passwordCheck;

	private String userName;

	private String birthday;

	private String email;

	private String phone;

	private String address;

	private String gender;

	private String role;
}
