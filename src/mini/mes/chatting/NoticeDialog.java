package mini.mes.chatting;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NoticeDialog extends JDialog{
	
	private JPanel pane 	= new JPanel();		// 공지사항 dialog
	private JPanel pane1 	= new JPanel();		// 공지사항 글쓰기 dialog
	
	private JLabel 		lb1		= new JLabel("제목");
	private JLabel 		lb11 	= new JLabel("제목내용");
	private JLabel 		lb2		= new JLabel("내용");
	private JLabel 		lb21 	= new JLabel("내용내용");
	private JLabel 		lb3 	= new JLabel("제목");
	private JLabel 		lb4 	= new JLabel("내용");
	
	private JLabel		titlelb = new JLabel();		//공지 제목 읽기
	private JLabel		textlb	= new JLabel();		//공지 내용 읽기
	
	private JTextField	titelfield 	= new JTextField();		//공지 제목 입력
	private JTextArea	textArea	= new JTextArea(); 		//공지 내용 입력	
	
	private JButton		callwrite 	= new JButton("쓰기");	//쓰기 dialog 호출
	private JButton		callrevise 	= new JButton("수정");	//쓰기 dialog 호출
	private JButton		deletebt	= new JButton("지우기");	
	
	private JButton		writebt 	= new JButton("쓰기");
	private JButton		cancelbt 	= new JButton("취소");
	
	ChattingGui gui;
	
	public NoticeDialog(ChattingGui gui) {
		this.gui = gui;
	}
	
	/**
	 * 쓰기/수정 dialog 메소드
	 */
	public void showWrite() {
		displayList();
		event();
	}
	
	/**
	 * 공지사항 dialog 메소드 
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
//	showList 메소드에 포함된 메소드
	public void displayList() {
		this.setContentPane(pane1);
	}
	

// show 메소드에 포함된 메소드	
	public void display() {
		this.setContentPane(pane);
		pane.setLayout(null);
		
		lb1.setBounds(12, 10, 50, 30);
		pane.add(lb1);
		lb11.setBounds(74, 10, 348, 30);
		pane.add(lb11);
		lb2.setBounds(12, 45, 50, 30);
		pane.add(lb2);
		lb21.setBounds(74, 45, 348, 170);
		pane.add(lb21);
		callwrite.setBounds(220, 225, 60, 26);
		pane.add(callwrite);
		callrevise.setBounds(292, 225, 60, 26);
		pane.add(callrevise);
		deletebt.setBounds(362, 225, 60, 26);
		pane.add(deletebt);
	}

	/**
	 * 이벤트 메소드
	 */
	public void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
	}
}
