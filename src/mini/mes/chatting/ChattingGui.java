package mini.mes.chatting;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
import mini.mes.file.FileManager;
import mini.mes.file.ReceiveClient;
import mini.mes.file.SendClient;
import mini.mes.net.Server;
 
/**
 * 채팅방 클래스
 * @author 허원석, 최범석
 */
public class ChattingGui extends JFrame{
	
	
	/**
	 * 파일 관리 매니저 인스턴스 생성
	 */
	FileManager file = new FileManager("chatDB.db");
		
		
	/**
	 * 변수 생성
	 */
	//라벨,버튼 관련
	private JPanel		panel 		= new JPanel();
  
  	private JButton		profilebt	= new JButton(new ImageIcon("files/image/profil.jpg"));		//프로파일 버튼
  	private JButton		addtalker	= new JButton(new ImageIcon("files/image/addtalker.jpg"));	//대화상대 추가  	
	private JButton		noticebt	= new JButton("공지사항");		//공지사항 라벨
	private JTextArea	area 		= new JTextArea();			//대화 출력창
	private JScrollPane	areascroll 	= new JScrollPane(area);	//대화 출력창 스크롤
	private JTextField	inputField 	= new JTextField();			//대화 입력창
	private JButton		sendBt 		= new JButton(new ImageIcon("files/image/sendbt.jpg"));		//입력대화 전송 버튼
	private JButton		emoticonBt 	= new JButton(new ImageIcon("files/image/emoticon.jpg"));		//이모티콘
	private JButton		fileSendBt 	= new JButton(new ImageIcon("files/image/filesend.jpg"));		//파일 전송 버튼
	
	//메뉴 관련
	private JMenuBar	bar 			= new JMenuBar();			//기능 구현용 메뉴바
	private JMenu		emoticon 		= new JMenu("이모티콘");		//이모티콘 메뉴
	private JMenu		filemanager 	= new JMenu("파일매니져");		//파일메니져 메뉴
	private JMenu		capture			= new JMenu("캡쳐");			//화면 캡쳐 메뉴
	private JMenu		info			= new JMenu("정보");			//버전과 종료
	private JMenu		emoticon1 		= new JMenu("라이언");		//이모티콘 캐릭터 선택 1
	private JMenu		emoticon2 		= new JMenu("무지");			//이모티콘 캐릭터 선택 2
	private JMenuItem[]	emoticonItem	= new JMenuItem[10];		//이모티콘 메뉴의 하위 메뉴 10개
	private JMenuItem	filesend 		= new JMenuItem("파일전송");	//파일 전송
	private JMenuItem	filerecive 		= new JMenuItem("받은파일");	//받은 파일
	private JMenuItem	screencapture 	= new JMenuItem("화면캡쳐"); 	//보이는 화면 캡쳐
	private JMenuItem	fullcapture 	= new JMenuItem("전체캡쳐");	//모든 대화내용 캡쳐
	private JMenuItem	seecapture 		= new JMenuItem("캡쳐보기");	//캡쳐된 이미지 보기
	private JMenuItem	version 		= new JMenuItem("버전 정보");	//프로그램 버전
	private JMenuItem	exit			= new JMenuItem("종료");		//프로그램 종료

	private ImageIcon[] icon = new ImageIcon[10];					//이모티콘 메뉴의 하위메뉴에 들어가는 이미지배열
	private String[]	emoticonTitle = {"lion1","lion2","lion3",
			"lion4","lion5","mugi1",
			"mugi2","mugi3","mugi4","sinanda"};						//이모티콘 메뉴의 하위메뉴 타이틀
	private Font		f1 = new Font("", Font.BOLD, 10);	//10사이즈 글씨 폰트
	private Font		f2 = new Font("", Font.BOLD, 20);	//20사이즈 글씨 폰트
	
	
	
	
	//대화상대 추가를 위한 변수
	private int 		totalfriends;	//사용자의 전체 친구 숫자
	private int 		talkers;		//사용자와 대화중인 친구 숫자
	private String[] 	friendname;		//친구 이름 배열
	private String[] 	friendid;		//친구 아이디 배열
	
	/**
	 * Dialog 인스턴스 선언
	 */
	EmoticonDialog	emoticonClick;	//이모티콘다이얼로그
	NoticeDialog	noticeClick;	//공지사항다이얼로그
	AddTalkerDialog	addTalkerClick; //대화상대추가다이얼로그
	DialogueCapture	captureClick;	//화면캡쳐
	
	//채팅 메시지 관련
	private boolean flag = true;
	private String sendText;
	private StringBuffer buf;
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
	
	//파일 관련
	private File sendFile;
	private static int port = 50000;
	
	/**
	 * 생성자
	 */
	public ChattingGui() {
		display();
		imageCut();
		menuIcon();
		menu();
		event();
		
		this.setTitle("306 Messenger");
		this.setSize(500, 730);
		//창 옵션 설정
		this.setLocationByPlatform(true);
		this.setResizable(false);
		
//		this.setVisible(true);

		//대화 내용 불러오기
		buf = file.fileInput();
		area.setText(buf.toString());
		
	}
	
  
	/**
	 *화면 배치 메소드
	 */
	public void display() {
		this.setContentPane(panel);
		panel.setLayout(null);
			
		profilebt.setBounds(12, 10, 50, 50);
		profilebt.setFont(f1);
		panel.add(profilebt);
		addtalker.setBounds(71, 10, 50, 50);
		panel.add(addtalker);
		noticebt.setBounds(130, 10, 343, 50);
		panel.add(noticebt);
		areascroll.setBounds(12, 70, 460, 500);
		panel.add(areascroll);
		inputField.setBounds(12, 580, 390, 28);
		panel.add(inputField);
		sendBt.setBounds(414, 579, 58, 28);
		panel.add(sendBt);
		emoticonBt.setBounds(12, 618, 57, 40);
		panel.add(emoticonBt);
		fileSendBt.setBounds(81, 618, 57, 40);
		panel.add(fileSendBt);
		
		area.setEditable(false);
	}
	/**
	 * 이모티콘 메뉴의 하위메뉴에 이미지와 타이틀 삽입하여 생성   
	 */
	public void menuIcon() {
		Image img;
		Image img1;
		ImageIcon newIcon;
		for( int i = 0; i < emoticonItem.length; i++) {
			img = icon[i].getImage();
			img1 = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);		//기존 이미지를 축소
			newIcon = new ImageIcon(img1);									//축소한 이미지를 새로운 ImageIcon 인스턴스 생성
			emoticonItem[i] = new JMenuItem(emoticonTitle[i], newIcon);
		}
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
		emoticon1.add(emoticonItem[0]);	emoticon1.add(emoticonItem[1]);
		emoticon1.add(emoticonItem[2]);	emoticon1.add(emoticonItem[3]);
		emoticon1.add(emoticonItem[4]);
		emoticon2.add(emoticonItem[5]);	emoticon2.add(emoticonItem[6]);
		emoticon2.add(emoticonItem[7]);	emoticon2.add(emoticonItem[8]);
		emoticon2.add(emoticonItem[9]);
		
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
					buf = new StringBuffer(area.getText());
					file.fileOutput(buf);
					System.exit(0);
				}
			}
		};
		this.addWindowListener(w);
		
		/**
		 * 대화창 상단, 대화상대 추가 버튼을 누르면 친구목록다이얼로그 생성
		 */
		addtalker.addActionListener(e->{
			addTalkerClick = new AddTalkerDialog(this);
			addTalkerClick.showDialog();
		});
		
		/**
		 * 대화창 상단, 공지사항 버튼 누르면 공지사항다이얼로그 생성
		 */
		noticebt.addActionListener(e->{
			noticeClick = new NoticeDialog(this);
			if( noticeClick.isShowing() == false) {
				noticeClick.showNotice();
			}
		});
		
		/**
		 * 대화창 하단, 이모티콘 버튼을 누르면 이모티콘다이얼로그 생성
		 */
		emoticonBt.addActionListener(e->{
			emoticonClick = new EmoticonDialog(this);
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
		
		
		/**
		 * 파일 전송 버튼 이벤트
		 */
		fileSendBt.addActionListener(e->{
			SendClient sc = new SendClient();
			ReceiveClient rc = new ReceiveClient();
		});
		/**
		 * 캡쳐 메뉴의 화면캡쳐 이벤트
		 */
		screencapture.addActionListener(e->{
			captureClick = new DialogueCapture(this);
			
			int x = this.getX()+21;
			int y = this.getY()+125;
			int x1 = area.getWidth();
			int y1 = area.getHeight();
			
			captureClick.areaCapture(x, y, x1, y1);
		});
		
		/**
		 * 캡쳐 메뉴의 캡쳐보기 이벤트 
		 */
		seecapture.addActionListener(e->{
			captureClick = new DialogueCapture(this);
			
			captureClick.openCapture();
		});
		
		/**
		 * 이모티콘 메뉴의 하위메뉴 선택 이벤트
		 */
		ActionListener listener = e->{
			String emoticonsend = "";
			
			for ( int i = 0; i < 10; i++) {
				if ( e.getSource() == emoticonItem[i]) {
						emoticonsend = emoticonItem[i].getText();
						System.out.println(emoticonsend);
						emoticonRecive("["+emoticonsend+"]");
				}
			}
		};
		for( int i = 0; i < 10; i++) {
			emoticonItem[i].addActionListener(listener);
		}
	}
	
	
	/**
	 * 채팅 입력 메소드
	 */
	public void inputChat() {
		this.setFlag(true);
		String text = inputField.getText();
		this.setSendText(text);
		inputField.setText("");
	}

	
	/**
	 * 나의 채팅을 화면에 출력하는 메소드
	 * @param 내가 보낸 메시지
	 */
	public void myChat(String text) {
		area.append("[나] : " + text + "\n");
	}
	
	
	/**
	 * 상대의 채팅을 화면에 출력하는 메소드
	 * @param 상대가 보낸 메시지
	 */
	public void yourChat(String text) {
		area.append("[상대] : " + text + "\n");
	}


	/**
	 * 상대의 채팅을 화면에 출력하는 메소드
	 * @param 상대가 보낸 메시지
	 */
	public void groupChat(String text) {
		area.append("[그룹] : " + text + "\n");
	}
	
	/**
	 * EmoticonDialog 클래스에서 선택된 이모티콘을 String으로 받는 메소드 
	 * @param emoticonsend
	 */
	public void emoticonRecive(String emoticonsend) {
		inputField.setText(inputField.getText() + emoticonsend);
	}
	
	/**
	 * 현재 대화상대 추가 가능한 인원을 리턴해준다.
	 * @param totalfriends
	 * @param talkers
	 * @return
	 */
	public int addTalkerCount() {
		return totalfriends - talkers;
	}
	
	/**
	 *AddTalkDialog에서 사용할 대화상대의 이름과 아이디를 더하여 문자열 배열로 생성   
	 */
	public String[] addTalkers() {
		String[] addTalkerNameId = new String[addTalkerCount()];
		for( int i = 0; i < addTalkerNameId.length; i++) {
			addTalkerNameId[i] = friendname[i] + " " + friendid[i];
		}
		return addTalkerNameId;
	}
	/**
	 * 이모티콘 메뉴의 하위메뉴에 넣을 이미티콘 이미지를 잘라 배열에 넣는다
	 */
	public void imageCut() {
		BufferedImage buf = null;
		try {
			buf = ImageIO.read(new File("files/image/emoticon10.jpg"));
			int w = buf.getWidth() / 10;
			int y = buf.getHeight();
			BufferedImage[] arr = new BufferedImage[10];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = buf.getSubimage(i*w, 0, w, y);
				icon[i] = new ImageIcon(arr[i]);
			}
			
		}catch(Exception err) {
			err.printStackTrace();
		}
	}

	/**
	 * 테스트용 메인 메소드
	 */
//	 public static void main(String[] args) {
//		
//		 ChattingGui chat = new ChattingGui();
//		 chat.setVisible(true);
//		
//	}



}
