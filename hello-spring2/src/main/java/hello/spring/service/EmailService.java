package hello.spring.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
	public void sendEmail(String email, String code);
}
