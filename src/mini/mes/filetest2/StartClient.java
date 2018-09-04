package mini.mes.filetest2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import mini.mes.chatting.ChattingGui;
import mini.mes.file.Board;

/**
 * 클라이언트 기능 실행 클래스
 * @author 최범석
 */
public class StartClient extends Thread{
		
	/**
	 * 변수 생성
	 */
	private Socket socket;
	private PrintWriter pw;
	private BufferedReader br;
	private ChattingGui chat;
	private String user;

	
	/**
	 * 생성자
	 */
	public StartClient(){
		try {
			this.chat = new ChattingGui();
			chat.setVisible(true);
			
//			String ip = "192.168.0.9";
			String ip = "127.0.0.1";
			String[] segments = ip.split("\\.");
			String serverIP = (Long.parseLong(segments[0])
								+ "." + Long.parseLong(segments[1]) 
								+ "."	+ Long.parseLong(segments[2])
								+ "." + Long.parseLong(segments[3]));
			socket = new Socket(serverIP,  Board.MAIN_PORTNUMBER);
			System.out.println("[클라이언트] 서버에 연결되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 지속적으로 메시지가 입력되면 보내는 기능 실행
	 */
		public void work() {
			try {
        	this.pw = new PrintWriter(
					new OutputStreamWriter(socket.getOutputStream()));
	        	
        	//아이디 보내기
        	user = "beomseok";
        	pw.println(user);
        	pw.flush();
        	
        	//메시지 보내기
   			while(true) {
   				String input = null;
//   				System.out.println("[클라이언트] flag : " + chat.isFlag());//테스트코드
   				Thread.sleep(300L);
   				if(chat.isFlag()) {

   					input = chat.getSendText();
   					Thread.sleep(50L);
   				}
   				if(input != null) {
   					chat.myChat(input);
   					pw.println(input);
   					pw.flush();
   					System.out.println("[클라이언트] ("+socket+")input : " + input);//테스트코드
   					chat.setFlag(false);
   				}
   			}
//   			socket.close();
//   			pw.close();
   		} catch (Exception e) {
   			e.printStackTrace();
   			System.out.println("[클라이언트] 메시지 서버로 전송 오류");
   		}
	}

		
		/**
		 * 반복해서 메시지를 수신하는 메소드
		 */
		public void run() {
			try {
				while(true) {
					this.br = new BufferedReader(
							new InputStreamReader(socket.getInputStream()));
					String text = br.readLine();
					System.out.println("[클라이언트] (" + socket + ")text : " + text);//테스트코드
					chat.yourChat(text);
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		/**
		 * 테스트용 메인
		 */
		public static void main(String[] args) {
			StartClient client = new StartClient();
			client.setDaemon(true);
			client.start();
			client.work();
		}

}
