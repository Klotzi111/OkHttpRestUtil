package de.klotzi111.util.OkHttpRestUtil.exception;

public class BuildHTTPRequestFailedException extends Exception {
	private static final long serialVersionUID = -2784149424619348822L;

	public BuildHTTPRequestFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public BuildHTTPRequestFailedException(String message) {
		this(message, null);
	}

	public BuildHTTPRequestFailedException() {
		this(null, null);
	}

}
