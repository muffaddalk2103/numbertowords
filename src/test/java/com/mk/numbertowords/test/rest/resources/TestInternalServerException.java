/**
 *
 */
package com.mk.numbertowords.test.rest.resources;

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
import org.mockito.Mockito;

import com.mk.numbertowords.processor.NumberToWordProcessor;
import com.mk.numbertowords.resources.BadURIExceptionMapper;
import com.mk.numbertowords.resources.NumberToWordRestResource;

/**
 * @author muffa
 *
 */
@TestInstance(Lifecycle.PER_CLASS)
public class TestInternalServerException extends JerseyTest {

	public static final String BASE_URI = "http://localhost:9998/";
	private NumberToWordProcessor numberToWordProcessor;
	private HttpServer server;

	@Override
	protected Application configure() {
		return new Application();
	}

	@BeforeAll
	public void setup() {
		this.numberToWordProcessor = Mockito.mock(NumberToWordProcessor.class);
		ResourceConfig resourceConfig = new ResourceConfig();
		resourceConfig.register(new NumberToWordRestResource(numberToWordProcessor));
		resourceConfig.register(BadURIExceptionMapper.class);

		server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig);
	}

	@Override
	@After
	public void tearDown() throws Exception {
		server.shutdownNow();
	}

	@Test
	public void testConvertNumberToWordWhenException() {
		Mockito.when(numberToWordProcessor.convertNumberToWord(Mockito.anyString()))
				.thenThrow(NullPointerException.class);
		Client client = ClientBuilder.newClient();
		Response response = client.target(BASE_URI + "numbertoword").path("123").request(MediaType.TEXT_PLAIN).get();
		Assertions.assertEquals(500, response.getStatus());
		String msg = response.readEntity(String.class);
		Assertions.assertEquals("We are facing technical issues right now, please try again later.", msg);
		response.close();
		client.close();
	}
}
