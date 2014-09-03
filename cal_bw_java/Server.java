package cal_bw_java;

import java.io.*;
import java.net.*;

public class Server {

	private byte[] buf;
	private String file;
	private int port;
	private int readCnt;
	public Server(String file, int port){
		this.file = file;
		this.port = port;
	}
	
	public int read2mem() throws Exception{
		File f = new File(this.file);
		int fileSize = (int)f.length();
		this.buf = new byte[fileSize];
		FileInputStream  fis = new FileInputStream(f);
		int readCnt = fis.read(buf);
		fis.close();	
		if (readCnt != fileSize){
			throw new IOException("readCnt != fileSize");
		}else{
			System.out.println("Bytes num " + readCnt+ " read into memory");
		}		
		this.readCnt = readCnt;
		return readCnt;
	}
	
	public void serverProcess()throws Exception{
		ServerSocket serverSocket = new ServerSocket(this.port);
		while(true){
			System.out.println("Waiting on port: " + this.port);
			Socket connSocket = serverSocket.accept();
			System.out.println("Client connected");
			OutputStream outs = connSocket.getOutputStream();
			outs.write(this.buf, 0, this.readCnt);
			outs.flush();
			outs.close();
			System.out.println("File transferred");
		}
	}
	
	public static void main(String[] args) throws Exception {
		if (args.length != 2){
			System.out.println("Usage: java cal_bw_java.Server <transfer_file> <listen_port>");
			System.exit(1);
		}
		String f = new String(args[0]);
		int listenPort = Integer.parseInt(args[1]);
		Server server = new Server(f, listenPort);
		server.read2mem();
		server.serverProcess();
	}
}
