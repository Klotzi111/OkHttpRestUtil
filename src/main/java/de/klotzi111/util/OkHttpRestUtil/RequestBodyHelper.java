package de.klotzi111.util.OkHttpRestUtil;

import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import de.klotzi111.util.GsonUtil.GsonSerializationHelper;
import de.klotzi111.util.OkHttpRestUtil.dto.ParameterSerializeable;
import de.klotzi111.util.OkHttpRestUtil.exception.BuildHTTPRequestFailedException;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RequestBodyHelper {

	private RequestBodyHelper() {
	}

	public static final String HEADER_NAME_ACCEPT = "Accept";

	public static Request.Builder addAcceptHeader(Request.Builder builder, MediaType mediaType) {
		builder.addHeader(HEADER_NAME_ACCEPT, mediaType.toString());
		return builder;
	}

	public static <T> RequestBody createJsonBody(Gson gson, T bodyObject) {
		String json = gson.toJson(bodyObject);
		return RequestBody.create(json, OkHttpHelper.MEDIATYPE_JSON);
	}

	public static <T> Request.Builder addJsonBody(Request.Builder builder, HTTPMethod method, Gson gson, T bodyObject) {
		if (!method.isAllowsRequestBody()) {
			throw new IllegalArgumentException("The HTTPMethod \"" + method.name() + "\" must not have a body");
		}

		RequestBody body = createJsonBody(gson, bodyObject);
		builder.method(method.name(), body);
		addAcceptHeader(builder, OkHttpHelper.MEDIATYPE_JSON);
		return builder;
	}

	public static <T> Request.Builder addFormBody(Request.Builder builder, HTTPMethod method, Gson gson, T bodyObject) {
		FormBody body = FormBodyHelper.addFormDataFromPojo(new FormBody.Builder(), gson, bodyObject)
			.build();
		builder.method(method.name(), body);
		return builder;
	}

	/**
	 * Format the given parameter object into string.
	 *
	 * @param gson
	 *            Gson instance
	 * @param param
	 *            Parameter
	 * @return String representation of the parameter
	 * @throws BuildHTTPRequestFailedException
	 */
	public static String parameterToString(Gson gson, Object param) throws BuildHTTPRequestFailedException {
		return parameterToString(gson, param, 0);
	}

	@SuppressWarnings("rawtypes")
	private static String parameterToString(Gson gson, Object param, int depth) throws BuildHTTPRequestFailedException {
		if (param == null) {
			return "";
		} else if (param instanceof Collection) {
			if (depth > 0) {
				throw new BuildHTTPRequestFailedException("Collection encountered while parameter serializing the value of a collection");
			}

			StringBuilder b = new StringBuilder();
			for (Object o : (Collection) param) {
				if (b.length() > 0) {
					b.append(",");
				}
				b.append(parameterToString(gson, o, depth + 1));
			}
			return b.toString();
		} else if (param instanceof ParameterSerializeable) {
			ParameterSerializeable paramSerializeable = (ParameterSerializeable) param;
			return paramSerializeable.serializeParameter();
		}

		JsonElement json = gson.toJsonTree(param);
		return GsonSerializationHelper.convertJsonElementToString(json);
	}
}
