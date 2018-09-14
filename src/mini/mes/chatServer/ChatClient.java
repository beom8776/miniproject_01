package mini.mes.chatServer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import mini.mes.chatting.ChattingGui;

/**
 * 클라이언트 기능 실행 클래스
 * @author 최범석
 */
public class ChatClient extends Thread {
		
	/**
	 * 변수 생성
	 */
	private ChattingGui chat;
	private Socket socket;
	private String user;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	private String baseDir = "C:\\Users\\user\\Documents";
	private File sendFile;
	private StringBuffer sb = new StringBuffer();
	
	
	/**
	 * 생성자
	 * @param list 채팅방에 속한 사용자 리스트
	 * @param ID 나의 아이디
	 */
	public ChatClient(List<String> list, String myID){
		try {
//			user = JOptionPane.showInputDialog(null,"아이디 입력"); //테스트코드
			user = myID;
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
			
			this.oos = new ObjectOutputStream(socket.getOutputStream());
			this.ois = new ObjectInputStream(socket.getInputStream());
			//반복 수신기능 시작
			this.setDaemon(true);
			this.start();
			
			//채팅방에 속한 사용자 리스트 보내기
			oos.writeInt(list.size());
			oos.flush();
			for(Object object : list) {
				String name = (String)object;
				oos.writeUTF(name);
				oos.flush();
			}
			
        	//아이디 보내기
			oos.writeUTF(user);
			oos.flush();
		} catch (Exception e) {
			System.out.println("[" + user + "] 서버에 연결 실패");
		}
	}
	
	
	/**
	 *서버로 메시지를 보내는 메소드
	 */
	public void send(String text) {
		try {
			oos.writeUTF(text);
			oos.flush();
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
			oos.writeUTF("[system]" + text);
			oos.flush();
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
				String sendUserID = ois.readUTF();
				String text = ois.readUTF();
				System.out.println("[" + user + "] " + sendUserID + "의 메시지 : " + text);//테스트코드
				
				//파일일 경우
				if(text.equals("[fileSend]")) {
					
					//파일이름 받기
					String fileName = ois.readUTF();
					System.out.println("[" + user + "] 받은 파일이름 = " + fileName);//테스트코드
					
					//파일 수신 확인 창
					int option = JOptionPane.showConfirmDialog(chat, fileName + " 파일을 수신하겠습니까?", "파일 수신 확인", JOptionPane.YES_NO_OPTION);
					if(option == 0) {
//				    	this.systemSend(user + "님이 파일 수신을 수락하였습니다");
						System.out.println("[" + user + "] 파일 받기를 시작합니다");//테스트코드
						this.send("[fileYes]");
						this.receiveFile(fileName);
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
					this.send(sendUserID);
					this.fileSend();
				}

				//채팅창을 종료하라는 명령일 경우
				else if (text.equals("[chatClose]")) {
					chat.setVisible(false);
				}
				
//				//채팅로그를 보낸다는 명령일 경우
//				else if (text.equals("[setLog]")) {
//					this.receiveLog();
//				}
						
				//메시지일 경우
				else {
					chat.inputChat(sendUserID, text);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[" + user + "] 메시지 / 파일 수신 실패");
		}
	}
	

//	private void receiveLog() {
//		try {
//			//파일 내용 받기
//			byte[] data = new byte[1024];
//			String s; 
//			while (true) {
//				int readBytes = ois.read(data);
//				s = new String(data, "UTF-8");
//				sb.append(s);
//				 if(readBytes != 1024) break;
//			}
//			chat.receiveLog(sb);
//		}catch(Exception e) {
//			e.printStackTrace();
//			System.out.println("[" + user + "] 서버로부터 채팅 로그 수신 실패");
//		}
//	}


	private void receiveFile(String fileName) {
		try {
			int pos = fileName.lastIndexOf( "." );
			String ext = fileName.substring( pos + 1 );
			
			//저장할 경로 및 파일 이름 지정 기능
			Dialog sd = new Dialog(baseDir, ext);
			String path = sd.getPath();
			File f = new File(path);
			System.out.println("[" + user + "] 저장할 경로 = " + f);//테스트코드

			
			
			//파일 크기 받기
			Long fileSize = ois.readLong();
			Long totalReadBytes = 0L;
			int readBytes = 0;
			
				//파일 내용 받기
				bos = new BufferedOutputStream(new FileOutputStream(f));
				byte[] data = new byte[1024];
				while (true) {
					readBytes = ois.read(data);
//					System.out.println("[" + user + "] readBytes = " + readBytes);//테스트코드
					totalReadBytes += readBytes;
					bos.write(data, 0, readBytes);
					bos.flush();
					 System.out.println("[" + user + "] 파일 수신 현황 : " + totalReadBytes + "/"
	                       + fileSize + " Byte(s) ("
	                       + (totalReadBytes * 100 / fileSize) + " %)");//테스트코드
					 if(readBytes != 1024) break;
				}
				bos.close();
//				ois.close();
		    	JOptionPane.showMessageDialog(null, "파일 수신이 완료되었습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
//				chat.systemMessage(user + "님 파일 수신을 완료하였습니다");
				System.out.println(user + "님 파일 수신 완료");
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
			if(fileName != null) {
				sendFile = new File(fileName);
				
				//시스템메시지 전송
				this.systemSend(user + "님이 파일을 전송합니다");
				
	            //파일임을 알려주기
	            oos.writeUTF("[fileSend]");
	            oos.flush();

	            //파일이름 보내기
	            oos.writeUTF(sendFile.getName());
	            oos.flush();
				System.out.println("[" + user + "] 서버로 " + sendFile.getName() + "를 보냅니다");//테스트코드
			}
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
            
            oos.writeLong(fileSize);
            oos.flush();
            
            //파일내용 보내기
            byte[] data = new byte[1024];
            while ((readBytes = bis.read(data)) != -1) {
                oos.write(data, 0, readBytes);
                totalReadBytes += readBytes;
                System.out.println("[" + user + "] 파일 전송 현황 : " + totalReadBytes + "/"
                        + fileSize + " Byte(s) ("
                        + (totalReadBytes * 100 / fileSize) + " %)");//테스트코드
            }
            if(totalReadBytes == fileSize) {
//            	JOptionPane.showMessageDialog(null, "파일 전송이 완료되었습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("[" + user + "] 서버로 파일 전송 완료");//테스트코드
            }
            oos.flush();
//            oos.close();
            bis.close();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("[" + user + "] 서버로 파일 전송 실패");
		}
	}
	
	
	/**
	 * 클라이언트 테스트용 메인
	 */
	public static void main(String[] args) {
		List<String> setList = new ArrayList<>();
		String userSize = JOptionPane.showInputDialog(null, "총 인원 수를 입력");
		int size = Integer.parseInt(userSize);
		String myID = JOptionPane.showInputDialog(null,  "나의 아이디를 입력");
		setList.add(myID);
		for(int i = 0; i < size-1; i++) {
			String ID = JOptionPane.showInputDialog(null,  "아이디를 입력");
			setList.add(ID);
		}
		ChatClient client = new ChatClient(setList, myID);
	}
	
}
