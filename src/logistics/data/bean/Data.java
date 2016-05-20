package logistics.data.bean;

/**
 * 数据类, 对应数据库数据表, 包含上传的硬件数据
 * 
 * @author myluo(lmy0217@126.com)
 * @version 1605
 */
public class Data implements Comparable<Data>, Cloneable {

	/** 数据存储ID */
	private int id;

	/** 上传硬件SSID */
	private String lower;

	/** 经度 */
	private String x;

	/** 纬度 */
	private String y;

	/** 温度, 单位:摄氏度 */
	private double t;

	/** X轴震动强度, 单位:米每二次方秒 */
	private double kx;

	/** Y轴震动强度, 单位:米每二次方秒 */
	private double ky;

	/** Z轴震动强度, 单位:米每二次方秒 */
	private double kz;

	/** 剩余电量百分比 */
	private int power;

	/** 报警标志, 0无报警, 1震动报警, 2温度报警, 3震动报警和温度报警 */
	private int alarm;

	/** 数据采集时间 */
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
	 * 获得包含此对象各数据项的字符串
	 * 
	 * @return 包含此对象各数据项的字符串
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
	 * 比较给定的数据对象与此数据对象
	 * 
	 * @param data
	 *            给定的数据对象
	 * 
	 * @return 若两个数据对象的所有数据项(除数据存储ID)都相同, 则返回0, 否则返回1
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
	 * 创建并返回此对象的一个复制
	 * 
	 * @return 此对象的一个复制
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
