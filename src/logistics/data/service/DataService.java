package logistics.data.service;

import java.util.List;

import logistics.data.bean.Data;

/**
 * ���ݷ���ӿ�, �ṩ���ݷ���
 * 
 * @author myluo(lmy0217@126.com)
 * @version 1605
 */
public interface DataService {

	/**
	 * �������������
	 * 
	 * @param data
	 *            ����������
	 * @return ����ɹ��򷵻�true, ���򷵻�false
	 */
	public boolean save(Data data);

	/**
	 * ���ݸ������������������������������
	 * 
	 * @param lower
	 *            �����ϴ�Ӳ��SSID
	 * @param startTime
	 *            �����ռ���ʼʱ��
	 * @param endTime
	 *            �����ռ���ֹʱ��
	 * @return ���������������������ݵ��б�
	 */
	public List<Data> get(int lower, String startTime, String endTime);

}
