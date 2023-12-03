package de.klotzi111.util.OkHttpRestUtil;

import java.net.URLConnection;

import okhttp3.MediaType;

public class OkHttpHelper {

	public static final MediaType MEDIATYPE_JSON = MediaType.parse("application/json; charset=utf-8");

	public static final MediaType MEDIATYPE_FORM_DATA = MediaType.parse("multipart/form-data");

	private OkHttpHelper() {
	}

	/**
	 * Guess Content-Type header from the given file name (defaults to "application/octet-stream").
	 *
	 * @param fileName
	 *            The given fileName
	 * @return The guessed Content-Type
	 */
	public static String guessContentTypeFromFileName(String fileName) {
		String contentType = URLConnection.guessContentTypeFromName(fileName);
		if (contentType == null) {
			return "application/octet-stream";
		} else {
			return contentType;
		}
	}

	public static MediaType guessMediaTypeFromFileName(String fileName) {
		return MediaType.parse(guessContentTypeFromFileName(fileName));
	}
}
