package logistics.data.filter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import logistics.data.net.DataSocketService;

public class ServletContext implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		new DataSocketService(60000);
	}

}
