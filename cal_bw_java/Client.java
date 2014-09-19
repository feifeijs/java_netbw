package cal_bw_java;

import java.io.*;
import java.net.*;

public class Client {

	private String serverIP;
	private int port;
	private final int bufSize = 1000000; //1MB
	private int numRecv = 0;
	private byte[] buf;

	public Client(String serverIP, int port, int bufRecvNum){
		this.serverIP = serverIP;
		this.port = port;
		this.buf = new byte[bufSize];
		this.numRecv = bufRecvNum;
	}
	
	public void getBufFromServer() throws Exception{
		Socket clientSocket = new Socket(this.serverIP, this.port);
		System.out.println("Connected to server");
		BufferedInputStream bis = new BufferedInputStream(clientSocket.getInputStream());

		System.out.println("Start fetching buf");
		long t = System.currentTimeMillis();

		int nRead = 0; 
		int totalRead = 0;

		while(true){
			nRead = bis.read(buf, 0, bufSize);
			if (nRead == -1){
				break;
			}else{
				totalRead += nRead;
			}
		}


		t = System.currentTimeMillis() - t;
		double bw = 0.0;
		bw = (double)totalRead / t * 1000;
		System.out.println("time(sec): " + t/1000.0);
		System.out.println("Total bytes recv: " + totalRead);
		System.out.println("Bandwidth(MBps): " + bw/1000000 );
		clientSocket.close();
		System.out.println("Socket closed");
	}
		
	public static void main(String[] args) throws Exception {
		if (args.length != 3){
			System.out.println("Usage: java cal_bw_java.Client <server_ip> <server_port> <bufRecvNum>");
			System.exit(1);
		}
		String serverIP = new String(args[0]);
		int port = Integer.parseInt(args[1]);
		int bufRecvNum = Integer.parseInt(args[2]);
		Client client = new Client(serverIP, port, bufRecvNum);
		client.getBufFromServer();
	}
}
