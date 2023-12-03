package de.klotzi111.util.OkHttpRestUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import de.klotzi111.util.OkHttpRestUtil.httpclient.interceptors.RequestParameterApplier;
import okhttp3.HttpUrl;
import okhttp3.HttpUrl.Builder;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestParameterApplierInterceptor implements Interceptor {

	private final RequestParameterApplier requestParameterApplier;

	public RequestParameterApplierInterceptor(RequestParameterApplier requestParameterApplier) {
		this.requestParameterApplier = requestParameterApplier;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		ArrayList<Tuple<String, String>> queryParams = new ArrayList<>();
		HashMap<String, String> headerParams = new HashMap<>();
		requestParameterApplier.applyToParams(queryParams, headerParams);

		Request oldRequest = chain.request();
		HttpUrl oldUrl = oldRequest.url();

		Builder newUrlBuilder = oldUrl.newBuilder();
		for (Tuple<String, String> tuple : queryParams) {
			newUrlBuilder.setQueryParameter(tuple.getKey(), tuple.getValue());
		}

		okhttp3.Request.Builder newRequestBuilder = oldRequest.newBuilder().url(newUrlBuilder.build());
		for (Entry<String, String> entry : headerParams.entrySet()) {
			newRequestBuilder.addHeader(entry.getKey(), entry.getValue());
		}

		return chain.proceed(newRequestBuilder.build());
	}

}
