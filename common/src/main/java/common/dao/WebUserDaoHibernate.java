package common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import common.entity.WebUser;

@Repository
public class WebUserDaoHibernate extends AbstractDaoHibernate<WebUser, Integer> implements WebUserDao {

	@Override
	public List<WebUser> findAll() {
		String hql = "from WebUser ";
		@SuppressWarnings("unchecked")
		List<WebUser> list = (List<WebUser>) getHibernateTemplate().find(hql, new Object[] {});
		return list;
	}

}
