package logistics.data.dao;

import logistics.data.bean.Data;

public interface DataDAO {
	
	public boolean create(int lower, String x, String y, int t, int k);
	
	public Data get(int lower, String startTime, String endTime);

}
