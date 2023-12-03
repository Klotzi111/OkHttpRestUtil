package de.klotzi111.util.OkHttpRestUtil.dto;

import okhttp3.MultipartBody.Part;

public interface MultipartParameterSerializeable {
	public Part serializeMultipartParameter(String paramName);
}
