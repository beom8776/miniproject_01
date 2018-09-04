package mini.mes.join;
import java.io.*;
import java.util.*;

public class Input {
	public static void main(String[] args)
			throws IOException, ClassNotFoundException {
		String str = "";
		File target = new File("join", str + ".db");
		FileInputStream fis = new FileInputStream(target);
		ObjectInputStream ois = new ObjectInputStream(fis);

		Member mb = (Member)ois.readObject();
		System.out.println(mb.toString());
	}
}