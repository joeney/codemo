package common.dao;

import java.util.List;

import common.entity.WebUser;

public interface WebUserDao extends BaseDao<WebUser, Integer> {

	List<WebUser> findAll();

}
