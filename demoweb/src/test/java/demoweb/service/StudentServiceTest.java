package demoweb.service;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import demoweb.domain.Student;
import demoweb.test.BaseUnitTest;

public class StudentServiceTest extends BaseUnitTest {
	private final Logger logger = LogManager.getLogger(getClass());

	@Test
	@Transactional(rollbackFor = Exception.class)
	@Rollback(true)
	public void testCreate() {
		Student s = studentService.create("Andy");
		logger.info(ToStringBuilder.reflectionToString(s));
	}
}
