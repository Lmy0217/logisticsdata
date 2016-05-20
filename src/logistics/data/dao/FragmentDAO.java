package logistics.data.dao;

/**
 * ��Ƭ�־û��ӿ�, ����Ƭ�־û�
 * 
 * @author myluo(lmy0217@126.com)
 * @version 1605
 */
public interface FragmentDAO {

	/**
	 * ����������Ƭ�ĳ־û�����(�������ݿ�)
	 * 
	 * @param id
	 *            ������Ƭ��ID
	 * @param value
	 *            ������Ƭ��ֵ
	 * @return �����־û�����(�������ݿ�)�ɹ��򷵻�true, ���򷵻�false
	 */
	public boolean create(String id, String value);

	/**
	 * ɾ��������Ƭ�ĳ־û�����
	 * 
	 * @param id
	 *            ������Ƭ��ID
	 * @return ɾ���־û�����ɹ��򷵻�true, ���򷵻�false
	 */
	public boolean delete(String id);

	/**
	 * ��ø�����Ƭ��ֵ
	 * 
	 * @param id
	 *            ������Ƭ��ID
	 * @return ������Ƭ��ֵ
	 */
	public String getValue(String id);
}
