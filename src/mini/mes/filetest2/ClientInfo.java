package mini.mes.filetest2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import mini.mes.chatting.ChattingGui;

public class ClientInfo extends Thread {
	private Socket socket;
	private Server server;
	private BufferedReader br;
	private PrintWriter pw;
	private String user;
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	
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
	
	@Override
		public void run() {
//			지속적으로 반복하여 사용자의 메시지를 수신
			try {
				this.br = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				
				//아이디 불러오기
				this.setUser(br.readLine());
				System.out.println("아이디 : " + this.getUser()); //테스트코드
				
				//메시지 불러오기
				while(true) {
					String text = br.readLine();
					System.out.println("[서버] " + socket + " : " + text);
					server.broadcast(text);
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
	
}
