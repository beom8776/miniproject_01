package mini.mes.net;

/**
 * 클라이언트 실행 클래스
 * @author 최범석
 */
public class OneClient{
	
/**
 * [1:1] 클라이언트 실행 메인 메소드
 */
	public static void main(String[] args) {
		NetManager server = new NetManager("localhost", 50000);
		server.workClient();
	}
}
