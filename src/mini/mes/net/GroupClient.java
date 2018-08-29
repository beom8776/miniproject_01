package mini.mes.net;

/**
 * 그룹채팅 클라이언트 실행 클래스
 * @author 최범석
 */
public class GroupClient {

//	그룹채팅 클라이언트 실행 메인
	public static void main(String[] args) {
		NetManager server = new NetManager("localhost", 50000);
		server.workGroupClient();
		server.start();
	}
}
