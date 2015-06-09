package common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import common.entity.WebUser;

@Service
public class WebUserServiceImpl extends AbstractService implements WebUserService {

	@Override
	public WebUser create(String name) {
		WebUser wu = new WebUser();
		wu.setName(name);
		webUserDao.save(wu);
		return wu;
	}

	@Override
	public List<WebUser> findAll() {
		return webUserDao.findAll();
	}

	@Override
	public WebUser get(int id) {
		return webUserDao.getById(id);
	}

	@Override
	public WebUser update(WebUser entity) {
		return webUserDao.update(entity);
	}

}
