package mini.mes.memberServer;


import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	private MemberManageServer server;
	
	
	public MemberManager(MemberManageServer server, Socket socket) {

		this.server = server;
		this.socket = socket;
		try {
		this.objectOut  = new ObjectOutputStream(socket.getOutputStream());
		this.objectIn = new ObjectInputStream(socket.getInputStream());
		}catch(Exception e) {
			System.out.println("스트림 설정 오류");
		}

	}
	
	
	public void run() {
		try {
			while(true) {
				String kind = objectIn.readUTF();
				System.out.println("kind : ["+kind+"]");
				/**
				 * 회원DB 생성 작업
				 */
				if(kind.equals("회원가입")) {
					this.create();
				}
				
				/**
				 * Client에서 ID검색을 요청하여 확인 후 결과 값을 반환해 주는 작업
				 */
				else if(kind.equals("친구찾기")) {
					this.start();
				}
				
				/**
				 * 나의정보 - 상태메시지 업데이트
				 */
				else if(kind.equals("상태메시지")) {
					this.myment();
				}
			
				/**
				 * 나의정보 - 이미지 업데이트
				 */
				else if(kind.equals("이미지")) {
					this.image();
				}
				
				/**
				 * 친구검색 후 친구추가시 나의DB에 추가
				 */
				else if(kind.equals("친구추가")) {
					this.addFriend();
				}
				
				/**
				 * 로그인 요청시 확
				 */
				else if(kind.equals("로그인")) {
					this.login();
				}
			}
		}catch(Exception e) {
			
		}
	}
	
	
	/**
	 * 회원의 검색요청을 받아 검색하는 ID가 있는지 확인하고 결과를 전송한다.
	 */
//	public void run() {
//		try {
////			objectIn = new ObjectInputStream(socket.getInputStream());
//			System.out.println("(Server)[[내용확인]] : [objectIn = "+objectIn+"]");
//			str = (String)objectIn.readObject();
//			System.out.println("(Server)현재상태 : [데이터 수신 완료 --- 3]");
//			System.out.println("(Server)현재내용 : [str = "+str+"] --- 3");
//			System.out.println();
//			
////			String path = "D:\\Java\\db\\membersDB\\"+str+".db";
//			String path = "D:\\eclipse-java-photon-R-win32-x86_64 (Test)\\workspace\\network_Test\\membersDB\\"+str+".db"; // 홈Test
//			
//			File findfile = new File(path);
//			
//			System.out.println("(Server)현재상태 : [요청 결과 처리 완료 --- 4]");
//			System.out.println("(Server)현재내용 : [findfile = "+findfile+" --- 4]");
//			
//			System.out.println("(Server)현재상태 : [결과값 전송 준비 --- 5]");
//			
//
//			System.out.println();
//			
//			System.out.println("(Server)[[조건문 확인]] : [findfile.getName() = "+findfile.getName()+"]");
//			
//			if((findfile.exists()) == false) {
//				
//				System.out.println("(Server)현재상태 : [결과값(if) 전송 준비 --- 6]");
//				
//				result = "No_result";
//				objectOut.writeObject(result);
//				
//				System.out.println("(Server)[[내용확인]] : [(if)objectOut = "+objectOut+" --- 6]");
//				
//				objectOut.flush();
//			
//				System.out.println("(Server)현재상태 : [결과값(if) 전송 --- 7]");
//			}
//			
//			else {
//				System.out.println("(Server)현재상태 : [결과값(else) 전송 준비 --- 6]");
//				result = "Exist_True";
//				objectOut.writeObject(result);
//				System.out.println("(Server)현재내용 : [(else)objectOut = "+objectOut+" --- 6]");
//				objectOut.flush();
//				System.out.println("(Server)현재상태 : [결과값(else) 전송 --- 7]");
//				
//				ObjectInputStream input = new ObjectInputStream(
//						new BufferedInputStream(new FileInputStream(findfile)));
//				System.out.println("(Server)[[내용확인]] : [input = "+input+"]");
//				//				BufferedInputStream buffIn = new BufferedInputStream(new FileInputStream(findfile))
//				mb = (Member)input.readObject();
//				System.out.println("(Server)[[내용확인]] : [mb.getname() = "+mb.getName()+"]");
//				System.out.println("(Server)[[내용확인]] : [mb.getment() = "+mb.getMent()+"]");
//				
//				objectOut.writeObject(mb);
//
//				System.out.println("(Server)현재내용 : [결과값 objectOut"+objectOut+" --- 7]");
//				objectOut.flush();
//				System.out.println("(Server)현재상태 : [결과 내용 전송 --- 8]");
//			}
//		} catch (Exception e) {e.printStackTrace();}
//	}

	
	/**
	 * 회원가입 정보를 받아 회원DB를 생성한다.
	 */
	public void create() {
		try {
			mb = (Member)objectIn.readObject();
			System.out.println("현재상태 : [데이터 받기 완료 --- 3]");
			System.out.println("현재상태 : [mb.getid() = "+mb.getId()+" --- 3]");
			File folder = new File(System.getProperty("user.dir")+"\\membersDB");
			if(!folder.exists()) {
				folder.mkdir();
			}
			File target = new File(System.getProperty("user.dir")+"\\membersDB\\"+mb.getId()+".db");
			System.out.println(target.getAbsolutePath());
			target.createNewFile();
			System.out.println("현재상태 : [파일 생성 --- 5]");
			ObjectOutputStream fileOut = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(target)));
			System.out.println("objectOut : ["+objectOut+"]");//테스트코드
			fileOut.writeObject(mb);
			fileOut.flush();
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
			String pw = objectIn.readUTF();
			System.out.println("받은 비밀번호 : " + pw);
			String path = System.getProperty("user.dir")+"\\membersDB\\"+str+".db";
			System.out.println("path : " + path);
			File file = new File(path);
			
			if(file.exists()) {
				ObjectInputStream fileIn = new ObjectInputStream(
						new BufferedInputStream(new FileInputStream(file)));
				Member mb = (Member) fileIn.readObject();
				System.out.println(str + "유저의 DB정보 : " + file);
				System.out.println("파일이 존재하여 비밀번호를 검사");
				if(mb.getPw().equals(pw)) {
					String result = "로그인 완료";
					objectOut.writeUTF(result);
					objectOut.flush();
				}
				else {
					System.out.println("비밀번호가 일치하지 않으므로 결과없음 메시지를 전송");
					String result = "결과없음";
					objectOut.writeUTF(result);
					objectOut.flush();
				}
					
			}
			else {
				System.out.println("파일이 존재하지 않으므로 결과없음 메시지를 전송");
				String result = "결과없음";
				objectOut.writeUTF(result);
				objectOut.flush();
			}
			
			System.out.println("login() 메소드 완료");
		} catch (Exception e) {
			e.printStackTrace();
			}
	}
}








