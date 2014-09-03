package cal_bw_java;

import java.io.*;
import java.net.*;

public class Client {

	private String serverIP;
	private int port;
	private final int MAX_BUF_SIZE = 200000000; //200M
	private byte[] buf;
	private int readCnt;
	public Client(String serverIP, int port){
		this.serverIP = serverIP;
		this.port = port;
		this.buf = new byte[this.MAX_BUF_SIZE];
		this.readCnt = 0;
	}
	
	public void getFileFromServer(String tmpfile) throws Exception{
		  Socket clientSocket = new Socket(this.serverIP, this.port);
		  System.out.println("Connected to server");
		  BufferedInputStream bis = new BufferedInputStream(clientSocket.getInputStream());
		  
		  System.out.println("Start fetching file");
		  long t = System.currentTimeMillis();
		  int bytesRead = 0;
		  while( (bytesRead = bis.read(this.buf, this.readCnt, this.MAX_BUF_SIZE - this.readCnt)) > -1){
			  this.readCnt += bytesRead;
		  }
		  t = System.currentTimeMillis() - t;
		  double bw = 0.0;
		  bw = (double)this.readCnt / t * 1000;
		  System.out.println("time(sec): " + t/1000.0);
		  System.out.println("Total bytes recv: " + this.readCnt);
		  System.out.println("Bandwidth(MBps): " + bw/1000000 );
		  clientSocket.close();
		  System.out.println("Socket closed");
		  File tmpf = new File(tmpfile);
		  FileOutputStream fos = new FileOutputStream(tmpf);
		  fos.write(this.buf, 0, this.readCnt);
		  fos.close();
		  System.out.println("File write done");
	}
		
	public static void main(String[] args) throws Exception {
		if (args.length != 3){
			System.out.println("Usage: java cal_bw_java.Client <server_ip> <server_port> <tmpfile>");
			System.exit(1);
		}
		String serverIP = new String(args[0]);
		int port = Integer.parseInt(args[1]);
		String tmpFile = new String(args[2]);
		Client client = new Client(serverIP, port);
		client.getFileFromServer(tmpFile);
	}
}
