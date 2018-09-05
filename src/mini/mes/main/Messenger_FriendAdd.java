package mini.mes.main;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
	

	private Member m = new Member();
	
	
//	친구 검색 결과 출력 (별도 클래스 필요)
	private JPanel hasResult = new JPanel();
	private JLabel noResult = new JLabel("검색결과가 없습니다.");
	private JLabel searpicture1 = new JLabel("사진");
	private JLabel searname1 = new JLabel("이름");
	private JLabel searment = new JLabel("상태메시지");
	private JButton searadd = new JButton("추가");

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
		noResult.setVisible(true);
		noResult.setFont(new Font("굴림", Font.BOLD, 12));
		noResult.setHorizontalAlignment(SwingConstants.CENTER);
		resultPan.add(noResult);
		noResult.setBounds(102, 268, 180, 25);
		
		/**
		 * 검색 후 결과 출력(별도 클래스 필요)
		 */
		hasResult.setLayout(null);
		resultPan.add(hasResult);
		hasResult.setBounds(2, 2, 370, 60);
		
		searpicture1.setHorizontalAlignment(SwingConstants.CENTER);
		searpicture1.setBounds(1, 1, 57, 57);
		hasResult.add(searpicture1);
		
		searname1.setBounds(70, 3, 160, 25);
		hasResult.add(searname1);
		
		searment.setBounds(70, 30, 230, 25);
		hasResult.add(searment);
		
		searadd.setBounds(298, 18, 72, 24);
		hasResult.add(searadd);
		searadd.setFont(new Font("굴림", Font.BOLD, 13));
	}
	public void event() {
		/**
		 * 검색버튼 이벤트
		 */
		searchBt.addActionListener(e->{
//			Server에 회원정보 검색요청
			try {
				Socket socket = new Socket("localhost", 50010);
				ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
				objectOut.writeObject(searchFd.getText());
				objectOut.flush();
				objectOut.close();
			} catch (Exception e1) {e1.printStackTrace();}
			
			
			
			
			
		});
	}
}





