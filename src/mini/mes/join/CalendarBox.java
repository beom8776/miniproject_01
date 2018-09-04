package mini.mes.join;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;

import javax.swing.*;

public class CalendarBox extends JFrame {
	JComboBox<Integer> cbYear = new JComboBox<Integer>();
	JComboBox<Integer> cbMonth = new JComboBox<Integer>();
	JComboBox<Integer> cbDate = new JComboBox<Integer>();
	
	public void addNumbers() {
		for(int i=1900; i<=2018; i++) {
			cbYear.addItem(i);
		}
		cbYear.setSelectedItem(1985);
		
		for(int i=1; i<=12; i++) {
			cbMonth.addItem(i);
		}
		cbMonth.setSelectedItem(1);
		
		for(int i=1; i<=31; i++) {
			cbDate.addItem(i);
		}
		cbDate.setSelectedItem(1);
	}
	
	public void event() {
		cbMonth.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ie) {
				Object obj = ie.getItem();
				if(obj.equals(2)) {
					for(int i=1; i<=28; i++) {
						cbDate.addItem(i);
					}
				} else if(obj.equals(4) || obj.equals(6) || obj.equals(9) || obj.equals(11)) {
					for(int i=1; i<=30; i++) {
						cbDate.addItem(i);
					}
				} else {
					for(int i=1; i<=31; i++) {
						cbDate.addItem(i);
					}
				}
			}
		});
	}
	
	public Calendar appendBirth() {
		int year = (int)cbYear.getSelectedItem();
		int month = (int)cbMonth.getSelectedItem(); 
		int date = (int)cbDate.getSelectedItem();
		Calendar birth = Calendar.getInstance();
		birth.set(year, month, date);
		return birth;
	}
}