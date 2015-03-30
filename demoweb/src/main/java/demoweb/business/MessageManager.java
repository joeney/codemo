package demoweb.business;

import java.util.Locale;

import demoweb.common.MessageKey;

public interface MessageManager {
	String getMessage(MessageKey key, Locale locale, String... params);
}
