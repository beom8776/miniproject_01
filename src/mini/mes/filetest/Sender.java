package mini.mes.filetest;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JFileChooser;


public class Sender {
	public static void main(String[] args) {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(chooser);
			String path = chooser.getSelectedFile().getPath();
			File file = new File(path);
			System.out.println("[클라이언트] 파일 인스턴스 생성 ok");
			Socket socket = new Socket("localhost", 50001);
			System.out.println("[클라이언트] 소켓 접속 ok");
			FileInputStream in = new FileInputStream(file);
			System.out.println("[클라이언트]  InputStream 연결 ok");
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("[클라이언트]  OutputStream 연결 ok");
			byte[] buffer = new byte[1024];
			while(true) {
				int size = in.read(buffer);
				if(size == -1)	break;
				out.write(buffer, 0, size);
			}
			in.close();
			out.close();
			System.out.println("[클라이언트]  송신 ok");
		}catch(Exception e){
			
		}
	}
}
