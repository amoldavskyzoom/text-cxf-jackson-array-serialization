package com.projectx.web.api.service.rest;

import java.time.Instant;
import java.util.*;
import java.util.Map.Entry;
import java.lang.System;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.projectx.web.api.BaseTest;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.transport.local.LocalConduit;
import org.apache.cxf.jaxrs.client.WebClient;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.projectx.web.api.test.config.spring.AppTestConfig;
import com.projectx.sdk.api.ApiResponse;
import com.projectx.web.api.data.search.SearchDTO;
import com.projectx.web.api.data.user.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		loader = AnnotationConfigContextLoader.class,
		classes = { UserRestTest.class, AppTestConfig.class }
)
@PowerMockIgnore({
		"javax.management.*",
		"javax.crypto.*"
})
@WebAppConfiguration
//@BootstrapWith(WebTestContextBootstrapper.class)
public class UserRestTest extends BaseTest {

	@Autowired
	Server jaxRsServer;

	List<Object> providers;
	WebClient webClient;

	UserRest userRestRS;
	ObjectMapper objectMapper;

	TypeReference ApiResponseUserTypeReference = new TypeReference<ApiResponse<UserDTO>>(){};

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks( this );
		/*
		userService = Mockito.mock( UserService.class );
		userRestRS = Mockito.spy( new UserRestImpl() );
		ReflectionTestUtils.setField( userRestRS, "userService", userService );
		*/

		this.providers = new ArrayList<>();
		JacksonJaxbJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider();
		providers.add( jacksonJsonProvider );

		// custom deserializers for our abstract types ( User and Collection )
		this.objectMapper = jacksonJsonProvider.locateMapper(null, MediaType.APPLICATION_JSON_TYPE);
//		SimpleModule module = new SimpleModule(
//				"Kur8orApiDeserializer",
//				new Version(1, 0, 0, null, "", "")
//		);
//		module.addDeserializer( com.projectx.sdk.user.User.class, userAbstractDeserializer );
//		module.addDeserializer( Collection.class, collectionAbstractDeserializer );
//
//		objectMapper.registerModule( module );

		webClient = WebClient.create( "http://localhost:8080/api", providers );
		WebClient.getConfig( webClient ).getRequestContext().put( LocalConduit.DIRECT_DISPATCH, Boolean.TRUE );
		webClient.accept( "application/json" );

	}

	@After
	public void tearDown() throws Exception {

	}

	@Configuration
	@ComponentScan(
			basePackages = {
					"com.projectx.web.api.test",
					"com.projectx.web.api.service"
			}
	)
	public static class TestContextConfig {

	}

	@Test
	public void test_getUser() {

		class Local {};
		String methodName = Local.class.getEnclosingMethod().getName();

		int testUserId = 11;
		String apiUrl = "/users/" + testUserId;

		webClient.path( apiUrl );

		UserDTO reponseUser = null;

		try {

			System.out.println( String.format(
					"%s: GET request to: %s", methodName, webClient.getCurrentURI() )
			);

			String response = webClient.get( String.class );

			System.out.println( String.format(
					"%s: %s: response: %s", methodName, apiUrl, response )
			);

			ApiResponse<UserDTO> apiResponse = objectMapper.readValue(
					response,
					objectMapper.getTypeFactory().constructType( new TypeReference<ApiResponse<UserDTO>>(){} )
			);

			reponseUser = apiResponse.getData();

		} catch( Exception e ) {

			e.printStackTrace();
			fail( e.getMessage() );
			return;

		}

		assertTrue( true );
	}

	@Test
	public void test_getUsers_single() {

		class Local {};
		String methodName = Local.class.getEnclosingMethod().getName();

		List<Integer> userIds = new ArrayList<Integer>();

		userIds.add( 11 );

		String apiUrl = "/users";

		webClient.path( apiUrl );
		webClient.query( "id", userIds );

		List<UserDTO> reponseUsers = null;

		try {

			System.out.println( String.format(
					"%s: GET request to: %s", methodName, webClient.getCurrentURI() )
			);
			String reponseUsersString = webClient.get( String.class );
			ApiResponse<SearchDTO<UserDTO>> apiResponse = objectMapper.readValue(
					reponseUsersString,
					objectMapper.getTypeFactory().constructType( new TypeReference<ApiResponse<SearchDTO<UserDTO>>>(){} )
			);
			SearchDTO searchResp = apiResponse.getData();
			System.out.println( String.format(
					"%s: %s: response: %s", methodName, apiUrl, reponseUsersString )
			);


		} catch( NotFoundException nfe ) {

			nfe.printStackTrace();
			fail( nfe.getMessage() );
			return;

		} catch( Exception e ) {

			e.printStackTrace();
			fail( e.getMessage() );
			return;

		}

		assertTrue( true );
	}

	@Test
	public void test_getUsers_many() {

		class Local {};
		String methodName = Local.class.getEnclosingMethod().getName();

		List<Integer> userIds = new ArrayList<Integer>();

		userIds.add( 11 );
		userIds.add( 22 );

		String apiUrl = "/users";

		webClient.path( apiUrl );
		webClient.query( "id", userIds );

		List<UserDTO> reponseUsers = null;

		try {

			System.out.println( String.format(
					"%s: GET request to: %s", methodName, webClient.getCurrentURI() )
			);
			String reponseUsersString = webClient.get( String.class );
			ApiResponse<SearchDTO<UserDTO>> apiResponse = objectMapper.readValue(
					reponseUsersString,
					objectMapper.getTypeFactory().constructType( new TypeReference<ApiResponse<SearchDTO<UserDTO>>>(){} )
			);
			SearchDTO searchResp = apiResponse.getData();
			System.out.println( String.format(
					"%s: %s: response: %s", methodName, apiUrl, reponseUsersString )
			);

		} catch( NotFoundException nfe ) {

			nfe.printStackTrace();
			fail( nfe.getMessage() );
			return;

		} catch( Exception e ) {

			e.printStackTrace();
			fail( e.getMessage() );
			return;

		}

		assertTrue( true );
	}

	private WebClient createWebClient() {

		WebClient webClient = WebClient.create( "http://localhost:8080/api", this.providers );
		WebClient.getConfig( webClient ).getRequestContext().put( LocalConduit.DIRECT_DISPATCH, Boolean.TRUE );
		webClient.accept( "application/json" );
		webClient.type( MediaType.APPLICATION_JSON );

		return webClient;
	}
}