package mini.mes.net;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 * 파일을 서버로 보내는 기능의 클래스
 * @author 최범석
 */
public class FileSender extends Thread{
	
	/**
	 * 변수 생성
	 */
	private Socket socket;
	private DataOutputStream dos;
	private BufferedInputStream bis;
	private String filename;
	private String user;
	
	/**
	 * 파일 보내기용 생성자
	 * @param socket 연결 소켓정보
	 * @param filestr 파일 절대경로
	 */
	public FileSender(String userID, Socket socket,String filestr) {
		this.user = userID;
		this.socket = socket;
		this.filename = filestr;
	}
	
	
	/**
	 * 파일을 서버로 보내는 메소드
	 */
    public void run() {
        try {
        	dos = new DataOutputStream(socket.getOutputStream());
            File f = new File(filename);
        	long fileSize = f.length();
            long totalReadBytes = 0;
            int readBytes;
            bis = new BufferedInputStream(new FileInputStream(f));
            
            //사용자 이름 보내기
//            dos.writeUTF(user);
//            dos.flush();
            
            //파일임을 알려주기
//            dos.writeUTF("[fileSend]");
//            dos.flush();
            
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
            dos.close();
            bis.close();
            System.out.println("[클라이언트] 서버로 파일 전송 완료");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[클라이언트] 파일 보내기 오류");
        }
    }


}
