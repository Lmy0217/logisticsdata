package logistics.data.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import logistics.data.util.ServiceFactory;

public class DataSocketService {

	private SocketListener socketListener;
	private int length = 5;
	private boolean test = false;
	private boolean testFlag = false;

	public DataSocketService(int port) {
		socketListener = new SocketListener(port);
		socketListener.start();
	}

	public void close() {
		socketListener.close();
	}

	public class SocketListener extends Thread {

		private ServerSocket serverSocket;
		private boolean flag = true;

		public SocketListener(int port) {
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void close() {
			flag = false;
		}

		public void run() {
			try {
				if (test)
					System.out.println("listener start...");
				while (flag) {
					if (test)
						System.out.println("listener while start...");
					SocketRun socketRun = new SocketRun(serverSocket.accept());
					socketRun.start();
				}
				if (test)
					System.out.println("listener stop...");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public class SocketRun extends Thread {

		private Socket socket;

		public SocketRun(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try {
				if (test)
					System.out.println("run start...");

				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));

				String line = null;
				int index = -1;
				String[] stringArray = new String[length];
				while ((line = in.readLine()) != null) {
					// TODO socket run
					if (test)
						System.out.println(line);
					index++;
					stringArray[index] = line;
					if (index == length - 1) {
						index = -1;
						if (test) {
							System.out.print("info:");
							for (String string : stringArray)
								System.out.print(" " + string);
							System.out.println();
						}
						testFlag = ServiceFactory.getDataService().create(
								Integer.parseInt(stringArray[0]),
								stringArray[1], stringArray[2],
								Integer.parseInt(stringArray[3]),
								Integer.parseInt(stringArray[4]));
						if (test)
							System.out.println("insert " + testFlag);
					}
				}

				in.close();
				socket.close();
				if (test)
					System.out.println("run stop...");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		DataSocketService dataSocketService = new DataSocketService(60000);
		dataSocketService.test = true;
	}

}
