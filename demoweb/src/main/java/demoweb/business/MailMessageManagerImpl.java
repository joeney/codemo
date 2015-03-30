package demoweb.business;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import demoweb.common.MailMessage;
import demoweb.common.MailMessageEvent;

@Component
public class MailMessageManagerImpl implements MailMessageManager, ApplicationEventPublisherAware {
	protected final Log logger = LogFactory.getLog(getClass());
	private ApplicationEventPublisher applicationEventPublisher = null;

	@Override
	public MailMessageSender send(final MailMessage message) {
		return new MailMessageSender(applicationEventPublisher, message);
	}

	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public class MailMessageSender implements Runnable {
		private ApplicationEventPublisher publisher;
		private MailMessage message;

		public MailMessageSender() {
			super();
		}

		public MailMessageSender(ApplicationEventPublisher publisher, MailMessage message) {
			super();
			this.publisher = publisher;
			this.message = message;
		}

		public void run() {
			logger.info("@@@@@@@@@@@ [" + Thread.currentThread().getName() + "] send email.");
			publisher.publishEvent(new MailMessageEvent(this, message));
		}

		public MailMessage getMessage() {
			return message;
		}

		public void setMessage(MailMessage message) {
			this.message = message;
		}

	}
}
