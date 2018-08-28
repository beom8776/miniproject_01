package mini.mes.chatting;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChattingGui extends JFrame{
	
	private JPanel		panel = new JPanel();
	
	//라벨,버튼 관련
	private JButton		profileBt = new JButton("profile");	//프로파일 버튼
	private JButton		noticeLb= new JButton("Notice");	//공지사항 라벨
	private JTextArea	area = new JTextArea();				//대화 출력창
	private JScrollPane	areascroll = new JScrollPane(area);	//대화 출력창 스크롤
	private JTextField	inputField = new JTextField();		//대화 입력창
	private JButton		sendBt = new JButton("send");		//입력대화 전송 버튼
	private JButton		emoticonBt = new JButton("emticon");	//이모티콘
	private JButton		fileSendBt = new JButton("fileSend");	//파일 전송 버튼
	
	//메뉴 관련
	private JMenuBar	bar = new JMenuBar();				//기능 구현용 메뉴바
	private JMenu		emoticon = new JMenu("이모티콘");		//이모티콘 메뉴
	private JMenu		filemanager = new JMenu("파일매니져");	//파일메니져 메뉴
	private JMenu		capture	= new JMenu("캡쳐");			//화면 캡쳐 메뉴
	private JMenu		info	= new JMenu("정보");			//버전과 종료
	private JMenu		noticemenu = new JMenu("공지사항");	//공지사항 메뉴
	
	private JMenuItem	emoticon1 = new JMenuItem("라이언");	//이모티콘 캐릭터 선택 1
	private JMenuItem	emoticon2 = new JMenuItem("무지");	//이모티콘 캐릭터 선택 2
	
	private JMenuItem	filesend = new JMenuItem("파일전송");	//파일 전송
	private JMenuItem	filerecive = new JMenuItem("받은파일");//받은 파일
	
	private JMenuItem	screencapture = new JMenuItem("화면캡쳐"); //보이는 화면 캡쳐
	private JMenuItem	fullcapture = new JMenuItem("전체캡쳐");	//모든 대화내용 캡쳐
	private JMenuItem	seecapture = new JMenuItem("캡쳐보기");	//캡쳐된 이미지 보기
	
	private JMenuItem	version = new JMenuItem("버전 정보");	//프로그램 버전
	private JMenuItem	exit	= new JMenuItem("종료");		//프로그램 종료
	
	/**
	 * 가칭 '공지사항'은 사용자에게 알려야 하는 내용을 대화창 상단에 노출.
	 * 공지사항은 시스템 운영자와 대화창을 개설한자가 작성(권한을 가진자)할 수 있다.
	 * '리스트'메뉴에서는 열람만 가능. 대화창 상단의 공지사항 버튼과 동일한 작동을 한다.
	 * '쓰기'메뉴와 '지우기'메뉴는 권한을 가진자만 실행할 수 있다.
	 * '설정'메뉴에서는 권한의 부여를 설정할 수 있다. 
	 */
	private JMenuItem	noticelist = new JMenuItem("리스트");	//공지사항 게시글 보기
	private JMenuItem	noticewrite= new JMenuItem("쓰기");	//공지사항 글쓰기
	private	JMenuItem	noticedel  = new JMenuItem("지우기");	//공지사항 지우기
	private JMenuItem	noticeset  = new JMenuItem("설정");	//공지사항 설정
	
	/**
	 * Dialog 인스턴스 생성.
	 */
	EmoticonDialog emoticonClick = new EmoticonDialog();
	
	/**
	 * 기본 생성자
	 */
	public ChattingGui() {
		display();
		event();
		menu();
		
		this.setTitle("306 Messenger");
		this.setSize(500, 730);
		this.setLocationByPlatform(true);
		this.setResizable(false);
		
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
		panel.add(areascroll);
		areascroll.setBounds(12, 70, 460, 500);
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
		bar.add(noticemenu);
		
		emoticon.add(emoticon1);
		emoticon.add(emoticon2);
		
		filemanager.add(filesend);
		filemanager.add(filerecive);
		
		capture.add(screencapture);
		capture.add(fullcapture);
		capture.add(seecapture);
		
		info.add(version);
		info.add(exit);
		
		noticemenu.add(noticelist);
		noticemenu.add(noticewrite);
		noticemenu.add(noticedel);
		noticemenu.add(noticeset);
		
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
		
		emoticonBt.addActionListener(e->{
			if( emoticonClick.isShowing() == false) {
				int x = this.getX();
				int y = this.getY() + this.getHeight();
				emoticonClick.show(x, y);
			}
		});
		
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