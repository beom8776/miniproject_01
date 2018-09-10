package mini.mes.chatfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import mini.mes.chatServer.Board;

/**
 * 파일 관리 클래스
 * @author 최범석
 */
public class FileManager {
	
	/**
	 * 변수 생성
	 */
	private File target;
	private int port = Board.MAIN_PORTNUMBER;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	/**
	 * 생성자
	 * @param 파일이름
	 */
	public FileManager(String fileName) {
		try {
			target = new File("files",fileName);
			if(!target.exists()) {
				target.createNewFile();
			}
//				System.out.println(target.getName() + "파일을 생성하였습니다.");
				
				out = new ObjectOutputStream(new FileOutputStream(target));
				in = new ObjectInputStream(new FileInputStream(target));
						
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("파일 생성 오류");
		}
	}


//	/**
//	 * 방 정보를 저장하는 메소드
//	 * @param roomMap
//	 */
//	public void outputRoomMap(Map<Integer, Map<String, ChatServerManager>> roomMap) {
//		try{
//			out.writeObject(roomMap);
//			out.close();
//		}catch(Exception e) {
//			e.printStackTrace();
//			System.out.println("파일 출력 오류");
//		}
//	}
	
	
//	/**
//	 * DB에서 방 정보를 불러오는 메소드
//	 * @return
//	 */
//	public Map<Integer, Map<String, ChatServerManager>> inputRoomMap() {
//		try{
//			Map<Integer, Map<String, ChatServerManager>> map = (Map<Integer, Map<String, ChatServerManager>>)in.readObject();
//			in.close();
//			return map;
//		}catch(Exception e) {
//			e.printStackTrace();
//			return new HashMap<>();
//		}
//	}
	
//	/**
//	 * 대화내용 파일 출력 메소드
//	 * @param 대화내용 저장소
//	 */
//	public void fileOutput(StringBuffer buf) {
//		try (ObjectOutputStream out = new ObjectOutputStream(
//				new BufferedOutputStream(new FileOutputStream(target)));) {
//			out.writeObject(buf);
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("파일 출력 오류");
//		}
//	}
	
	
//	/**
//	 * 대화내용 파일 입력 메소드
//	 * @return 대화내용 저장소
//	 */
//	public StringBuffer fileInput() {
//		try(ObjectInputStream in = new ObjectInputStream(
//				new BufferedInputStream(new FileInputStream(target)));){
//			StringBuffer sb = (StringBuffer)in.readObject();
//			return sb;
//		}catch(Exception e) {
//			e.printStackTrace();
//			System.out.println("파일 입력 오류");
//			return new StringBuffer();
//		}
//	}
	         
	
}
