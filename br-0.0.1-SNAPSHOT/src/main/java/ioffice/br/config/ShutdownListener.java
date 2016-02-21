package ioffice.br.config;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ShutdownListener implements ServletContextListener {
	// private final Logger logger = LogFactory.getLogger(ShutdownListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// logger.info("Context initialized.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		deregisterJdbcDrivers();
		// more clean-up tasks here
	}

	private void deregisterJdbcDrivers() {
		final Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			final Driver driver = drivers.nextElement();
			if (this.getClass().getClassLoader().equals(getClass().getClassLoader())) {
				try {
					DriverManager.deregisterDriver(driver);
					// logger.info("Deregistered '{}' JDBC driver.", driver);
				} catch (SQLException e) {
					// logger.warn("Failed to deregister '{}' JDBC driver.", driver);
				}
			}
		}
	}
}
