package hello.spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "member") //DB 속성을 담는게 ENtity(field값)
public class MemberEntity {

	@Id
	String userId;
	
	String password;
	
	String passwordCheck;
	
	String userName;
	
	String birthday;
	  
    String email;
  
    String phone;
  
    String address;
  
    String gender;

	  
}
