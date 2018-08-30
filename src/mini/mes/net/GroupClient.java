package mini.mes.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import mini.mes.chatting.ChattingGui;

/**
 * 그룹채팅 클라이언트 실행 클래스
 * @author 최범석
 */
public class GroupClient extends Thread{

	private int port;
	private InetAddress inet;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ChattingGui chat;
	
	/**
	 * 생성자
	 * @param ip 접속할 아이피 주소
	 * @param port 포트번호
	 */
	public GroupClient(String ip, int port) {
		 try {
			this.chat = new ChattingGui();
			chat.setVisible(true);
			this.port = port;
			this.inet = InetAddress.getByName("localhost");
			socket = new Socket(inet,port);
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("아이피 설정 오류");
		}
	}
	
	
	/**
	 * 보낼 메시지를 반복확인하여 전달하는 메소드 
	 */
	public void work() {
		try {
			while(true) {
				String input = null;
				if(chat.isFlag()) 
					input = chat.getSendText();
				Thread.sleep(200L);
				if(input != null) {
					out.writeUTF(input);
					out.flush();
					System.out.println("["+socket+"]input : " + input);//테스트코드
					chat.setFlag(false);
				}
			}
//			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 반복해서 메시지를 수신하는 메소드
	 */
	public void run() {
		try {
			while(true) {
				String text = in.readUTF();
				chat.groupChat(text);
//				System.out.println("text : " + text);//테스트코드
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
	 * 그룹채팅 클라이언트 실행 메인
	 */
	public static void main(String[] args) {
		
		GroupClient group = new GroupClient("localhost", 50000);
		group.setDaemon(true);
		group.start();
		group.work();
		
	}
	
}
