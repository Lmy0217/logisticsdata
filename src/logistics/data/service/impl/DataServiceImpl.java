package logistics.data.service.impl;

import logistics.data.bean.Data;
import logistics.data.dao.DataDAO;
import logistics.data.service.DataService;
import logistics.data.util.DAOFactory;

public class DataServiceImpl implements DataService {
	
	private DataDAO dataDAO;
	
	public DataServiceImpl() {
		dataDAO = DAOFactory.getDataDAO();
	}

	@Override
	public boolean create(int lower, String x, String y, int t, int k) {
		return dataDAO.create(lower, x, y, t, k);
	}

	@Override
	public Data get(int lower, String startTime, String endTime) {
		// TODO 自动生成的方法存根
		return null;
	}

}
