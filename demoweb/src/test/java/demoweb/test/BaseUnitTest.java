package demoweb.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demoweb.business.MailMessageManager;
import demoweb.service.BookService;
import demoweb.service.StudentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "../../spring/applicationContext.xml" })
public abstract class BaseUnitTest {
	@Autowired
	protected ApplicationContext applicationContext;
	@Autowired
	protected StudentService studentService;
	@Autowired
	protected BookService bookService;
	@Autowired
	protected MailMessageManager mailMessageManager;
	@Autowired
	@Qualifier("emailSenderDefault")
	protected JavaMailSender mailSender;
}
