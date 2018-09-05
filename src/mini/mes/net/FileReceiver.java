package mini.mes.net;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;
import mini.mes.file.Dialog;

/**
 * 파일 수신용 클라이언트 접속 클래스
 * @author 최범석
 */
public class FileReceiver extends Thread {
	
	/**
	 * 변수 생성
	 */
	private Socket socket;
	private String baseDir = "C:\\Users\\user\\Documents";
	private DataInputStream dis;
	private BufferedOutputStream bos;
	private String fileName;
	
	
	/**
	 * 생성자
	 * @param socket 소켓정보
	 */
	public FileReceiver(Socket socket) {
		this.socket = socket;
	}
	
	
	/**
	 * 파일을 지속적으로 수신하는 메소드(쓰레드)
	 */
	@Override
	public void run() {
		try {
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
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[수신 클라이언트] 파일 받기 오류");
        }
	}
	
}
