package mini.mes.chatServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 채팅 서버 클래스
 * @author 최범석
 */
public class ChatServer {
	
		/**
		 * 변수 생성
		 */
		private ServerSocket serverSocket = null;
		private Socket socket = null;
		private Map<Integer, Map<String, ChatServerManager>> roomMap;
//		private Map<Integer, StringBuffer> chatMap;
		private int roomNumber;
		private List<String> roomUserList;
		private Map<Integer, Map<String, String>> fileSendUserMap;
		public Map<Integer, Map<String, String>> getFileSendUserMap() {
			return fileSendUserMap;
		}
		public void setFileSendUserMap(Map<Integer, Map<String, String>> fileSendUserMap) {
			this.fileSendUserMap = fileSendUserMap;
		}

		

		/**
		 * 생성자
		 */
		public ChatServer() {
			try {
				this.roomMap = new HashMap<>();
//				System.out.println("[서버] roomData를 불러옵니다");//테스트코드
//				this.readRoomMap();
				Collections.synchronizedMap(roomMap);
				this.fileSendUserMap = new HashMap<>();
//				this.chatMap = new HashMap<>();
				
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
					ChatServerManager client = new ChatServerManager(this, socket);
					client.setDaemon(true);
					client.start();
					Thread.sleep(50L);
					
					//리스트에 해당 방에 속한 사용자들을 추가
					this.roomUserList = client.getList();
					System.out.println("[서버] roomUserList : " + this.roomUserList); //테스트코드
					
					// 기존 대화방이 있는지 확인
					 boolean flag = this.isRoomExist(this.roomUserList);
					 
					// 있다면 그 방에 입장
					if(flag) {
						if(roomMap.get(roomNumber).get(client.getUser()) != null) {
							roomMap.get(roomNumber).get(client.getUser()).send("서버", "[chatClose]");
						}
						System.out.println("[서버] 기존 방이 있으므로 방에 입장합니다 "); //테스트코드
						this.enterRoom(roomNumber, client.getUser(), client);
					}
					
					//	 없다면 방을 생성 후 입장
					else {
						System.out.println("[서버] 기존 방이 없으므로 방을 생성합니다 "); //테스트코드
						this.addRoom(this.roomUserList);
						this.enterRoom(roomNumber, client.getUser(), client);
					}
					
					//클라이언트 정보에 방번호를 알려줌
					System.out.println("[서버] roomNumber : " + roomNumber); //테스트코드
					client.setRoomNumber(roomNumber);
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("[서버] 클라이언트 접속 실패");
			}
		}
		
		
		/**
		 * 1:1대화방 입장 시 기존에 있던 방이 있는지 없는지 여부를 검사하는 메소드
		 */
		public boolean isRoomExist(List<String> userList) {
			System.out.println("[서버] roomMap.size() : " + roomMap.size());//테스트코드
			if(roomMap.size() == 0) {
				System.out.println("[서버] 기존 대화 방이 존재하지 않습니다");//테스트코드
				return false;
			}
			else {
				for(int roomNumber = 1; roomNumber <= Board.MAXIMUM_ROOMNUMBER; roomNumber++) {
					int count = 0;
					if(roomMap.get(roomNumber) != null) {
						if(roomMap.get(roomNumber).size() == userList.size()) {
							for(Object object : userList) {
								String name = (String) object;
								for ( String key : roomMap.get(roomNumber).keySet() ) {
									if(name.equals(key)) {
										count ++;
									}
								}
							}
							if(count == userList.size()) {
								System.out.println("[서버] 기존 대화 방이 존재합니다");//테스트코드
								this.roomNumber = roomNumber;
								return true;
							}
						}
					}
				}
				System.out.println("[서버] 기존 대화 방이 존재하지 않습니다");//테스트코드
				return false;
			}
		}
	
		
		
		/**
		 * 채팅방을 생성하고 사용자 리스트를 추가하는 메소드
		 * @param list
		 */
		public void addRoom(List<String> list) {
			if(roomMap.size() == 0) {
				this.roomNumber = 1;
				roomMap.put(this.roomNumber, new HashMap<>());
				fileSendUserMap.put(this.roomNumber, new HashMap<>());
//				chatMap.put(this.roomNumber, new StringBuffer());
				for(Object object : list) {
					String name = (String) object;
					roomMap.get(this.roomNumber).put(name, null);
				}
				System.out.println("[서버] 생성한 방의 번호 : " + 1);//테스트코드
				System.out.println("[서버] 생성한 방의 인원 수 : " + roomMap.get(this.roomNumber).size());//테스트코드
				System.out.println("[서버] 생성한 방에 속한 유저 정보 : " + list);//테스트코드
				System.out.println("[서버] 전체 방 개수 : " + roomMap.size());//테스트코드
			}
			else {
				int count = 0;
				for(int i = 1; i < Board.MAXIMUM_ROOMNUMBER; i++) {
					for (int key : roomMap.keySet()) {
						if(key == i) {
							count++;
						}
					}
					if(count < i) {
						this.roomNumber = i;
						roomMap.put(i, new HashMap<>());
						fileSendUserMap.put(this.roomNumber, new HashMap<>());
//						chatMap.put(this.roomNumber, new StringBuffer());
						for(Object object : list) {
							String name = (String) object;
							roomMap.get(this.roomNumber).put(name, null);
						}
						System.out.println("[서버] 생성한 방의 번호 : " + 1);//테스트코드
						System.out.println("[서버] 생성한 방의 인원 수 : " + roomMap.get(this.roomNumber).size());//테스트코드
						System.out.println("[서버] 생성한 방에 속한 유저 정보 : " + list);//테스트코드
						System.out.println("[서버] 전체 방 개수 : " + roomMap.size());//테스트코드
						return;
					}
				}
			}
		}
		
		
		/**
		 * 채팅방에 입장하는 메소드
		 * @param roomNumber 방 번호
		 * @param ID 접속할 아이디
		 * @param client 사용자 정보
		 */
		public void enterRoom(int roomNumber, String ID, ChatServerManager client) {
			System.out.println("[서버] " + roomNumber + "번방에 " + ID + "님 입장");//테스트코드
			roomMap.get(roomNumber).put(ID, client);
//			this.writeRoomMap();
			int count = 0;
			for ( String key : roomMap.get(roomNumber).keySet() ) {
				if(roomMap.get(roomNumber).get(key) != null) {
					count++;
			    System.out.println("[서버] 접속한 사람 : ID : " + key +" / socket정보 : " +  roomMap.get(roomNumber).get(key));//테스트코드
				}
			}
			System.out.println("[서버] " + roomNumber + "번방에 접속한 인원 수 : " + count);//테스트코드
		}
		
		
		/**
		 * 채팅방에서 퇴장하는 메소드(채팅창만 종료)
		 * @param roomNumber 방 번호
		 * @param ID 퇴장할 아이디
		 */
		public void exitRoom(int roomNumber, String ID) {
			roomMap.get(roomNumber).put(ID, null);
//			this.writeRoomMap();
			System.out.println("[서버] " + roomNumber + "번방에서 " + ID + "님 퇴장");//테스트코드
//			int count = 0;
			for ( String key : roomMap.get(roomNumber).keySet() ) {
//				if(roomMap.get(roomNumber).get(key) != null) {
//					count++;
				    System.out.println("[서버] 접속한 사람 : ID : " + key +" / socket정보 : " +  roomMap.get(roomNumber).get(key));//테스트코드
//				}
			}
//			System.out.println("[서버] " + roomNumber + "번방에 접속한 인원 수 : " + count);//테스트코드
			System.out.println("[서버] " + roomNumber + "번방에 접속한 인원 수 : " + roomMap.get(roomNumber).size());//테스트코드
		}
		
		
		/**
		 * 채팅방을 삭제하는 메소드(완전퇴장)
		 * 	@param roomNumber 방 번호
		 * @param userID 나간 사용자 ID
		 */
		public void removeRoom(int roomNumber, String ID) {
			roomMap.get(roomNumber).remove(ID);
//			this.writeRoomMap();
			System.out.println("[서버] 방에 남아있는 인원 수 : " + roomMap.get(roomNumber).size());//테스트코드
			if(roomMap.get(roomNumber).size() == 0) {
				roomMap.remove(roomNumber);
				fileSendUserMap.remove(roomNumber);
//				this.writeRoomMap();
//				chatMap.remove(roomNumber);
				System.out.println("[서버] " + roomNumber + "번 방이 삭제되었습니다");//테스트코드
				System.out.println("[서버] 전체 방 개수 : " + roomMap.size());//테스트코드
			}

		}
		
		
		/**
		 * roomMap을 불러오는 메소드
		 */
//		private void readRoomMap() {
//			try {
//				FileManager file = new FileManager("roomData.db");
//				this.roomMap = file.inputRoomMap();
//			} catch (Exception e) {
//				e.printStackTrace();
//				System.out.println("[서버] roomMap 파일 불러오기 오류 ");
//			}
//		}
		
		
//		/**
//		 * roomMap을 파일에 저장하는 메소드
//		 */
//		private void writeRoomMap() {
//			FileManager file = new FileManager("roomData.db");
//			file.outputRoomMap(roomMap);
//		}
		
		
		/**
		 * 방에속한 전체에게 메시지를 전송하는 기능
		 * @param roomNumber 방 번호
		 * @param userID 보내는 사람 ID
		 * @param text 보내는 메시지
		 */
		public void broadcast(int roomNumber, String userID, String text) {
//			System.out.println("[서버] 메시지를 브로드캐스트 합니다");// 테스트코드
			try {
				for ( String key : roomMap.get(roomNumber).keySet() ) {
					if(roomMap.get(roomNumber).get(key) != null) {
						roomMap.get(roomNumber).get(key).send(userID, text);
//				 	   System.out.println("[서버] 브로드 캐스트 할 ID : " + key +" / socket정보 : " +  roomMap.get(roomNumber).get(key));//테스트코드
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("[서버] 메시지 브로드캐스트 실패");
			}
		}
  
		
		/**
		 * 파일을 보내는 사람을 제외한 사용자에게 파일이름을 전송하는 메소드
		 * @param roomNumber 방 번호
		 */
		public void fileNameBroadcast(int roomNumber) {
//			System.out.println("[서버] 파일이름을 브로드캐스트 합니다");// 테스트코드
			try {
				String fileSendUser = null;
				String fileName = null;
				for(String key : this.getFileSendUserMap().get(roomNumber).keySet()) {
					fileSendUser = key;
					fileName = this.getFileSendUserMap().get(roomNumber).get(key);
				}
				for ( String key : roomMap.get(roomNumber).keySet() ) {
					if(roomMap.get(roomNumber).get(key) != null) {
						if(!key.equals(fileSendUser)) {
							roomMap.get(roomNumber).get(key).sendFileName(fileSendUser, fileName);
//						 	   System.out.println("[서버] 파일 이름을 보낼 ID : " + key +" / socket정보 : " +  roomMap.get(roomNumber).get(key));//테스트코드
						}
					}

				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("[서버] 파일 이름 브로드캐스트 실패");
			}
		}
		
		
		/**
		 * 클라이언트에게 파일크기를 전송하는 메소드
		 * @param roomNumber 방 번호
		 * @param receiveUser 받는 사람 ID
		 * @param fileSize 보내는 파일의 크기
		 */
		public void fileSizeBroadcast(int roomNumber, String receiveUser, Long fileSize) {
//			System.out.println("[서버] 파일내용을 브로드캐스트 합니다");// 테스트코드
			try {
				for (String key : roomMap.get(roomNumber).keySet() ) {
//				    System.out.println("[서버] 접속한 사람 : ID : " + key +" / socket정보 : " +  roomMap.get(roomNumber).get(key));//테스트코드
					if(key.equals(receiveUser)) {
//						System.out.println("[서버] fileSize : " + fileSize);//테스트코드
						roomMap.get(roomNumber).get(key).sendFileSize(fileSize);
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("[서버] 파일 크기 브로드캐스트 실패");
			}
		}
		
		
		/**
		 * 클라이언트에게 파일내용을 전송하는 메소드
		 * @param roomNumber 방 번호
		 * @param receiveUser 받는 사람 ID
		 * @param data 보내는 파일의 정보
		 * @param size 보내는 파일의 잘린 크기
		 */
		public void fileBroadcast(int roomNumber, String receiveUser, byte[] data, int size) {
//			System.out.println("[서버] 파일내용을 브로드캐스트 합니다");// 테스트코드
			try {
				for ( String key : roomMap.get(roomNumber).keySet() ) {
//				    System.out.println("[서버] 접속한 사람 : ID : " + key +" / socket정보 : " +  roomMap.get(roomNumber).get(key));//테스트코드
					if(key.equals(receiveUser)) {
//						System.out.println("[서버] size : " + size);
						roomMap.get(roomNumber).get(key).sendFile(data, size);
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("[서버] 파일 내용 브로드캐스트 실패");
			}
		}
		
		
		public void fileSendRequest(int roomNumber, String userID, String text) {
//			System.out.println("[서버] 파일 보내기 요청을 합니다");// 테스트코드
			try {
				String fileSendUser = null;
				for(String key : this.getFileSendUserMap().get(roomNumber).keySet()) {
					fileSendUser = key;
				}
				for ( String key : roomMap.get(roomNumber).keySet() ) {
//				    System.out.println("[서버] 접속한 사람 : ID : " + key +" / socket정보 : " +  roomMap.get(roomNumber).get(key));//테스트코드
					if(key.equals(fileSendUser)) {
						roomMap.get(roomNumber).get(key).sendFileRequest(userID, text);
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("[서버] 브로드캐스트 실패");
			}
			
		}
		
		
//		/**
//		 * 서버에서 수신한 채팅 로그를 기록하는 메소드
//		 * @param roomNumber 방 번호
//		 * @param text 받은 메시지
//		 */
//		public void writeLog(int roomNumber, String text) {
//			 chatMap.get(roomNumber).append(new StringBuffer(text));
//		}
		
		
//		/**
//		 * 서버에서 수신한 채팅 로그를 기록하는 메소드
//		 * @param roomNumber 방 번호
//		 * @param text 받은 메시지
//		 */
//		public void readLog(Map<Integer, StringBuffer> chatMap) {
//			
//		}
		
		
		/**
		 * 서버시작 메인
		 */
		public static void main(String[] args) {
			ChatServer server = new ChatServer();
			server.work();
		}


}
