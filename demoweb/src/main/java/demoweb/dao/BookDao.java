package demoweb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import demoweb.domain.Book;

public interface BookDao extends JpaRepository<Book, Integer> {

	@Query("select e from Book e where id = :bookId")
	Book getSomeOne(@Param("bookId") Integer id);

	@Query("select count(*) from Book where name like :bookName")
	int getCountByName(@Param("bookName") String name);

	@Modifying
	@Query("delete from Book where id = :bookId")
	void deleteById(@Param("bookId") Integer id);
}
