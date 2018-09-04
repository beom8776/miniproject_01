package mini.mes.chatting;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NoticeDialog extends JDialog{
	
	private JPanel pane 	= new JPanel();		// 공지사항 dialog
	private JPanel pane1 	= new JPanel();		// 공지사항 글쓰기 dialog
	
	private JLabel 		lb1		= new JLabel("제목 :");
	private JLabel 		lb2		= new JLabel("내용 :");
	
	
	private JTextField	titlefield 	= new JTextField();			//공지 제목 입력
	private JTextArea	textArea	= new JTextArea(); 			//공지 내용 입력
	private JScrollPane areaScroll	= new JScrollPane(textArea);//공지 내용 스크롤	
	
	private JButton		writebt	 	= new JButton("쓰기");	//쓰기 dialog 호출
	private JButton		deletebt	= new JButton("삭제");	
	
	private JButton		savebt 		= new JButton("저장");
	private JButton		erasebt		= new JButton("지우기");
	private JButton		cancelbt 	= new JButton("취소");
	
	private String[]	text		= new String[]{
			"공지사항이 있습니다",
			"9월 7일에 프로젝트 발표가 있습니다. 모두 열심히 노력하여 멋짓 결과를 이루어냅시다!!"};		//공지사항을 담을 String배열
	
	
	ChattingGui gui;
	
	public NoticeDialog(ChattingGui gui) {
		this.gui = gui;
	}
	
	
	/**
	 * 공지사항 dialog 설정 메소드 
	 */
	public void showNotice() {
		display();
		event();
		
		setTitle("공지사항");
		this.setSize(450, 300);
		this.setLocationByPlatform(true);
		this.setResizable(false);
		this.setModal(true);
		
		this.setVisible(true);
	}
	

	/**
	 * 다이얼로그창 버튼 등 배치
	 */
	public void display() {
		this.setContentPane(pane);
		pane.setLayout(null);
		
		lb1.setBounds(12, 10, 50, 30);
		pane.add(lb1);
		titlefield.setBounds(74, 10, 348, 30);
		titlefield.setEditable(false);
		pane.add(titlefield);
		lb2.setBounds(12, 45, 50, 30);
		pane.add(lb2);
		areaScroll.setBounds(74, 45, 348, 170);
		textArea.setEditable(false);
		pane.add(areaScroll);
		writebt.setBounds(292, 225, 60, 26);
		pane.add(writebt);
		deletebt.setBounds(362, 225, 60, 26);
		pane.add(deletebt);
		
		titlefield.setText(text[0]);
		textArea.setText(text[1]);
		textArea.setLineWrap(true);
	}
	
	/**
	 * 공지사항을 쓰려고 할때 변경되는 다이얼로그창
	 */
	public void display1() {
		this.setContentPane(pane1);
		pane1.setLayout(null);
		
		lb1.setBounds(12, 10, 50, 30);
		pane1.add(lb1);
		titlefield.setBounds(74, 10, 348, 30);
		titlefield.setEditable(true);
		pane1.add(titlefield);
		lb2.setBounds(12, 45, 50, 30);
		pane1.add(lb2);
		areaScroll.setBounds(74, 45, 348, 170);
		textArea.setEditable(true);
		pane1.add(areaScroll);
		savebt.setBounds(220, 225, 60, 26);
		pane1.add(savebt);
		erasebt.setBounds(292, 225, 60, 26);
		pane1.add(erasebt);
		cancelbt.setBounds(362, 225, 60, 26);
		pane1.add(cancelbt);
	}

	/**
	 * 이벤트 메소드
	 */
	public void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		writebt.addActionListener(e->{
			this.setVisible(false);
			display1();
			this.setVisible(true);
		});
		
		deletebt.addActionListener(e->{
			
		});
		
		savebt.addActionListener(e->{
			text[0]=titlefield.getText();
			text[1]=textArea.getText();
		});
		
		erasebt.addActionListener(e->{
			titlefield.setText("");
			textArea.setText("");
		});
		
		cancelbt.addActionListener(e->{
			dispose();
		});
		
		
		
	}
}
