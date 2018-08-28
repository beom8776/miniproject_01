package mini.mes.net;

/**
 * 서버 실행 클래스
 * @author 최범석
 */
public class Server{
	
//	클라이언트 실행 메인
	public static void main(String[] args) {
		NetManager server = new NetManager(50000);
		server.workServer();
	}
}
