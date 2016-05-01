package logistics.data.util;

import logistics.data.dao.DataDAO;
import logistics.data.dao.impl.DataDAOImpl;

public class DAOFactory {
	
	public static DataDAO getDataDAO() {
		return new DataDAOImpl();
	}

}
