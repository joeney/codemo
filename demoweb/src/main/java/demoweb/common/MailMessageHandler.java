package demoweb.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MailMessageHandler implements ApplicationListener<MailMessageEvent> {
	protected final Log logger = LogFactory.getLog(getClass());

	public void onApplicationEvent(MailMessageEvent event) {
		logger.info("@@@@@@@@@@ [" + Thread.currentThread().getName() + "] handle sending message: " + event.getMessage().getSubject());
	}

}