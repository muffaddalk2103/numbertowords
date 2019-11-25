/**
 *
 */
package com.mk.numbertowords.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.mk.numbertowords.resources.BadURIExceptionMapper;
import com.mk.numbertowords.resources.NumberToWordRestResource;

/**
 * Helper class which helps load jersey resources
 *
 * @author muffa
 *
 */
public class ResourceLoader extends Application {

	/**
	 * Returns a set of jersey resources
	 */
	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(NumberToWordRestResource.class);
		classes.add(BadURIExceptionMapper.class);
		return classes;
	}

}
