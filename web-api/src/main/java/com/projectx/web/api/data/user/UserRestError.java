package com.projectx.web.api.data.user;

import com.projectx.logic.api.service.validation.ApiError;
import com.projectx.logic.api.service.validation.UserError;
import com.projectx.web.api.service.rest.RestError;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Basically a map of API error codes mapped to a Rest code and HTTP status code
 *
 * @author Assaf Moldavsky
 */

public enum UserRestError implements RestError {

	INTERNAL_ERROR( UserError.INTERNAL_ERROR );

	private UserError userError;
	private final int code;
	private final int httpStatus;
	private final String message;

	UserRestError( UserError userError ) {
		this.userError = userError;
		this.code = userError.getCode();
		this.httpStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		this.message = userError.getMessage();
	}
	UserRestError( UserError userError, int responseCode ) {
		this.userError = userError;
		this.code = responseCode;
		this.httpStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		this.message = userError.getMessage();

	}
	UserRestError( UserError userError, int responseCode, int responseHttpStatus ) {
		this.userError = userError;
		this.code = responseCode;
		this.httpStatus = responseHttpStatus;
		this.message = userError.getMessage();

	}
	UserRestError( UserError userError, int responseCode, int responseHttpStatus, String responseMessage ) {
		this.userError = userError;
		this.code = responseCode;
		this.httpStatus = responseHttpStatus;
		this.message = responseMessage;
	}

	// Reverse-lookup map for getting a day from an abbreviation
	public static final Map<Integer,RestError> lookup = new HashMap<>();
	static {
		for( UserRestError err : UserRestError.values() ) {
			lookup.put( err.getApiError().getCode(), err );
		}
	}

	@Override
	public int getCode() {
		return this.code;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public ApiError getApiError() { return this.userError; }

	@Override
	public int getHttpStatus() { return this.httpStatus; }

	@Override
	public RestError getRestError( int apiErrorCode ) {
		return lookup.get( apiErrorCode );
	}

}
