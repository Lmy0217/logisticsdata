package logistics.data.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import logistics.data.bean.Data;
import logistics.data.net.DataSocketService;

public class TransportCheck {

	private static List<Data> sendList;
	private static int count;
	
	public static boolean flag;
	
	static {
		sendList = new ArrayList<Data>();
		setZero();
	}
	
	public static void setZero() {
		count = 0;
	}
	
	public static int getCount() {
		return count;
	}
	
	public static void sendCheck(Data data) {
		//System.out.println("send " + data);
		sendList.add(data);
	}
	
	public static boolean receiveCheck(Data data) {
		//System.out.println("receive " + data);

		if (sendList.size() == 0) {
			count++;
			System.out.println(errInfo());
			return false;
		}

		if (data.compareTo(sendList.get(0)) == 1) {
			if (sendList.size() == 1 || data.compareTo(sendList.get(1)) == 1) {
				count++;
				System.out.println(errInfo());
				return false;
			} else {
				sendList.remove(1);
				System.out.println(successInfo() + " For 1!");
				return true;
			}
		} else {
			sendList.remove(0);
			System.out.println(successInfo() + " For 0!");
			return true;
		}
	}
	
	public static String successInfo() {
		return "Transport check data success!";
	}
	
	public static String errInfo() {
		return "Transport check data error! Count is " + getCount();
	}
	
	public static void main(String[] args) {
		
		flag = true;
		
		new DataSocketService(60000);
		
		try {
			new DataSocket(1, InetAddress.getByName("127.0.0.1"), 60000);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
}
