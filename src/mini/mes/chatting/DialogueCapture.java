package mini.mes.chatting;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DialogueCapture extends JDialog{
	
	Rectangle rect;
	JFileChooser chooser;
	Robot robot;
	File target;
	BufferedImage img;
	
	ChattingGui gui;
	
	public DialogueCapture(ChattingGui gui) {
		this.gui = gui;
	}
	
	/**
	 * 대화창의 보여지는 영역의 대화내용을 jpg 파일로 캡쳐한다.
	 * @param x - 캡처하게 되는 x축 시작점
	 * @param y - 캡처하게 되는 y축 시작점
	 * @param x1 - 캡처하게 되는 x축 종료점
	 * @param y1 - 캡처하게 되는 y축 종료점
	 */
	public void areaCapture(int x, int y, int x1, int y1) {
		rect = new Rectangle(x, y, x1, y1);
		try {
			robot = new Robot();
			chooser = new JFileChooser(System.getProperty("user.dir"));
			chooser.setFileFilter(new FileNameExtensionFilter("JEPG File", "jpg"));
			if(chooser.showSaveDialog(null)  == 0 ) {
				target = chooser.getSelectedFile();
				img = robot.createScreenCapture(rect);
				ImageIO.write(img, "jpg", target);
				System.out.println(chooser.getSelectedFile());
			}else {
				JOptionPane.showMessageDialog(null, "캡쳐 저장이 취소 되었습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * 전체 대화 캡쳐 : 미구현
	 */
	public void fullCapture() {
		
	}
	
	/**
	 * 대화창 메뉴 중 '캡쳐보기'를 선택하면,
	 * 파일다이얼로그 실행되어 파일 선택시 해당 이미지를
	 * 다이얼로그창에 띄운다. 
	 */
	public void openCapture() {
			try {
				chooser = new JFileChooser(System.getProperty("user.dir"));
				chooser.setFileFilter(new FileNameExtensionFilter("JPEG File", "jpg"));
				if(chooser.showOpenDialog(null) == 0) {
					target = chooser.getSelectedFile();
					img = ImageIO.read(target);
					showImage(img);
				}else {
					JOptionPane.showMessageDialog(null, "캡쳐 보기가 취소 되었습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	}
	
	/**
	 * openCapture메소드에서 img 인스턴스를 받아와서,
	 *  img와 동일한 크기의 다이얼로그창 만들고 불러온 이미지를
	 *  창에 그린다. 
	 * @param img
	 */
	public void showImage(BufferedImage img) {
		JPanel		pane = new JPanel();
		
		ImageIcon	icon = new ImageIcon(img);
		JLabel		lb = new JLabel(icon);
		
		this.setContentPane(pane);
		pane.setLayout(null);
		
		lb.setBounds(0, 0, img.getWidth(),	img.getHeight());
		pane.add(lb);
		
		this.setTitle("캡쳐보기");
		this.setSize(img.getWidth(), img.getHeight());
		this.setLocationByPlatform(true);
		this.setResizable(false);
		this.setVisible(true);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
