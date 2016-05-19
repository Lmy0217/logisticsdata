package logistics.data.dao;

public interface FragmentDAO {

	public boolean create(String id, String value);
	
	public boolean delete(String id);
	
	public String getValue(String id);
}
