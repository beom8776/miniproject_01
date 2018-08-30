package mini.mes.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 그룹 클라이언트 관리 클래스
 * @author 최범석
 */
public class ClientInfo extends Thread{
	
	/**
	 * 변수 생성
	 */
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Server server;
	
	
	/**
	 * 생성자
	 * @param server 서버 클래스
	 * @param socket 소켓 정보
	 */
	public ClientInfo(Server server, Socket socket) {
		try {
			this.socket = socket;
			this.server = server;
			this.in = new ObjectInputStream(socket.getInputStream());
			this.out = new ObjectOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("입력 연결 오류");
		}
	}
	

	/**
	 * 서버에서 메시지를 반복 수신하고 브로드캐스트 하는 메소드(스레드)
	 */
	public void run() {
		try {
			while(true) {
				String text = in.readUTF();
//				System.out.println(socket + " : " + text);//테스트코드
				server.broadcast(text);
			} 
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("메시지 읽기 오류");
		}
	}
	
	
	/**
	 * 받은 메시지를 자기 자신에게 보내는 메소드
	 * @param text 받은 메시지
	 */
	public void send(String text) {
		try {
				out.writeUTF(text);
				out.flush();
//				System.out.println(socket + "에 텍스트 - " + text + "를 send 완료");//테스트코드
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("메시지 전송 오류");
		}
	}
	
}
