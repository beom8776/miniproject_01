package mini.mes.nettest;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 채팅 서버 클래스
 * @author 최범석
 */
public class ChatServer2 extends Thread {
		
		/**
		 * 변수 생성
		 */
		private ServerSocket serverSocket = null;
		private Socket socket = null;
		private Map<String, ChatServerManager2> map;
		private String fileSendUser;

		public String getFileSendUser() {
			return fileSendUser;
		}
		public void setFileSendUser(String fileSendUser) {
			this.fileSendUser = fileSendUser;
		}


		/**
		 * 생성자
		 */
		public ChatServer2() {
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
					ChatServerManager2 client = new ChatServerManager2(this, socket);
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
//			System.out.println("[서버] 메시지를 브로드캐스트 합니다");// 테스트코드
			try {
				for ( String key : map.keySet() ) {
//				    System.out.println("[서버] 접속 ID : " + key); // 테스트코드
//				    System.out.println("[서버] socket정보 : " + map.get(key)); // 테스트코드
					map.get(key).send(userID, text);
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("[서버] 브로드캐스트 실패");
			}
		}

		
		/**
		 * 클라이언트에게 파일이름을 전송하는 메소드
		 */
		public void fileNameBroadcast(String userID, String fileName) {
//			System.out.println("[서버] 파일이름을 브로드캐스트 합니다");// 테스트코드
			try {
				for ( String key : map.keySet() ) {
//				    System.out.println("[서버] 접속 ID : " + key); // 테스트코드
//				    System.out.println("[서버] socket정보 : " + map.get(key)); // 테스트코드
					if(!key.equals(userID)) {
						map.get(key).sendFileName(userID, fileName);
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("[서버] 브로드캐스트 실패");
			}
		}
		
		
		/**
		 * 클라이언트에게 파일내용을 전송하는 메소드
		 */
		public void fileBroadcast(String userID, byte[] data, int size) {
//			System.out.println("[서버] 파일내용을 브로드캐스트 합니다");// 테스트코드
			try {
				for ( String key : map.keySet() ) {
//				    System.out.println("[서버] 접속 ID : " + key); // 테스트코드
//				    System.out.println("[서버] socket정보 : " + map.get(key)); // 테스트코드
					if(!key.equals(userID)) {
						map.get(key).sendFile(userID, data, size);
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("[서버] 브로드캐스트 실패");
			}
		}
		
		
		/**
		 * 대화방 나가기 메소드
		 */
		public void chatExit(String userID) {
			map.remove(userID);
			System.out.println("[서버] 접속한 인원 수 : " + map.size());//테스트코드
			for ( String key : map.keySet() ) {
			    System.out.println("[서버] 접속한 사람 : ID : " + key +" / socket정보 : " + map.get(key));//테스트코드
			}
		}
		
		
		public void fileSendRequest(String fileSendUser, String userID, String text) {
//			System.out.println("[서버] 파일 보내기 요청을 합니다");// 테스트코드
			try {
				for ( String key : map.keySet() ) {
//				    System.out.println("[서버] 접속 ID : " + key); // 테스트코드
//				    System.out.println("[서버] socket정보 : " + map.get(key)); // 테스트코드
					if(key.equals(fileSendUser)) {
						map.get(key).sendFileRequest(userID, text);
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("[서버] 브로드캐스트 실패");
			}
			
		}
		
		
		/**
		 * 서버시작 메인
		 */
		public static void main(String[] args) {
			ChatServer2 server = new ChatServer2();
			server.work();
		}

}
