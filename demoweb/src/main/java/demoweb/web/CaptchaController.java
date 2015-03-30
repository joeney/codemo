package demoweb.web;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CaptchaController extends BaseController {
	protected final Log logger = LogFactory.getLog(getClass());
	private static final String NAME_DEFAULT = "default";
	private static final String NAME_BIG = "big";

	@RequestMapping("/captcha/{name}.jpeg")
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response, @PathVariable String name) {
		try {
			if (NAME_DEFAULT.equals(name)) genDefaultCaptcha(request, response);
			else if (NAME_BIG.equals(name)) genBigCaptcha(request, response);
			else throw new RuntimeException("captcha name not exist! - " + name);
		} catch (Exception e) {
			logger.error("CaptchaController error! ", e);
		}
		return null;
	}

	private void genBigCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
		setResponse(response);

		String randomStr = RandomStringUtils.randomAlphabetic(4).toLowerCase();
		request.getSession().setAttribute(KEY_CAPTCHA, randomStr);
		BufferedImage bi = new Captcha().generate(200, 50, randomStr).getImage();

		writeResponse(response, bi);
	}

	private void genDefaultCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
		setResponse(response);

		String randomStr = RandomStringUtils.randomAlphabetic(4).toLowerCase().replaceAll("i", "m").replaceAll("j", "n");
		System.out.println("captcha randomStr=" + randomStr);
		request.getSession().setAttribute(KEY_CAPTCHA, randomStr);
		BufferedImage bi = new Captcha().generate(100, 25, randomStr).getImage();

		writeResponse(response, bi);
	}

	private void writeResponse(HttpServletResponse response, BufferedImage bi) throws IOException {
		//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
		//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
		//        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
		//        param.setQuality(1f, true);
		//        encoder.encode(bi);
		ImageIO.write(bi, "JPEG", response.getOutputStream());
	}

	private void setResponse(HttpServletResponse response) {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
	}
}
