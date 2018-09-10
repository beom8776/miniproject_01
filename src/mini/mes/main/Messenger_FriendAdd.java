package mini.mes.main;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.border.LineBorder;

import mini.mes.join.Member;

/**
 * 친구찾기 / 추가 Tab
 * @author 김현진
 *
 */
public class Messenger_FriendAdd extends JPanel{

	/**
	 * 친구 검색 영역
	 */
	private JPanel searchPan = new JPanel();
	private JLabel titlelb = new JLabel("친구를 찾아보세요");
	private JTextField searchFd = new JTextField();
	private JLabel searchtitle = new JLabel("친구 ID");
	private JButton searchBt = new JButton("검색");
	private JLabel label = new JLabel("검색결과");
	

	/**
	 * 검색 결과 영역
	 */
	private JPanel resultPan = new JPanel();
	private Member mb = new Member();
	
	private JPanel hasResult = new JPanel();
	
	private JLabel noResult = new JLabel("검색결과가 없습니다.");
	private JLabel searpicture1 = new JLabel("사진");
//	private JLabel searname1 = new JLabel("이름");
	private JLabel searment = null;
	private JButton searadd = new JButton("추가");
	
	private String kind = null;	// Server에 어떤 처리 작업을 할것인지 지정해주는 변수
	
	public Messenger_FriendAdd() {
		this.event();
		
		/**
		 * 친구찾기 영역 패널
		 */
		this.setLayout(null);	
		this.add(searchPan);
		searchPan.setBounds(0, 0, 378, 200);
		searchPan.setLayout(null);
		
		titlelb.setFont(new Font("굴림", Font.BOLD, 14));
		titlelb.setHorizontalAlignment(SwingConstants.CENTER);
		searchPan.add(titlelb);
		titlelb.setBounds(122, 20, 140, 30);
		
		searchFd.setBounds(102, 60, 180, 22);
		searchPan.add(searchFd);
		searchFd.setColumns(20);
		
		searchtitle.setFont(new Font("굴림", Font.BOLD, 12));
		searchPan.add(searchtitle);
		searchtitle.setBounds(45, 60, 45, 22);
		
		searchBt.setFont(new Font("굴림", Font.BOLD, 12));
		searchPan.add(searchBt);
		searchBt.setBounds(222, 92, 60, 23);
		
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("굴림", Font.BOLD, 12));
		searchPan.add(label);
		label.setBounds(122, 176, 140, 19);
		
		/**
		 * 검색결과영역 패널
		 */
		resultPan.setLayout(null);
		this.add(resultPan);
		resultPan.setBounds(1, 200, 378, 533); //382 / 560
		resultPan.setBorder(new LineBorder(Color.ORANGE, 2));
		
		/**
		 * 검색결과 없음 출력
		 */
		noResult.setEnabled(false);
		noResult.setVisible(false);
		noResult.setFont(new Font("굴림", Font.BOLD, 12));
		noResult.setHorizontalAlignment(SwingConstants.CENTER);
		resultPan.add(noResult);
		noResult.setBounds(102, 268, 180, 25);
		
		/**
		 * 검색 후 결과 출력(별도 클래스 필요)
		 */
//		resultPan.add(hasResult);
//		hasResult.setLayout(null);
//		hasResult.setBounds(2, 2, 370, 60);
//		
//		searpicture1.setHorizontalAlignment(SwingConstants.CENTER);
//		searpicture1.setBounds(1, 1, 57, 57);
//		hasResult.add(searpicture1);
//		
//		searname1.setBounds(70, 3, 160, 25);
//		hasResult.add(searname1);
//		
//		searment.setBounds(70, 30, 230, 25);
//		hasResult.add(searment);
//		
//		searadd.setBounds(298, 18, 72, 24);
//		hasResult.add(searadd);
//		searadd.setFont(new Font("굴림", Font.BOLD, 13));
	}
	public void event() {
		/**
		 * 검색버튼 이벤트
		 */
		searchBt.addActionListener(e->{
//			Server에 회원정보 검색요청
			try {
				Socket socket = new Socket("localhost", 10001);
				System.out.println("(Client)현재상태 : [전송 접속대기] --- 1");
				System.out.println("(Client)현재내용 : ["+socket+"]");
				
				ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
				System.out.println("(Client)현재상태 : [전송 접속완료] --- 2");
				kind = "친구찾기";
				objectOut.writeObject(kind);
				objectOut.flush();
				
				
				System.out.println("(Client)현재내용 : ["+objectOut+"] --- 2");
				objectOut.writeObject(searchFd.getText());
				System.out.println("(Client)[[내용확인]] : [objectOut = "+objectOut+"]");
				
				objectOut.flush();
				System.out.println("(Client)현재상태 : [전송 완료]");
				
				System.out.println("(Client)현재상태 : [수신 접속대기] --- 3");
				ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
				System.out.println("(Client)현재상태 : [수신 접속완료] --- 4");
				System.out.println("(Client)현재내용 : ["+objectIn+"] --- 4");
				
				String str = (String)objectIn.readObject();
				System.out.println("(Client)현재상태 : [서버 결과값(if/else)수신 완료] --- 5");
				System.out.println("(Client)현재내용 : ["+str+"] --- 5");
				
				if(str.equals("No_result")) {
					System.out.println("(Client)현재상태 : [서버 결과값(else)처리 준비] --- 6");
					noResult.setVisible(true);
					System.out.println("(Client)현재상태 : [서버 결과값(else)처리 완료] --- 7");
					objectOut.close();
					objectIn.close();
					System.out.println("(Client)현재상태 : [연결 종료] --- 8");
				}
				else {
					System.out.println("(Client)현재상태 : [서버 결과값(if)처리 준비] --- 6");
					noResult.setVisible(false);
					mb = (Member)objectIn.readObject();
					System.out.println("(Client)현재내용 : ["+mb.getName()+"] --- 7");
					System.out.println("(Client)현재상태 : [서버 결과값(if)처리 완료] --- 7");
					
					JPanel test1 = new JPanel();
					JLabel testName = new JLabel(mb.getName());
					searment = new JLabel(mb.getMent());
					System.out.println("(Client)[[내용확인]] : [mb.getMent() = "+mb.getMent()+"] --- 7");
					
					System.out.println("(Client)[[내용확인]] : [mb.getName() = "+mb.getName()+"] --- 7");
					
					resultPan.add(test1);
					test1.setLayout(null);
					test1.setBounds(2, 2, 370, 60);
					
					searpicture1.setHorizontalAlignment(SwingConstants.CENTER);
					searpicture1.setBounds(1, 1, 57, 57);
					test1.add(searpicture1);
					
					testName.setBounds(70, 3, 160, 25);
//					testName.setBounds(1, 1, 57, 57);
					test1.add(testName);
					
					searment.setBounds(70, 30, 230, 25);
					test1.add(searment);
					
					searadd.setBounds(298, 18, 72, 24);
					test1.add(searadd);
					searadd.setFont(new Font("굴림", Font.BOLD, 13));
					System.out.println("(Client)현재상태 : [작업완료] --- 8");
					
					objectOut.close();
					objectIn.close();
					System.out.println("(Client)현재상태 : 연결종료 --- 9");
				}
			} catch (Exception e1) {e1.printStackTrace();}
			
//			try {
//				ServerSocket server = new ServerSocket(10001);
//				Socket socket = server.accept();
//				System.out.println("-----접속-----");
//				
//				if(objectIn.equals("No_result"))
//					
//				else {

//				}

//			} catch (Exception e1) {e1.printStackTrace();}
			
			
			
			
		});
	}
}





