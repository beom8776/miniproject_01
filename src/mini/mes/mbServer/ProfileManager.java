package mini.mes.mbServer;


import java.io.*;
import java.net.*;

public class ProfileManager extends Thread{
	private DataInputStream dataIn = null;
	private FileOutputStream fileOut = null;
	private Socket socket;
	private byte[] buffer;
	private FileOutputStream fout;
	
	public ProfileManager(Socket socket) {
		this.socket = socket;
		 buffer = new byte[1024];
	}
	
	public void run() {
		try {
			dataIn = new DataInputStream(socket.getInputStream());
			String filename = dataIn.readUTF();
			int size;
			
			fout = new FileOutputStream(new File(filename));
			while((size = dataIn.read(buffer)) != -1) {
				fout.write(buffer);
			}
			dataIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}








