//package mini.mes.join;
//
//
//import java.io.*;
//
//import java.net.Socket;
//
//public class InfoReceiver extends Thread{
//	
//	private Socket socket;
//	private ObjectInputStream objectIn = null;
//	private ObjectOutputStream objectOut = null;
//	private Member m = new Member();
//	
//	public InfoReceiver(Socket socket) {
//		this.socket = socket;
//	}
//
//	public void run() {
//		try {
//			/**
//			 * ment를 받아 DB파일에 저장
//			 */
//			objectIn = new ObjectInputStream(socket.getInputStream());
//			try {
//				objectIn.readObject();
//				File target = new File("D:\\Java\\db\\membersDB\\"+m.getId() + ".txt");
//				new ObjectOutputStream( new BufferedOutputStream(new FileOutputStream(target)));
//				objectOut.writeObject(objectIn);
//				
//				objectOut.close();
//				objectIn.close();
//			} catch (Exception e) {e.printStackTrace();}
//			dataIn = new DataInputStream(socket.getInputStream());
//			String str = dataIn.readUTF();
//				
//				FileWriter mentWriter = new FileWriter("D:\\Java\\db\\ment.txt");	//DB 저장 경로
//				BufferedWriter buffWriter = new BufferedWriter(mentWriter);
//				buffWriter.write(str);
//				buffWriter.close();
//				
//			
//			
//			
//			
//			
//				/** 
//				 * 회원정보
//				 */
//				objectIn = new ObjectInputStream(socket.getInputStream());
//				try {
//					Member m = (Member)objectIn.readObject();
//					File target = new File("D:\\Java\\db\\membersDB\\"+m.getId() + ".txt");
//					objectOut = new ObjectOutputStream( new BufferedOutputStream(new FileOutputStream(target)));
//					BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Java\\db\\membersDB\\"+m.getId() + ".txt"));
//					objectOut.writeObject(m);
//					BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Java\\db\\membersDB\\"+m.getId() + ".txt"));
//					writer.write(m.getId());
//					File target = ;
//					objectOut.close();
//					
//					FileOutputStream fos = new FileOutputStream(target);
//				} catch (ClassNotFoundException e) {
//					e.printStackTrace();
//				}
//				
//
//		} catch (IOException e) {e.printStackTrace();}
//	}
//}
