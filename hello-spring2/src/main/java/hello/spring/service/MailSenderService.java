package hello.spring.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public interface MailSenderService extends MailSender {

	@Override
	default void send(SimpleMailMessage... simpleMessages) throws MailException {

	}

}
