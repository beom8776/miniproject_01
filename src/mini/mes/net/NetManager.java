package mini.mes.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;


public class NetManager extends Thread{
	
	/**
	 * 변수 생성
	 */
	private InetAddress inet;
	private int port;
	private Socket socket;
	private ServerSocket server;
	
	/**
	 * 서버용 생성자
	 * @param port 포트번호
	 */
	public NetManager(int port) {
		this.port = port;
	}
	
	
/**
 * 클라이언트용 생성자
 * @param ip  수신측 IP
 * @param port 포트번호
 */
	public NetManager(String ip, int port){
		try {
			this.inet = InetAddress.getByName(ip);
			this.port = port;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("아이피 설정 오류");
		}
	}
	
	
	/**
	 *	서버 연결 실행 메소드
	 */
	public void workServer() {
		try{
//			[1] 서버 준비
			this.server = new ServerSocket(port);
//			[2] 연결 대기
			socket = server.accept();
//			[3] 스레드 생성(수신)
			this.setDaemon(true);
			this.start();
//			[4] 전송 실행
			this.send();
			
			server.close();
					
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 클라이언트 연결 실행 메소드
	 */
	public void workClient() {
		try{
//			[1] 연결 시도
			socket = new Socket(inet, port);
//			[2] 스레드 생성(수신)
			this.setDaemon(true);
			this.start();
//			[3] 전송 실행
			this.send();
			
			socket.close();
					
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 수신 메소드(스레드)
	 * - 수신 도구 생성
	 * - 무한루프로 수신 후 출력
	 */
	public void run() {
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			
			while(true) {
				String line = in.readLine();
//				System.out.println("line = " + line); //테스트 출력
			}
			
//			in.close();
		}
		catch(Exception e){
			e.getStackTrace();
		}
	}
	
	/**
	 * 전송 메소드
	 * - 사용자에게 입력받기
	 * - 상대에게 전송
	 */
	public void send() {
		try {
			PrintWriter out = new PrintWriter(
					new OutputStreamWriter(socket.getOutputStream()));

				String input = JOptionPane.showInputDialog("메시지");
				if(input != null) {
					out.println(input);
					out.flush();

			}
			
//			out.close();
		}
		catch(Exception e){
			e.getStackTrace();
		}
	}
	
	
////	테스트용 메인(서버)
//	public static void main(String[] args) throws UnknownHostException {
//		NetManager server = new NetManager(50000);
//		server.workClient();
//	}
	
	
////테스트용 메인(클라이언트)
//public static void main(String[] args) throws UnknownHostException {
//		NetManager client = new NetManager("localhost", 50000);
//		server.workClient();
//}
	
}
