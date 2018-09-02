package mini.mes.filetest;

import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileManager extends Thread {

//	private ServerSocket server;
//	private Socket socket;
	private int port;
	private String ip;
	private byte[] buffer;
	
	
	public FileManager() throws InterruptedException{
			this.port = 50001;
			this.ip = "localhost";
			buffer = new byte[1024];
			
			this.receive();
			Thread.sleep(3000L);
			this.send();

	}
	
	
	public void receive(){
		try {
			ServerSocket server = new ServerSocket(port);
//			server = new ServerSocket(port);
			System.out.println("[서버] 수신 대기중... ok");
			Socket socket = server.accept();
//			socket = server.accept();
			System.out.println("[서버]  InputStream 연결 시도");
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			while(true) {
				int size = in.read(buffer);
//				System.out.println(size);
				if(size == -1)	break;
			}
			in.close();
			socket.close();
			System.out.println("[서버]  수신해서 byte[] buffer에 저장 ok");
		}catch(Exception e) {
			
		}
	}
	
	
	public void send() {
		try {
			Socket socket = new Socket("192.168.0.9", 50002);
			System.out.println("[클라이언트] 소켓 접속 ok");
			OutputStream out = socket.getOutputStream();
			System.out.println("[클라이언트]  OutputStream 연결 ok");
			out.write(buffer);
			out.close();
			System.out.println("[서버] buffer내용을 클라이언트에게 전송 ok");
		}catch(Exception e){
		
		}
		
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		FileManager manager = new FileManager();
		
	}
	
	
}
