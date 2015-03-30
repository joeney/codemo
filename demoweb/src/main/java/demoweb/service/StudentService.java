package demoweb.service;

import java.util.List;

import demoweb.domain.Student;

public interface StudentService {

	Student create(String name);

	Student update(Integer id, String name);

	List<Student> findByName(String name);

	Student get(Integer id);

}
