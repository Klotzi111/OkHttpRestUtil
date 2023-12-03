package de.klotzi111.util.OkHttpRestUtil;

import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import de.klotzi111.util.GsonUtil.GsonObjectMapper;
import okhttp3.FormBody;

public class FormBodyHelper {

	private FormBodyHelper() {
	}

	public static <T> FormBody.Builder addFormDataFromPojo(FormBody.Builder builder, Gson gson, T data) {
		JsonObject jsonObj = gson.toJsonTree(data).getAsJsonObject();
		return addFormData(builder, GsonObjectMapper.convertJsonObjectToStringMap(jsonObj));
	}

	public static FormBody.Builder addFormData(FormBody.Builder builder, Map<String, String> data, boolean alreadyEncoded) {
		for (Entry<String, String> entry : data.entrySet()) {
			if (alreadyEncoded) {
				builder.addEncoded(entry.getKey(), entry.getValue());
			} else {
				builder.add(entry.getKey(), entry.getValue());
			}
		}
		return builder;
	}

	public static FormBody.Builder addFormData(FormBody.Builder builder, Map<String, String> data) {
		return addFormData(builder, data, false);
	}
}
