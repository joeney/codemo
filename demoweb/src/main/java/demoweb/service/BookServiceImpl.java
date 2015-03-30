package demoweb.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demoweb.dao.BookDao;
import demoweb.domain.Book;

@Service
@Transactional
public class BookServiceImpl implements BookService {
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private BookDao bookDao;

	@Override
	public Book getById(int id) {
		return bookDao.getOne(id);
	}

	@Override
	public void updateTwo(int id1, int id2) {
		Book b;
		b = getById(id1);
		b.setRemark(b.getRemark() + "1");

		//how to commit a transaction partly?
		//bookDao.saveAndFlush(b);
		//int a = 1 / 0;

		b = getById(id2);
		b.setRemark(b.getRemark() + "2");
	}

	@Override
	public List<Book> findAll() {
		return bookDao.findAll();
	}

	@Override
	public Book getSomeOne() {
		Integer id = 1001;
		return bookDao.getSomeOne(id);
	}

	@Override
	public int count(String name) {
		return bookDao.getCountByName(name);
	}

}
