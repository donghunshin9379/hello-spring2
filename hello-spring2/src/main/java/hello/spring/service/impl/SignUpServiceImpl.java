package hello.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import hello.spring.data.dto.MemberDTO;
import hello.spring.entity.MemberEntity;
import hello.spring.handler.MemberDataHandler;
import hello.spring.service.SignUpService;
import jakarta.persistence.EntityManager;

@Service
@Component
public class SignUpServiceImpl implements SignUpService {
	  private final MemberDataHandler memberDataHandler; 

	  @Autowired
	  public SignUpServiceImpl(MemberDataHandler memberDataHandler) {
		  this.memberDataHandler = memberDataHandler;
	  }
	  
	  @Override
	  public MemberDTO saveMember(MemberDTO memberDTO) {
		  MemberEntity memberEntity = memberDataHandler.saveMemberEntity(memberDTO);
		  MemberDTO memberDTO2 = new MemberDTO(memberEntity.getUserId(),
				  	memberEntity.getPassword(), memberEntity.getPasswordCheck(),
				  	memberEntity.getUserName(), memberEntity.getBirthday(),
				  	memberEntity.getEmail(), memberEntity.getPhone(),
				  	memberEntity.getAddress(), memberEntity.getGender());
		  return memberDTO2;
	  }
	  
	  
}
