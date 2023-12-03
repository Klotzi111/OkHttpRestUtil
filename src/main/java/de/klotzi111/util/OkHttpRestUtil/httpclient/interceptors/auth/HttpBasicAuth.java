package de.klotzi111.util.OkHttpRestUtil.httpclient.interceptors.auth;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import de.klotzi111.util.OkHttpRestUtil.Tuple;
import okhttp3.Credentials;

public class HttpBasicAuth extends Authentication<HttpBasicAuth> {
	private String username;
	private String password;

	public HttpBasicAuth(Consumer<HttpBasicAuth> refreshAuthenticationFunction) {
		super(refreshAuthenticationFunction);
	}

	public HttpBasicAuth() {
		this(null);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void applyToParams(List<Tuple<String, String>> queryParams, Map<String, String> headerParams) {
		if (username == null && password == null) {
			return;
		}

		headerParams.put("Authorization", Credentials.basic(
			username == null ? "" : username,
			password == null ? "" : password));
	}
}
