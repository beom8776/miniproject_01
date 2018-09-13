package mini.mes.memberServer;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import mini.mes.join.Member;

public class MemberManager extends Thread implements Serializable{
	
	private Socket socket;
	private ObjectInputStream objectIn = null;
	private ObjectOutputStream objectOut = null;
	private Member mb;
	private String result = null;
	private String str = null;
	
	public MemberManager(Socket socket, ObjectInputStream objectIn) {
		try {
			this.socket = socket;
			this.objectIn = objectIn;
			this.objectOut  = new ObjectOutputStream(socket.getOutputStream());
		}catch(Exception e) {
			System.out.println("스트림 설정 오류");
		}

	}
	
	/**
	 * 회원의 검색요청을 받아 검색하는 ID가 있는지 확인하고 결과를 전송한다.
	 */
	public void run() {
		try {
//			objectIn = new ObjectInputStream(socket.getInputStream());
			System.out.println("(Server)[[내용확인]] : [objectIn = "+objectIn+"]");
			str = (String)objectIn.readObject();
			System.out.println("(Server)현재상태 : [데이터 수신 완료 --- 3]");
			System.out.println("(Server)현재내용 : [str = "+str+"] --- 3");
			System.out.println();
			
//			String path = "D:\\Java\\db\\membersDB\\"+str+".db";
			String path = "D:\\eclipse-java-photon-R-win32-x86_64 (Test)\\workspace\\network_Test\\membersDB\\"+str+".db"; // 홈Test
			
			File findfile = new File(path);
			
			System.out.println("(Server)현재상태 : [요청 결과 처리 완료 --- 4]");
			System.out.println("(Server)현재내용 : [findfile = "+findfile+" --- 4]");
			
			System.out.println("(Server)현재상태 : [결과값 전송 준비 --- 5]");
			

			System.out.println();
			
			System.out.println("(Server)[[조건문 확인]] : [findfile.getName() = "+findfile.getName()+"]");
			
			if((findfile.exists()) == false) {
				
				System.out.println("(Server)현재상태 : [결과값(if) 전송 준비 --- 6]");
				
				result = "No_result";
				objectOut.writeObject(result);
				
				System.out.println("(Server)[[내용확인]] : [(if)objectOut = "+objectOut+" --- 6]");
				
				objectOut.flush();
			
				System.out.println("(Server)현재상태 : [결과값(if) 전송 --- 7]");
			}
			
			else {
				System.out.println("(Server)현재상태 : [결과값(else) 전송 준비 --- 6]");
				result = "Exist_True";
				objectOut.writeObject(result);
				System.out.println("(Server)현재내용 : [(else)objectOut = "+objectOut+" --- 6]");
				objectOut.flush();
				System.out.println("(Server)현재상태 : [결과값(else) 전송 --- 7]");
				
				ObjectInputStream input = new ObjectInputStream(
						new BufferedInputStream(new FileInputStream(findfile)));
				System.out.println("(Server)[[내용확인]] : [input = "+input+"]");
				//				BufferedInputStream buffIn = new BufferedInputStream(new FileInputStream(findfile))
				mb = (Member)input.readObject();
				System.out.println("(Server)[[내용확인]] : [mb.getname() = "+mb.getName()+"]");
				System.out.println("(Server)[[내용확인]] : [mb.getment() = "+mb.getMent()+"]");
				
				objectOut.writeObject(mb);

				System.out.println("(Server)현재내용 : [결과값 objectOut"+objectOut+" --- 7]");
				objectOut.flush();
				System.out.println("(Server)현재상태 : [결과 내용 전송 --- 8]");
			}
		} catch (Exception e) {e.printStackTrace();}
	}

	
	/**
	 * 회원가입 정보를 받아 회원DB를 생성한다.
	 */
	public void create() {
		try {
			mb = (Member)objectIn.readObject();
			System.out.println("현재상태 : [데이터 받기 완료 --- 3]");
//			File target = new File("D:\\Java\\db\\membersDB\\"+mb.getId() + ".db");
			System.out.println("현재상태 : [mb.getid() = "+mb.getId()+" --- 3]");
			File target = new File("D:\\eclipse-java-photon-R-win32-x86_64 (Test)\\workspace\\network_Test\\membersDB\\"+mb.getId()+".db");
			System.out.println("현재상태 : [파일 생성 --- 5]");
			objectOut = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(target)));

			objectOut.writeObject(mb);
			objectOut.flush();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	/**
	 * 나의 상태메시지를 받아 회원DB에 저장한다.
	 */
	public void myment() {
		try {
			mb = (Member)objectIn.readObject();
			System.out.println("mb(1) : " + mb.getMent());
			String str = mb.getMent();
			
			ObjectInputStream input = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream("D:\\eclipse-java-photon-R-win32-x86_64 (Test)\\workspace\\network_Test\\membersDB\\Test01.db")));
			mb = (Member)input.readObject();
			System.out.println("mb(2) : " + mb.getMent());
			mb.setMent(str);
			File target = new File("D:\\eclipse-java-photon-R-win32-x86_64 (Test)\\workspace\\network_Test\\membersDB\\Test01.db"); // 테스트
			
			objectOut = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(target)));

			
			System.out.println("mb(check1)ID : " + mb.getId());
			System.out.println("mb(check2)NAME : " + mb.getName());
			System.out.println("mb(check3)MENT : " + mb.getMent());
			
			
			objectOut.writeObject(mb);
			objectOut.flush();
//			File target = new File("D:\\Java\\db\\membersDB\\"+mb.getId() + ".db");
			System.out.println("mb.ID : " +mb.getId());
			
			mb.setMent(mb.getMent());
			
			mb.getMent();
			System.out.println("mb(3) : " + mb.getMent());
//			objectOut = new ObjectOutputStream(
//					new BufferedOutputStream(new FileOutputStream(target)));
			
//			objectOut.writeObject(mb);
//			objectOut.flush();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	/**
	 * 나의 이미지를 받아와 DB에 저장한다.
	 */
	public void image() {
		try {
//			Member mb = new Member();
			BufferedImage img = (BufferedImage)objectIn.readObject();
			System.out.println("현재상태 : [데이터 받는중 --- 3]");
			File imgFile = new File(
					"D:\\eclipse-java-photon-R-win32-x86_64 (Test)\\workspace\\network_Test\\membersDB\\Test01.jpg");
			System.out.println("현재상태 : [데이터 저장 경로 지정 --- 4]");
			objectOut = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(imgFile)));
			
			objectOut.writeObject(img);
			System.out.println("현재상태 : [데이터 writer --- 5]");
			objectOut.flush();
			System.out.println("현재상태 : [실행 --- 6]");
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public void addFriend() {
		try {
			String str = (String)objectIn.readObject();
			System.out.println("현재상태 : [str내용 확인 : "+str+" --- 3]");
			File file = new File("D:\\eclipse-java-photon-R-win32-x86_64 (Test)\\workspace\\network_Test\\membersDB\\"+str+".db");
			FileInputStream fileIn = new FileInputStream(file);
			FileOutputStream fileout = new FileOutputStream(new File("D:\\eclipse-java-photon-R-win32-x86_64 (Test)\\workspace\\network_Test\\membersDB\\Test01\\"+str+".db"));
			
			int readBuff = 0;
			byte[] buffer = new byte[512];
			while((readBuff = fileIn.read(buffer)) != -1) {
				fileout.write(buffer, 0, readBuff);
			}
			fileIn.close();
			fileout.close();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public void login() {
		try {
			String str = objectIn.readUTF();
			System.out.println("받은 아이디 : " + str);
			String path = System.getProperty("user.dir")+"\\membersDB\\Test01\\"+str+".db";
			File file = new File(path);
			System.out.println(str + "유저의 DB정보 : " + file);
			
			if(file.exists()) {
				String result = "로그인 완료";
				objectOut.writeUTF(result);
				objectOut.flush();
			}
			else {
				String result = "결과없음";
				objectOut.writeObject(result);
				objectOut.flush();
			}
			
			
		} catch (Exception e) {e.printStackTrace();}
	}
}









