package mini.mes.chatfile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

/**
 * 파일 관리 클래스
 * @author 최범석
 */
public class FileManager extends Thread {
	
	
	/**
	 * 변수 생성
	 */
	private File target;
	private int port = 5000;
	
	
	/**
	 * 생성자
	 * @param 파일이름
	 */
	public FileManager(String fileName) {
		target = new File("files",fileName);
		if(!target.exists()) {
			try {
				target.createNewFile();
//				System.out.println(target.getName() + "파일을 생성하였습니다.");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("파일 생성 오류");
			}
		}
	}
	
	/**
	 * 대화내용 파일 출력 메소드
	 * @param 대화내용 저장소
	 */
	public void fileOutput(StringBuffer buf) {
		try (ObjectOutputStream out = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(target)));) {
			out.writeObject(buf);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("파일 출력 오류");
		}
	}
	
	/**
	 * 대화내용 파일 입력 메소드
	 * @return 대화내용 저장소
	 */
	public StringBuffer fileInput() {
		try(ObjectInputStream in = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream(target)));){
			StringBuffer sb = (StringBuffer)in.readObject();
			return sb;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("파일 입력 오류");
			return new StringBuffer();
		}
	}
	         
	
}
