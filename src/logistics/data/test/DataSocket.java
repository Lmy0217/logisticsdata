package logistics.data.test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class DataSocket {

	private int numberOfClients;
	private InetAddress address;
	private int port;

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
		
		try {
			for(int i = 0; i < number; i++) {
				Client client = new Client(new Socket(address, port));
				client.start();
				numberOfClients++;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public int numberOfClients() {
		return numberOfClients;
	}

	public class Client extends Thread {

		private Socket socket;
		private int lower;
		private double X;
		private double Y;
		private int directX;
		private int directY;
		private boolean runFlag = true;

		public Client(Socket socket) {
			this.socket = socket;
			initLower();
			initXY();
		}

		public void run() {
			try {
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream()));
				
				while(runFlag) {
					// TODO socket write
					out.write("" + lower + "\n" + getXY() + getTK());
					out.flush();
					sleep(1000);
				}
				
				out.close();
				socket.close();
				numberOfClients--;
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		private void initLower() {
			lower = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
		}
		
		private void initXY() {
			
			double defaultX = 116.00;
			double defaultY = 28.60;
			
			X = defaultX + (Math.random() - 0.5) * 1;
			Y = defaultY + (Math.random() - 0.5) * 1;
			
			directX = Math.random() > 0.5 ? 1 : -1;
			directY = Math.random() > 0.5 ? 1 : -1;
		}
		
		private String getXY() {
			
			double changeX = 0.0001;
			double changeY = 0.0001;
			
			X += Math.random() * changeX * directX;
			Y += Math.random() * changeY * directY;
			
			return "" + X + "\n" + Y + "\n";
		}
		
		private String getTK() {
			
			int T = (int)(Math.random() * 40);
			int K = (int)(Math.random() * 10);
			
			return "" + T + "\n" + K + "\n";
		}
	}
	
	public static void main(String[] args) {
		try {
			new DataSocket(1, InetAddress.getByName("127.0.0.1"), 60000);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
