package mini.mes.join;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class JoinMemberManager extends Thread{
	
	private Socket socket;
	private ObjectInputStream objectIn = null;
	private ObjectOutputStream objectOut = null;
	private Member mb;
	private String result = null;
	private String str = null;
	
	public JoinMemberManager(Socket socket, ObjectInputStream objectIn) {
		this.socket = socket;
		this.objectIn = objectIn;
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
			
			String path = "D:\\Java\\db\\membersDB\\"+str+".db";
//			String path = "D:\\eclipse-java-photon-R-win32-x86_64 (Test)\\workspace\\network_Test\\membersDB\\"+str+".db"; // 홈Test
			
			File findfile = new File(path);
			
			System.out.println("(Server)현재상태 : [요청 결과 처리 완료 --- 4]");
			System.out.println("(Server)현재내용 : [findfile = "+findfile+" --- 4]");
			
			System.out.println("(Server)현재상태 : [결과값 전송 준비 --- 5]");
			
			ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("(Server)[[내용확인]] : [objectOut = "+objectOut+"]");
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
//			objectIn = new ObjectInputStream(socket.getInputStream());
			System.out.println("현재상태 : [데이터 받는중 --- 3]");
			mb = (Member)objectIn.readObject();
			System.out.println("현재상태 : [데이터 받기 완료 --- 3]");
			File target = new File("D:\\Java\\db\\membersDB\\"+mb.getId() + ".db");
			System.out.println("현재상태 : [파일 생성 --- 5]");
			objectOut = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(target)));

			objectOut.writeObject(mb);
			objectOut.flush();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public void myment() {
		try {
			mb = new Member();
			System.out.println("mb(1) : " + mb.getMent());
			
			mb = (Member)objectIn.readObject();
			System.out.println("mb(2) : " + mb.getMent());
//			File target = new File("D:\\Java\\db\\membersDB\\"+mb.getId() + ".db");
			File target = new File("D:\\Java\\db\\membersDB\\Test01.db"); // 테스트
			mb.setMent(mb.getMent());
			
			mb.getMent();
			System.out.println("mb(3) : " + mb.getMent());
//			objectOut = new ObjectOutputStream(
//					new BufferedOutputStream(new FileOutputStream(target)));
			
//			objectOut.writeObject(mb);
//			objectOut.flush();
		} catch (Exception e) {e.printStackTrace();}
	}
	
}
