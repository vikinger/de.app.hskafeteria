package de.app.hskafeteria.database;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WebAppPropertiesListener implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent sce) {
		String rootPath = sce.getServletContext().getRealPath("/");
        System.setProperty("webroot", rootPath);
	}

}
