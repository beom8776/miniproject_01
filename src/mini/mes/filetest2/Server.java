package mini.mes.filetest2;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 채팅 서버 클래스
 * @author 최범석
 */
public class Server {
		
		/**
		 * 변수 생성
		 */
		private ServerSocket serverSocket = null;
		private Socket socket = null;
		private Map<String, ClientInfo> map;
//		private List<ClientInfo> list = new ArrayList<>();//테스트코드
		
		
		/**
		 * 생성자
		 */
		public Server() {
			try {
				map = new HashMap<>();
				//서버 연결 준비
				this.serverSocket = new ServerSocket(Board.MAIN_PORTNUMBER);
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("[서버] 서버 생성 오류");
			}
		}
		
		
		/**
		 * 서버의 수신 대기 및 클라이언트의 정보를 저장하는 메소드(서버 기능 실행)
		 */
		public void work() {
			try {
				while(true) {
					System.out.println("[서버] 수신 대기중... ok");
					socket = serverSocket.accept();
					ClientInfo client = new ClientInfo(this, socket);
					client.setDaemon(true);
					client.start();
					Thread.sleep(50L);
					map.put(client.getUser(), client);
					System.out.println("[서버] 접속한 인원 수 : " + map.size());//테스트코드
					for ( String key : map.keySet() ) {
					    System.out.println("[서버] 접속한 사람 : ID : " + key +" / socket정보 : " + map.get(key));//테스트코드
					}

				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("[서버] 클라이언트 접속 실패");
			}
		}
		
		
		/**
		 * 전체에게 메시지를 전송하는 기능
		 * @param text 보내는 메시지
		 */
		public void broadcast(String userID, String text) {
			try {
				for ( String key : map.keySet() ) {
//				    System.out.println("[서버] 접속 ID : " + key); // 테스트코드
//				    System.out.println("[서버] socket정보 : " + map.get(key)); // 테스트코드
					if(!key.equals(userID))
						map.get(key).send(text);
				}
//				for(ClientInfo client : list) {
//					System.out.println("[서버] 리스트꺼냄 : " + client.getName()); //테스트코드
//					System.out.println("[서버] text : " + text); //테스트코드
//					client.send(text);
//				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("[서버] 브로드캐스트 실패");
			}
			
		}

		
		/**
		 * 테스트용 메인
		 */
		public static void main(String[] args) {
			Server server = new Server();
			server.work();
		}
}
