package mini.mes.main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *	메신저 리스트 Test 입니다.
 */
class List extends JFrame{
	
//	컴포넌트를 배치할 영역을 JPanel로 구현
	private JPanel con = new JPanel();
	
	private Scrollbar scrollbar = new Scrollbar();
	private JButton friendList = new JButton("친구목록");
	private JButton searchFriend = new JButton("친구찾기");
	private JButton myInfo = new JButton("내정보");
	private JPanel myPanel = new JPanel();
	private JLabel picture = new JLabel("사진");
	private JLabel ststus = new JLabel("상태정보");
	
	
	
	public List() {
		this.display();
		this.event();
		this.menu();
		
		this.setTitle("Java Messenger");
//		this.setLocation(100, 100);
		this.setLocationByPlatform(true);
		this.setSize(400, 800);
		this.setResizable(false);
		this.setVisible(true);
	}
	/**
	 * 화면 구현 메소드
	 */
	public void display() {
		this.setContentPane(con);//con을 Component 설정 영역으로 등록
		con.setLayout(null);
		
		con.add(scrollbar);
		scrollbar.setBounds(360, 0, 24, 761);
		
		/**
		 * 상단 메뉴 버튼
		 */
		con.add(friendList);
		friendList.setBounds(0, 0, 120, 50);
		
		con.add(searchFriend);
		searchFriend.setBounds(120, 0, 120, 50);
		
		con.add(myInfo);
		myInfo.setBounds(240, 0, 120, 50);
		
		/**
		 * 친구목록 나의 상태정보
		 */
		con.add(myPanel);	
		myPanel.setLayout(null);
		myPanel.setBounds(0, 65, 360, 75);
		
		myPanel.add(picture);
		picture.setBounds(0, 0, 75, 75);
		
		myPanel.add(ststus);
		ststus.setBounds(76, 0, 284, 75);
		
	}
	/**
	 * 이벤트 설정 메소드
	 */
	public void event() {
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//x누르면 창 소멸
		WindowListener closer = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int close = JOptionPane.showConfirmDialog(con, "종료하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION);
				if(close == 0) System.exit(0);
			}
		};
		this.addWindowListener(closer);
		
		friendList.addActionListener(e->{});
		searchFriend.addActionListener(e->{});
		myInfo.addActionListener(e->{});
		
	}
	/**
	 * 메뉴 구현 메소드
	 */
	public void menu() {
		
	}
}

public class Messenger_List {
	public static void main(String[] args) {
		List window = new List();
		

		
	}
}