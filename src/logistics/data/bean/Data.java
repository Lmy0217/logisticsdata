package logistics.data.bean;

/**
 * ������, ��Ӧ���ݿ����ݱ�, �����ϴ���Ӳ������
 * 
 * @author myluo(lmy0217@126.com)
 * @version 1605
 */
public class Data implements Comparable<Data>, Cloneable {

	/** ���ݴ洢ID */
	private int id;

	/** �ϴ�Ӳ��SSID */
	private String lower;

	/** ���� */
	private String x;

	/** γ�� */
	private String y;

	/** �¶�, ��λ:���϶� */
	private double t;

	/** X����ǿ��, ��λ:��ÿ���η��� */
	private double kx;

	/** Y����ǿ��, ��λ:��ÿ���η��� */
	private double ky;

	/** Z����ǿ��, ��λ:��ÿ���η��� */
	private double kz;

	/** ʣ������ٷֱ� */
	private int power;

	/** ������־, 0�ޱ���, 1�𶯱���, 2�¶ȱ���, 3�𶯱������¶ȱ��� */
	private int alarm;

	/** ���ݲɼ�ʱ�� */
	private String time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLower() {
		return lower;
	}

	public void setLower(String lower) {
		this.lower = lower;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public double getT() {
		return t;
	}

	public void setT(double t) {
		this.t = t;
	}

	public double getKx() {
		return kx;
	}

	public void setKx(double kx) {
		this.kx = kx;
	}

	public double getKy() {
		return ky;
	}

	public void setKy(double ky) {
		this.ky = ky;
	}

	public double getKz() {
		return kz;
	}

	public void setKz(double kz) {
		this.kz = kz;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getAlarm() {
		return alarm;
	}

	public void setAlarm(int alarm) {
		this.alarm = alarm;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * ��ð����˶������������ַ���
	 * 
	 * @return �����˶������������ַ���
	 */
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		sb.append("Data: ");
		sb.append("id=" + id + ", ");
		sb.append("lower=" + lower + ", ");
		sb.append("x=" + x + ", ");
		sb.append("y=" + y + ", ");
		sb.append("t=" + t + ", ");
		sb.append("kx=" + kx + ", ");
		sb.append("ky=" + ky + ", ");
		sb.append("kz=" + kz + ", ");
		sb.append("power=" + power + ", ");
		sb.append("alarm=" + alarm + ", ");
		sb.append("time=" + time);

		return sb.toString();
	}

	/**
	 * �Ƚϸ��������ݶ���������ݶ���
	 * 
	 * @param data
	 *            ���������ݶ���
	 * 
	 * @return ���������ݶ��������������(�����ݴ洢ID)����ͬ, �򷵻�0, ���򷵻�1
	 */
	@Override
	public int compareTo(Data data) {

		if (lower.equals(data.getLower()) && x.equals(data.getX())
				&& y.equals(data.getY()) && t == data.getT()
				&& kx == data.getKx() && ky == data.getKy()
				&& kz == data.getKz() && power == data.getPower()
				&& alarm == data.getAlarm() && time.equals(data.getTime()))
			return 0;

		return 1;
	}

	/**
	 * ���������ش˶����һ������
	 * 
	 * @return �˶����һ������
	 */
	@Override
	public Data clone() {

		Data data = new Data();

		data.setId(id);
		data.setLower(lower);
		data.setX(x);
		data.setY(y);
		data.setT(t);
		data.setKx(kx);
		data.setKy(ky);
		data.setKz(kz);
		data.setPower(power);
		data.setAlarm(alarm);
		data.setTime(time);

		return data;
	}

}
