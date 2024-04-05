package hello.spring.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.spring.data.dao.MemberDAO;
import hello.spring.data.dto.MemberDTO;
import hello.spring.entity.MemberEntity;
import hello.spring.handler.MemberDataHandler;
import jakarta.transaction.Transactional;

@Service
@Transactional //DATA CRUD 실패시 ROLLBACK용
public class MemberDataHandlerImpl implements MemberDataHandler{
	  MemberDAO memberDAO;
	 
	  @Autowired
	  public MemberDataHandlerImpl(MemberDAO memberDAO){
	    this.memberDAO = memberDAO;
	  }

	  public MemberEntity saveMemberEntity(MemberDTO memberDTO) {
		  MemberEntity memberEntity = new MemberEntity(memberDTO.getUserId(),
				  	memberDTO.getPassword(), memberDTO.getPasswordCheck(),
				  	memberDTO.getUserName(), memberDTO.getBirthday(),
				  	memberDTO.getEmail(), memberDTO.getPhone(),
				  	memberDTO.getAddress(), memberDTO.getGender());

	    return memberDAO.saveMember(memberEntity);
	  }
	  
	  public MemberEntity getMemberByUserId(String userId) {
		  
		return memberDAO.getMemberByUserId(userId);
	  }

	  @Override
	  public boolean isUserIdExists(String userId) {
		  
		return memberDAO.isUserIdExists(userId);
	  }

	  
}
