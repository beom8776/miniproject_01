package mini.mes.main;

import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.scene.input.DataFormat;
import mini.mes.join.Member;


/**
 * 나의정보 Tab
 * @author 김현진
 *
 */
public class Messemger_Myinfo extends JPanel implements Serializable{
	
	
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
	
	private String kind = null;	// Server에 어떤 처리 작업을 할것인지 지정해주는 변수
	private Member mb = new Member();
	
	
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

		imgChange.addActionListener(e->{
			/**
			 * 프로필 사진 변경
			 * 사진 변경후 해당 회원의 DB에 등록시킨다. (작업중)
			 */
			
//			이미지 사이즈 변경 후 불러오기
			int returnVal = imageFile.showOpenDialog(Messemger_Myinfo.this);
			imageFile.setMultiSelectionEnabled(false);
			String path = imageFile.getSelectedFile().getPath();
			if(returnVal == 0) {
				ImageIcon changeImg = new ImageIcon(path);
				Image origin = changeImg.getImage();
				Image changedImg = origin.getScaledInstance(100, 140, Image.SCALE_SMOOTH);
				mypicture.setIcon(new ImageIcon(changedImg));
				
				/**
				 * 변경된 이미지를 Server 나의 DB에 전송
				 * 이미지 이름은 나의 ID.png로 저장한다.
				 */
				try {
					Socket socket = new Socket("localhost", 10001);
					ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
					String kind = "이미지";
					objectOut.writeObject(kind);
					objectOut.flush();
					
					File imgFile = new File(path);
					BufferedImage img = null;
					img = ImageIO.read(imgFile);
					
					
					objectOut.writeObject(img);
					objectOut.flush();
				} catch (Exception e2) {e2.printStackTrace();}
			}	
		});
		
		/**
		 * 삭제버튼 이벤트 (기본 이미지로 변경)
		 */
		imgDel.addActionListener(e->{
			String path = "D:\\Java\\workspace\\Image_group\\NoIMG.jpg";
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
			
			mb.setMent(textArea.getText());
			System.out.println("[[내용확인]] : [mb.setMent = "+mb.getMent()+"]");
			
			/**
			 * 상태메시지 Server DB로 전송
			 */
			String ip = "127.0.0.1";
			ObjectOutputStream objectOut;
				try {
					Socket socket = new Socket("localhost", 10001);
					ObjectOutputStream objectIn = new ObjectOutputStream(socket.getOutputStream());
					kind = "상태메시지";
					objectIn.writeObject(kind);
					objectIn.flush();
					
					objectIn.writeObject(mb);
					System.out.println("mb : " + mb.getMent());
					objectIn.flush();
					objectIn.close();
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





