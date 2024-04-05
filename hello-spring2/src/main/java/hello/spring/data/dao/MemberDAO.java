package hello.spring.data.dao;
import hello.spring.entity.MemberEntity;

public interface MemberDAO {
	MemberEntity saveMember(MemberEntity memberEntity);
	MemberEntity getMemberByUserId(String userId);
	boolean isUserIdExists(String userId);
}
