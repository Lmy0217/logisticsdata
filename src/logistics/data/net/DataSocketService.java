package logistics.data.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import logistics.data.test.TransportCheck;
import logistics.data.util.ByteUtil;
import logistics.data.util.ServiceFactory;

public class DataSocketService {

	private SocketListener socketListener;
	public static boolean test = false;
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
				
				InputStream in = socket.getInputStream();

				byte[] b = new byte[1];
				List<Byte> byteList = new ArrayList<Byte>();
				List<Byte> fragList = new ArrayList<Byte>();
				int headCount = 0;
				boolean dataFlag = false;
				boolean tcpStart = true;
				boolean tcpDataStart = false;
				boolean startFlag = true;
				boolean fragFlag = false;
				String id = null;
				
				while (in.read(b) != -1) {
					
					if(b[0] == 0x7E) {
						headCount++;
						if(headCount == 2 && dataFlag == true) {
							
							if (!tcpStart) {
								
								if(byteList.size() != 0) {
									DataTranslate dataTranslate = new DataTranslate(byteList);
									id = dataTranslate.getId();
									if(id != null) {
										if(test)
											System.out.println(dataTranslate.toData());
										
										if(TransportCheck.flag)
											TransportCheck.receiveCheck(dataTranslate.toData());
										
										testFlag = ServiceFactory.getDataService().save(dataTranslate.toData());
										if(test)
											System.out.println("Data persistence " + (testFlag ? "success" : "failed") + "!");
									}
								}
								
								if (tcpDataStart && id != null) {
									fragFlag = true;
									tcpDataStart = false;
								}
								
								if (fragFlag) {
									String value = ServiceFactory.getFragmentService().getValue(id);
									
									if (value != null) {
										ServiceFactory.getFragmentService().delete(id);
										byte[] bytearray = ByteUtil.hexStr2Bytes(value);
										for (int i = bytearray.length - 1; i >= 0; i--) {
											fragList.add(0, bytearray[i]);
										}
									}
									
									if(fragList.size() != 0) {
										DataTranslate fragTranslate = new DataTranslate(fragList);
										id = fragTranslate.getId();
										if(id != null) {
											if(test)
												System.out.println(fragTranslate.toData());
											
											if(TransportCheck.flag)
												TransportCheck.receiveCheck(fragTranslate.toData());
											
											testFlag = ServiceFactory.getDataService().save(fragTranslate.toData());
											if(test)
												System.out.println("Data persistence " + (testFlag ? "success" : "failed") + "!");
										}
									}
									
									fragList.clear();
									fragFlag = false;
								}

								byteList.clear();
							} else {
								tcpStart = false;
							}
							headCount = 0;
							dataFlag = false;
						} else if(dataFlag == false) {
							tcpStart = false;
							headCount = 1;
						}
					} else {
						if(headCount == 1 || (headCount == 0 && dataFlag == false)) {
							headCount = 1;
							dataFlag = true;
							if(startFlag && !tcpStart) {
								tcpDataStart = true;
								startFlag = false;
							}
							if(tcpStart) {
								fragList.add(b[0]);
							} else {
								byteList.add(b[0]);
							}
						}
					}
				}
				if(dataFlag == true) {
					StringBuilder value = new StringBuilder();
					for(Byte e : byteList) {
						value.append(ByteUtil.byte2HexStr(new byte[]{e}));
					}
					testFlag = ServiceFactory.getFragmentService().create(id, value.toString());
					if(test)
						System.out.println("Fragment persistence " + (testFlag ? "success" : "failed") + "!");
				}
				headCount = 0;
				dataFlag = false;

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
		test = true;
		new DataSocketService(60000);
	}

}
