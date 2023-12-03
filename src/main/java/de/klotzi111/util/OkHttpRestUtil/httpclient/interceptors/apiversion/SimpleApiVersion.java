package de.klotzi111.util.OkHttpRestUtil.httpclient.interceptors.apiversion;

import java.util.List;
import java.util.Map;

import de.klotzi111.util.OkHttpRestUtil.Tuple;
import de.klotzi111.util.OkHttpRestUtil.httpclient.interceptors.ParameterLocation;

public class SimpleApiVersion implements ApiVersioning {

	private final ParameterLocation location;
	private final String paramName;

	private String apiVersion;

	public SimpleApiVersion(ParameterLocation location, String paramName, String apiVersion) {
		this.location = location;
		this.paramName = paramName;
		this.apiVersion = apiVersion;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public ParameterLocation getLocation() {
		return location;
	}

	public String getParamName() {
		return paramName;
	}

	@Override
	public void applyToParams(List<Tuple<String, String>> queryParams, Map<String, String> headerParams) {
		if (apiVersion == null) {
			return;
		}

		String value = apiVersion;

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
