package mini.mes.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 서버의 여러 기능을 수행하는 클래스
 * @author 최범석
 */
public class ChatServerManager extends Thread {
	
	/**
	 * 변수 생성
	 */
	private Socket socket;
	private ChatServer server;
	private DataOutputStream dos;
	private DataInputStream dis;
	private String user = null;

	
	/**
	 * 생성자
	 * @param server 서버 클래스
	 * @param socket 소켓정보
	 */
	public ChatServerManager(ChatServer server, Socket socket) {
			this.server = server;
			this.socket = socket;
	
			try {
				this.dos = new DataOutputStream(socket.getOutputStream());
				this.dis = new DataInputStream(socket.getInputStream());
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
			
			//아이디 불러오기
			String userID = dis.readUTF();
			this.setUser(userID);
			System.out.println("[서버] " + socket + " : " + this.getUser() + "님 접속");//테스트코드
			
			
			while(true) {
				//보낸 메시지 불러오기
				String text = dis.readUTF();
				System.out.println("[서버] " + this.getUser() + " : " + text);//테스트코드

				//파일일 경우
				if(text.equals("[fileSend]")) {
					System.out.println("[서버] 파일을 수신합니다");
					String fileName = dis.readUTF();
					System.out.println("[서버] 받은 fileName : " + fileName);//테스트코드
					server.fileNameBroadcast(userID, fileName);
					byte[] data = new byte[1024];
					int size = 0;
//					while ((size = dis.read(data)) != -1) {
////						System.out.println("[서버] size : " + size); //테스트코드
//						server.fileBroadcast(userID, data, size);
//					}
					while (true) {
						size = dis.read(data);
//						System.out.println("[서버] size = " + size);//테스트코드
						server.fileBroadcast(userID, data, size);
						if(size != 1024) break;
					}
//					dis.close();
		            System.out.println("[서버] 파일 수신 완료");
				}
				
				//일반 메시지일 경우
				else {
//					System.out.println("[서버] 메시지를 수신하였습니다");
					server.broadcast(userID, text);
				}
			} 
		}
		catch (IOException e) {
			server.chatExit(this.getUser());
			System.out.println("[서버] " + this.getUser() + "님이 대화방을 나가셨습니다");
		}
	}


	/**
	 * 받은 메시지를 클라이언트에게 보내는 메소드
	 * @param text 받은 메시지
	 */
	public void send(String userID, String text) {
		try {
			dos.writeUTF(userID);
			dos.flush();
			System.out.println("[서버] 에서 보내주는 userID : " + userID);//테스트코드
			
			dos.writeUTF(text);
			dos.flush();
			System.out.println("[서버] 에서 보내주는 text : " + text);//테스트코드
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("[서버] 메시지 출력 실패");
		}
	}

	
	/**
	 * 받은 파일이름을 클라이언트에게 보내는 메소드
	 * @param userID 보내는 사용자 ID
	 * @param fileName 보내는 파일 이름
	 */
	public void sendFileName(String userID, String fileName) {
		try {
			//사용자ID 보내기
			dos.writeUTF(userID);
			dos.flush();
			
			//파일임을 알려주기
			dos.writeUTF("[fileSend]");
			dos.flush();
			
	        //파일이름 보내기
	        dos.writeUTF(fileName);
	        dos.flush();
//	        System.out.println("[서버] sendFile 에서 보내는 파일이름 : " + fileName);
//	        System.out.println("[서버] 클라이언트로 파일이름 전송 완료");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("[서버] 클라이언트로 파일이름 전송 실패");
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
            dos.write(data, 0, size);
            dos.flush();
//            dos.close();
//            System.out.println("[서버] 클라이언트로 파일 전송 완료");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("[서버] 클라이언트로 파일 전송 실패");
		}
	}
	
	
	public void setUser(String ID) {
		this.user = ID;
	}
	public String getUser() {
		return user;
	}
	
}
