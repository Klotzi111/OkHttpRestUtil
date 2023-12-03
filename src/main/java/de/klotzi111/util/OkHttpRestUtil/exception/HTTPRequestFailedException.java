package de.klotzi111.util.OkHttpRestUtil.exception;

public class HTTPRequestFailedException extends Exception {
	private static final long serialVersionUID = -9187115339345926784L;

	protected static final String FORMAT = "The HTTP request \"%s\" failed";

	private final String requestName;
	private final String rawMessage;

	public HTTPRequestFailedException(String requestName) {
		this(requestName, null, null);
	}

	public HTTPRequestFailedException(String requestName, String message) {
		this(requestName, message, null);
	}

	public HTTPRequestFailedException(String requestName, Throwable cause) {
		this(requestName, null, cause);
	}

	public HTTPRequestFailedException(String requestName, String message, Throwable cause) {
		super(message, cause);
		this.requestName = requestName;
		rawMessage = message;
	}

	public String getRequestName() {
		return requestName;
	}

	public String getRawMessage() {
		return rawMessage;
	}

	protected String formatBaseMessage() {
		return String.format(FORMAT, requestName);
	}

	@Override
	public String getMessage() {
		String message = formatBaseMessage();
		if (rawMessage != null) {
			message += ": " + rawMessage;
		}
		return message;
	}

}
