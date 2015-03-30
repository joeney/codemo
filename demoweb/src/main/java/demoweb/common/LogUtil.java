package demoweb.common;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * CAUTION:
 * Before updating the invoking structure of this class, understand the {@code INVOKE_STEP} first!
 */
public abstract class LogUtil {
	private static final Log loggerForSec = LogFactory.getLog("loggername.security");
	private static final Log loggerForBiz = LogFactory.getLog("loggername.business");
	private static final Logger loggerDefault = Logger.getLogger("default");
	private static final int INVOKE_STEP = 5;
	private static final String LEVEL_INFO = "INFO";
	private static final String LEVEL_WARN = "WARN";
	private static final String LEVEL_ERROR = "ERROR";

	private static String getLevelName(Level level) {
		if (null == level) return "*";
		if (Level.INFO.equals(level)) return LEVEL_INFO;
		if (Level.WARNING.equals(level)) return LEVEL_WARN;
		if (Level.SEVERE.equals(level)) return LEVEL_ERROR;
		return level.getName();
	}

	private static String getThreadName() {
		return Thread.currentThread().getName();
	}

	private static StackTraceElement getStackTraceElement(int steps) {
		StackTraceElement[] stes = Thread.currentThread().getStackTrace();
		if (null == stes || 0 == stes.length) return null;
		if (steps >= stes.length) return null;
		return stes[steps];
	}

	private static String getInvokePositionInfo(int steps) {
		StackTraceElement ste = getStackTraceElement(steps);
		if (null == ste) return "*";
		return ste.getClassName() + "." + ste.getMethodName() + ":" + ste.getLineNumber();
	}

	private static void log(Log logger, Level level, Object message, Throwable t) {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(getThreadName()).append("]");
		sb.append(" ").append(getLevelName(level)).append(" ");
		sb.append(getInvokePositionInfo(INVOKE_STEP)).append(" - ");
		sb.append(message);
		if (Level.INFO.equals(level)) {
			logger.info(sb.toString(), t);
		} else if (Level.WARNING.equals(level)) {
			logger.warn(sb.toString(), t);
		} else if (Level.SEVERE.equals(level)) {
			logger.error(sb.toString(), t);
		} else {
			loggerDefault.severe("log error! level[" + level + "] not supported!");
		}
	}

	public static void warnSec(Object message) {
		log(loggerForSec, Level.WARNING, message, null);
	}

	public static void warnSec(Object message, Throwable t) {
		log(loggerForSec, Level.WARNING, message, t);
	}

	public static void errorSec(Object message) {
		log(loggerForSec, Level.SEVERE, message, null);
	}

	public static void errorSec(Object message, Throwable t) {
		log(loggerForSec, Level.SEVERE, message, t);
	}

	public static void infoBiz(Object message) {
		log(loggerForBiz, Level.INFO, message, null);
	}

	public static void infoBiz(Object message, Throwable t) {
		log(loggerForBiz, Level.INFO, message, t);
	}

	public static void warnBiz(Object message) {
		log(loggerForBiz, Level.WARNING, message, null);
	}

	public static void warnBiz(Object message, Throwable t) {
		log(loggerForBiz, Level.WARNING, message, t);
	}

	public static void errorBiz(Object message) {
		log(loggerForBiz, Level.SEVERE, message, null);
	}

	public static void errorBiz(Object message, Throwable t) {
		log(loggerForBiz, Level.SEVERE, message, t);
	}
}
