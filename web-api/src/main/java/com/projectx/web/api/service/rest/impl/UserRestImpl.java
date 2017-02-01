package com.projectx.web.api.service.rest.impl;

import java.util.*;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.projectx.web.api.data.user.UserRestError;
import com.projectx.web.api.service.rest.JaxrsService;
import com.projectx.web.api.service.rest.UserRest;

import com.projectx.web.api.service.rest.RestError;
import org.apache.cxf.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectx.logic.api.service.UserService;
import com.projectx.logic.api.service.util.ValueResponse;
import com.projectx.sdk.api.ApiResponse;
import com.projectx.web.api.data.search.SearchDTO;
import com.projectx.web.api.data.user.UserDTO;

/**
 * Actual implementation of the UserRest interface, which contains the
 * actual CXF annotations.
 *
 * @author Assaf Moldavsky
 *
 */
@JaxrsService
@Service("com.projectx.web.api.service.rest.UserRest")
public class UserRestImpl implements UserRest {

	protected static Logger log = LogManager.getLogger( UserRestImpl.class );

	@Context // Instantiated by SessionFilter
			SecurityContext securityContext;

	// TODO: move this into a utility class
	private WebApplicationException generateError( int httpStatusCode, ApiResponse apiResponse ) {

		apiResponse.setResultCode( ApiResponse.FAILURE );
		javax.ws.rs.core.Response response = Response.status( httpStatusCode ).entity( apiResponse ).build();
		return new WebApplicationException( response );

	}

	@Override
	public ApiResponse<UserDTO> getUser( String userIdParam ) {

		ApiResponse<UserDTO> resp = new ApiResponse<>( ApiResponse.SUCCESS, "", createDummyUser() );;

		return resp;
	}

	@Override
	public ApiResponse<SearchDTO<UserDTO>> getUsers( final HashSet<Integer> userIds ) {

		List<UserDTO> users = new ArrayList<>();

		if( userIds == null || userIds.isEmpty() ) {
			users.add( createDummyUser() );
		} else {
			userIds.forEach(
					userId -> {
						users.add( createDummyUser() );
					}
			);
		}

		return new ApiResponse<>(
				ApiResponse.SUCCESS,
				"",
				new SearchDTO<>( users )
		);
	}

	private UserDTO createDummyUser() {

		UserDTO dummyUser = new UserDTO();

		dummyUser.setFirstName( "cool" );
		dummyUser.setLastName( "user" );
		dummyUser.setUsername( "cool.user" );
		dummyUser.setEmail( "cool.user@example.com" );

		return dummyUser;

	}

}