package de.app.hskafeteria.database;

import java.util.Enumeration;
import java.util.logging.*;
import java.sql.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WebAppPropertiesListener implements ServletContextListener{

	static Logger log = Logger.getLogger("WebAppPropertiesListener");
	
	public void contextDestroyed(ServletContextEvent arg0) {
		
        // This manually deregisters JDBC driver, which prevents Tomcat 7 from complaining about memory leaks wrto this class
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                log.log(Level.INFO, String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
            	log.log(Level.SEVERE, String.format("Error deregistering driver %s", driver), e);
            }

        }
		
	}

	public void contextInitialized(ServletContextEvent sce) {
		String rootPath = sce.getServletContext().getRealPath("/");
        System.setProperty("webroot", rootPath);
	}

}
