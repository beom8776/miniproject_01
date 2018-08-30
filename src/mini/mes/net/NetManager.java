package mini.mes.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import mini.mes.chatting.ChattingGui;

/**
 * 네트워크 매니저 클래스
 * @author 최범석
 */
public class NetManager extends Thread{
	
	/**
	 * 변수 생성
	 */
	private InetAddress inet;
	private int port;
	private Socket socket;
	private ServerSocket server;
	private String text;
	private List<ClientInfo> list = new ArrayList<>();
	private ChattingGui chat = new ChattingGui();
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	
	/**
	 * 서버용 생성자
	 * @param port 포트번호
	 */
	public NetManager(int port) {
		this.port = port;
	}
	
	
/**
 * 클라이언트용 생성자
 * @param ip  접속할 IP
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
	 *	[1:1] 서버 채팅  연결 실행 메소드
	 */
	public void workServer(ServerSocket server) {
		try{
//			[0] 채팅 프로그램 실행
			chat.setVisible(true);
//			[1] 서버 준비
			this.server = server;
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
			System.out.println("서버 연결 오류");
		}
	}
	
	
	/**
	 * [1:1] 클라이언트 채팅 연결 실행 메소드
	 */
	public void workClient() {
		try{
//			[0] 채팅 프로그램 실행
			chat.setVisible(true);
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
			System.out.println("클라이언트 연결 오류");
		}
	}
	
	
	/**
	 * [1:1] 채팅 수신 메소드(스레드)
	 */
	public void run() {
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			while(true) {
				String line = in.readLine();
				chat.yourChat(line);
			}
//			in.close();
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("메시지 불러오기 오류");
		}
	}
	
	
	/**
	 * [1:1] 채팅 전송 메소드
	 */
	public void send() {
		try {
			PrintWriter out = new PrintWriter(
					new OutputStreamWriter(socket.getOutputStream()));
			while(true) {
				String input = null;
				if(chat.isFlag()) 
					input = chat.getSendText();
				Thread.sleep(200L);
				if(input != null) {
					chat.myChat(input);
					out.println(input);
					out.flush();
					chat.setFlag(false);
				}
			}
//			out.close();
		}
		catch(Exception e){
			e.getStackTrace();
			System.out.println("메시지 보내기 오류");
		}
	}
	
	
	/**
	 * [그룹] 채팅 서버 실행 메소드
	 * @param server 서버 클래스
	 * @param serverSocket 서버소켓 정보
	 */
	public void workGroupServer(Server server, ServerSocket serverSocket) {
		try {
			while(true) {
				Socket socket = serverSocket.accept();
				ClientInfo client = new ClientInfo(server, socket);
				list.add(client);
				client.setDaemon(true);
				client.start();
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("오류");
		}
	}
	
	
	/**
	 * [그룹] 받은 메시지를 브로드캐스팅 하는 메소드
	 * @param message그룹 클라이언트로 부터 받은 메시지
	 */
	public void chatBroadcast(String message) {
		for(ClientInfo client : list) {
			System.out.println("리스트꺼냄 : " + client.getName()); //테스트코드
			System.out.println("text : " + message); //테스트코드
			client.send(message);
		}
	}
	
}
