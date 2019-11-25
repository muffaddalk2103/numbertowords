/**
 *
 */
package com.mk.numbertowords.test.processor;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.mk.numbertowords.resources.BadURIExceptionMapper;
import com.mk.numbertowords.resources.NumberToWordRestResource;

/**
 * @author muffa
 *
 */
@TestInstance(Lifecycle.PER_CLASS)
public class IntegrationTests extends JerseyTest {
	public static final String BASE_URI = "http://localhost:9999/";
	private HttpServer server;

	@Override
	protected Application configure() {
		ResourceConfig config = new ResourceConfig(NumberToWordRestResource.class, BadURIExceptionMapper.class);
		return config;
	}

	@BeforeAll
	public void setup() {
		final ResourceConfig rc = new ResourceConfig(NumberToWordRestResource.class, BadURIExceptionMapper.class);
		server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
	}

	@Override
	@After
	public void tearDown() throws Exception {
		server.shutdownNow();
	}

	@Test
	public void testConvertNumberToWord() {
		Client client = ClientBuilder.newClient();
		Response response = client.target("http://localhost:9999/numbertoword").path("123")
				.request(MediaType.TEXT_PLAIN).get();
		Assertions.assertEquals(200, response.getStatus());
		String msg = response.readEntity(String.class);
		Assertions.assertEquals("ONE HUNDRED AND TWENTY THREE", msg);
		response.close();
		client.close();
	}

	@Test
	public void testConvertNumberToWordWhenBadValue() {
		Client client = ClientBuilder.newClient();
		Response response = client.target("http://localhost:8080/numbertoword").path("abc")
				.request(MediaType.TEXT_PLAIN).get();
		Assertions.assertEquals(400, response.getStatus());
		String msg = response.readEntity(String.class);
		Assertions.assertEquals(
				"Input value \"abc\" provided is invalid please provide a whole number in range of -2,147,483,648 to 2,147,483,647",
				msg);
		response.close();
		client.close();
	}

	@Test
	public void testConvertNumberToWordWhenNoValue() {
		Client client = ClientBuilder.newClient();
		Response response = client.target("http://localhost:8080/numbertoword").request(MediaType.TEXT_PLAIN).get();
		Assertions.assertEquals(404, response.getStatus());
		String msg = response.readEntity(String.class);
		Assertions.assertEquals("Resource not found.", msg);
		response.close();
		client.close();
	}
}
