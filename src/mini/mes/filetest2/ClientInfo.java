package mini.mes.filetest2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 서버에 접속한 클라이언트의 정보를 보관하는 클래스
 * @author 최범석
 */
public class ClientInfo extends Thread {
	
	/**
	 * 변수 생성
	 */
	private Socket socket;
	private Server server;
	private BufferedReader br;
	private PrintWriter pw;
	private String user = "";
	
	/**
	 * 생성자
	 * @param server 서버 클래스
	 * @param socket 소켓정보
	 */
	public ClientInfo(Server server, Socket socket) {
			this.server = server;
			this.socket = socket;
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
			this.br = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			
			//아이디 불러오기
			String userID = br.readLine();
			this.setUser(userID);
			System.out.println("[서버] " + socket + " : " + this.getUser() + "님 접속");//테스트코드
			
			//메시지 불러오기
			while(true) {
				String text = br.readLine();
				System.out.println("[서버] " + this.getUser() + " : " + text);//테스트코드
				server.broadcast(this.getUser(), text);
			} 
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("[서버] 메시지 수신 오류");
		}
	}


	/**
	 * 받은 메시지를 자기 자신에게 보내는 메소드
	 * @param text 받은 메시지
	 */
	public void send(String text) {
		try {
			this.pw = new PrintWriter(
					new OutputStreamWriter(socket.getOutputStream()));
			pw.println(text);
			pw.flush();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("[서버] 메시지 출력 실패");
		}
	}

	public void setUser(String ID) {
		this.user = ID;
	}
	public String getUser() {
		return user;
	}
	
}
