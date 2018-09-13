package mini.mes.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class Messenger_m extends JFrame{
	/**
	 * Messenger Main ȭ��
	 * @author ������
	 *
	 */
		private JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP);
		private Messenger_List list = new Messenger_List();
		private Messenger_FriendAdd search = new Messenger_FriendAdd();
		private Messemger_Myinfo myinfo = new Messemger_Myinfo();
		private Messenger_ChatList chat = new Messenger_ChatList();
		
		public Messenger_m() {
			this.display();
			this.event();
			
			this.setTitle("Java �޽���");
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (dim.width/2)-(400/2);
			int y = (dim.height/2)-(800/2);
			this.setLocation(x, y);
			this.setSize(400, 800);
			this.setResizable(false);
			this.setVisible(true);
		}
		/**
		 * ȭ�� ���� �޼ҵ�
		 */
		public void display() {
			
			this.getContentPane().add(tab, BorderLayout.CENTER);
			
			/**
			 * Tab �߰�
			 */
			tab.addTab("ģ�����", list);
			tab.addTab("ä�ø��", chat);
			tab.addTab("ģ��ã��", search);
			tab.addTab("��������", myinfo);
			
		}
		
		/**
		 * �̺�Ʈ ���� �޼ҵ�
		 */
		public void event() {
			
			this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			WindowListener closer = new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					int close = JOptionPane.showConfirmDialog(Messenger_m.this, "�����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);
					if(close == 0) System.exit(0);
				}
			};
			this.addWindowListener(closer);
		}
}
