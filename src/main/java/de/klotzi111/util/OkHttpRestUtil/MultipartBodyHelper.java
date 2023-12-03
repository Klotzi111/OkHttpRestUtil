package de.klotzi111.util.OkHttpRestUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;

import de.klotzi111.util.GsonUtil.GsonObjectMapper;
import de.klotzi111.util.OkHttpRestUtil.dto.MultipartParameterSerializeable;
import de.klotzi111.util.OkHttpRestUtil.exception.BuildHTTPRequestFailedException;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MultipartBodyHelper {

	private MultipartBodyHelper() {
	}

	/**
	 * Build a multipart (file uploading) request body with the given form parameters,
	 * which could contain text fields and file fields.
	 *
	 * @param gson
	 *            Gson instance
	 * @param formParams
	 *            Form parameters in the form of Map
	 * @return RequestBody
	 * @throws BuildHTTPRequestFailedException
	 */
	public static RequestBody createMultipartRequestBody(Gson gson, Map<String, Object> formParams) throws BuildHTTPRequestFailedException {
		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		for (Entry<String, Object> param : formParams.entrySet()) {
			Object value = param.getValue();
			if (value instanceof MultipartParameterSerializeable) {
				MultipartParameterSerializeable multipartSerializeable = (MultipartParameterSerializeable) value;
				builder.addPart(multipartSerializeable.serializeMultipartParameter(param.getKey()));
			} else {
				builder.addFormDataPart(param.getKey(), RequestBodyHelper.parameterToString(gson, value));
			}
		}
		return builder.build();
	}

	public static <T> RequestBody createMultipartRequestBody(Gson gson, T fromParamsObject) throws BuildHTTPRequestFailedException {
		try {
			HashMap<String, Object> formParams = GsonObjectMapper.convertObjectToMapViaGsonRules(gson, fromParamsObject, null, false);
			return createMultipartRequestBody(gson, formParams);
		} catch (Exception e) {
			throw new BuildHTTPRequestFailedException("Exception while creating multipart request body", e);
		}
	}
}
