/**
 *
 */
package com.mk.numbertowords.resources;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Handles resource not found conditions
 *
 * @author muffa
 *
 */
@Provider
public class BadURIExceptionMapper implements ExceptionMapper<NotFoundException> {

	/**
	 * Returns a custom message in case path request isn't found.
	 */
	@Override
	public Response toResponse(NotFoundException exception) {
		return Response.status(Response.Status.NOT_FOUND).entity("Resource not found.").build();
	}

}
