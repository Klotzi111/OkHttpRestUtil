package de.klotzi111.util.OkHttpRestUtil.httpclient.interceptors.auth;

import java.util.function.Consumer;

import de.klotzi111.util.OkHttpRestUtil.httpclient.interceptors.ParameterLocation;

public class OAuth extends ApiKeyAuth<OAuth> {

	public OAuth(Consumer<OAuth> refreshAuthenticationFunction) {
		super(ParameterLocation.HEADER, "Authorization", refreshAuthenticationFunction);
		setApiKeyPrefix("Bearer");
	}

	public OAuth() {
		this(null);
	}

}
