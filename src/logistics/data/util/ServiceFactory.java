package logistics.data.util;

import logistics.data.service.DataService;
import logistics.data.service.impl.DataServiceImpl;

public class ServiceFactory {
	
	public static DataService getDataService() {
		return new DataServiceImpl();
	}

}
