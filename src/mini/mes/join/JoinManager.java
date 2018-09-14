package mini.mes.join;

import java.awt.event.*;
import mini.mes.join.Member;
import java.io.*;
import java.net.Socket;

import javax.swing.*;

/**
 * 회원 가입 기능을 띄우는 데 가장 밑바탕이 되는 class
 * @author 강정호
 *
 */

class JoinWindow extends JFrame {
	private JoinField jf = new JoinField();
	private CalendarBox cb = new CalendarBox();
	
	JPanel con = new JPanel();
	
	private JLabel lb0 = new JLabel("ID");
	private JLabel lb1 = new JLabel("비밀 번호");
	private JLabel lb2 = new JLabel("이름");
	
	private JLabel lb010 = new JLabel("010 -");
	private JLabel lb3 = new JLabel("전화 번호");
	private JLabel lbDash = new JLabel("-");
	
	
	private JLabel lb4 = new JLabel("생년월일");
	private JLabel lbYear = new JLabel("년");
	private JLabel lbMonth = new JLabel("월");
	private JLabel lbDate = new JLabel("일");
	
	private JButton btn0 = new JButton("가　입");
	private JButton btn1 = new JButton("지우기");
	
	public void display() {
		con.setLayout(null);
		setContentPane(con);

		//구분 딱지

		lb0.setBounds(80,60,80,20);
		con.add(lb0);

		lb1.setBounds(80,90,80,20);
		con.add(lb1);

		lb2.setBounds(80, 120, 80, 20);
		con.add(lb2);

		lb3.setBounds(80, 150, 80, 20);
		con.add(lb3);

		lb4.setBounds(80, 180, 80, 20);
		con.add(lb4);

		//기본 입력란
		jf.tfId.setBounds(160,60,150,20);
		con.add(jf.tfId);
		jf.pwf.setBounds(160,90,150,20);
		con.add(jf.pwf);
		jf.tfName.setBounds(160,120,150,20);
		con.add(jf.tfName);
		
		//전화 번호 입력란
		lb010.setBounds(160, 150, 48, 20);
		con.add(lb010);
		lbDash.setBounds(249, 150, 15, 20);
		con.add(lbDash);
		jf.tfPhone2.setBounds(195,150,47,20);
		con.add(jf.tfPhone2);
		jf.tfPhone3.setBounds(263, 150, 47, 21);
		con.add(jf.tfPhone3);
		
		//생년월일 선택 창 추가
		cb.addNumbers();
		cb.cbYear.setBounds(160, 180, 55, 20);
		con.add(cb.cbYear);
		cb.cbMonth.setBounds(240, 180, 40, 20);
		con.add(cb.cbMonth);
		cb.cbDate.setBounds(305, 180, 40, 20);
		con.add(cb.cbDate);
		
		//선택 창 바로 뒤의 년, 월, 일 글자
		lbYear.setBounds(220, 180, 15, 20);
		lbMonth.setBounds(285, 180, 15, 20);
		lbDate.setBounds(350, 180, 15, 20);
		
		con.add(lbYear);
		con.add(lbMonth);
		con.add(lbDate);
		
		//단추 - 가입하느냐 내용을 전부 지우느냐
		btn0.setBounds(100,240,80,20);
		con.add(btn0);
		btn1.setBounds(210,240,80,20);
		con.add(btn1);
	}
	
	
	public void event() {
		//나가기 Event
		WindowListener listen = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

				int result = JOptionPane.showConfirmDialog(
						con, "취소하시겠습니까?", "경고",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);

				if(result == 0)
					dispose();
				else if(result == 1)
					return;
			}
		};
		this.addWindowListener(listen);
		
		//회원 가입 ButtonButton Event
		btn0.addActionListener(e -> {
			Member mb = new Member();
			boolean flag = false;
			cb.event();
			if(jf.event() == true) {
				dispose();
//				System.out.println();
//				Socket socket = null;
//				JoinMemberManager jmm = new JoinMemberManager(socket);
//				jmm.create();
			}
			
			mb.setId(jf.tfId.getText());
			mb.setPw(jf.pwf.getText());
			mb.setName(jf.tfName.getText());
			mb.setPhone(jf.appendNumber());
			mb.setBirth(cb.appendBirth());
			System.out.println(mb.toString());
			
			
		});
		
		//지우기 Button Event(쓰여진 내용 모두 지우기)
		btn1.addActionListener(e -> {
			jf.tfId.setText("");
			jf.pwf.setText("");
			jf.tfName.setText("");
			jf.tfPhone2.setText("");
			jf.tfPhone3.setText("");
		});
		
		//전화번호 2번째 입력란은 숫자 4개까지만 입력
		jf.tfPhone2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				JTextField tf = (JTextField) e.getSource();
				if(tf.getText().length() >= 4)
					e.consume();//5번째 글자부터는 소모시켜라(화면에서 지워라 또는 없애라).
			}
		});
		
		//전화번호 3번째 입력란은 숫자 4개까지만 입력
		jf.tfPhone3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				JTextField tf = (JTextField) e.getSource();
				if(tf.getText().length() >= 4)
					e.consume();//5번째 글자부터는 소모시켜라(화면에서 지워라 또는 없애라).
			}
		});
	}
	
	public JoinWindow() {
		this.display();
		this.event();
		this.setTitle("회원 가입을 위해 빈칸을 채워 주세요.");
		this.setSize(450,600);
		this.setLocation(200, 200);
		this.setResizable(false);
		this.setVisible(true);
	}
	
}

public class JoinManager {
	public static void main(String[] args) {
		JoinWindow jw = new JoinWindow();
	}
}