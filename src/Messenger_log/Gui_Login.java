package Messenger_log;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *	�α��� ȭ�� JDialog
 */
class Window_Login extends JDialog{
	
//	������Ʈ�� ��ġ�� ������ JPanel�� ����
	private JPanel con = new JPanel();
	
	
	private JLabel logLabel = new JLabel("�޽��� �α���", JLabel.CENTER); 
	private JTextField idFiled = new JTextField();	//ID
	private JTextField pwFiled = new JPasswordField();	//��й�ȣ
	private JButton btLogin = new JButton("�α���");
	private JButton btCancel = new JButton("���");
	private JButton btJoin = new JButton("ȸ������");

	
	
	public Window_Login() {
		this.display();
		this.event();
		this.menu();
		
		this.setTitle("LogIn ȭ��");
		this.setLocationByPlatform(true);
		this.setSize(400, 400);
		this.setResizable(false);
		this.setVisible(true);
	}
	/**
	 * ȭ�� ���� �޼ҵ�
	 */
	public void display() {
		this.setContentPane(con);//con�� Component ���� �������� ���
		this.setLayout(null);
		
		con.add(logLabel);
		logLabel.setBounds(100, 50, 200, 50);
		
		con.add(idFiled);
		idFiled.setBounds(100, 110, 200, 35);
		idFiled.setColumns(10);
		
		con.add(pwFiled);
		pwFiled.setBounds(100, 155, 200, 35);
		pwFiled.setColumns(10);
		
		con.add(btLogin);
		btLogin.setBounds(100, 200, 98, 35);
		
		con.add(btCancel);
		btCancel.setBounds(202, 200, 98, 35);
		
		con.add(btJoin);
		btJoin.setBounds(100, 245, 200, 23);
	}
	
	/**
	 * �̺�Ʈ ���� �޼ҵ�
	 */
	public void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//x������ â �Ҹ�
		
		/**
		 * ��ư �̺�Ʈ (�̱���)
		 */
		btCancel.addActionListener(e->{});
		btLogin.addActionListener(e->{});
		btJoin.addActionListener(e->{});
	}
	
	/**
	 * �޴� ���� �޼ҵ� (�̱���)
	 */
	public void menu() {
	}
}

public class Gui_Login {
	public static void main(String[] args) {
		Window_Login window = new Window_Login();

	}
}









