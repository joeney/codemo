package demoweb.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

import demoweb.common.UnauthenticatedException;

@ControllerAdvice
public class GlobalControllerHandler {
	protected final Log logger = LogFactory.getLog(getClass());

	@ModelAttribute
	public Object newUser() {
		logger.info("应用到所有@RequestMapping注解方法，在其执行之前把返回值放入Model");
		return new Object();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		logger.info("应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器");
	}

	@ExceptionHandler(UnauthenticatedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String processException(NativeWebRequest request, Exception e) {
		logger.info("应用到所有@RequestMapping注解的方法，在其抛出UnauthenticatedException异常时执行");
		return "common/error"; // 返回一个逻辑视图名
	}
}
