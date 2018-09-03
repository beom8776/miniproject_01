package mini.mes.file;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 파일 수신용 클라이언트 접속 클래스
 * @author 최범석
 */
public class ReceiveClient {
	public static void main(String[] args) {
		
		/**
		 * 변수 생성
		 */
		int port = Board.SUB_PORTNUMBER;
		ServerSocket serverSocket = null;
		Socket socket = null;
		String baseDir = "C:\\Users\\user\\Documents";
		DataInputStream dis;
		BufferedOutputStream bos;
		String fileName;
		
		//서버 연결 준비
		try {
			serverSocket = new ServerSocket(port);
			socket = serverSocket.accept();
			System.out.println("[수신 클라이언트] 수신 대기중... ok");
			
			//파일 수신 기능
			dis = new DataInputStream(socket.getInputStream());
		 
			//파일 이름 받기
			fileName = dis.readUTF();
			System.out.println("[수신 클라이언트] 받은 파일이름 = " + fileName);//테스트코드
			int pos = fileName.lastIndexOf( "." );
			String ext = fileName.substring( pos + 1 );
				
			//저장할 경로 및 파일 이름 지정 기능
			Dialog sd = new Dialog(baseDir, ext);
			String path = sd.getPath();
			File f = new File(path);
				
			//파일 내용 받기
			bos = new BufferedOutputStream(new FileOutputStream(f));
			byte[] data = new byte[1024];
			int size;
			while ((size = dis.read(data)) != -1) {
				bos.write(data, 0, size);
			}
			bos.flush();
			bos.close();
			dis.close();
            System.out.println("[수신 클라이언트] 파일 수신 완료");
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("[수신 클라이언트] 파일 받기 오류");
	        }
			
	}
}
