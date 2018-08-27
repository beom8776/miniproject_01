package mini.mes.chatting;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChattingGui extends JFrame{
	
	private JPanel		panel = new JPanel();
	
	private JButton		profileBt = new JButton("profile");	//프로파일 버튼
	private JLabel		noticeLb= new JLabel("Notice");		//공지사항 라벨
	private JTextArea	textArea = new JTextArea();			//대화 출력창
	private JTextField	inputField = new JTextField();		//대화 입력창
	private JButton		sendBt = new JButton("send");		//입력대화 전송 버튼
	private JButton		emoticonBt = new JButton("emticon");	//이모티콘
	private JButton		fileSendBt = new JButton("fileSend");	//파일 전송 버튼
	
	private JMenuBar	bar = new JMenuBar();		//기능 구현용 메뉴바
	private JMenu		emoticon = new JMenu("이모티콘");		//이모티콘 메뉴
	private JMenu		filemanager = new JMenu("파일매니져");	//파일메니져 메뉴
	private JMenu		capture	= new JMenu("캡쳐");		//화면 캡쳐 메뉴
	private JMenu		info	= new JMenu("정보");		//버전과 종료
	
	private JMenuItem	emoticon1 = new JMenuItem("라이언");	//이모티콘 캐릭터 선택 1
	private JMenuItem	emoticon2 = new JMenuItem("무지");	//이모티콘 캐릭터 선택 2
	
	private JMenuItem	filesend = new JMenuItem("파일전송");	//파일 전송
	private JMenuItem	filerecive = new JMenuItem("받은파일");//받은 파일
	
	private JMenuItem	screencapture = new JMenuItem("화면캡쳐"); //보이는 화면 캡쳐
	private JMenuItem	fullcapture = new JMenuItem("전체캡쳐");	//모든 대화내용 캡쳐
	private JMenuItem	seecapture = new JMenuItem("캡쳐보기");	//캡쳐된 이미지 보기
	
	private JMenuItem	version = new JMenuItem("버전 정보");	//프로그램 버전
	private JMenuItem	exit	= new JMenuItem("종료");		//프로그램 종료
	
	public ChattingGui() {
		display();
		event();
		menu();
		
		this.setTitle("Messenger");
		this.setSize(500, 710);
		this.setLocationByPlatform(true);
		this.setResizable(true);
		
		this.setVisible(true);
	}
	
	/**
	 *화면 배치 메소드
	 */
	public void display() {
		this.setContentPane(panel);
		panel.setLayout(null);
			
		panel.add(profileBt);
		profileBt.setBounds(12, 10, 50, 50);
		panel.add(noticeLb);
		noticeLb.setBounds(74, 10, 398, 50);
		panel.add(textArea);
		textArea.setBounds(12, 70, 460, 500);
		panel.add(inputField);
		inputField.setBounds(12, 580, 390, 28);
		panel.add(sendBt);
		sendBt.setBounds(414, 579, 58, 28);
		panel.add(emoticonBt);
		emoticonBt.setBounds(12, 618, 57, 40);
		panel.add(fileSendBt);
		fileSendBt.setBounds(81, 618, 57, 40);
						
	}
	/**
	 * 메뉴 메소드
	 */
	public void menu() {
		this.setJMenuBar(bar);
		
		bar.add(emoticon);
		bar.add(filemanager);
		bar.add(capture);
		bar.add(info);
		
		emoticon.add(emoticon1);
		emoticon.add(emoticon2);
		
		filemanager.add(filesend);
		filemanager.add(filerecive);
		
		capture.add(screencapture);
		capture.add(fullcapture);
		capture.add(seecapture);
		
		info.add(version);
		info.add(exit);
		
	}
	/**
	 *이벤트 메소드
	 */
	public void event() {
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		/**
		 * 대화창 x버튼 프로그램 종료 Dialog 
		 */
		WindowListener w = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				int exitConfirm = JOptionPane.showConfirmDialog(panel,
						"프로그램을 종료하겠습니까?",
						"종료 확인",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				if ( exitConfirm == 0 ) System.exit(0);
			}
		};
		this.addWindowListener(w);
		
		
		/**
		 * 정보메뉴 프로그램 버젼 출력 Dialog
		 */
		version.addActionListener(e->{
			JOptionPane.showMessageDialog(panel, "메신져 버젼 : v0.1");
		});
		/**
		 * 정보메뉴 프로그램 종료
		 */
		exit.addActionListener(e->{
			System.exit(0);
		});
		
	}
		
}
