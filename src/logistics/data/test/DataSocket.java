package logistics.data.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import logistics.data.bean.Data;
import logistics.data.util.ByteUtil;

public class DataSocket {

	private int numberOfClients;
	private InetAddress address;
	private int port;
	
	public static boolean test;

	public DataSocket(InetAddress address, int port) {
		this(1, address, port);
	}
	
	public DataSocket(int number, InetAddress address, int port) {
		setOptions(address, port);
		addClient(number);
	}
	
	private void setOptions(InetAddress address, int port) {
		this.address = address;
		this.port = port;
	}

	public boolean addClient(int number) {
		
		for (int i = 0; i < number; i++) {
			Client client = new Client();
			client.start();
			numberOfClients++;
		}
		
		return true;
	}
	
	public int numberOfClients() {
		return numberOfClients;
	}

	public class Client extends Thread {

		private Socket socket;
		private Data data;
		private byte[] byteArray;
		private List<Byte> fragList;
		private int index;
		
		private int arrayLength = 1024;
		private int packLength = 55;
		
		private double defaultX = 116.00;
		private double defaultY = 28.60;
		private int directX = Math.random() > 0.5 ? 1 : -1;
		private int directY = Math.random() > 0.5 ? 1 : -1;
		private double changeX = 0.0001;
		private double changeY = 0.0001;
		
		private boolean runFlag = true;

		public Client() {
			data = new Data();
			byteArray = new byte[arrayLength];
			fragList = new ArrayList<Byte>();
			index = 0;
			initData();
		}

		public void run() {
			try {
				while(runFlag) {
					// TODO socket write
					setData();
					
					if(test)
						System.out.println(data);
					
					if(TransportCheck.flag)
						TransportCheck.sendCheck(data.clone());
					
					client();
					sleep(1000);
					
					System.out.println("index=" + index);
					
					if (index == arrayLength) {
						socket = new Socket(address, port);
						OutputStream out = socket.getOutputStream();
						out.write(byteArray);
						
						if(test) {
							ByteUtil.printHexString(byteArray);
							System.out.println();
						}
						
						index = 0;
						out.flush();
						//sleep(10000);
						out.close();
						socket.close();
					}
				}
				numberOfClients--;
				
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		private void initData() {
			//lower = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
			data.setLower("" + getId());
			
			data.setX("" + (defaultX + (Math.random() - 0.5) * 1));
			data.setY("" + (defaultY + (Math.random() - 0.5) * 1));
		}
		
		private void setData() {
			
			data.setLower("FFFFFFFFFFFFFFFFFFFF");
			
			data.setX("" + (Double.parseDouble(data.getX()) + Math.random() * changeX * directX));
			data.setX("" + ((double)(int)(Double.parseDouble(data.getX()) * 1000000) / 1000000));
			data.setY("" + (Double.parseDouble(data.getY()) + Math.random() * changeY * directY));
			data.setY("" + ((double)(int)(Double.parseDouble(data.getY()) * 1000000) / 1000000));
			
			data.setT((int)(Math.random() * 200) - 100);
			
			data.setKx(Math.random() * 25.6);
			data.setKx((double)(int)(data.getKx() * 10) / 10);
			data.setKy(Math.random() * 25.6);
			data.setKy((double)(int)(data.getKy() * 10) / 10);
			data.setKz(Math.random() * 25.6);
			data.setKz((double)(int)(data.getKz() * 10) / 10);
			
			data.setPower((int)(Math.random() * 100));
			
			data.setAlarm((int)(Math.random() * 4) % 4);
			
			data.setTime(MessageFormat.format("{0,date,yyyy-MM-dd HH:mm:ss}",
					new Object[] { new Date(System.currentTimeMillis()) }));
		}
		
		private void client() {
			
			for (; fragList.size() != 0; index++) {
				byteArray[index] = fragList.get(0);
				fragList.remove(0);
			}
			
			escape(translate());
			
			if(index + fragList.size() <= arrayLength) {
				for(; fragList.size() != 0; index++) {
					byteArray[index] = fragList.get(0);
					fragList.remove(0);
				}
			} else {
				int empty = arrayLength - index;
				for(int i = 0; i < empty; i++, index++) {
					byteArray[index] = fragList.get(0);
					fragList.remove(0);
				}
			}
		}
		
		public byte[] translate() {
			
			byte[] fragArray = new byte[packLength];
			byte[] temp;
			
			fragArray[0] = (byte) 0x7E;
			fragArray[1] = (byte) 0x03;
			
			//
			for(int i = 2; i < 30; i++) {
				fragArray[i] = (byte)0xFF;
			}
			
			fragArray[30] = (byte) 0x00;//
			fragArray[31] = (byte) 0x01;//
			fragArray[32] = (byte) 0x01;
			
			fragArray[33] = (ByteUtil.hexStr2Bytes(ByteUtil.int2HexStr((int)(data.getT() + 100), 2)))[0];
			
			fragArray[34] = (ByteUtil.hexStr2Bytes(ByteUtil.int2HexStr((int)(data.getKx() * 10), 2)))[0];
			fragArray[35] = (ByteUtil.hexStr2Bytes(ByteUtil.int2HexStr((int)(data.getKy() * 10), 2)))[0];
			fragArray[36] = (ByteUtil.hexStr2Bytes(ByteUtil.int2HexStr((int)(data.getKz() * 10), 2)))[0];
			
			temp = (ByteUtil.hexStr2Bytes(ByteUtil.int2HexStr((int)(Double.parseDouble(data.getX()) * 1000000), 8)));
			fragArray[37] = temp[0];
			fragArray[38] = temp[1];
			fragArray[39] = temp[2];
			fragArray[40] = temp[3];
			temp = (ByteUtil.hexStr2Bytes(ByteUtil.int2HexStr((int)(Double.parseDouble(data.getY()) * 1000000), 8)));
			fragArray[41] = temp[0];
			fragArray[42] = temp[1];
			fragArray[43] = temp[2];
			fragArray[44] = temp[3];
			
			fragArray[45] = (ByteUtil.hexStr2Bytes(ByteUtil.int2HexStr(data.getPower(), 2)))[0];
			
			fragArray[46] = (ByteUtil.hexStr2Bytes(ByteUtil.int2HexStr(data.getAlarm(), 2)))[0];
			
			fragArray[47] = (ByteUtil.hexStr2Bytes(data.getTime().substring(2, 4)))[0];
			fragArray[48] = (ByteUtil.hexStr2Bytes(data.getTime().substring(5, 7)))[0];
			fragArray[49] = (ByteUtil.hexStr2Bytes(data.getTime().substring(8, 10)))[0];
			fragArray[50] = (ByteUtil.hexStr2Bytes(data.getTime().substring(11, 13)))[0];
			fragArray[51] = (ByteUtil.hexStr2Bytes(data.getTime().substring(14, 16)))[0];
			fragArray[52] = (ByteUtil.hexStr2Bytes(data.getTime().substring(17, 19)))[0];
			
			byte check = fragArray[1];
			for(int i = 2; i < 53; i++) {
				check ^= fragArray[i];
			}
			fragArray[53] = check;
			
			fragArray[54] = (byte) 0x7E;
			
			return fragArray;
		}
		
		public void escape(byte[] byteArray) {
			fragList.add((byte) 0x7E);
			for (int i = 1; i < packLength - 1; i++) {
				if (byteArray[i] == 0x7E) {
					fragList.add((byte) 0x7D);
					fragList.add((byte) 0x01);
				} else if (byteArray[i] == 0x7D) {
					fragList.add((byte) 0x7D);
					fragList.add((byte) 0x02);
				} else {
					fragList.add(byteArray[i]);
				}
			}
			fragList.add((byte) 0x7E);
		}
	}
	
	public static void main(String[] args) {
		test = true;
		try {
			new DataSocket(1, InetAddress.getByName("127.0.0.1"), 60000);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
