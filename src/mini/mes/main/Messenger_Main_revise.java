package mini.mes.main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *	Swing에서 사용하는 Frame : JFrame 
 */
class Window01 extends JFrame{
	
//	컴포넌트를 배치할 영역을 JPanel로 구현
	private JPanel con = new JPanel();
	
	private JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP);
	
	
	
	public Window01() {
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
		
		this.getContentPane().add(tab, BorderLayout.CENTER);
		
	}
	/**
	 * 이벤트 설정 메소드
	 */
	public void event() {
//		awt에서는 WindowEvent를 직접 구현해서 x버튼을 처리
//		 -> Swing에서는 옵션을 통해 지정하도록 변경
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);//x누르면 프로그램 종료
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x누르면 창 소멸
//		this.setDefaultCloseOperation(HIDE_ON_CLOSE);//x누르면 창 숨김
//		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//사용자 직접 구현
		
		
	}
	/**
	 * 메뉴 구현 메소드
	 */
	public void menu() {
		
	}
	
}

public class Messenger_Main_revise {
	public static void main(String[] args) {
		Window01 window = new Window01();
	}
}