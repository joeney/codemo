package common.service;

import java.util.List;

import common.entity.WebUser;

public interface WebUserService {

	WebUser create(String name);

	List<WebUser> findAll();

	WebUser get(int id);

	WebUser update(WebUser entity);

}
