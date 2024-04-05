package hello.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.spring.entity.MemberEntity;

											//레포짓터리가 사용할 Entity, primary key
public interface MemberRepository extends JpaRepository<MemberEntity, String> {

	MemberEntity findByUserId(String userId);
	boolean existsByUserId(String userId);
}
