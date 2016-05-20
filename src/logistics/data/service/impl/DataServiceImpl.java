package logistics.data.service.impl;

import java.util.List;

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
	public boolean save(Data data) {
		return dataDAO.create(data);
	}

	@Override
	public List<Data> get(int lower, String startTime, String endTime) {
		return dataDAO.get(lower, startTime, endTime);
	}

}
