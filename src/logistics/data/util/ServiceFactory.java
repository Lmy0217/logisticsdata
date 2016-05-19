package logistics.data.util;

import logistics.data.service.DataService;
import logistics.data.service.FragmentService;
import logistics.data.service.impl.DataServiceImpl;
import logistics.data.service.impl.FragmentServiceImpl;

public class ServiceFactory {
	
	public static DataService getDataService() {
		return new DataServiceImpl();
	}

	public static FragmentService getFragmentService() {
		return new FragmentServiceImpl();
	}
}
