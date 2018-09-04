package mini.mes.net;

import java.net.ServerSocket;

/**
 * 그룹채팅 서버 실행 클래스
 * @author 최범석
 */
public class Server extends Thread{
	
	/**
	 * 변수 생성
	 */
	private int port;
	private ServerSocket server;
	private NetManager manager;
	
	
	/**
	 * 생성자
	 * @param port 포트번호
	 */
	public Server(int port) {
		try {
			this.port = port;
			this.server = new ServerSocket(port);
			this.manager = new NetManager(port);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("서버 소켓 오류");
		}
	}
	
	
	/**
	 * [1:1] 채팅 서버 실행 메소드
	 */
	public void workServerOne() {
		manager.workServer(server);
	}
	
	
	/**
	 * [그룹] 채팅 서버 실행 메소드
	 */
	public void workGroup() {
		manager.workGroupServer(this, server);
	}
	
	
	/**
	 * [그룹] 받은 메시지를 브로드캐스팅 하는 메소드
	 * @param text 그룹 클라이언트로 부터 받은 메시지
	 */
	public void broadcast(String text) {
		manager.chatBroadcast(text);
	}
	
	
	/**
	 * 채팅 서버 실행 메인
	 */
//	public static void main(String[] args) {
//		
//		Server group = new Server(50000);
//		1:1 채팅일경우
//		group.workServerOne();
//		그룹 채팅일경우
//		group.workGroup();
//	}
}
