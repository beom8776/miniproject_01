package mini.mes.join;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Member implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String pw;
	private String name;
	private String phone;
	private Calendar birth;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Calendar getBirth() {
		return birth;
	}
	public void setBirth(Calendar birth) {
		this.birth = birth;
	}
	

//	public void save() {
	
//		File target = new File("join", this.getId() + ".db");
//		FileOutputStream fos = new FileOutputStream(target);
//		ObjectOutputStream oos = new ObjectOutputStream(fos);
//		oos.writeObject(this);
//	}
	
	@Override
	public String toString() {
		String str = birth.get(Calendar.YEAR) + "년 "
				+ (birth.get(Calendar.MONTH) + 1) + "월 " + birth.get(Calendar.DATE) + "일";
		
		return "Member\nid\t" + getId() + " \npw\t" + getPw()
		+ " \nname\t" + getName() + " \nphone\t" + getPhone()
				+ " \nbirth\t" + str;
	}
}