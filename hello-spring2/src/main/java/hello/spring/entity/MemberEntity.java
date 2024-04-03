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
@Table(name = "member")
public class MemberEntity {

	@Id
	String userId;
	
	String password;
	
	String passwordCheck;
	
	String userName;
	
	private String birthday;
	  
    private String email;
  
    private String phone;
  
    private String address;
  
    private String gender;

	  
}
