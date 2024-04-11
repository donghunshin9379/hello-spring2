package hello.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import hello.spring.data.dto.MemberDTO;
import hello.spring.data.dto.PrincipalDetails;
import hello.spring.entity.MemberEntity;
import hello.spring.repository.MemberRepository;

//SpringSecurity(/auth)에서 UserDetailsServic참조된 서비스를 자동으로 찾아 진행시킴
public class PrincipalDetailsService implements UserDetailsService {
	@Autowired
	private MemberRepository memberRepository;
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		MemberEntity memberEntity = memberRepository.findByUserId(userId);

		// 비밀번호 암호화(passwordEncoder)
		// memberDTO에서 가져온 비밀번호를 암호화된 비밀번호로 다시 설정

		MemberDTO member = new MemberDTO(memberEntity.getUserId(), passwordEncoder.encode(memberEntity.getPassword()),
				passwordEncoder.encode(memberEntity.getPasswordCheck()), memberEntity.getUserName(), memberEntity.getBirthday(),
				memberEntity.getEmail(), memberEntity.getPhone(), memberEntity.getAddress(), memberEntity.getGender(),
				memberEntity.getRole());
		if (member != null) {
			return new PrincipalDetails(member); // 권한을 부여함
		}
		return null;
	}

}
