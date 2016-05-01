package logistics.data.service;

import logistics.data.bean.Data;

public interface DataService {
	
	public boolean create(int lower, String x, String y, int t, int k);
	
	public Data get(int lower, String startTime, String endTime);

}
