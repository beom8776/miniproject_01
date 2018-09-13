package mini.mes.main;

import java.awt.*;
import java.awt.event.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.*;

import mini.mes.join.JoinManager;
import mini.mes.join.Member;

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
 * 로그인 화면에서 메인 화면으로 연결
 * @author 김현진
 *
 */
class Window_Login extends JFrame{
	
//	컴포넌트를 배치할 영역을 JPanel로 구현
	private JPanel con = new JPanel();
	
	private JLabel logLabel = new JLabel("메신저 로그인", JLabel.CENTER); 
	private JTextField idFiled = new JTextField();//ID
	private JTextField pwFiled = new JPasswordField();	//비밀번호
	private JButton btLogin = new JButton("로그인");
	private JButton btJoin = new JButton("회원가입");
	
	private ObjectOutputStream objectOut;
	private ObjectInputStream objectIn;
	
	public Window_Login() {
		this.display();
		this.event();

		
		this.setLocationByPlatform(true);
		this.setTitle("메신저 로그인");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width/2)-(400/2);
		int y = (dim.height/2)-(800/2);
		this.setLocation(x, y);
		this.setSize(400, 400);
		this.setResizable(false);
		this.setVisible(true);
		try {
			InetAddress inet = InetAddress.getByName("localhost");
			Socket socket = new Socket(inet, 10001);
			System.out.println("socket : ["+socket+"]");
			objectIn = new ObjectInputStream(socket.getInputStream());
			objectOut = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("objectOut : ["+objectOut+"]");

			System.out.println("objectIn : ["+objectIn+"]");
		}catch(Exception e) {
			e.printStackTrace();
		}

		
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
		
		con.add(btJoin);
		btJoin.setBounds(202, 200, 98, 35);
	}
	
	/**
	 * 이벤트 설정 메소드
	 */
	public void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x누르면 창 소멸
		
		/**
		 * 로그인 버튼 이벤트
		 * 회원DB에서 정보확인 요청 후 일치할 경우 Main 화면 출력
		 */
		btLogin.addActionListener(e->{
			try {

				String kind = "로그인";
				objectOut.writeUTF(kind);
				objectOut.flush();
				System.out.println("objectOut.write : ["+kind+"]");//테스트코드
				
				objectOut.writeUTF(idFiled.getText());
				objectOut.flush();
				System.out.println("objectOut.write : ["+idFiled.getText()+"]");//테스트코드
				
				String str = objectIn.readUTF();
				System.out.println("str : ["+str+"]");	
				
				if(str.equals("로그인 완료")) {
					JOptionPane.showMessageDialog(this, "로그인 완료");
					Messenger_m java_Messenger = new Messenger_m();
					this.dispose();
				}
				else if(str.equals("결과없음")){
					JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 확인해 주세요.");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		btJoin.addActionListener(e->{
			String[] args = null;
			JoinManager.main(args);
		});
	}
}

/**
 * 메인 클래스
 * @author 김현진
 *
 */
public class Messenger_Main {
	public static void main(String[] args) {	
		Window_Login login = new Window_Login();
//		Messenger_m login = new Messenger_m();
	}
}














