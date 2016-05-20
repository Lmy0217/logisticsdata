package logistics.data.dao;

import java.util.List;

import logistics.data.bean.Data;

/**
 * 数据持久化接口, 将数据持久化
 * 
 * @author myluo(lmy0217@126.com)
 * @version 1605
 */
public interface DataDAO {

	/**
	 * 创建给定数据的持久化对象(存入数据库)
	 * 
	 * @param data
	 *            给定的数据
	 * @return 创建持久化对象(存入数据库)成功则返回true, 否则返回false
	 */
	public boolean create(Data data);

	/**
	 * 根据给定的条件获得所有满足条件的持久化对象对应的数据
	 * 
	 * @param lower
	 *            数据上传硬件SSID
	 * @param startTime
	 *            数据收集起始时间
	 * @param endTime
	 *            数据收集终止时间
	 * @return 包含所有满足条件的持久化对象对应数据的列表
	 */
	public List<Data> get(int lower, String startTime, String endTime);

}
