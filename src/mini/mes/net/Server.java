package mini.mes.net;

public class Server {
	public static void main(String[] args) {
		
		NetManager server = new NetManager(50000);
		server.workServer();
		
		
	}
}
