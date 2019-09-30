package com.ipartek.formacion.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;

/**
 * Application Lifecycle Listener implementation class Log4jListener
 *
 */
@WebListener
public class Log4jListener implements ServletContextListener {

	private final static Log LOG = LogFactory.getLog(Log4jListener.class);

	/**
	 * Default constructor.
	 */
	public Log4jListener() {
		super();
		BasicConfigurator.configure();

	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		LOG.info("Aplicacion parada");
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {

		// al arrancar la aplicacion web configuramos el Log4j
		LOG.info("Aplicacion iniciada");

	}

}
