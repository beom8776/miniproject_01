package mini.mes.chatting;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

import mini.mes.file.FileManager;

/**
 * 채팅방 클래스
 * @author 허원석, 최범석
 */
public class ChattingGui extends JFrame{
	
	
	/**
	 * 변수 생성
	 */
	private JPanel		panel = new JPanel();
	
	private JButton		profileBt = new JButton("profile");		//프로필 버튼
	private JLabel			noticeLb= new JLabel("Notice");			//공지사항 라벨
	private JTextArea	textArea = new JTextArea();					//대화 출력창
	private JTextField	inputField = new JTextField();				//대화 입력창
	private JButton		sendBt = new JButton("send");				//입력대화 전송 버튼
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
	
	private boolean flag = true;
	private String sendText;
	private StringBuffer buf;
	
	//Setter & Getter
	public String getSendText() {
		return sendText;
	}
	public void setSendText(String text) {
		this.sendText = text;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	
/**
 * 파일 관리 매니저 인스턴스 생성
 */
	FileManager file = new FileManager("chatDB.db");
	
	
	/**
	 * 생성자
	 */
	public ChattingGui() {
		display();
		event();
		menu();
		
		//창 옵션 설정
		this.setTitle("Messenger");
		this.setSize(500, 710);
		this.setLocationByPlatform(true);
		this.setResizable(true);
		
		this.setVisible(true);

		//대화 내용 불러오기
		buf = file.fileInput();
		textArea.setText(buf.toString());
		
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
			public void windowClosing(WindowEvent e) {
				int exitConfirm = JOptionPane.showConfirmDialog(panel,
						"프로그램을 종료하겠습니까?",
						"종료 확인",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				if ( exitConfirm == 0 ) {
					buf = new StringBuffer(textArea.getText());
					file.fileOutput(buf);
					System.exit(0);
				}
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
		
		
		/**
		 * 채팅 메시지 입력 버튼 이벤트
		 */
		//전송버튼누를때
		sendBt.addActionListener(e->{
			inputChat();
		});
		//엔터단축키설정
		KeyListener enter = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					inputChat();
			}
		};
		inputField.addKeyListener(enter);
	}
		
	
	/**
	 * 채팅 입력 메소드
	 */
	public void inputChat() {
		this.setFlag(true);
		String text = inputField.getText();
		this.myChat(text);
		this.setSendText(text);
		inputField.setText("");
	}

	
	/**
	 * 나의 채팅을 화면에 출력하는 메소드
	 * @param 내가 보낸 메시지
	 */
	public void myChat(String text) {
		textArea.append("[나] : " + text + "\n");
	}
	
	
	/**
	 * 상대의 채팅을 화면에 출력하는 메소드
	 * @param 상대가 보낸 메시지
	 */
	public void yourChat(String text) {
		textArea.append("[상대] : " + text + "\n");
	}



	/**
	 * 테스트용 메인 메소드
	 */
	 public static void main(String[] args) {
		
		 ChattingGui chat = new ChattingGui();
		
	}



}
