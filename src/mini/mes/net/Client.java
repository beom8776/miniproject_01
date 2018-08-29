package mini.mes.net;

public class Client {
	public static void main(String[] args) {

		NetManager client = new NetManager("localhost", 50000);
		client.workClient();
		
	}
}
