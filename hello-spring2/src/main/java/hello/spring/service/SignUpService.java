package hello.spring.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import hello.spring.data.dto.MemberDTO;

@Service
@Component
public interface SignUpService {

  public MemberDTO saveMember(MemberDTO memberDTO);

}
