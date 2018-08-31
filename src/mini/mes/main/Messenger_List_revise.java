package mini.mes.main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *	Swing에서 사용하는 Frame : JFrame 
 */
class List extends JFrame {
	
//	컴포넌트를 배치할 영역을 JPanel로 구현
//	private JPanel con = new JPanel();
	private JPanel frList = new JPanel();			//친구리스트 고정패널
	private JLabel picture = new JLabel("사진");
	private JLabel name = new JLabel("김현진");
	private JLabel ment = new JLabel("오늘도 신나게 화이팅 해보자");
	
	private JPanel main_myinfo = new JPanel();			//나의정보 고정패널
	private JLabel testlb = new JLabel("테스트하는 부분");
	
	public List() {	
		this.add(frList);
		frList.setBounds(0, 100, 384, 661);
		this.getContentPane().add(frList);
		frList.setLayout(null);
		
		frList.add(picture, BorderLayout.WEST);
		frList.add(name, BorderLayout.CENTER);
		frList.add(ment, BorderLayout.CENTER);
		
		this.add(main_myinfo);
		main_myinfo.setBounds(0, 30, 384, 65);
		this.getContentPane().add(main_myinfo);
		main_myinfo.setLayout(new BorderLayout(10, 1));
		
		main_myinfo.add(testlb);
		
	}
}

//public class Messenger_List_revise {
//	public static void main(String[] args) {
//		List list = new List();
//	}
//}