package demoweb.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demoweb.domain.Student;

public interface StudentDao extends JpaRepository<Student, Integer> {

	List<Student> findByName(String name);

}
