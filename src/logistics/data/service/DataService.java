package logistics.data.service;

import java.util.List;

import logistics.data.bean.Data;

public interface DataService {
	
	public boolean create(int lower, String x, String y, int t, int k);
	
	public List<Data> get(int lower, String startTime, String endTime);

}
