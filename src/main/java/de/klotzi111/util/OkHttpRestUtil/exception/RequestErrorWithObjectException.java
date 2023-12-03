package de.klotzi111.util.OkHttpRestUtil.exception;

import com.google.gson.Gson;

import de.klotzi111.util.OkHttpRestUtil.dto.RequestErrorDto;

public abstract class RequestErrorWithObjectException extends RequestErrorException {
	private static final long serialVersionUID = -3955437523206106770L;

	private final Gson gson;

	public RequestErrorWithObjectException(int statusCode, String requestName, Gson gson) {
		super(statusCode, requestName);
		this.gson = gson;
	}

	public RequestErrorWithObjectException(int statusCode, String requestName, String message, Gson gson) {
		super(statusCode, requestName, message);
		this.gson = gson;
	}

	public abstract RequestErrorDto getErrorResponse();

	public String getErrorResponseAsString() {
		return gson.toJson(getErrorResponse());
	}

	public Gson getGson() {
		return gson;
	}

	@Override
	public String getMessage() {
		String message = super.getMessage();
		message += "\nwith errorResponse =\n" + getErrorResponseAsString();
		return message;
	}

}
