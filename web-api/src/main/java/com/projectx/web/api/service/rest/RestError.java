package com.projectx.web.api.service.rest;

import com.projectx.logic.api.service.validation.ApiError;

/**
 * Encapsulated errors related to a particular entity.
 *
 * @author Assaf Moldavsky
 */

public interface RestError {

	ApiError getApiError();
	int getCode();
	int getHttpStatus();
	String getMessage();
	RestError getRestError( int apiErrorCode );

}
