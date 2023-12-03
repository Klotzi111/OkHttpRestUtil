package de.klotzi111.util.OkHttpRestUtil.httpclient.interceptors.auth;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import de.klotzi111.util.OkHttpRestUtil.Tuple;
import de.klotzi111.util.OkHttpRestUtil.httpclient.interceptors.ParameterLocation;

public class ApiKeyAuth<SELF extends Authentication<SELF>> extends Authentication<SELF> {

	private final ParameterLocation location;
	private final String paramName;

	private String apiKey;
	private String apiKeyPrefix;

	public ApiKeyAuth(ParameterLocation location, String paramName, Consumer<SELF> refreshAuthenticationFunction) {
		super(refreshAuthenticationFunction);
		this.location = location;
		this.paramName = paramName;
	}

	public ApiKeyAuth(ParameterLocation location, String paramName) {
		this(location, paramName, null);
	}

	public ParameterLocation getLocation() {
		return location;
	}

	public String getParamName() {
		return paramName;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiKeyPrefix() {
		return apiKeyPrefix;
	}

	public void setApiKeyPrefix(String apiKeyPrefix) {
		this.apiKeyPrefix = apiKeyPrefix;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void applyToParams(List<Tuple<String, String>> queryParams, Map<String, String> headerParams) {
		if (refreshAuthenticationFunction != null) {
			refreshAuthenticationFunction.accept((SELF) this);
		}

		if (apiKey == null) {
			return;
		}

		String value;
		if (apiKeyPrefix != null) {
			value = apiKeyPrefix + " " + apiKey;
		} else {
			value = apiKey;
		}

		switch (location) {
			case HEADER:
				headerParams.put(paramName, value);
				break;
			case QUERY:
				queryParams.add(new Tuple<String, String>(paramName, value));
				break;
		}
	}

}
