package de.klotzi111.util.OkHttpRestUtil.exception;

public class RequestErrorException extends HTTPRequestFailedException {
	private static final long serialVersionUID = 8231576452721153796L;

	protected static final String FORMAT = HTTPRequestFailedException.FORMAT + " with status code %d";

	private final int statusCode;
	private final String requestName;
	private final String rawMessage;

	public RequestErrorException(int statusCode, String requestName) {
		this(statusCode, requestName, null);
	}

	public RequestErrorException(int statusCode, String requestName, String message) {
		super(requestName, message);
		this.statusCode = statusCode;
		this.requestName = requestName;
		rawMessage = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	@Override
	public String getRequestName() {
		return requestName;
	}

	@Override
	public String getRawMessage() {
		return rawMessage;
	}

	@Override
	protected String formatBaseMessage() {
		return String.format(FORMAT, requestName, statusCode);
	}

}
