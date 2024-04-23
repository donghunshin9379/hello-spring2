package hello.spring.data.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import hello.spring.data.dao.MemberDAO;
import hello.spring.entity.MemberEntity;
import hello.spring.repository.MemberRepository;

@Service
public class MemberDAOImpl implements MemberDAO {

	MemberRepository memberRepository;

	@Autowired
	public MemberDAOImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public MemberEntity saveMember(MemberEntity memberEntity) {
		memberRepository.save(memberEntity);
		return memberEntity;
	}

	@Override
	public MemberEntity getMemberByUserId(String userId) {
		return memberRepository.findByUserId(userId);
	}

	@Override
	public boolean isUserIdExists(String userId) {
		return memberRepository.existsByUserId(userId);
	}

	@Override
	public MemberEntity updateMember(MemberEntity memberEntity) {
		memberRepository.save(memberEntity);
		return memberEntity;
	}
	
	
}
