package logistics.data.dao;

/**
 * 碎片持久化接口, 将碎片持久化
 * 
 * @author myluo(lmy0217@126.com)
 * @version 1605
 */
public interface FragmentDAO {

	/**
	 * 创建给定碎片的持久化对象(存入数据库)
	 * 
	 * @param id
	 *            给定碎片的ID
	 * @param value
	 *            给定碎片的值
	 * @return 创建持久化对象(存入数据库)成功则返回true, 否则返回false
	 */
	public boolean create(String id, String value);

	/**
	 * 删除给定碎片的持久化对象
	 * 
	 * @param id
	 *            给定碎片的ID
	 * @return 删除持久化对象成功则返回true, 否则返回false
	 */
	public boolean delete(String id);

	/**
	 * 获得给定碎片的值
	 * 
	 * @param id
	 *            给定碎片的ID
	 * @return 给定碎片的值
	 */
	public String getValue(String id);
}
