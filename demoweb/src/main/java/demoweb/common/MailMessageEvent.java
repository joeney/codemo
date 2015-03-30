package demoweb.common;

import org.springframework.context.ApplicationEvent;

public class MailMessageEvent extends ApplicationEvent {
	private static final long serialVersionUID = -5821901043769769634L;
	private MailMessage message;

	public MailMessageEvent(Object source, MailMessage message) {
		super(source);
		this.message = message;
	}

	public MailMessage getMessage() {
		return message;
	}

	public void setMessage(MailMessage message) {
		this.message = message;
	}


}