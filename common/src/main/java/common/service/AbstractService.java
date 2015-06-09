package common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.dao.WebUserDao;

@Service
public abstract class AbstractService {
	@Autowired
	protected WebUserDao webUserDao;
}
