package mini.mes.login;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *	로그인 화면 JDialog
 */
class Window_Login extends JDialog{
	
//	컴포넌트를 배치할 영역을 JPanel로 구현
	private JPanel con = new JPanel();
	
	private JLabel logLabel = new JLabel("메신저 로그인", JLabel.CENTER); 
	private JTextField idFiled = new JTextField();//ID
	private JTextField pwFiled = new JPasswordField();	//비밀번호
	private JButton btLogin = new JButton("로그인");
	private JButton btCancel = new JButton("취소");
	private JButton btJoin = new JButton("회원가입");
	
	public Window_Login() {
		this.display();
		this.event();
		this.menu();
		
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
		this.setLayout(null);
		
		con.add(logLabel);
		logLabel.setBounds(100, 50, 200, 50);
		
		con.add(idFiled);
		idFiled.setBounds(100, 110, 200, 35);
		idFiled.setColumns(10);
		
		con.add(pwFiled);
		pwFiled.setBounds(100, 155, 200, 35);
		pwFiled.setColumns(10);
		
		con.add(btLogin);
		btLogin.setBounds(100, 200, 98, 35);
		
		con.add(btCancel);
		btCancel.setBounds(202, 200, 98, 35);
		
		con.add(btJoin);
		btJoin.setBounds(100, 245, 200, 23);
	}
	
	/**
	 * 이벤트 설정 메소드
	 */
	public void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x누르면 창 소멸
		
		/**
		 * 버튼 이벤트 (미구현)
		 */
		btCancel.addActionListener(e->{});
		btLogin.addActionListener(e->{});
		btJoin.addActionListener(e->{});
	}
	
	/**

	 * 메뉴 구현 메소드 (미구현)
	 */
	public void menu() {
	}
}

public class Gui_Login {
	public static void main(String[] args) {
		Window_Login window = new Window_Login();

	}
}