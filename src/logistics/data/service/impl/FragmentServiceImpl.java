package logistics.data.service.impl;

import logistics.data.dao.FragmentDAO;
import logistics.data.service.FragmentService;
import logistics.data.util.DAOFactory;

public class FragmentServiceImpl implements FragmentService {
	
	private FragmentDAO fragmentDAO;
	
	public FragmentServiceImpl() {
		fragmentDAO = DAOFactory.getFragmentDAO();
	}

	@Override
	public boolean create(String id, String value) {
		return fragmentDAO.create(id, value);
	}
	
	@Override
	public boolean delete(String id) {
		return fragmentDAO.delete(id);
	}

	@Override
	public String getValue(String id) {
		return fragmentDAO.getValue(id);
	}

}
