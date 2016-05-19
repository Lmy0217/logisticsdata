package logistics.data.service;

import java.util.List;

import logistics.data.bean.Data;

public interface DataService {
	
	public boolean create(Data data);
	
	public List<Data> get(int lower, String startTime, String endTime);

}
