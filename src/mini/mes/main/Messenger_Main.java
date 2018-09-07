package mini.mes.main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Messenger Main 화면
 * @author 김현진
 *
 */
class Messenger_m extends JFrame{
	
	private JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP);
	private Messenger_List list = new Messenger_List();
	private Messenger_FriendAdd search = new Messenger_FriendAdd();
	private Messemger_Myinfo myinfo = new Messemger_Myinfo();
	private Messenger_ChatList chat = new Messenger_ChatList();
	
	public Messenger_m() {
		this.display();
		this.event();
		
		this.setTitle("Java 메신저");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width/2)-(400/2);
		int y = (dim.height/2)-(800/2);
		this.setLocation(x, y);
		this.setSize(400, 800);
		this.setResizable(false);
		this.setVisible(true);
	}
	/**
	 * 화면 구현 메소드
	 */
	public void display() {
		
		this.getContentPane().add(tab, BorderLayout.CENTER);
		
		/**
		 * Tab 추가
		 */
		tab.addTab("친구목록", list);
		tab.addTab("채팅목록", chat);
		tab.addTab("친구찾기", search);
		tab.addTab("나의정보", myinfo);
		
	}
	
	/**
	 * 이벤트 설정 메소드
	 */
	public void event() {
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		WindowListener closer = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int close = JOptionPane.showConfirmDialog(Messenger_m.this, "종료하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION);
				if(close == 0) System.exit(0);
			}
		};
		this.addWindowListener(closer);
	}
}

/**
 * 메인 클래스
 * @author 김현진
 *
 */
public class Messenger_Main {
	public static void main(String[] args) {
		Messenger_m java_Messenger = new Messenger_m();
	}
}














