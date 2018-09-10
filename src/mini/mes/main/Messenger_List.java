package mini.mes.main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * 친구목록 Tab
 * @author 김현진
 *
 */
class Messenger_List extends JPanel{
	
	
	private JPanel frList = new JPanel();
	private JPanel friend1 = new JPanel();
	
//	private ImageIcon fricon1 = new ImageIcon("D:\\Java\\workspace\\Image_group\\NoIMG.jpg");
//	private JLabel frpictur1 = new JLabel(fricon1);
//	private JLabel frname1 = new JLabel("홍길동");
//	private JLabel frment1 = new JLabel("내가 누구죠? 그게 정말 너무 궁금해요~~~ 알죠?");
//	private JButton startfr1 = new JButton("1:1대화");
	
	private JPanel friend2 = new JPanel();
	
	private ImageIcon fricon2 = new ImageIcon("D:\\Java\\workspace\\Image_group\\NoIMG.jpg");
	private JLabel frpictur2 = new JLabel(fricon2);
	private JLabel frname2 = new JLabel("홍진영");
	private JLabel frment2 = new JLabel("네네네네네 배터리");
	private JButton startfr2 = new JButton("1:1대화");

	
	public Messenger_List() {
		this.event();
		this.setLayout(null);
		
		
		
		/**
		 * 친구리스트영역 패널
		 */
		this.add(frList);
		frList.setLayout(null);
		frList.setBounds(1, 1, 378, 732);
		
		frList.add(friend1);
		friend1.setBounds(1, 1, 376, 60); // X, Y, width, height
		friend1.setLayout(null);
		friend1.setBorder(new LineBorder(Color.ORANGE, 1));
		
//		친구 추가에 대한 별도 클래스 필요
//		friend1.add(frpictur1);
//		frpictur1.setBounds(1, 1, 57, 57);
//		friend1.add(frname1);
//		frname1.setBounds(70, 3, 160, 25);
//		friend1.add(frment1);
//		frment1.setBounds(70, 30, 230, 25);
//		friend1.add(startfr1);
//		startfr1.setBounds(298, 18, 72, 24);
//		startfr1.setFont(new Font("굴림", Font.BOLD, 9));
		
//		frList.add(friend2);
//		friend2.setBounds(1, 65, 376, 60); // X, Y, width, height
//		friend2.setLayout(null);
//		friend2.setBorder(new LineBorder(Color.ORANGE, 1));
//		
//		friend2.add(frpictur2);
//		frpictur2.setBounds(1, 1, 57, 57);
//		friend2.add(frname2);
//		frname2.setBounds(70, 3, 160, 25);
//		friend2.add(frment2);
//		frment2.setBounds(70, 30, 230, 25);
//		friend2.add(startfr2);
//		startfr2.setBounds(298, 18, 72, 24);
//		startfr2.setFont(new Font("굴림", Font.BOLD, 9));
	}
	
	public void event() {
//		startfr1.addActionListener(e->{
//			String myID = "야호";
//			List<String> list = new ArrayList<>();
//			list.add(frname1.getText());
//			list.add("호랑나비");
//			list.add(myID);
//			ChatClient client = new ChatClient(list, myID);
//		});
	}
}





