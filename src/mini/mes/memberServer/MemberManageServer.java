package mini.mes.memberServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import mini.mes.chatServer.Board;
import mini.mes.chatServer.ChatServerManager;

/**
 * 회원관리 서버
 * @author 김현진, 최범석
 */

public class MemberManageServer {
	
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	int port = Board.SUB_PORTNUMBER;
	
	public  MemberManageServer() {
		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("소켓생성오류");
		}
	}
	
	public void work() {
		try {
		while(true) {
			System.out.println("[서버] 수신 대기중... ok");
			socket = serverSocket.accept();
			MemberManager manager = new MemberManager(this, socket);
			manager.setDaemon(true);
			manager.start();
			Thread.sleep(50L);
			
		}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("[서버] 클라이언트 접속 실패");
		}
	}
	
	//실행용 메인
	public static void main(String[] args) {
		MemberManageServer server = new MemberManageServer();
		server.work();
	}
}