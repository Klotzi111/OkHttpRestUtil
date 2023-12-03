package de.klotzi111.util.OkHttpRestUtil;

public enum HTTPMethod {
	GET(false, true),
	HEAD(false, false),
	POST(true, true),
	PUT(true, true),
	DELETE(true, true),
	CONNECT(false, false),
	OPTIONS(false, true),
	TRACE(false, false),
	PATCH(true, true);

	private final boolean allowsRequestBody;
	private final boolean allowsResponseBody;

	private HTTPMethod(boolean allowsRequestBody, boolean allowsResponseBody) {
		this.allowsRequestBody = allowsRequestBody;
		this.allowsResponseBody = allowsResponseBody;
	}

	public boolean isAllowsRequestBody() {
		return allowsRequestBody;
	}

	public boolean isAllowsResponseBody() {
		return allowsResponseBody;
	}

	@Override
	public String toString() {
		return name();
	}
}
