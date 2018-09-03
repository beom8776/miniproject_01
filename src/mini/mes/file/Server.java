package mini.mes.file;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 파일 서버에 접속하는 클래스
 * @author 최범석
 */
public class Server {
	public static void main(String[] args) {
		
		/**
		 * 변수 생성
		 */
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		//서버 연결 준비
		try {
		serverSocket = new ServerSocket(Board.MAIN_PORTNUMBER);
		socket = serverSocket.accept();
		System.out.println("[서버] 수신 대기중... ok");
		
		//파일 수신 기능
		FileServerManager fr = new FileServerManager(socket);
		fr.start();
		
		//수신자 클라이언트에게 파일 보내기 기능
		fr.send();
		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("서버접속오류");
		}
	}
}
