package de.klotzi111.util.OkHttpRestUtil.exception;

public class ParseHTTPResponseFailedException extends Exception {
	private static final long serialVersionUID = 8846322240154193660L;

	public ParseHTTPResponseFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParseHTTPResponseFailedException(String message) {
		this(message, null);
	}

	public ParseHTTPResponseFailedException() {
		this(null, null);
	}

}
