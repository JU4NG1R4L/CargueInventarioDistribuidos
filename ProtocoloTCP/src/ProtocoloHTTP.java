import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProtocoloHTTP implements Runnable{
	
	Socket connection;
	private static int BLOCK_SIZE = 1000;
	
  public ProtocoloHTTP(Socket connection) {
	  this.connection = connection;
  }
  
  @Override
  public void run() {
  	// TODO Auto-generated method stub
	  try {
		  	OutputStream salida = connection.getOutputStream();
		    BufferedReader entrada = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		    
		    String comando = entrada.readLine().toLowerCase();
		    String[]campos = comando.split(" ");
		   		    
		    Path ruta = Paths.get("../ProtocoloTCP/templates" , campos[1]);
		    implementarProtocolo(salida, connection, ruta);
		    
	  }catch (Exception e) {
		// TODO: handle exception
		  e.printStackTrace();
	  }
  }
  
  private static void implementarProtocolo(OutputStream salida, Socket client, Path ruta) throws IOException {
      String status, contentType, content_lenght;
      boolean existe;
      byte[] bloque = null; 
      int bytesRead = 0;
      FileInputStream contenido = null;
      
	  if(Files.exists(ruta)) {
    	  contenido = new FileInputStream(ruta.toString());
    	  bloque = new byte[BLOCK_SIZE];
          status = "200 OK";
          contentType = Files.probeContentType(ruta);
          content_lenght = Files.readAllBytes(ruta).length+"";
          existe = true;
          
      }else {
    	  
    	  status = "404 Not Found";
          contentType = "text/html";
          bloque = "<h1>404 Not found</h1>".getBytes();
          content_lenght = bloque.length+"";
          existe = false; 
          
      }
	  
	  salida.write(("HTTP/1.1 \r\n" + status).getBytes());
      System.out.println("HTTP/1.1 " + status);
      
      salida.write(("Content-Length: " + content_lenght + "\r\n").getBytes());
      System.out.println("Content-Length: " + content_lenght);
      
      salida.write(("ContentType: " + contentType + "\r\n").getBytes());
      System.out.println("ContentType: " + contentType);
      
      salida.write("\r\n".getBytes());
      System.out.println("\r\n");
   
      if(existe) {
    	  while((bytesRead = contenido.read(bloque))==BLOCK_SIZE){
              salida.write(bloque, 0, bytesRead);
              System.out.println(new String(bloque));
          } 
      }else {
    	  salida.write(bloque);
      }
      
      
      salida.write("\r\n\r\n".getBytes());
      System.out.println("\r\n\r\n");
      
      salida.flush();
      client.close();
  } 
}