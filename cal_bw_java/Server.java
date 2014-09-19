package cal_bw_java;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Server {

	private byte[] buf;
	private final int bufSize = 1000000; //1MB
	private int numSend = 0;
	private int port;
	private int readCnt;

	public Server(int port, int bufSendNum){
		this.port = port;
		this.buf = new byte[bufSize];
		this.numSend = bufSendNum;
		int i;
		Random r = new Random();
		r.nextBytes(buf);
	}
	
	public void serverProcess()throws Exception{
		int i = 0;
		ServerSocket serverSocket = new ServerSocket(this.port);
		while(true){
			System.out.println("Waiting on port: " + this.port);
			Socket connSocket = serverSocket.accept();
			System.out.println("Client connected");
			OutputStream outs = connSocket.getOutputStream();
			for (i=0; i<numSend; i++){
				outs.write(buf, 0, bufSize);
			}
			outs.flush();
			outs.close();
			System.out.println("buf transferred");
		}
	}
	
	public static void main(String[] args) throws Exception {
		if (args.length != 2){
			System.out.println("Usage: java cal_bw_java.Server <listen_port> <bufSendNum>");
			System.exit(1);
		}
		int listenPort = Integer.parseInt(args[0]);
		int bufSendNum = Integer.parseInt(args[1]);
		Server server = new Server(listenPort, bufSendNum);
		server.serverProcess();
	}
}
