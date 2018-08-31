package mini.mes.chatting;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AddTalkerDialog extends JDialog {

	private JPanel	pane = new JPanel();
	
	private JTextArea	area = new JTextArea();
	private JScrollPane	js = new JScrollPane(area);
//	private JCheckBox[] friends;
	private JCheckBox[] friends = new JCheckBox[10];
	private JButton		addbt = new JButton("친구추가");
	private JButton		cancel= new JButton("취소");
	
	private String		addid="";

	ChattingGui gui;
	
	public AddTalkerDialog(ChattingGui gui) {
		this.gui = gui;
	}
	
	public void showDialog() {
//		totalArry(total_friendsNo, talkerNo);
		friendsArray();
		display();
		event();
		
		this.setTitle("대화상대 추가");
		this.setSize(283, 400);
		this.setLocationByPlatform(true);
		this.setResizable(false);
		
		this.setVisible(true);
	}
	
	public void friendsArray() {
		for(int i = 0; i < friends.length; i++) {
			friends[i] = new JCheckBox("name"+"#"+"addid"+i);
		}
	}
	
//	public void setArry() {
//		totalArry();
//		
//	}
	/**
	 * 대화상대 추가 가능한 친구 숫자만큼 체크박스 배열 생성 
	 * @param total_friendsNo -전체 친구의 숫자
	 * @param talkerNo -현대 대화하고 있는 대화상대의 숫자
	 */
//	public void totalArry() {
//		this.friends = new JCheckBox[gui.addTalkers().length];
//		
//	}
	
	public void display() {
		
		this.setContentPane(pane);
		pane.setLayout(null);
		
		pane.add(area);
		area.setBounds(3, 0, 260, 310);
		area.setEditable(false);
		int y = 1;
		for(int i = 0; i < friends.length; i++) {
			friends[i].setBounds(5, y, 250, 30);
			area.add(friends[i]);
			y += 30;
		}
		addbt.setBounds(155, 315, 50, 40);
		pane.add(addbt);
		cancel.setBounds(210, 315, 50, 40);
		pane.add(cancel);
		
	}
	
	public void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		addbt.addActionListener(e->{
			addid = "";
			for( int i = 0; i < friends.length; i++) {
				if(friends[i].isSelected()) {
					addid += " "+friends[i].getText().substring(friends[i].getText().indexOf("#")+1);
				}
			}
			
			System.out.println(addid);
			dispose();
		});
		
		cancel.addActionListener(e->{
			dispose();
		});
	}
}
