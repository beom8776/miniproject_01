package mini.mes.mbServer;


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
	 * ȸ���� �˻���û�� �޾� �˻��ϴ� ID�� �ִ��� Ȯ���ϰ� ����� �����Ѵ�.
	 */
	public void run() {
		try {
//			objectIn = new ObjectInputStream(socket.getInputStream());
			System.out.println("(Server)[[����Ȯ��]] : [objectIn = "+objectIn+"]");
			str = (String)objectIn.readObject();
			System.out.println("(Server)������� : [������ ���� �Ϸ� --- 3]");
			System.out.println("(Server)���系�� : [str = "+str+"] --- 3");
			System.out.println();
			
			String path = "D:\\Java\\db\\membersDB\\"+str+".db";
//			String path = "D:\\eclipse-java-photon-R-win32-x86_64 (Test)\\workspace\\network_Test\\membersDB\\"+str+".db"; // ȨTest
			
			File findfile = new File(path);
			
			System.out.println("(Server)������� : [��û ��� ó�� �Ϸ� --- 4]");
			System.out.println("(Server)���系�� : [findfile = "+findfile+" --- 4]");
			
			System.out.println("(Server)������� : [����� ���� �غ� --- 5]");
			
			ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("(Server)[[����Ȯ��]] : [objectOut = "+objectOut+"]");
			System.out.println();
			
			System.out.println("(Server)[[���ǹ� Ȯ��]] : [findfile.getName() = "+findfile.getName()+"]");
			
			if((findfile.exists()) == false) {
				
				System.out.println("(Server)������� : [�����(if) ���� �غ� --- 6]");
				
				result = "No_result";
				objectOut.writeObject(result);
				
				System.out.println("(Server)[[����Ȯ��]] : [(if)objectOut = "+objectOut+" --- 6]");
				
				objectOut.flush();
			
				System.out.println("(Server)������� : [�����(if) ���� --- 7]");
			}
			
			else {
				System.out.println("(Server)������� : [�����(else) ���� �غ� --- 6]");
				result = "Exist_True";
				objectOut.writeObject(result);
				System.out.println("(Server)���系�� : [(else)objectOut = "+objectOut+" --- 6]");
				objectOut.flush();
				System.out.println("(Server)������� : [�����(else) ���� --- 7]");
				
				ObjectInputStream input = new ObjectInputStream(
						new BufferedInputStream(new FileInputStream(findfile)));
				System.out.println("(Server)[[����Ȯ��]] : [input = "+input+"]");
				//				BufferedInputStream buffIn = new BufferedInputStream(new FileInputStream(findfile))
				mb = (Member)input.readObject();
				System.out.println("(Server)[[����Ȯ��]] : [mb.getname() = "+mb.getName()+"]");
				System.out.println("(Server)[[����Ȯ��]] : [mb.getment() = "+mb.getMent()+"]");
				
				objectOut.writeObject(mb);

				System.out.println("(Server)���系�� : [����� objectOut"+objectOut+" --- 7]");
				objectOut.flush();
				System.out.println("(Server)������� : [��� ���� ���� --- 8]");
			}
		} catch (Exception e) {e.printStackTrace();}
	}

	
	/**
	 * ȸ������ ������ �޾� ȸ��DB�� �����Ѵ�.
	 */
	public void create() {
		try {
//			objectIn = new ObjectInputStream(socket.getInputStream());
			System.out.println("������� : [������ �޴��� --- 3]");
			mb = (Member)objectIn.readObject();
			System.out.println("������� : [������ �ޱ� �Ϸ� --- 3]");
			File target = new File("D:\\Java\\db\\membersDB\\"+mb.getId() + ".db");
			System.out.println("������� : [���� ���� --- 5]");
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
			File target = new File("D:\\Java\\db\\membersDB\\Test01.db"); // �׽�Ʈ
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
