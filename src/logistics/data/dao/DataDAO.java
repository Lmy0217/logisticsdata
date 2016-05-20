package logistics.data.dao;

import java.util.List;

import logistics.data.bean.Data;

/**
 * ���ݳ־û��ӿ�, �����ݳ־û�
 * 
 * @author myluo(lmy0217@126.com)
 * @version 1605
 */
public interface DataDAO {

	/**
	 * �����������ݵĳ־û�����(�������ݿ�)
	 * 
	 * @param data
	 *            ����������
	 * @return �����־û�����(�������ݿ�)�ɹ��򷵻�true, ���򷵻�false
	 */
	public boolean create(Data data);

	/**
	 * ���ݸ�������������������������ĳ־û������Ӧ������
	 * 
	 * @param lower
	 *            �����ϴ�Ӳ��SSID
	 * @param startTime
	 *            �����ռ���ʼʱ��
	 * @param endTime
	 *            �����ռ���ֹʱ��
	 * @return �����������������ĳ־û������Ӧ���ݵ��б�
	 */
	public List<Data> get(int lower, String startTime, String endTime);

}
