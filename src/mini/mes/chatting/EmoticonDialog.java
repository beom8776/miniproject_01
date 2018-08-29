package mini.mes.chatting;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EmoticonDialog extends JDialog{
	
	private JPanel		panel = new JPanel();
	
	private JLabel		lion = new JLabel("라이언");		//라이언 이모티콘 라벨
	private JButton[]	jbt = new JButton[10];			//이모티콘 버튼 배열 10개 생성
	private String[]	jbtTitle = {"lion1","lion2","lion3","lion4","lion5",
			"mugi1","mugi2","mugi3","mugi4","mugi5"
	};
	
	private JLabel		mugi = new JLabel("무지");		//무지 이모티콘 라벨

	public void show(int x1, int y1) {
		display();
		event();
		
		this.setTitle("이모티콘 선택창");
		this.setSize(350, 250);
		int x = x1 - this.getWidth();
		int y = y1 - this.getHeight();
		this.setLocation(x, y);
		this.setResizable(false);
		
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
		mugi.setBounds(5, 100, 82, 30);
		
		panel.add(lion);
		panel.add(mugi);
		
		int x = 5;
		int x1 = 5;
		for( int i = 0; i < jbt.length; i++) {
			panel.add(jbt[i] = new JButton(jbtTitle[i]));
			
			if( i < 5) {
				jbt[i].setBounds(x, 35, 60, 60);
				x += 60;
			}else if( i >= 5) {
				jbt[i].setBounds(x1, 135, 60, 60);
				x1 += 60;
			}
		}
	}
	
	/**
	 * 이모티콘 이벤트 메소드
	 */
	public void event() {
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
}
