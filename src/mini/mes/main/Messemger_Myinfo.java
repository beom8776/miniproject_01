package mini.mes.main;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.management.VMOption.Origin;

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
	
	
	private JLabel myname = new JLabel("이름");
	private JLabel myment = new JLabel("상태메시지");
	private JButton logout = new JButton("로그아웃");
	private JButton reviseBt = new JButton("수정");

	private JPanel etcPan = new JPanel();
	private JButton listmanager = new JButton("친구관리");
	private JButton membersOut = new JButton("회원탈퇴");
	
	
	
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
		myinfo.add(imgDel);
		imgDel.setBounds(76, 160, 60, 20);
		
		myname.setBounds(140, 30, 110, 25);
		myinfo.add(myname);
		
		myment.setBounds(140, 60, 200, 90);
		myinfo.add(myment);
		
		logout.setBounds(284, 10, 90, 20);
		myinfo.add(logout);
		
		reviseBt.setBounds(291, 160, 60, 23);
		myinfo.add(reviseBt);
		
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
		/**
		 * 프로필 사진 변경
		 */
		imgChange.addActionListener(e->{
			
//			파일 확장자 필터
			FileNameExtensionFilter filterJpg = new FileNameExtensionFilter("JPG파일", "jpg");
			FileNameExtensionFilter filterPng = new FileNameExtensionFilter("PNG파일", "png");
			imageFile.setFileFilter(filterJpg);
			imageFile.setFileFilter(filterPng);
			
//			이미지 사이즈 변경 후 불러오기
			int returnpath = imageFile.showOpenDialog(Messemger_Myinfo.this);
			if(returnpath == 0) {
				String path = imageFile.getSelectedFile().getPath();
				ImageIcon changeImg = new ImageIcon(path);
				Image origin = changeImg.getImage();
				Image changedImg = origin.getScaledInstance(100, 140, Image.SCALE_SMOOTH);
				mypicture.setIcon(new ImageIcon(changedImg));
			}
		});
		
		/**
		 * 삭제버튼 이벤트 (기본 이미지로 변경)
		 */
		imgDel.addActionListener(e->{
			String path = "D:\\Java\\workspace\\Image_group\\noImg.jpg";
			mypicture.setIcon(new ImageIcon(path));
		});
		
		/**
		 * 수정 버튼 이벤트 (텍스트 입력 후 출력)
		 */
		reviseBt.addActionListener(e->{
			String ment = JOptionPane.showInputDialog("상태메시지를 입력하세요.");
			myment.setText(ment);
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





