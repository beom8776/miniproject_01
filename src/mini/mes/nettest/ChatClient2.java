package mini.mes.nettest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;
import mini.mes.chatfile.Dialog;
import mini.mes.chatting.ChattingGui;

/**
 * 클라이언트 기능 실행 클래스
 * @author 최범석
 */
public class ChatClient2 extends Thread{
		
	/**
	 * 변수 생성
	 */
	private Socket socket;
	private ChattingGui chat;
	private String user;
	private DataOutputStream dos;
	private DataInputStream dis;
	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	private String baseDir = "C:\\Users\\user\\Documents";
	private File sendFile;
	
	/**
	 * 생성자
	 */
	public ChatClient2(){
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
			System.out.println("[" + user + "] " + user + "님이 서버에 연결되었습니다.");//테스트코드
			
			this.dos = new DataOutputStream(socket.getOutputStream());
			this.dis = new DataInputStream(socket.getInputStream());
			
			//반복 수신기능 시작
			this.setDaemon(true);
			this.start();
			
        	//아이디 보내기
			dos.writeUTF(user);
			dos.flush();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[" + user + "] 서버에 연결 실패");
		}
	}
	
	
	/**
	 *서버로 메시지를 보내는 메소드
	 */
	public void send(String text) {
		try {
			dos.writeUTF(text);
			dos.flush();
			System.out.println("[" + user + "] 보낸메시지 : " + text);//테스트코드
			System.out.println();//테스트코드
		} catch (Exception e) {
			e.printStackTrace();
   			System.out.println("[" + user + "] 서버로 메시지 전송 실패");
		}
	}
	
	
	/**
	 * 시스템메시지를 서버로 보내는 메소드
	 * @param text 시스템 메시지
	 */
	public void systemSend(String text) {
		try {
			dos.writeUTF("[system]" + text);
			dos.flush();
			System.out.println("[시스템메시지] " + text + "를 서버로 전송");
			System.out.println();//테스트코드
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[" + user + "] 서버로 시스템 메시지 전송 실패");
		}
	}
	
	/**
	* 반복해서 메시지를 수신하는 메소드
	*/
	public void run() {
		try {
			while(true) {
				System.out.println();//테스트코드
				String sendUserID = dis.readUTF();
				String text = dis.readUTF();
				System.out.println("[" + user + "] " + sendUserID + "의 메시지 : " + text);//테스트코드
				
				//파일일 경우
				if(text.equals("[fileSend]")) {
					
					//파일이름 받기
					String fileName = dis.readUTF();
					System.out.println("[" + user + "] 받은 파일이름 = " + fileName);//테스트코드
					
					//파일 수신 확인 창
					int option = JOptionPane.showConfirmDialog(chat, fileName + " 파일을 수신하겠습니까?", "파일 수신 확인", JOptionPane.YES_NO_OPTION);
					if(option == 0) {
						System.out.println("[" + user + "] 파일 받기를 시작합니다");//테스트코드
						this.send("[fileYes]");
					}
					else if(option == 1){ 
//						 System.out.println(user + "님 파일 수신 거부");
						 this.systemSend(user + "님이 파일 수신을 거부하였습니다");	
					}

				}
				
				//시스템 메시지일 경우
				else if (text.startsWith("[system]")) {
					chat.systemMessage(text.substring(8));
				}
				
				//파일 수신 yes일 경우
				else if (text.equals("[fileYes]")) {
					System.out.println(sendUserID + "님이 파일 수신을 수락하였습니다.");//테스트코드
					this.send("[fileReceive]");
					this.fileSend();
				}
				
				//메시지일 경우
				else {
					chat.inputChat(sendUserID, text);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[" + user + "] 메시지 / 파일 수신 실패");
		}
	}
	
		
	private void fileReceive(String fileName) {
		try {
			int pos = fileName.lastIndexOf( "." );
			String ext = fileName.substring( pos + 1 );
			
			//저장할 경로 및 파일 이름 지정 기능
			Dialog sd = new Dialog(baseDir, ext);
			String path = sd.getPath();
			File f = new File(path);
			System.out.println("[" + user + "] 저장할 경로 = " + f);//테스트코드

			//파일 내용 받기
			bos = new BufferedOutputStream(new FileOutputStream(f));
			byte[] data = new byte[1024];
			int size;
//			while (true) {
//				size = dis.read(data);
//				System.out.println("[" + user + "] size = " + size);//테스트코드
//				bos.write(data, 0, size);
//				bos.flush();
//				if(size != 1024) break;
//			}
			while ((size = dis.read(data)) != -1) {
				System.out.println("[" + user + "] size = " + size);//테스트코드
				bos.write(data, 0, size);
				bos.flush();
			}
			bos.close();
//			dis.close();
//	    	JOptionPane.showMessageDialog(null, "파일 수신이 완료되었습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
//			chat.systemMessage(user + "님 파일 수신을 완료하였습니다");
	    	this.systemSend(user + "님 파일 수신을 완료하였습니다");
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("[" + user + "] 서버로부터 파일 수신 실패");
		}
	}


	/**
	 * 서버로 파일을 보낸다고 알려주는메소드
	 */
	public void fileNameSend() {
		try {
			//보낼 파일 선택 후 준비
			Dialog dialog = new Dialog();
			String fileName = dialog.getPath();
			sendFile = new File(fileName);
			
			//시스템메시지 전송
			this.systemSend(user + "님이 파일을 전송합니다");
			
            //파일임을 알려주기
            dos.writeUTF("[fileSend]");
            dos.flush();
			System.out.println("[" + user + "] 서버로 " + fileName + "를 보냅니다");//테스트코드
			
            //파일이름 보내기
            dos.writeUTF(sendFile.getName());
            dos.flush();
            
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("[" + user + "] 서버로 파일 이름 전송 실패");
		}
	}
	
	
	/**
	 * 서버로 파일 보내기 메소드
	 */
	public void fileSend() {
		try {
			System.out.println("[" + user + "] 서버로 파일 내용을 보냅니다");//테스트코드
        	long fileSize = sendFile.length();
            long totalReadBytes = 0;
            int readBytes;
            bis = new BufferedInputStream(new FileInputStream(sendFile));
            
            //파일내용 보내기
            byte[] data = new byte[1024];
            while ((readBytes = bis.read(data)) != -1) {
                dos.write(data, 0, readBytes);
                totalReadBytes += readBytes;
                System.out.println("[" + user + "] 파일 전송 현황 : " + totalReadBytes + "/"
                        + fileSize + " Byte(s) ("
                        + (totalReadBytes * 100 / fileSize) + " %)");//테스트코드
            }
            if(totalReadBytes == fileSize) {
//            	JOptionPane.showMessageDialog(null, "파일 전송이 완료되었습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("[" + user + "] 서버로 파일 전송 완료");//테스트코드
            }
            dos.flush();
//            dos.close();
            bis.close();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("[" + user + "] 서버로 파일 전송 실패");
		}
	}
	
	
	/**
	 * 클라이언트 실행용 메인
	 */
	public static void main(String[] args) {
		ChatClient2 client = new ChatClient2();
	}
	
}
