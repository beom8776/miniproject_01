package mini.mes.chatting;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mini.mes.chatting.ChattingGui;

public class EmoticonDialog extends JDialog{
	
	private JPanel		panel = new JPanel();
	
	private JLabel		lion = new JLabel("라이언");		//라이언 이모티콘 라벨
	private JLabel		mugi = new JLabel("무지");		//무지 이모티콘 라벨
	
	private JButton[]	jbt = new JButton[10];			//이모티콘 버튼 배열 10개 생성
	private String[]	jbtTitle = {"lion1","lion2","lion3","lion4","lion5",
			"mugi1","mugi2","mugi3","mugi4","mugi5"};
	
	private ImageIcon[] icon = new ImageIcon[10];
	
	/**
	 * ChattingGui 클래스 send 객체를 선언
	 */
	ChattingGui send;
	/**
	 * ChattingGui 클래스를 연결하는 생성자
	 * @param send
	 */
	public EmoticonDialog(ChattingGui send) {
		this.send = send;
	}	

	/**
	 * 이모티콘 dialog를 화면에 생성하는 메소드
	 * @param x1 -채팅창의 width를 넘겨준다
	 * @param y1 -채팅창의 height를 넘겨준다
	 */
	public void show(int x1, int y1) {
		emoticonImage();
		display();
		event();
		
		this.setTitle("이모티콘 선택창");
		this.setSize(500, 330);
		int x = x1 - this.getWidth();
		int y = y1 - this.getHeight();
		this.setLocation(x, y);
		this.setResizable(false);
		this.setModal(true);
		
		this.setVisible(true);
	}

	/**
	 * 이모티콘 다이얼로그 화면 구현 메소드
	 * 라이언과 무지, 두가지 캐릭터이며,
	 * 각각 5개씩 이모티콘 사용가능
	 */
	public void display() {
		this.setContentPane(panel);
		panel.setLayout(null);

		lion.setBounds(5, 0, 82, 30);
		mugi.setBounds(5, 125, 82, 70);
		
		panel.add(lion);
		panel.add(mugi);
		
		int x = 5;
		int x1 = 5;
		System.out.println("버튼 배열 : "+jbt.length);
		System.out.println("아이콘 배열 : "+icon.length);
		for( int i = 0; i < jbt.length; i++) {
			panel.add(jbt[i] = new JButton(jbtTitle[i]));
			if( i < 5) {
				jbt[i].setBounds(x, 35, 92, 92);
				x += 92;
			}else if( i >= 5) {
				jbt[i].setBounds(x1, 175, 92, 92);
				x1 += 92;
			}
			jbt[i].setIcon(icon[i]);		
		}
	}
	
	/**
	 * 이모티콘 이벤트 메소드
	 */
	public void event() {
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
	
		ActionListener listener = e->{
			String emoticonsend = "";
			
			for ( int i = 0; i < 10; i++) {
				if ( e.getSource() == jbt[i]) {
					if( i < 5) {
						emoticonsend = "[lion"+i+"]";
						System.out.println(emoticonsend);
						send.emoticonRecive(emoticonsend);
					}else if( i >= 5) {
						emoticonsend = "[muji"+i+"]";
						System.out.println(emoticonsend);
						send.emoticonRecive(emoticonsend);
					}
				}
			}
		};
		for( int i = 0; i < 10; i++) {
			jbt[i].addActionListener(listener);
		}
		
	}
	/**
	 * 이모티콘 이미지를 배열로 자르는 메소드
	 * 1000x100사이즈 이미지를 BufferedImage arr 배열로 나눠 담고,
	 * ImageIcon icon 배열에 넘긴다.
	 */
	public void emoticonImage() {
		BufferedImage buf = null;
		try {
			buf = ImageIO.read(new File("files/image/emoticon10.jpg"));
			int w = buf.getWidth() / 10;
			int y = buf.getHeight();
			BufferedImage[] arr = new BufferedImage[10];
			System.out.println("버퍼 배열 : "+arr.length);
			for (int i = 0; i < arr.length; i++) {
				arr[i] = buf.getSubimage(i*w, 0, w, y);
				icon[i] = new ImageIcon(arr[i]);
			}
			
		}catch(Exception err) {
			err.printStackTrace();
		}finally {
			
		}
	}		
}
