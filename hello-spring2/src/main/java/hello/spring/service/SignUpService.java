package hello.spring.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import hello.spring.data.dto.MemberDTO;

@Service
@Component
public interface SignUpService {

  public MemberDTO saveMember(MemberDTO memberDTO);
  
  public MemberDTO getMemberByUserId(String userId);
  
  public PasswordEncoder passwordEncoder();

  public boolean isUserIdExists(String userId);
  
}
