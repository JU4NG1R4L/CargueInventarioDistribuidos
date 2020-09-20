import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	 public static void main( String[] args ) throws Exception {
	    	try (ServerSocket serverSocket = new ServerSocket(45000)) {

	            while (true) {
		    		long t1 = System.nanoTime();
	            	Socket client = serverSocket.accept();
	                ProtocoloHTTP hilo = new ProtocoloHTTP(client);
	                new Thread(hilo).start();
			        long t = System.nanoTime() - t1;
			        System.out.println("t(ms) = "+t/1000000 + " ms");
	            }
	        }
	    }
	 
}
