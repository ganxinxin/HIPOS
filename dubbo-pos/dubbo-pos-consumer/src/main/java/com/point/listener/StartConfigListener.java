package com.point.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartConfigListener implements ServletContextListener {

	@SuppressWarnings("unused")
	private String path="/dubbo-consumer.xml";
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent se) {
		
	}

}
