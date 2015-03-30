package demoweb.service;

import java.util.List;

import demoweb.domain.Book;

public interface BookService {

	Book getById(int id);

	void updateTwo(int id1, int id2);

	List<Book> findAll();

	Book getSomeOne();

	int count(String name);

}
