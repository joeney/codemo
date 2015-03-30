package demoweb.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import demoweb.common.MessageKey.Common;
import demoweb.domain.Student;

@Controller
public class StudentController extends BaseController {
	protected final Log logger = LogFactory.getLog(getClass());

	@ResponseBody
	@RequestMapping(value = "/student/{id}.json", method = RequestMethod.GET)
	public Student downloadJson(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {
		logger.info("============begin=============");

		nocache(response);
		response.setContentType("application/json; charset=UTF-8");

		Student st = studentService.get(id);
		return st;
	}

	@RequestMapping(value = "/student/{id}.pdf", method = RequestMethod.GET)
	public ModelAndView downloadPdf(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {
		logger.info("============begin=============");

		nocache(response);
		response.setContentType("application/pdf; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=Student-" + id + ".pdf");

		Student st = studentService.get(id);

		logger.info("########:" + messageManager.getMessage(Common.WELCOME, request.getLocale(), st.getName()));

		return new ModelAndView(studentProfilePdfView, "student", st);
	}

	@RequestMapping(value = "/student.htm", method = RequestMethod.GET)
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) {
		logger.info("============begin=============");
		String name = WebUtils.findParameterValue(request, "name");
		if (StringUtils.isBlank(name)) {
			logger.error("name can not be empty.");
			return null;
		}
		List<Student> s = studentService.findByName(name);
		logger.info(ToStringBuilder.reflectionToString(s));
		return null;
	}

	@RequestMapping(value = "/student.htm", method = RequestMethod.PUT)
	public ModelAndView put(HttpServletRequest request, HttpServletResponse response) {
		logger.info("============begin=============");
		String name = WebUtils.findParameterValue(request, "name");
		if (StringUtils.isBlank(name)) {
			logger.error("name can not be empty.");
			return null;
		}
		Student s = studentService.create(name);
		logger.info(ToStringBuilder.reflectionToString(s));
		return null;
	}

	@RequestMapping(value = "/student.htm", method = RequestMethod.POST)
	public ModelAndView post(HttpServletRequest request, HttpServletResponse response) {
		logger.info("============begin=============");
		String id = WebUtils.findParameterValue(request, "id");
		String name = WebUtils.findParameterValue(request, "name");
		if (StringUtils.isBlank(name)) {
			logger.error("name can not be empty.");
			return null;
		}
		Student s = studentService.update(Integer.parseInt(id), name);
		logger.info(ToStringBuilder.reflectionToString(s));
		return null;
	}

}
