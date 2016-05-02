package logistics.data.dao;

import java.util.List;

import logistics.data.bean.Data;

public interface DataDAO {
	
	public boolean create(int lower, String x, String y, int t, int k);
	
	public List<Data> get(int lower, String startTime, String endTime);

}
