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
      
      /*----------------TIPOS DE CACHE-------------------*/
      
//      /*El caché no debe almacenar nada sobre la solicitud del cliente o la respuesta del servidor. 
//       * Se envía una solicitud al servidor y se descarga una respuesta completa cada vez.*/
//      salida.write(("Cache-Control: no-store").getBytes());
//      System.out.println("Cache-control: no-store");
//
//      /*El caché no debe almacenar nada sobre la solicitud del cliente o la respuesta del servidor. 
//       * Se envía una solicitud al servidor y se descarga una respuesta completa cada vez.*/
//      salida.write(("Cache-Control: no-cache, no-store, must-revalidate").getBytes());
//      System.out.println("Cache-control: no-cache, no-store, must-revalidate");
//      
//	  /*Por otro lado, la directiva "private" indica que la respuesta está dirigida a un solo usuario y no debe ser 
//	  almacenada por un caché compartido. Un caché de navegador privado puede almacenar la respuesta en este caso.*/
//      salida.write(("Cache-Control: private").getBytes());
//      System.out.println("Cache-control: private");
//      
//      /*La directiva "public" indica que la respuesta puede ser almacenada en caché por cualquier caché. 
//       *Esto puede ser útil, si las páginas con autentificación HTTP o códigos de estado de respuesta que normalmente 
//       *no se pueden almacenar en caché, ahora deben almacenarse en caché.*/
//      salida.write(("Cache-Control: public").getBytes());
//      System.out.println("Cache-control: public");
      
      /*La directiva más importante aquí es "max-age=<seconds>" que es la máxima cantidad de tiempo que un recurso 
       * será considerado nuevo. Contrariamente a Expires, esta directiva es relativa al momento de la solicitud. 
       * Para los archivos de la aplicación que no cambiarán, generalmente se puede agregar almacenamiento en caché 
       * agresivo.Esto incluye archivos estáticos como imágenes, archivos CSS y archivos JavaScrip*/
      salida.write(("Cache-Control: max-age=3600").getBytes());
      //System.out.println("Cache-control: max-age=3600");
      
      /*----------------TIPOS DE CACHE-------------------*/
      
      salida.write(("Content-Length: " + content_lenght + "\r\n").getBytes());
      //System.out.println("Content-Length: " + content_lenght);
      
      salida.write(("ContentType: " + contentType + "\r\n").getBytes());
      //System.out.println("ContentType: " + contentType);
      
      salida.write("\r\n".getBytes());
      //System.out.println("\r\n");
   
      if(existe) {
    	  while((bytesRead = contenido.read(bloque))==BLOCK_SIZE){
              salida.write(bloque, 0, bytesRead);
              //System.out.println(new String(bloque));
          } 
      }else {
    	  salida.write(bloque);
      }
      
      salida.write("\r\n\r\n".getBytes());
      //System.out.println("\r\n\r\n");
      
      salida.flush();
      client.close();
  } 
}