package mini.mes.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;
import mini.mes.chatting.ChattingGui;
import mini.mes.file.Board;
import mini.mes.file.Dialog;

/**
 * 클라이언트 기능 실행 클래스
 * @author 최범석
 */
public class StartClient extends Thread{
		
	/**
	 * 변수 생성
	 */
	private Socket socket;
//	private PrintWriter pw;
//	private BufferedReader br;
	private ChattingGui chat;
	private String user;
	private DataOutputStream dos;
	private DataInputStream dis;
	private BufferedInputStream bis;
	
	/**
	 * 생성자
	 */
	public StartClient(){
		try {
			user = JOptionPane.showInputDialog(null,"아이디 입력"); //테스트코드
			this.chat = new ChattingGui(this);
			chat.setVisible(true);
//			String ip = "192.168.0.9";
			String ip = "127.0.0.1";
			String[] segments = ip.split("\\.");
			String serverIP = (Long.parseLong(segments[0])
								+ "." + Long.parseLong(segments[1]) 
								+ "."	+ Long.parseLong(segments[2])
								+ "." + Long.parseLong(segments[3]));
			socket = new Socket(serverIP,  Board.MAIN_PORTNUMBER);
			System.out.println("[클라이언트] " + user + "님이 서버에 연결되었습니다.");
			
//        	this.pw = new PrintWriter(
//					new OutputStreamWriter(socket.getOutputStream()));
//			this.br = new BufferedReader(
//					new InputStreamReader(socket.getInputStream()));
			this.dos = new DataOutputStream(socket.getOutputStream());
			this.dis = new DataInputStream(socket.getInputStream());
			
			
			this.setDaemon(true);
			this.start();
//			this.readFile();
			this.work();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[클라이언트] 서버에 연결 실패");
		}
	}
	
	
	/**
	 * 서버로 아이디를 보내는 메소드
	 */
	public void work() {
		try {
        	//아이디 보내기
			dos.writeUTF(user);
			dos.flush();
//			pw.println(user);
//			pw.flush();
//    		socket.close();
//    		pw.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[클라이언트] 서버로 아이디 전송 실패");
		}
	}
		
		
	/**
	 *서버로 메시지를 보내는 메소드
	 */
	public void send(String text) {
		try {
			dos.writeUTF(text);
			dos.flush();
//			pw.println(text);
//			pw.flush();
			System.out.println("[클라이언트] 보낸메시지 : " + text);
		} catch (Exception e) {
			e.printStackTrace();
   			System.out.println("[클라이언트] 서버로 메시지 전송 실패");
		}
	}
		
		/**
		 * 반복해서 메시지를 수신하는 메소드
		 */
		public void run() {
			try {
				while(true) {
					String sendUserID = dis.readUTF();
					String text = dis.readUTF();
//					String sendUserID = br.readLine();
//					System.out.println("[클라이언트] 보낸사람 : " + br.readLine());//테스트코드
//					String text = br.readLine();
//					System.out.println("[클라이언트] 받은메시지 : " + text);//테스트코드
					chat.inputChat(sendUserID, text);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("[클라이언트] 메시지 수신 실패");
			}
		}
		
		
		/**
		 * 서버로 파일 보내기 메소드
		 */
		public void fileSend() {
			try {
				//보낼 파일 선택
				Dialog dialog = new Dialog();
				String fileName = dialog.getPath();
				
				//파일 보내기 기능 실행
//				FileSender sender = new FileSender(user, socket, fileName);
//				sender.setDaemon(true);
//				sender.start();
				File f = new File(fileName);
	        	long fileSize = f.length();
	            long totalReadBytes = 0;
	            int readBytes;
	            bis = new BufferedInputStream(new FileInputStream(f));
	            
	            //파일이름 보내기
	            dos.writeUTF(f.getName());
	            dos.flush();
	            
	            //파일내용 보내기
	            byte[] data = new byte[1024];
	            while ((readBytes = bis.read(data)) != -1) {
	                dos.write(data, 0, readBytes);
	                totalReadBytes += readBytes;
	                System.out.println("[클라이언트] 파일 전송 현황 : " + totalReadBytes + "/"
	                        + fileSize + " Byte(s) ("
	                        + (totalReadBytes * 100 / fileSize) + " %)");
	            }
	            if((totalReadBytes*100 / fileSize) == 100) 
	            	JOptionPane.showMessageDialog(null, "파일 전송이 완료되었습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
	            dos.flush();
//	            dos.close();
	            bis.close();
	            System.out.println("[클라이언트] 서버로 파일 전송 완료");
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("[클라이언트] 서버로 파일 전송 실패");
			}
		}
		
		
		/**
		 * 서버에서 넘어오는 파일을 받는 메소드
		 */
		public void readFile() {
			FileReceiver receiver = new FileReceiver(socket);
			receiver.setDaemon(true);
			receiver.start();
		}
		
		/**
		 * 테스트용 메인
		 */
		public static void main(String[] args) {
			StartClient client = new StartClient();
//			client.setDaemon(true);
//			client.start();
//			client.work();
		}
		
}
