package demoweb.web;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.mail.SimpleMailMessage;

import demoweb.common.MailMessage;
import demoweb.domain.Book;
import demoweb.domain.Student;
import demoweb.test.BaseUnitTest;

public class MainTest extends BaseUnitTest {
	private final Logger logger = LogManager.getLogger(getClass());

	//@Test
	public void testSendEmail() {
		//Send Email Demo
		/********************* Method One - asynchronous *************************/
		/**
		 * This is a showcase of Spring Publisher Framework, not about email. 
		 * Using JavaMailSenderImpl is a good way while sending email.
		 */
		MailMessage message = new MailMessage();
		message.setSubject("Hello! - " + new Date().getTime());
		logger.info("@@@@@@@@@@ [" + Thread.currentThread().getName() + "] begin to send email.");
		new Thread(mailMessageManager.send(message)).start();

		/********************* Method Two - synchronous *************************/
		try {
			SimpleMailMessage mimeMessage = new SimpleMailMessage();
			mimeMessage.setFrom("from@mail.com");
			mimeMessage.setTo("to@mail.com");
			mimeMessage.setSubject("Hello! - " + new Date().getTime());
			mimeMessage.setText("This is a test.");
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			logger.error("send email error! ", e);
		}

	}

	@Test
	public void testStudent() {
		List<Student> ss = studentService.findByName("test");
		logger.info(ss);
		logger.info(bookService.findAll());
		logger.info(ToStringBuilder.reflectionToString(bookService.getSomeOne()));
		logger.error(bookService.count("%java%"));
		try {
			//int a = 1 / 0;
			//System.out.println(a);
		} catch (Exception e) {
			logger.error("some error!", e);
		}
	}

	//@Test
	public void testBook() {
		Book ss = bookService.getById(1001);
		logger.info(ToStringBuilder.reflectionToString(ss));
	}

	//@Test
	public void testBookTransations() {
		bookService.updateTwo(1001, 1002);
	}

}
