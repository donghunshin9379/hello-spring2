package hello.spring.handler;

import hello.spring.data.dto.MemberDTO;
import hello.spring.entity.MemberEntity;

public interface MemberDataHandler {
	 MemberEntity saveMemberEntity(MemberDTO memberDTO);
	 
}