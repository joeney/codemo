package common.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractDaoHibernate<T, PK extends Serializable> extends HibernateDaoSupport implements BaseDao<T, PK> {
	protected final Log logger = LogFactory.getLog(getClass());
	protected Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public AbstractDaoHibernate() {
		this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	private Class<T> getEntityClass() {
		return this.entityClass;
	}

	@Resource
	protected void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}

	@Override
	public T getById(Serializable PK) {
		return getHibernateTemplate().get(getEntityClass(), PK);
	}

	@Override
	@Transactional(readOnly = false)
	public T save(T entity) {
		getHibernateTemplate().save(entity);
		return entity;
	}

	@Override
	@Transactional(readOnly = false)
	public T update(T entity) {
		getHibernateTemplate().update(entity);
		return entity;
	}
}
