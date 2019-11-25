/**
 *
 */
package com.mk.numbertowords.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mk.numbertowords.processor.NumberToWordProcessor;
import com.mk.numbertowords.processor.impl.NumberToWordProcessorImpl;

/**
 * Simple REST resource which accepts and returns text. Returned text can be a
 * error message or converted number in words.
 *
 * @author muffa
 *
 */
@Path("/numbertoword")
public class NumberToWordRestResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(NumberToWordRestResource.class);
	private NumberToWordProcessor numberToWordProcessor;

	/**
	 * Initializes service class.
	 */
	public NumberToWordRestResource() {
		numberToWordProcessor = new NumberToWordProcessorImpl();
	}

	/**
	 * Calls {@link NumberToWordProcessorImpl} to convert provided number to words.
	 * 
	 * @param value number to be processed
	 * @return error message or converted number in words.
	 */
	@GET
	@Path("{number}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response processNumberToWord(@PathParam(value = "number") String value) {
		LOGGER.info("inside processNumberToWord");
		String output;
		try {
			output = numberToWordProcessor.convertNumberToWord(value);
		} catch (IllegalArgumentException iaex) {
			LOGGER.error(iaex.getMessage(), iaex);
			return Response.status(Status.BAD_REQUEST).entity(iaex.getMessage()).build();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("We are facing technical issues right now, please try again later.").build();
		}
		return Response.ok(output, MediaType.TEXT_PLAIN).build();
	}
}
