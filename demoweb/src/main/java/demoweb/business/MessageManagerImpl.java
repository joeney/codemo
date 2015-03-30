package demoweb.business;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import demoweb.common.MessageKey;

@Component
public class MessageManagerImpl implements MessageManager {
	@Autowired
	private MessageSource messages;

	@Override
	public String getMessage(MessageKey key, Locale locale, String... params) {
		return messages.getMessage(key.getCode(), params, locale);
	}

}
