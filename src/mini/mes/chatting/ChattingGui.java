package mini.mes.chatting;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mini.mes.net.Server;


public class ChattingGui extends JFrame{
	
	private Server server = new Server();
	
	private JPanel		panel = new JPanel();
	
	private JButton		profileBt = new JButton("profile");		//프로필 버튼
	private JLabel			noticeLb= new JLabel("Notice");			//공지사항 라벨
	private JTextArea	textArea = new JTextArea();					//대화 출력창
	private JTextField	inputField = new JTextField();				//대화 입력창
	private JButton		sendBt = new JButton("send");				//입력대화 전송 버튼
	private JButton		emoticonBt = new JButton("emticon");	//이모티콘
	private JButton		fileSendBt = new JButton("fileSend");	//파일 전송 버튼
	
	
	public void inputMessage() {
		String message = inputField.getText();
		inputField.setText("");
		textArea.append(message+"\n");
	}
	
	public ChattingGui() {
		display();
		event();
		
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
	 *이벤트 메소드
	 */
	public void event() {
		sendBt.addActionListener(e->{
			this.inputMessage();
		});
	}
		
	/**
	 * 테스트용 메인 메소드
	 */
	 public static void main(String[] args) {
		
		 ChattingGui chat = new ChattingGui();
		 
	}
	
}
	


