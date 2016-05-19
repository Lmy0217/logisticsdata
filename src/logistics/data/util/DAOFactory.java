package logistics.data.util;

import logistics.data.dao.DataDAO;
import logistics.data.dao.FragmentDAO;
import logistics.data.dao.impl.DataDAOImpl;
import logistics.data.dao.impl.FragmentDAOImpl;

public class DAOFactory {
	
	public static DataDAO getDataDAO() {
		return new DataDAOImpl();
	}

	public static FragmentDAO getFragmentDAO() {
		return new FragmentDAOImpl();
	}
}
