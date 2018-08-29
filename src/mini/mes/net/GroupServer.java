package mini.mes.net;

/**
 * 그룹채팅 서버 실행 클래스
 * @author 최범석
 */
public class GroupServer {
	
//	그룹채팅 서버 실행 메인
	public static void main(String[] args) {
		NetManager server = new NetManager(50000);
		server.workGroupServer();
	}
}
