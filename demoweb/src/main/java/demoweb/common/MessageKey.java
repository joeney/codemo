package demoweb.common;

public interface MessageKey {
	String getCode();

	public enum Common implements MessageKey {
		/**common.greeting*/
		GREETING("common.greeting"),
		/**common.welcome*/
		WELCOME("common.welcome");

		private String code;

		private Common(String code) {
			this.code = code;
		}

		@Override
		public String getCode() {
			return code;
		}
	}

	public enum Account implements MessageKey {
		/**account.login*/
		LOGIN("account.login"),
		/**account.logout*/
		LOGOUT("account.logout");

		private String code;

		private Account(String code) {
			this.code = code;
		}

		@Override
		public String getCode() {
			return code;
		}
	}
}
