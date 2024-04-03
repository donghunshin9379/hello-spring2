package hello.spring.data.dao;

import hello.spring.entity.MemberEntity;

public interface MemberDAO {
	MemberEntity saveMember(MemberEntity memberEntity);
}
