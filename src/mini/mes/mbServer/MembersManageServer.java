package mini.mes.mbServer;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 회원관리 서버
 * @author 김현진
 */

public class MembersManageServer {
	public static void main(String[] args) {
		
		/**
		 * 서버 연결 준비
		 */
		ServerSocket server = null;
		Socket socket = null;
		int searchPort = 10001;
		
		ObjectInputStream objectIn = null;
		String kind = null;
		
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
			
			/**
			 * 어떤 작업을 실행 할 것인지를 받는 단계
			 */
			objectIn = new ObjectInputStream(socket.getInputStream());
			kind = (String)objectIn.readObject();
			JoinMemberManager members  = new JoinMemberManager(socket, objectIn);	
			
			/**
			 * 회원DB 생성 작업
			 */
			if(kind.equals("회원가입")) {
				members.create();
			}
			
			/**
			 * Client에서 ID검색을 요청하여 확인 후 결과 값을 반환해 주는 작업
			 */
			if(kind.equals("친구찾기")) {
				members.start();
			}
			
			/**
			 * 나의정보 - 상태메시지 업데이트
			 */
			if(kind.equals("상태메시지")) {
				members.myment();
			}
			
		} catch (Exception e) {e.printStackTrace();}
		
		
		
	}
}
