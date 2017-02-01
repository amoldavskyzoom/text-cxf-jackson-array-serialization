package com.projectx.web.api.service.rest;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;

import java.util.HashSet;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import com.projectx.sdk.api.ApiResponse;
import com.projectx.web.api.data.search.SearchDTO;
import com.projectx.web.api.data.user.UserDTO;

/**
 *
 * @author Assaf Moldavsky
 */
@JaxrsService
@Named
@CrossOriginResourceSharing(
		allowAllOrigins = true,
        allowCredentials = true, 
        maxAge = 1209600
)
public interface UserRest {

    @Path("/users/{userId}")
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    public ApiResponse<UserDTO> getUser(
            @PathParam("userId") final String userId
    );


    @Path("/users")
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    public ApiResponse<SearchDTO<UserDTO>> getUsers(
            @QueryParam("id") final HashSet<Integer> userIds
    );
}
