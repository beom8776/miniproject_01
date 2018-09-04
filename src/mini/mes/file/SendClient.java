package mini.mes.file;

import java.io.IOException;
import java.net.Socket;

/**
 * 파일 송신용 클라이언트 접속 클래스
 * @author 최범석
 */
public class SendClient {
	public static void main(String[] args) {
		
		/**
		 * 파일을 받는 클라이언트의 접속을 실행						(연결해서 처리할것)
		 */
//		ReceiveClient client = new ReceiveClient();
		
		/**
		 * 변수 생성
		 */
		String ip = "127.0.0.1";
		int serverPort = Board.MAIN_PORTNUMBER;
		Socket socket = null;
		String[] segments = ip.split("\\.");
		String serverIP = (Long.parseLong(segments[0])
							+ "." + Long.parseLong(segments[1]) 
							+ "."	+ Long.parseLong(segments[2])
							+ "." + Long.parseLong(segments[3]));

		//보낼 파일 선택 창
		Dialog dialog = new Dialog();
		String fileName = dialog.getPath();
		
		//서버 접속
		try {
		socket = new Socket(serverIP, serverPort);
		System.out.println("[송신 클라이언트] 서버에 연결되었습니다.");
		
		//파일보내기 기능
		FileSender fs = new FileSender(socket,fileName);
		fs.fileSend();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[송신 클라이언트] 서버로 연결이 실패하였습니다");
		}
		
	}
}
