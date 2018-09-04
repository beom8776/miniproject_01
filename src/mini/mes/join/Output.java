package mini.mes.join;
import java.io.*;
import java.util.*;


public class Output {
	public static void main(String[] args) {
		try {
			List<Member> mbList = new ArrayList<>();
			Member mb = new Member();

			mb.setId("kurook");
			mb.setPw("1234");
			mb.setName("김꾸꾸");
			mb.setPhone("010-0000-1234");
			mb.setBirth(Calendar.getInstance());
			mbList.add(mb);
			save(mb.getName(), mbList);
			
			mb.setId("smefg");
			mb.setPw("5678");
			mb.setName("강정호");
			mb.setPhone("010-0000-5678");
			mb.setBirth(Calendar.getInstance());
			mbList.add(mb);
			save(mb.getName(), mbList);

			mb.setId("abcd");
			mb.setPw("1234");
			mb.setName("알파벳");
			mb.setPhone("010-1357-1234");
			mb.setBirth(Calendar.getInstance());
			mbList.add(mb);
			save(mb.getName(), mbList);		} catch(IOException e) {			e.printStackTrace();		}	}

	public static void save(String name, List<Member> mbList) throws IOException {
		File target =new File("join", name + ".db");
		FileOutputStream fos = new FileOutputStream(target, true);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(mbList);
	}
}