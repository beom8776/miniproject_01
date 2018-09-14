package mini.mes.memberServer;

import java.net.ServerSocket;
import java.net.Socket;

import mini.mes.chatServer.Board;

/**
 * 회원관리 서버
 * @author 김현진, 최범석
 */

public class MemberManageServer {
	public static void main(String[] args) {
		
		/**
		 * 서버 연결 준비
		 */
		ServerSocket server = null;
		Socket socket = null;
		int searchPort = Board.SUB_PORTNUMBER;
//		String kind = null;
		
			try {
				
				/**
				 * Server와 Client간의 연결
				 */
				server = new ServerSocket(searchPort);
				System.out.println("(Server)현재상태 : [접속대기] --- 1");
				System.out.println("(Server)현재내용 : ["+server+"] --- 1");
				socket = server.accept();
				System.out.println("(Server)현재상태 : [접속완료] --- 2");
				System.out.println("(Server)현재내용 : ["+socket+"] --- 2");
				System.out.println();
				
				MemberManager members  = new MemberManager(socket);
				members.setDaemon(true);
				members.run();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
