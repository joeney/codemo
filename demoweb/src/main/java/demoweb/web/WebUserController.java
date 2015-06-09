package demoweb.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import common.entity.WebUser;

@Controller
public class WebUserController extends BaseController {
	protected final Log logger = LogFactory.getLog(getClass());

	@ResponseBody
	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public Map<String, Object> listUser(HttpServletRequest request, HttpServletResponse response) {
		logger.info(getParameters(request));
		Map<String, Object> map = new HashMap<String, Object>();
		List<WebUser> wus = webUserService.findAll();
		logger.info(wus);
		map.put("users", wus);
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public Map<String, Object> create(HttpServletRequest request, HttpServletResponse response) {
		logger.info(getParameters(request));
		Map<String, Object> map = new HashMap<String, Object>();
		String name = request.getParameter("name");
		if (StringUtils.isBlank(name)) {
			logger.warn("user name is empty!");
			return map;
		}
		WebUser wu = webUserService.create(name);
		logger.info(wu);
		map.put("user", wu);
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	public Map<String, Object> update(HttpServletRequest request, HttpServletResponse response) {
		logger.info(getParameters(request));
		Map<String, Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		if (StringUtils.isBlank(id) || StringUtils.isBlank(name)) {
			logger.warn("user id or name is empty!");
			return map;
		}
		WebUser wu = webUserService.get(Integer.parseInt(id));
		wu.setName(name);
		webUserService.update(wu);
		logger.info(wu);
		map.put("user", wu);
		return map;
	}
}
