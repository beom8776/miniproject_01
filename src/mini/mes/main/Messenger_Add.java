package mini.mes.main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *	친구추가
 */
class Add extends JFrame{
	
//	컴포넌트를 배치할 영역을 JPanel로 구현
	private JPanel con = new JPanel();
	
	
	private JLabel titlelb = new JLabel("친구찾기", JLabel.CENTER);
	private JTextField searchTf = new JTextField();
	private JButton searchBt = new JButton("찾기");
	private JPanel searchList = new JPanel();
	
	public Add() {
		this.display();
		this.event();
		this.menu();
		
		this.setTitle("Swing 예제");
//		this.setLocation(100, 100);
		this.setLocationByPlatform(true);
		this.setSize(400, 400);
		this.setResizable(false);
		this.setVisible(true);
	}
	/**
	 * 화면 구현 메소드
	 */
	public void display() {
		this.setContentPane(con);//con을 Component 설정 영역으로 등록
		con.setLayout(null);
		
		con.add(titlelb);
		titlelb.setBounds(55, 10, 276, 40);
		
//		검색영역
		con.add(searchTf);
		searchTf.setBounds(55, 60, 200, 40);
		searchTf.setColumns(10);
		
		con.add(searchBt);
		searchBt.setBounds(267, 60, 60, 40);
		
//		검색결과영역
		con.add(searchList);
		searchList.setBounds(30, 130, 325, 200);
	}
	/**
	 * 이벤트 설정 메소드
	 */
	public void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x누르면 창 소멸
		
//		찾기버튼 기능
		searchBt.addActionListener(e->{});
		
	}
	/**
	 * 메뉴 구현 메소드
	 */
	public void menu() {
		
	}
}


public class Messenger_Add {
	private static JTextField textField;
	public static void main(String[] args) {
		Add friendAdd = new Add();

	}
}