package demoweb.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demoweb.common.LogUtil;

@Controller
public class CommonController extends BaseController {
	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping("/error/404.htm")
	public ModelAndView notfound(HttpServletRequest request, HttpServletResponse response) {
		logger.info("ERROR!404! " + getClientIp(request) + ", " + getHeaders(request) + ", " + getParameters(request));
		String requestUri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
		loggerForPageErr.info("404 - page not found: " + requestUri);
		response.setStatus(HttpServletResponse.SC_OK);
		LogUtil.errorSec("404 - page not found: " + requestUri);
		return new ModelAndView("common/error");
	}

	@RequestMapping("/error/exception.htm")
	public ModelAndView exception(HttpServletRequest request, HttpServletResponse response) {
		logger.info("ERROR!EXCEPTION! " + getClientIp(request) + ", " + getHeaders(request) + ", " + getParameters(request));
		Throwable th = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		String requestUri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
		LogUtil.errorBiz("error in page[" + requestUri + "]", th);
		response.setStatus(HttpServletResponse.SC_OK);
		return new ModelAndView("common/error");
	}

}
