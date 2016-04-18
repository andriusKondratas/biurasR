package ioffice.br.config;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;

@Configuration
@Component
public class CustomContextLoaderListener extends ContextLoaderListener {
	private final Log log = LogFactory.getLog(CustomContextLoaderListener.class);

	/**
	 * Initialize the root web application context.
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		//some custom setup if needed
		log.info("Br application context initialized");
	}

	/**
	 * Close the root web application context.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		//some custom destroy if needed
		deregisterJdbcDrivers();
		log.info("Br application context destroyed");
	}

	private void deregisterJdbcDrivers() {
		final Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			final Driver driver = drivers.nextElement();
			if (this.getClass().getClassLoader().equals(getClass().getClassLoader())) {
				try {
					DriverManager.deregisterDriver(driver);
					log.info("Deregistered '{" + driver.getClass() + "}' JDBC driver.");
				} catch (SQLException e) {
					log.warn("Failed to deregister '{" + driver.getClass() + "}' JDBC driver.");
				}
			}
		}
	}
}
