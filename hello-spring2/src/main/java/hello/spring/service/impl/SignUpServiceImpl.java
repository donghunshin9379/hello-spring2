package hello.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import hello.spring.data.dto.MemberDTO;
import hello.spring.entity.MemberEntity;
import hello.spring.handler.MemberDataHandler;
import hello.spring.service.SignUpService;

@Service
@Component
public class SignUpServiceImpl implements SignUpService {
	private final MemberDataHandler memberDataHandler;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public SignUpServiceImpl(MemberDataHandler memberDataHandler, PasswordEncoder passwordEncoder) {
		this.memberDataHandler = memberDataHandler;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public MemberDTO saveMember(MemberDTO memberDTO) {
		// 비밀번호 암호화(passwordEncoder)
		String encodedPassword = passwordEncoder.encode(memberDTO.getPassword());
		String encodedPasswordCheck = passwordEncoder.encode(memberDTO.getPasswordCheck());
		// memberDTO에서 가져온 비밀번호를 암호화된 비밀번호로 다시 설정
		memberDTO.setPassword(encodedPassword);
		memberDTO.setPasswordCheck(encodedPasswordCheck);

		// dataHandler값을 받아서 entity에 넣음
		MemberEntity memberEntity = memberDataHandler.saveMemberEntity(memberDTO);
		MemberDTO memberDTO2 = new MemberDTO(memberEntity.getUserId(), memberEntity.getPassword(),
		memberEntity.getPasswordCheck(), memberEntity.getUserName(), memberEntity.getBirthday(),
		memberEntity.getEmail(), memberEntity.getPhone(), memberEntity.getAddress(), memberEntity.getGender(),
		memberEntity.getRole());
		return memberDTO2;
	}

	@Override
	public MemberDTO getMemberByUserId(String userId) {
		// 클래스 객체에 담아서 보냄
		MemberEntity memberEntity = memberDataHandler.getMemberByUserId(userId);
		MemberDTO member = new MemberDTO(memberEntity.getUserId(), memberEntity.getPassword(),
		memberEntity.getPasswordCheck(), memberEntity.getUserName(), memberEntity.getBirthday(),
		memberEntity.getEmail(), memberEntity.getPhone(), memberEntity.getAddress(), memberEntity.getGender(),
		memberEntity.getRole());
		return member;
	}

	@Override
	public boolean isUserIdExists(String userId) {
		// memberDataHandler를 통해 userId가 이미 존재하는지 확인 후 리턴
		return memberDataHandler.isUserIdExists(userId);
	}

	@Override
	public PasswordEncoder passwordEncoder() { // Singletone으로 이루어져있으니까(이미 있는 하나의 객체를 사용함)
		return this.passwordEncoder;
	}

}
