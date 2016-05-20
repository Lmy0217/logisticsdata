package logistics.data.service;

import java.util.List;

import logistics.data.bean.Data;

/**
 * 数据服务接口, 提供数据服务
 * 
 * @author myluo(lmy0217@126.com)
 * @version 1605
 */
public interface DataService {

	/**
	 * 保存给定的数据
	 * 
	 * @param data
	 *            给定的数据
	 * @return 保存成功则返回true, 否则返回false
	 */
	public boolean save(Data data);

	/**
	 * 根据给定的条件获得所有满足条件的数据
	 * 
	 * @param lower
	 *            数据上传硬件SSID
	 * @param startTime
	 *            数据收集起始时间
	 * @param endTime
	 *            数据收集终止时间
	 * @return 包含所有满足条件的数据的列表
	 */
	public List<Data> get(int lower, String startTime, String endTime);

}
