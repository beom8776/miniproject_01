package mini.mes.chatServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 사용자의 소켓 정보 등을 보관하는 클래스
 * @author 최범석
 */
public class ChatServerManager extends Thread {
	
	/**
	 * 변수 생성
	 */
	private Socket socket;
	private ChatServer server;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String sendFileName;
	private List<String> list;
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public String getSendFileName() {
		return sendFileName;
	}
	public void setSendFileName(String sendFileName) {
		this.sendFileName = sendFileName;
	}
	
	private String fileSendUser;
	public String getFileSendUser() {
		return fileSendUser;
	}
	public void setFileSendUser(String fileSendUser) {
		this.fileSendUser = fileSendUser;
	}
	
	private int roomNumber;
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public int getRoomNumber() {
		return roomNumber;
	}
	
	private String user;
	public void setUser(String ID) {
		this.user = ID;
	}
	public String getUser() {
		return user;
	}
	
	/**
	 * 생성자
	 * @param server 서버 클래스
	 * @param socket 소켓정보
	 */
	public ChatServerManager(ChatServer server, Socket socket) {
			this.list = new ArrayList<>();
			this.server = server;
			this.socket = socket;
	
			try {
				this.oos = new ObjectOutputStream(socket.getOutputStream());
				this.ois = new ObjectInputStream(socket.getInputStream());
			}catch(Exception e) {
				e.printStackTrace();
			}
	}

	
	@Override
	public String toString() {
		return "Client [socket=" + socket + "]";
	}
	
	
	/**
	 * 사용자의 메시지를 지속적으로 수신하는 메소드
	 */
	@Override
	public void run() {
		try {
			//사용자 리스트 불러오기
			List<String> userList = new ArrayList<>();
			int listSize = ois.readInt();
			for(int i = 0; i < listSize; i++) {
				String name = ois.readUTF();
				userList.add(name);
			}
			this.setList(userList);
			
			//아이디 불러오기
			String userID = ois.readUTF();
			this.setUser(userID);
			System.out.println("[서버] " + socket + " : " + this.getUser() + "님 접속");//테스트코드
			
			while(true) {
				//보낸 메시지 불러오기
				String text = ois.readUTF();
				System.out.println("[서버] (" +this.getRoomNumber() + "번 방) " + this.getUser() + " : " + text);//테스트코드

				//파일일 경우
				if(text.equals("[fileSend]")) {
					System.out.println("[서버] 파일이름을 수신합니다");
					String fileName = ois.readUTF();
					System.out.println("[서버] 받은 fileName : " + fileName);//테스트코드
					Map <Integer, Map<String, String>> map = server.getFileSendUserMap();
					map.get(roomNumber).put(this.getUser(), fileName);
					server.setFileSendUserMap(map);
					server.fileNameBroadcast(this.getRoomNumber());
				}
				
				//파일 수신 사인 받기
				else if(text.equals("[fileYes]")) {
					Map < Integer, Map<String, String>> map = server.getFileSendUserMap();
					String fileSendUser = null;
					for(String key : map.get(roomNumber).keySet()) {
						fileSendUser = key;
					}
					System.out.println("[서버] " + fileSendUser + "에게 파일 송신 요청을 합니다");//테스트코드
					server.fileSendRequest(this.getRoomNumber(), userID , text);
				}
				
				//파일 수신 기능 실행
				else if(text.equals("[fileReceive]")) {
					
					//수신자 ID 받기
					String receiveUser = ois.readUTF();
					
					//파일 사이즈 받기
					Long fileSize = ois.readLong();
					server.fileSizeBroadcast(this.getRoomNumber(), receiveUser, fileSize);
					
					//파일 내용 받기
					byte[] data = new byte[1024];
					int size = 0;
					while (true) {
						size = ois.read(data);
//						System.out.println("[서버] size = " + size);//테스트코드
						server.fileBroadcast(this.getRoomNumber(), receiveUser, data, size);
						if(size != 1024) break;
					}
//					ois.close();
		            System.out.println("[서버] 파일 수신 완료");
				}
				
				//일반 메시지일 경우
				else {
//					System.out.println("[서버] 메시지를 수신하였습니다");//테스트코드
//					server.writeLog(this.getRoomNumber(), text);
					server.broadcast(this.getRoomNumber(), userID, text);
				}
			} 
		}
		catch (IOException e) {
			server.exitRoom(roomNumber, this.getUser());
		}
	}


	/**
	 * 받은 메시지를 클라이언트에게 보내는 메소드
	 * @param userID 메시지를 보낸 사용자 ID
	 * @param text 받은 메시지
	 */
	public void send(String userID, String text) {
		try {
			//송신자 ID 보내기
			oos.writeUTF(userID);
			oos.flush();
//			System.out.println("[서버] 에서 보내주는 userID : " + userID);//테스트코드
			
			//메시지 내용 보내기
			oos.writeUTF(text);
			oos.flush();
//			System.out.println("[서버] 에서 보내주는 text : " + text);//테스트코드
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("[서버] 메시지 출력 실패");
		}
	}

	
	/**
	 * 사용자에게 파일을 보낼것을 요청하는 메소드
	 * @param userID 파일을 수신하는 사용자 ID
	 * @param text [fileYes] 메시지
	 */
	public void sendFileRequest(String userID, String text) {
		try {
			//파일을 보낼 아이디 보내기
			oos.writeUTF(userID);
			oos.flush();
//			System.out.println("[서버] 파일을 보낼 userID : " + userID);//테스트코드
			
			//파일 전송 요청 메시지 보내기
			oos.writeUTF(text);
			oos.flush();
//			System.out.println("[서버] 에서 보내주는 text : " + text);//테스트코드
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("[서버] 메시지 출력 실패");
		}
	}
	
	
	/**
	 * 파일을 보낼것을 알려주고 파일의 이름을 클라이언트에게 보내는 메소드
	 * @param sendFileUser 파일을 보내는 사용자 ID
	 * @param fileName 보내는 파일의 이름
	 */
	public void sendFileName(String sendFileUser, String fileName) {
		try {
			//사용자ID 보내기
			oos.writeUTF(sendFileUser);
			oos.flush();
			
			//파일임을 알려주기
			oos.writeUTF("[fileSend]");
			oos.flush();
			
	        //파일이름 보내기
	        oos.writeUTF(fileName);
	        oos.flush();
//	        System.out.println("[서버] sendFile 에서 보내는 파일이름 : " + fileName);//테스트코드
//	        System.out.println("[서버] 클라이언트로 파일이름 전송 완료");//테스트코드
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("[서버] 클라이언트로 파일이름 전송 실패");
		}
	}
	
	
	/**
	 * 받은 파일크기을 클라이언트에게 보내는 메소드
	 * @param fileSize 보내는 파일의 길이
	 */
	public void sendFileSize(Long fileSize) {
		try {
			//파일내용 보내기
            oos.writeLong(fileSize);
            oos.flush();
//            oos.close();
//          System.out.println("[서버] 클라이언트로 파일크기 전송 완료"); //테스트코드
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("[서버] 클라이언트로 파일크기 전송 실패");
		}
	}
	
	
	/**
	 * 받은 파일내용을 클라이언트에게 보내는 메소드
	 * @param data byte[] 정보
	 * @param size 보내는 byte[]의 길이
	 */
	public void sendFile(byte[] data, int size) {
		try {
			//파일내용 보내기
            oos.write(data, 0, size);
            oos.flush();
//            oos.close();
//            System.out.println("[서버] 클라이언트로 파일 전송 완료"); //테스트코드
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("[서버] 클라이언트로 파일 전송 실패");
		}
	}
	
}
