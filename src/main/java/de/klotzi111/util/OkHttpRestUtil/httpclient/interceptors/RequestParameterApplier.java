package de.klotzi111.util.OkHttpRestUtil.httpclient.interceptors;

import java.util.List;
import java.util.Map;

import de.klotzi111.util.OkHttpRestUtil.Tuple;

public interface RequestParameterApplier {
	/**
	 * Apply the settings to header and query params.
	 *
	 * @param queryParams
	 *            List of query parameters
	 * @param headerParams
	 *            Map of header parameters
	 */
	void applyToParams(List<Tuple<String, String>> queryParams, Map<String, String> headerParams);
}
