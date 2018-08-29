package mini.mes.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 그룹채팅 클라이언트의 관리 클래스
 * @author 최범석
 */
public class ClientInfo extends Thread {
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private NetManager netmanager;
	
	
	/**
	 * 생성자
	 * @param netmanager 네트워크매니저 클래스
	 * @param socket 소켓정보
	 */
	public ClientInfo(NetManager netmanager, Socket socket) {
		try {
			this.netmanager = netmanager;
			this.socket = socket;
			this.in = new ObjectInputStream(socket.getInputStream());
			this.out = new ObjectOutputStream(socket.getOutputStream());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 지속적으로 반복하여 사용자의 메시지를 수신하여 브로드캐스팅하는 메소드
	 */
	@Override
		public void run() {
			try {
				while(true) {				
					String text = in.readUTF();
//					System.out.println(socket + " : " + text);//테스트코드
					netmanager.chat.yourChat(text);
					netmanager.broadcast(text);
				} 
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	
	/**
	 * 메시지를 전송하는 메소드
	 * @param text 보낼 메시지
	 */
	public void send(String text) {
		try {
			out.writeUTF(text);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

