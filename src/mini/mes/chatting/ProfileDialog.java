package mini.mes.chatting;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/*
 * 대화창(ChattinGui) 상단에 '프로필'을 누르면,
 */
public class ProfileDialog extends JDialog {
	
	private int profilecount = 10;		//Test용으로 10을 주었다.
	
	private JPanel		pane = new JPanel();
	
	private ImageIcon[] icon = new ImageIcon[10];					//이모티콘 메뉴의 하위메뉴에 들어가는 이미지배열
	
	
//	private JList		list = new JList();
	private Box			box = Box.createVerticalBox();
	
	private JLabel[]	lb = new JLabel[profilecount];
	private JScrollPane js = new JScrollPane(box);
	
	/**
	 * ChattingGui클래스 인스턴스 선언과 생성자로 연결
	 */
	ChattingGui	gui;
	
	public ProfileDialog(ChattingGui gui) {
		this.gui = gui;
	}
	
	/**
	 * 메소드 호출시 다이얼로그 설정 및 visable
	 */
	public void showDialog() {
		imageCut();
		addLabelArray();
		display();
		
		this.setTitle("대화상대 프로필 보기");
		this.setSize(300, 600);
		this.setLocationByPlatform(true);
		this.setResizable(false);
		
		this.setVisible(true);
	}
	
	public void addLabelArray() {
		Image img;
		Image img1;
		ImageIcon newIcon;
		for (int i = 0; i < lb.length; i++) {
			img = icon[i].getImage();
			img1 = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);		//기존 이미지를 축소
			newIcon = new ImageIcon(img1);
			lb[i] = new JLabel("1234", newIcon, JLabel.LEFT);
		}
	}
	
	
	public void display() {
		this.setContentPane(pane);
		pane.setLayout(new FlowLayout());
//		pane.setLayout(null);
		
		js.setPreferredSize(new Dimension(280,550));
		pane.add(js);
		
		addLabelBounds();
		
	}
	
	public void addLabelBounds() {
		int y = 3; 
		for (int i = 0; i < lb.length; i++) {
//			lb[i].setBounds(3, y, 250, 50);
			lb[i].setBounds(new Rectangle(250, 50));
			box.add(lb[i]);
			y += 50;
		}
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
}
