import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	 public static void main( String[] args ) throws Exception {
	    	try (ServerSocket serverSocket = new ServerSocket(45000)) {
	            while (true) {
	            	Socket client = serverSocket.accept();
	                ProtocoloHTTP hilo = new ProtocoloHTTP(client);
	                new Thread(hilo).start();
	            }
	        }
	    }
	 
}
