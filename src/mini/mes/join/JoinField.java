package mini.mes.join;
import java.awt.Container;
import java.util.Calendar;
import java.util.regex.Pattern;

import javax.swing.*;

/**
 * ID, 이름, 전화번호 검사 Event
 * @author 강정호
 *
 */

public class JoinField extends JFrame {
	Container con = getContentPane();
	JTextField[] tf = new JTextField[4];
	
	JTextField tfId = new JTextField(10);
	JPasswordField pwf = new JPasswordField(10);
	JTextField tfName = new JTextField(10);
	JTextField tfPhone2 = new JTextField(10);
	JTextField tfPhone3 = new JTextField(10);
	
	//입력창(한 줄짜리) 검사 후 확인창 띄우기
	public boolean event() {
		tf[0] = this.tfId;
		tf[1] = this.tfName;
		tf[2] = this.tfPhone2;
		tf[3] = this.tfPhone3;
		
		//4자리 숫자만 입력되었는가?
		String regex = "^[0-9]{4}$";
		String str2 = tf[2].getText();
		String str3 = tf[3].getText();
		boolean flag2 = Pattern.matches(regex, str2);
		boolean flag3 = Pattern.matches(regex, str2);
		int cnt = 0;
		
		//빈칸이 있으면 횟수 증가
		for(int i=0; i < tf.length; i++) {
			if(!tf[i].getText().equals("")) {
				cnt++;
			}
		}
		
		
		String strPw = pwf.getText();
		
		if (cnt == tf.length && !strPw.equals("")) {
			//입력창(한 줄짜리) 4개와 비밀번호 입력창을 모두 채웠는가?
			if(flag2 == true && flag3 == true) {
				//빈칸도 모두 채우고 전화번호까지 숫자로만 입력되었는가?
				JOptionPane.showMessageDialog(con, "축하합니다.",
						"가입 완료", JOptionPane.PLAIN_MESSAGE);
				dispose();
				return true;
			} else if(flag2 == false || flag3 == false) {
				//전화번호에 숫자 이외의 문자가 있는가?
				try {
					JOptionPane.showMessageDialog(con,
							"전화 번호는 숫자로만 입력하시오.", "경고",
							JOptionPane.WARNING_MESSAGE);
					return false;
				} catch(StringIndexOutOfBoundsException e) {

				}
				return false;
			}
			return false;
		} else {
			//빈칸이 하나라도 있는가?
			JOptionPane.showMessageDialog(con,
					"빈 칸을 채우시오.", "경고",
					JOptionPane.WARNING_MESSAGE);
			
			if(flag2 == false || flag3 == false) {
				if(tfPhone2.getText().equals("")
						&& tfPhone3.getText().equals(""))
					return false;
				//전화번호가 빈칸이면, 전화번호를 숫자로만 입력하라고 굳이 경고하지 아니함.
			}
			
		}
		return false;
	}

	//두 칸씩 따로 입력한 전화번호를 010과 함께 이어 붙인다.
	public String appendNumber() {
		return "010-"+ tfPhone2.getText()+"-"+ tfPhone3.getText();
	}
}