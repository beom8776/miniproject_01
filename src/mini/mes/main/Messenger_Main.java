package mini.mes.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import mini.mes.chatServer.Board;
import mini.mes.join.JoinManager;

/**
 * 로그인 화면에서 메인 화면으로 연결
 * @author 김현진, 최범석
 *
 */
class Windows_Login extends JFrame{
	
//	컴포넌트를 배치할 영역을 JPanel로 구현
	private JPanel con = new JPanel();
	
	private JLabel logLabel = new JLabel("메신저 로그인", JLabel.CENTER); 
	private JTextField idFiled = new JTextField();//ID
	private JTextField pwFiled = new JPasswordField();	//비밀번호
	private JButton btLogin = new JButton("로그인");
	private JButton btJoin = new JButton("회원가입");
	private ObjectOutputStream objectOut;
	private ObjectInputStream objectIn;
	private Socket socket;
	
	public Windows_Login() {
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
			String ip = "127.0.0.1";
			String[] segments = ip.split("\\.");
			String serverIP = (Long.parseLong(segments[0])
								+ "." + Long.parseLong(segments[1]) 
								+ "."	+ Long.parseLong(segments[2])
								+ "." + Long.parseLong(segments[3]));
			socket = new Socket(serverIP,  Board.SUB_PORTNUMBER);
			System.out.println("socket : ["+socket+"]");
			objectIn = new ObjectInputStream(socket.getInputStream());
			System.out.println("objectIn : ["+objectIn+"]");
			objectOut = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("objectOut : ["+objectOut+"]");

		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("설정 오류");
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
			this.sendInfo();
			this.receiveInfo();
		});
		
		btJoin.addActionListener(e->{
			this.openJoin();
		});
	}
	
	
	public void openJoin() {
		try {
//		String kind = "회원가입";
//		objectOut.writeUTF(kind);
//		objectOut.flush();
//		System.out.println("objectOut.write : ["+kind+"]");//테스트코드
		
		JoinManager join = new JoinManager(this.socket, this.objectOut);
		} catch (Exception e1) {
			
		}
	}
	
	
	public void sendInfo() {
		try {
			String kind = "로그인";
			objectOut.writeUTF(kind);
			objectOut.flush();
			System.out.println("objectOut.write : ["+kind+"]");//테스트코드
			
			objectOut.writeUTF(idFiled.getText());
			objectOut.flush();
			System.out.println("objectOut.write : ["+idFiled.getText()+"]");//테스트코드
			
			objectOut.writeUTF(pwFiled.getText());
			objectOut.flush();
			System.out.println("objectOut.write : ["+pwFiled.getText()+"]");//테스트코드
			pwFiled.setText("");
			
		} catch (Exception e1) {
			
		}
	}
	
	
	private void receiveInfo() {
		try {
			String mes = objectIn.readUTF();
			System.out.println("수신메시지 : ["+mes+"]");	
			
			if(mes.equals("로그인 완료")) {
				JOptionPane.showMessageDialog(this, "로그인 완료");
				Messenger_m java_Messenger = new Messenger_m();
				this.dispose();
			}
			else if(mes.equals("결과없음")){
				JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 확인해 주세요.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("메시지 수신 오류");
		}
		
	}
	
}



/**
 * 메인 클래스
 * @author 김현진
 *
 */
public class Messenger_Main {
	public static void main(String[] args) {	
		Windows_Login login = new Windows_Login();
//		Messenger_m login = new Messenger_m();
	}
}














