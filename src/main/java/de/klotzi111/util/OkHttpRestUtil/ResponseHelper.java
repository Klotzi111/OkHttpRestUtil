package de.klotzi111.util.OkHttpRestUtil;

import static de.klotzi111.util.OkHttpRestUtil.OkHttpHelper.MEDIATYPE_JSON;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import de.klotzi111.util.OkHttpRestUtil.dto.RequestErrorDto;
import de.klotzi111.util.OkHttpRestUtil.exception.RequestErrorWithObjectException;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ResponseHelper {

	private ResponseHelper() {
	}

	public static boolean isJsonResponseBody(ResponseBody responseBody) {
		MediaType contentType = responseBody.contentType();
		Charset charset = contentType.charset();
		if (charset == null) {
			// if no charset is specified we fallback to UTF-8
			charset = StandardCharsets.UTF_8;
		}
		return Objects.equals(contentType.type(), MEDIATYPE_JSON.type())
			&& Objects.equals(contentType.subtype(), MEDIATYPE_JSON.subtype())
			&& Objects.equals(charset, MEDIATYPE_JSON.charset());
	}

	public static boolean hasJsonResponseBody(Response response) {
		try (ResponseBody body = response.body()) {
			return isJsonResponseBody(body);
		}
	}

	public static <T> T parseJsonResponseBody(ResponseBody body, Gson gson, TypeToken<T> typeToken) throws JsonParseException {
		if (!isJsonResponseBody(body)) {
			throw new JsonParseException("The response body has not the required content type: " + MEDIATYPE_JSON.type());
		}

		try (Reader bodyStream = body.charStream()) {
			T responseObject = gson.fromJson(bodyStream, typeToken);
			return responseObject;
		} catch (JsonParseException e) {
			throw e;
		} catch (IOException e) {
			throw new JsonIOException(e);
		} catch (Exception e) {
			throw new JsonParseException("An exception occured while reading", e);
		}
	}

	public static <T> T parseJsonResponseBody(ResponseBody body, Gson gson, Class<T> type) throws JsonParseException {
		return parseJsonResponseBody(body, gson, TypeToken.get(type));
	}

	public static <T> T parseJsonResponseBody(Response response, Gson gson, TypeToken<T> typeToken) throws JsonParseException {
		try (ResponseBody body = response.body()) {
			return parseJsonResponseBody(body, gson, typeToken);
		}
	}

	public static <T> T parseJsonResponseBody(Response response, Gson gson, Class<T> type) throws JsonParseException {
		return parseJsonResponseBody(response, gson, TypeToken.get(type));
	}

	@FunctionalInterface
	public static interface RequestErrorExceptionFactoryFunction<FT extends RequestErrorDto, E extends RequestErrorWithObjectException> {
		public E createException(int statusCode, String requestName, FT failureObject, Gson gson);
	}

	public static <ST, FT extends RequestErrorDto, E extends RequestErrorWithObjectException> ST getJsonResponseOrThrow(Response response, Gson gson, TypeToken<ST> successTypeToken,
		TypeToken<FT> failureTypeToken, RequestErrorExceptionFactoryFunction<FT, E> exceptionFactory, int expectedStatusCode, String requestName) throws E {
		try (ResponseBody body = response.body()) {
			if (response.code() == expectedStatusCode) {
				ST responseObject = parseJsonResponseBody(body, gson, successTypeToken);
				return responseObject;
			}

			FT failureObject = parseJsonResponseBody(body, gson, failureTypeToken);
			E exception = exceptionFactory.createException(response.code(), requestName, failureObject, gson);
			throw exception;
		}
	}

	public static <ST, FT extends RequestErrorDto, E extends RequestErrorWithObjectException> ST getJsonResponseOrThrow(Response response, Gson gson, Class<ST> successTypeToken, Class<FT> failureTypeToken,
		RequestErrorExceptionFactoryFunction<FT, E> exceptionFactory, int expectedStatusCode, String requestName) throws E {
		return getJsonResponseOrThrow(response, gson, TypeToken.get(successTypeToken), TypeToken.get(failureTypeToken), exceptionFactory, expectedStatusCode, requestName);
	}

}
