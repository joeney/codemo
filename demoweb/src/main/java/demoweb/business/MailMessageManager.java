package demoweb.business;

import demoweb.business.MailMessageManagerImpl.MailMessageSender;
import demoweb.common.MailMessage;

public interface MailMessageManager {
	MailMessageSender send(MailMessage message);
}
