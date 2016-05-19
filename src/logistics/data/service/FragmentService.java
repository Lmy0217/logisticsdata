package logistics.data.service;

public interface FragmentService {

	public boolean create(String id, String value);
	
	public boolean delete(String id);
	
	public String getValue(String id);
}
