package mini.mes.main;

import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * 나의정보 Tab
 * @author 김현진
 *
 */
public class Messemger_Myinfo extends JPanel{
	
	
	private JPanel myinfo = new JPanel();
	
	private JFileChooser imageFile = new JFileChooser();
	private ImageIcon noImage = new ImageIcon("D:\\Java\\workspace\\Image_group\\noImg.jpg");
	private JLabel mypicture = new JLabel(noImage);
	
	private JButton imgChange = new JButton("변경");
	private JButton imgDel = new JButton("삭제");
	
	
	private JButton logout = new JButton("로그아웃");
	private JLabel myname = new JLabel("이름");
	private JLabel myId = new JLabel("아이디");
	private JTextArea textArea = new JTextArea();
	
	private JButton reviseBt = new JButton("수정");
	private JButton completeReviseBt = new JButton("완료");

	private JPanel etcPan = new JPanel();
	private JButton listmanager = new JButton("친구관리");
	private JButton membersOut = new JButton("회원탈퇴");
	
	
	private Date date = new Date();
	private SimpleDateFormat sdate = new SimpleDateFormat("yyyy_MM_dd");
	private String mdate = sdate.format(date);
	
	public Messemger_Myinfo() {
		this.event();
		this.setLayout(null);
		/**
		 * 프로필 관리 패널
		 */
		this.add(myinfo);
		myinfo.setLayout(null);
		myinfo.setBounds(1, 1, 378, 200);
		myinfo.setBorder(new LineBorder(Color.ORANGE, 2));
		
		mypicture.setHorizontalAlignment(SwingConstants.CENTER);
		myinfo.add(mypicture);
		mypicture.setBounds(24, 15, 100, 140);
		
		myinfo.add(imgChange);
		imgChange.setBounds(13, 160, 60, 20);
		imgChange.setVisible(false);
		
		myinfo.add(imgDel);
		imgDel.setBounds(76, 160, 60, 20);
		imgDel.setVisible(false);
		
		myinfo.add(myname);
		myname.setBounds(140, 15, 120, 25);
		
		myId.setBounds(140, 45, 120, 25);
		myinfo.add(myId);
		textArea.setLineWrap(true);
		
		myinfo.add(textArea);
		textArea.setBounds(140, 70, 200, 70);
		textArea.setVisible(true);
		textArea.setEditable(false);
		
		myinfo.add(logout);
		logout.setBounds(284, 10, 90, 20);
		
		myinfo.add(reviseBt);
		reviseBt.setBounds(291, 160, 60, 23);
		reviseBt.setVisible(true);
		
		myinfo.add(completeReviseBt);
		completeReviseBt.setBounds(291, 160, 60, 23);
		completeReviseBt.setVisible(false);
		
		/**
		 * 친구관리버튼 및 회원탈퇴버튼 관리 패널
		 */
		this.add(etcPan);
		etcPan.setLayout(null);
		etcPan.setBounds(1, 205, 378, 528);
		etcPan.setBorder(new LineBorder(Color.ORANGE, 2));
		
		etcPan.add(listmanager);
		listmanager.setBounds(20, 10, 90, 25);
		
		membersOut.setBounds(130, 10, 90, 25);
		etcPan.add(membersOut);
	}
	
	public void event() {
//			파일 확장자 필터
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG파일", "jpg", "png");
			imageFile.setFileFilter(filter);

		/**
		 * 프로필 사진 변경
		 * 사진 변경후 해당 회원의 DB에 등록시킨다. (작업중)
		 */
		imgChange.addActionListener(e->{
//			이미지 사이즈 변경 후 불러오기
			int returnVal = imageFile.showOpenDialog(Messemger_Myinfo.this);
			imageFile.setMultiSelectionEnabled(false);
			String path = imageFile.getSelectedFile().getPath();
			if(returnVal == 0) {
				ImageIcon changeImg = new ImageIcon(path);
				Image origin = changeImg.getImage();
				Image changedImg = origin.getScaledInstance(100, 140, Image.SCALE_SMOOTH);
				mypicture.setIcon(new ImageIcon(changedImg));
			}
			
			/**
			 * 이미지 변경 후 DB에 저장
			 */
			File imgFile = new File(path);
			String fname = imgFile.getName();
			int pos = fname.lastIndexOf(".");
			String extension = fname.substring(pos+1);

			
			try {
				Image img = ImageIO.read(imgFile);
				BufferedImage buffImg = (BufferedImage)img;
				
				File picFile = new File("D:\\eclipse-java-photon-R-win32-x86_64\\workspace\\Project\\db\\"+mdate+"."+extension);
				ImageIO.write(buffImg, extension, picFile);
			} catch (IOException e1) {e1.printStackTrace();}
			
		});
		
		/**
		 * 삭제버튼 이벤트 (기본 이미지로 변경)
		 */
		imgDel.addActionListener(e->{
			String path = "D:\\Java\\workspace\\Image_group\\noImg.jpg";
			mypicture.setIcon(new ImageIcon(path));
		});
		
		/**
		 * 수정 버튼 이벤트 (사진변경 및 삭제 버튼 / 상태메시지 작성 활성화)
		 */
		reviseBt.addActionListener(e->{
			imgChange.setVisible(true);
			imgDel.setVisible(true);
			textArea.setVisible(true);
			completeReviseBt.setVisible(true);
			reviseBt.setVisible(false);
			textArea.setEditable(true);
		});
		
		/**
		 * 완료버튼 이벤트 (사진변경 및 삭제 버튼 / 상태메시지 작성 비활성화)
		 * 상태메시지를 화면에 출력하고 내용을 DB에 저장한다.
		 */
		completeReviseBt.addActionListener(e->{
			imgChange.setVisible(false);
			imgDel.setVisible(false);
			completeReviseBt.setVisible(false);
			reviseBt.setVisible(true);
			textArea.setEditable(false);
			
			/**
			 * 상태메시지 Server DB로 전송
			 */

			String ip = "127.0.0.1";
			DataOutputStream dout;
				try {
					Socket socket = new Socket("localhost", 50001);
					dout = new DataOutputStream(socket.getOutputStream());
					
					dout.writeUTF(textArea.getText());
					dout.flush();
					
					dout.close();
					} catch (IOException e1) {e1.printStackTrace();}	
		});
		
		/**
		 * 로그아웃 이벤트
		 */
		logout.addActionListener(e->{
			int logout = JOptionPane.showConfirmDialog(this, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION);

//			연결작업 준비중
			if(logout == 0) {}
		});
	}
}





