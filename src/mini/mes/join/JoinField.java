package mini.mes.join;
import java.awt.Container;
import java.util.Calendar;
import java.util.regex.Pattern;

import javax.swing.*;

public class JoinField extends JFrame {
	Container con = getContentPane();
	JTextField[] tf = new JTextField[4];
	
	JTextField tfId = new JTextField(10);
	JPasswordField pwf = new JPasswordField(10);
	JTextField tfName = new JTextField(10);
	JTextField tfPhone2 = new JTextField(10);
	JTextField tfPhone3 = new JTextField(10);
	
	/**
	 * ID, 이름, 전화번호 검사 이벤트
	 */
	public void event() {
		tf[0] = this.tfId;
		tf[1] = this.tfName;
		tf[2] = this.tfPhone2;
		tf[3] = this.tfPhone3;
		
		String regex = "^[0-9]{4}$";
		String str2 = tf[2].getText();
		String str3 = tf[3].getText();
		boolean flag2 = Pattern.matches(regex, str2);
		boolean flag3 = Pattern.matches(regex, str2);
		int cnt = 0;

		for(int i=0; i < tf.length; i++) {
			if(!tf[i].getText().equals("")) {
				cnt++;
			}
		}

		String strPw = pwf.getText();
		if (cnt == tf.length && !strPw.equals("")) {
			if(flag2 == true && flag3 == true) {
				JOptionPane.showMessageDialog(con, "축하합니다.",
						"가입 완료", JOptionPane.PLAIN_MESSAGE);
				dispose();
			} else if(flag2 == false || flag3 == false) {
				try {
					JOptionPane.showMessageDialog(con,
							"전화 번호는 숫자로만 입력하시오.", "경고",
							JOptionPane.WARNING_MESSAGE);
					return;
				} catch(StringIndexOutOfBoundsException e) {

				}
			}
		} else {
			JOptionPane.showMessageDialog(con,
					"빈 칸을 채우시오.", "경고",
					JOptionPane.WARNING_MESSAGE);
			if(flag2 == false || flag3 == false) {
				if(tfPhone2.getText().equals("")
						&& tfPhone3.getText().equals(""))
					return;

			}
		}
	}

	
	public String appendNumber() {
		return "010-"+ tfPhone2.getText()+"-"+ tfPhone3.getText();
	}
}