package de.klotzi111.util.OkHttpRestUtil;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;

import de.klotzi111.util.GsonUtil.GsonObjectMapper;
import de.klotzi111.util.OkHttpRestUtil.dto.QueryParameterSerializeable;
import de.klotzi111.util.OkHttpRestUtil.exception.BuildHTTPRequestFailedException;
import okhttp3.HttpUrl;
import okhttp3.HttpUrl.Builder;
import okhttp3.Request;

public class RequestURLHelper {

	private RequestURLHelper() {
	}

	public static HttpUrl createHttpUrlWithQueryParams(HttpUrl url, Gson gson, Map<String, Object> queryParams) throws BuildHTTPRequestFailedException {
		Builder urlBuilder = url.newBuilder();
		for (Entry<String, Object> param : queryParams.entrySet()) {
			Object value = param.getValue();
			String valueString;
			if (value instanceof QueryParameterSerializeable) {
				QueryParameterSerializeable querySerializeable = (QueryParameterSerializeable) value;
				valueString = querySerializeable.serializeQueryParameter();
			} else {
				valueString = RequestBodyHelper.parameterToString(gson, value);
			}
			urlBuilder.addQueryParameter(param.getKey(), valueString);
		}
		return urlBuilder.build();
	}

	public static <T> Request.Builder addQueryParams(Request.Builder builder, Gson gson, T queryParamsObject) throws BuildHTTPRequestFailedException {
		try {
			HashMap<String, Object> queryParams = GsonObjectMapper.convertObjectToMapViaGsonRules(gson, queryParamsObject, null, false);
			builder.url(createHttpUrlWithQueryParams(builder.getUrl$okhttp(), gson, queryParams));
			return builder;
		} catch (Exception e) {
			throw new BuildHTTPRequestFailedException("Exception while adding query params", e);
		}
	}

	/**
	 * This method checks that the given url is valid and returns a {@link HttpUrl} instance.
	 * This instance's url always has an leading slash.
	 *
	 * @param url
	 * @return the HttpUrl instance
	 * @throws MalformedURLException
	 */
	public static HttpUrl checkUrlValid(String url) throws MalformedURLException {
		try {
			HttpUrl urlObj = HttpUrl.get(url);
			if (urlObj == null) {
				throw new MalformedURLException();
			}
			return urlObj;
		} catch (MalformedURLException e) {
			throw e;
		} catch (Exception e) {
			throw new MalformedURLException(e.getMessage());
		}
	}

}
