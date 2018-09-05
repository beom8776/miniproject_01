package mini.mes.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 파일 서버의 기능 구현 클래스
 * @author 최범석
 */
public class FileServerManager extends Thread{
	
	/**
	 * 변수 생성
	 */
	private Socket socket;
	private DataInputStream dis;
	private byte[] data;
	private DataOutputStream dos;
	private String fileName;
	private Server server;
	private String userID;
	
	/**
	 * 생성자
	 * @param socket 소켓 정보
	 */
	public FileServerManager(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
		data = new byte[1024];
	}
	  
	
	/**
	 * 송신 클라이언트에게 파일을 받아 byte[]에 저장하는 메소드
	 */
	public void run() {
		try {
			System.out.println("[서버] socket = " + socket);//테스트코드
			dis = new DataInputStream(socket.getInputStream());
			System.out.println("[서버] dis = " + dis);//테스트코드
	 
			//사용자 아이디 받기
			userID = dis.readUTF();
			
			//파일 이름 받기
			fileName = dis.readUTF();
			
			//파일 내용 받기
			int size;
			while ((size = dis.read(data)) != -1) {
			}
//			Thread.sleep(50L);
			server.fileBroadcast(userID, fileName, data);
//			dis.close();
            System.out.println("[서버] 파일 수신 완료");
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("[서버] 파일 받기 오류");
	        }
	    }
	
	
	/**
	 *  수신 클라이언트에게 파일을 전송하는 메소드
	 */
	public void send(String userID, String fileName, byte[] data) {
        	try {
        		dos = new DataOutputStream(socket.getOutputStream());
        		
        		//사용자 아이디 보내기
            	dos.writeUTF(userID);
            	
                //파일이름 보내기
                dos.writeUTF(fileName);
                dos.flush();
//                Thread.sleep(50L);
                
                //파일내용 보내기
                dos.write(data);
                dos.flush();
                
//                dos.close();
                System.out.println("[서버] 클라이언트로 파일 전송 완료");
    		}catch(Exception e) {
    			e.printStackTrace();
    			System.out.println("[서버] 클라이언트에 접속 실패");
    		}
	}
}