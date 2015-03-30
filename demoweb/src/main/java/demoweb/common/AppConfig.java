package demoweb.common;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
	protected final Log logger = LogFactory.getLog(getClass());

	public static String NAME;

	public static String VERSION;

	public static String ENVIR;

	public static String BUILD_TIME;

	public static Boolean CAPTCHA_VALIDATION_DISABLED;

	public static String HOST_IP;

	public static String STARTUP_TIME;

	public static String INSTANCE_ID;

	@Value("${app.name}")
	public void setNAME(String name) {
		NAME = name;
	}

	@Value("${app.version}")
	public void setVERSION(String version) {
		VERSION = version;
	}

	@Value("${app.envir}")
	public void setENVIR(String envir) {
		ENVIR = envir;
	}

	@Value("${app.build.time}")
	public void setBUILD_TIME(String build_time) {
		BUILD_TIME = build_time;
	}

	@Value("${captcha.validation.disabled}")
	public void setCAPTCHA_VALIDATION_DISABLED(Boolean captcha_validation_disabled) {
		CAPTCHA_VALIDATION_DISABLED = captcha_validation_disabled;
	}

	@PostConstruct
	public void postConstruct() {
		initStaticField();
		printAppInfo();
	}

	private void initStaticField() {
		INSTANCE_ID = new BigInteger(UUID.randomUUID().toString().replaceAll("\\-", ""), 16).toString(Character.MAX_RADIX).toUpperCase();
		STARTUP_TIME = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuilder allIp = new StringBuilder();
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
						allIp.append(inetAddress.getHostAddress().toString() + ",");
					}
				}
			}
			HOST_IP = StringUtils.removeEnd(allIp.toString(), ",");
		} catch (Exception e) {
			logger.error("get host ip error!", e);
			HOST_IP = "unknow:" + e.getMessage();
		}

	}

	private void printAppInfo() {
		String pathStr = "path = " + AppConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		pathStr = pathStr.replace(AppConfig.class.getSimpleName() + ".class", "");
		pathStr = pathStr.replace("/classes/" + AppConfig.class.getPackage().getName().replaceAll("\\.", "/"), "");
		logger.fatal(StringUtils.center(" Application Information Begin ", pathStr.length(), "="));
		logger.fatal("name = " + NAME);
		logger.fatal("version = " + VERSION);
		logger.fatal("envir = " + ENVIR);
		logger.fatal("host.ip = " + HOST_IP);
		logger.fatal("instance.id = " + INSTANCE_ID);
		logger.fatal("startup.time = " + STARTUP_TIME);
		logger.fatal("build.time = " + BUILD_TIME);
		logger.fatal(pathStr);
		logger.fatal("captcha.validation.disabled = " + CAPTCHA_VALIDATION_DISABLED);
		logger.fatal(StringUtils.center(" Application Information End ", pathStr.length(), "="));
	}
}
