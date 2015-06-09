package common.dao;

import java.io.Serializable;

public interface BaseDao<T, PK extends Serializable> {

	T getById(Serializable id);

	T save(T entity);

	T update(T entity);
}
