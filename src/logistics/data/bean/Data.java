package logistics.data.bean;

public class Data implements Comparable<Data>, Cloneable {
	
	private int id;
	private String lower;
	private String x;
	private String y;
	private double t;
	private double kx;
	private double ky;
	private double kz;
	private int power;
	private int alarm; 
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
