package mini.mes.main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *	Swing에서 사용하는 Frame : JFrame 
 */
class Main extends JFrame{
	
//	컴포넌트를 배치할 영역을 JPanel로 구현
	private JPanel con = new JPanel();
	/**
	 * 상단고정
	 */
	private JPanel myPanel = new JPanel();
	private JLabel picture = new JLabel("사진");
	private JLabel name = new JLabel("김현진");
	private JLabel ment = new JLabel(" 안녕하세요. 저는 김현진 입니다.");
	private JButton logout = new JButton("OUT");
	private JButton friendList = new JButton("친구목록");
	private JButton search = new JButton("친구찾기");
	private JButton myInfo = new JButton("내정보");
	
//	Panel List의 기본틀
	private JPanel basicPanel = new JPanel();
	private JScrollPane scrollPane = new JScrollPane();
	private JPanel listPanel = new JPanel();		//	Panel List
	
	private JPanel add1 = new JPanel();

	private JLabel pict = new JLabel("사진");
	private JLabel name1 = new JLabel("김현진");
	private JLabel info1 = new JLabel("음... 오늘은 정말이지 뭔가 잘 안돼..");
	private JButton talk = new JButton("대화신청");
	


	
	
	public Main() {
		this.display();
		this.event();
		this.menu();
		
		this.setTitle("Java Messenger");
//		this.setLocation(800, 100);
		this.setLocationByPlatform(true);
		this.setSize(400, 900);
		this.setResizable(true);
		this.setVisible(true);
	}
	/**
	 * 화면 구현 메소드
	 */
	public void display() {
		this.setContentPane(con);//con을 Component 설정 영역으로 등록
		con.setLayout(null);
		myPanel.setLayout(null);
		con.add(myPanel);
		myPanel.setBounds(0, 0, 384, 60);
		
		myPanel.add(picture);
		picture.setBounds(0, 0, 60, 60);
		myPanel.add(name);
		name.setBounds(70, 3, 100, 25);
		myPanel.add(ment);
		ment.setBounds(70, 32, 235, 25);
		myPanel.add(logout);
		logout.setBounds(340, 36, 36, 23);
		
		con.add(friendList);
		friendList.setBounds(0, 60, 128, 50);
		con.add(search);
		search.setBounds(128, 60, 128, 50);
		con.add(myInfo);
		myInfo.setBounds(256, 60, 128, 50);
		
		/**
		 * ListPanel의 기본틀
		 */
		con.add(basicPanel);
		basicPanel.setLayout(null);
		basicPanel.setBounds(0, 110, 384, 751);
		basicPanel.add(scrollPane);
		scrollPane.setBounds(369, 0, 15, 751);
//		ListPanel 
		basicPanel.add(listPanel);
		listPanel.setBounds(0, 110, 368, 751);
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		
		listPanel.add(add1);
//		listPanel.setVisible(false);
		add1.setBounds(0, 110, 370, 60);
		add1.setLayout(null);

		add1.add(pict);
		pict.setBounds(0, 0, 60, 60);
		
		add1.add(name1);
		name1.setBounds(69, 10, 96, 15);
		
		add1.add(info1);
		info1.setBounds(69, 35, 179, 15);
		
		add1.add(talk);
		talk.setBounds(261, 19, 97, 23);
		
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
		
		search.addActionListener(e->{

		});
		
		logout.addActionListener(e->{
			int out = JOptionPane.showConfirmDialog(con, "로그아웃 하시겠습니까?", "Logout", JOptionPane.YES_NO_OPTION);
		});
		
		
	}
	/**
	 * 메뉴 구현 메소드
	 */
	public void menu() {
		
	}
}

public class Messenger_Main {
	public static void main(String[] args) {
		Main window = new Main();
		

		

		

		


		

		

		

		
		
		
	}
}