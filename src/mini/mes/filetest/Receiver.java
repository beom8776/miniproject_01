package mini.mes.filetest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFileChooser;

public class Receiver {
	public static void main(String[] args) {
		try {

			JFileChooser chooser = new JFileChooser();
			chooser.showSaveDialog(null);
			String path = chooser.getSelectedFile().getPath();
			System.out.println("[서버]  파일경로 : " + path);
			File file = new File(path);
			System.out.println("[서버]  파일인스턴스 : " + file);
			FileOutputStream out = new FileOutputStream(file);
			System.out.println("[서버]  FileInputStream 인스턴스 생성 ok");
			
			ServerSocket server = new ServerSocket(50001);
			Socket socket = server.accept();
			System.out.println("[서버] 수신 대기중... ok");
			InputStream in = socket.getInputStream();
			System.out.println("[서버]  InputStream 연결 ok");
			
			
			byte[] buffer = new byte[1024];
			while(true) {
				int size = in.read(buffer);
				if(size == -1)	break;
				out.write(buffer, 0, size);
			}
			out.close();
			in.close();
			System.out.println("[클라이언트]  수신 ok");
		}catch(Exception e) {
			
		}
		}
}
