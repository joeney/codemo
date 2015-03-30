package demoweb.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demoweb.dao.StudentDao;
import demoweb.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {
	protected final Log logger = LogFactory.getLog(getClass());
	private StudentDao studentDao;

	@Autowired
	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	@Override
	@Transactional
	public Student create(String name) {
		Student s = new Student();
		s.setId(Integer.parseInt(new SimpleDateFormat("MMddHHmmss").format(new Date())));
		s.setName(name);
		studentDao.save(s);
		return s;
	}

	@Override
	@Transactional
	public Student update(Integer id, String name) {
		Student s = studentDao.getOne(id);
		s.setName("1." + name);
		/** These changes will be flushed to the database automatically at the end of transaction, so that you don't need to do anything to save these changes explicitly.
		*   http://stackoverflow.com/questions/11881479/how-do-i-update-an-entity-using-spring-data-jpa
		*/
		//studentDao.update(s);
		//int a = 1 / 0;
		s.setName("2." + name);
		//studentDao.update(s);
		return s;
	}

	@Override
	public List<Student> findByName(String name) {
		List<Student> ss = studentDao.findByName(name);
		return ss;
	}

	@Override
	public Student get(Integer id) {
		return studentDao.findOne(id);
	}
}
