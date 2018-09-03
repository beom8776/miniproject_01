package mini.mes.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * 그룹채팅 서버 실행 클래스
 * @author 최범석
 */
public class Server extends Thread{
	
	/**
	 * 변수 생성
	 */
	private int port;
	private ServerSocket server;
	private NetManager manager;
	
	
	/**
	 * 생성자
	 * @param port 포트번호
	 */
	public Server(int port) {
		try {
			this.port = port;
			this.server = new ServerSocket(port);
			this.manager = new NetManager(port);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("서버 소켓 오류");
		}
	}
	
	
	/**
	 * [1:1] 채팅 서버 실행 메소드
	 */
	public void workServerOne() {
		manager.workServer(server);
	}
	
	
	/**
	 * [그룹] 채팅 서버 실행 메소드
	 */
	public void workGroup() {
		manager.workGroupServer(this, server);
	}
	
	
	/**
	 * [그룹] 받은 메시지를 브로드캐스팅 하는 메소드
	 * @param text 그룹 클라이언트로 부터 받은 메시지
	 */
	public void broadcast(String text) {
		manager.chatBroadcast(text);
	}
	
	
	/**
	 * [1:1] 클라이언트가 보낸 파일을 보관?
	 */
	public void fileReceiver() {
		
        try {
            Socket socket = server.accept();
            
            if(socket.isConnected()) {
            	System.out.println("클라이언트가 접속하였습니다.");
            	
            }
            InputStream in = socket.getInputStream();
            
        	JFileChooser chooser = new JFileChooser();
        	chooser.showSaveDialog(chooser);
        	String path = chooser.getSelectedFile().getPath();
        	File target = new File(path);
            FileOutputStream out = new FileOutputStream(target);
            
             
            String fileName = target.getName();
            int port =  50000;
			long fileSize = target.length();
	        long totalReadBytes = 0;
	        int readBytes;
            byte[] buffer = new byte[1024];
            
            while ((readBytes = in.read(buffer)) != -1) {
                out.write(buffer, 0, readBytes);
                totalReadBytes += readBytes;
                System.out.println("파일  수신 현황 : " + totalReadBytes + "/"
                        + fileSize + " Byte(s) ("
                        + (totalReadBytes * 100 / fileSize) + " %)");
            }
            if((totalReadBytes*100 / fileSize) == 100) 
            	JOptionPane.showMessageDialog(null, "파일 수신이 완료되었습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
                 
            in.close();
            out.close();
            socket.close();
            server.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("서버 파일 받기 오류");
        }


	}
	
	public void run() {
		System.out.println("ㅋ");
	}
	
	/**
	 * 채팅 서버 실행 메인
	 */
	public static void main(String[] args) {
		
		Server group = new Server(50000);
		group.fileReceiver();
//		1:1 채팅일경우
//		group.workServerOne();
//		그룹 채팅일경우
//		group.workGroup();
		
	}
}
