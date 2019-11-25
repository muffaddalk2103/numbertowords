/**
 *
 */
package com.mk.numbertowords;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mk.numbertowords.config.ResourceLoader;

/**
 * This is the main class which initializes the embedded tomcat server and
 * jersey container
 *
 * @author muffa
 *
 */
public class NumberToWords {
	private static final Logger LOGGER = LoggerFactory.getLogger(NumberToWords.class);

	/**
	 * Initializes tomcat server and jersey container
	 *
	 * @param args optional arguments
	 * @throws LifecycleException thrown when issues with staring up tomcat server
	 */
	private static Tomcat tomcat;

	public static void main(String[] args) throws LifecycleException {
		LOGGER.info("Starting the app");
		tomcat = new Tomcat();
		String webPort = System.getProperty("server.port");
		if (webPort == null || webPort.isEmpty()) {
			webPort = "8080";
		}
		tomcat.setPort(Integer.valueOf(webPort));
		Context context = tomcat.addWebapp("", new File(".").getAbsolutePath());
		Tomcat.addServlet(context, "jersey-container-servlet", resourceConfig());
		context.addServletMappingDecoded("/*", "jersey-container-servlet");
		Thread thread = new Thread(() -> {
			LOGGER.info("Shutting down app");
			if (tomcat != null) {
				try {
					tomcat.stop();
				} catch (LifecycleException lex) {
					LOGGER.error(lex.getMessage(), lex);
				}
			}
		});
		Runtime.getRuntime().addShutdownHook(thread);
		tomcat.start();
		tomcat.getServer().await();
	}

	/**
	 * Initalizes jerser servlet container
	 *
	 * @return {@link ServletContainer}
	 */
	private static ServletContainer resourceConfig() {
		return new ServletContainer(new ResourceConfig(new ResourceLoader().getClasses()));
	}
}
