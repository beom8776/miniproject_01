package mini.mes.chatfile;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 파일 열기 또는 저장 시 창 구현 클래스
 * @author 최범석
 */
public class Dialog extends Thread{
	
	private String ext;
	private String path = "";
	private String dir;
	private JFileChooser chooser = new JFileChooser();
	
	/**
	 * 생성자(파일 열기 용)
	 */
	public Dialog() {
        this.openFile();
	}
	
	
	/**
	 * 생성자(파일 저장 용)
	 * @param extension 확장자명
	 */
	public Dialog(String extension) {
		this.ext = extension;
        this.setPath();
	}

	
	/**
	 * 생성자(파일 저장 용)
	 * @param baseDirectory 기본폴더
	 * @param extension 확장자명
	 */
	public Dialog(String baseDirectory, String extension) {
		this.dir = baseDirectory;
		this.ext = extension;
        this.setPath();
	}
	
	
	/**
	 * 저장 경로 설정 창 실행 메소드
	 */
	public void setPath() {
//		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//폴더만 선택하는 옵션
        chooser.setFileFilter(new FileNameExtensionFilter(ext + " 파일", ext));
        chooser.setCurrentDirectory(new File(dir));// 기본폴더 설정
        int option = chooser.showSaveDialog(chooser);
        if(option != JFileChooser.APPROVE_OPTION) {
//			System.exit(0);
		}
        path = chooser.getSelectedFile().getAbsolutePath() + "."+ ext;
	}
	
	
	/**
	 * 경로를 반환하는 메소드
	 * @return 파일 경로
	 */
	public String getPath() {
		return path;
	}
	
	
	/**
	 * 파일 열기 창 실행 메소드
	 */
	 public void openFile() {
		int option = chooser.showOpenDialog(chooser);
		if(option != JFileChooser.APPROVE_OPTION) {
			path = null;
		}
		else {
			path = chooser.getSelectedFile().getAbsolutePath();
			System.out.println("[클라이언트] path : " + path);
		}
	 }
	 
}


