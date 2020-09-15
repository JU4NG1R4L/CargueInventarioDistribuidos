package co.edu.eam.sd.examen1.serverside.workers;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO: la solicitudes de conexion de esta clase se deben implementar en un hilo y el procesamiento
//   de esas solicitudes se debe hacer concurrentemente usando el pool de hilos. CalculateRequest
public class CalculatorNode implements Runnable{

  public static int SERVER_PORT = 50000;
  public static int POOL_SIZE = 100;

  //TODO: crear el pool de conexiones cuyo tamano sera POOL_SIZE
  private ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);

  private int myPort;
  private String myName;
  private String ipMaster;
  private int portMaster;
  private Socket conexion;

  public CalculatorNode(int myPort, String myName, String ipMaster, int portMaster) throws Exception {
    this.myPort = myPort;
    this.myName = myName;
    this.ipMaster = ipMaster;
    this.portMaster = portMaster;
    notifyToCentralServer(ipMaster, portMaster, myName, myPort);
  }

  

  //TODO: avisar al servidor central la presencia de este nodo.
  //  implementar el protocolo de notificacion al servidor.
  //  1. conectarse al master
  //  2. enviarle la cadena: acceptme
  //  3. enviar mi ip
  //  4. enviar mi puerto
  //  5. enviar myname
  //  6. desconectarse
  public void notifyToCentralServer(String ipMaster, int portMaster, String myName, int myPort) throws Exception {
    String miIp = "localhost"; //por el momento....
    ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
    conexion = serverSocket.accept();
    OutputStream salida = conexion.getOutputStream();
    salida.write("acceptme".getBytes());
    salida.write(miIp.getBytes());
    salida.write((myPort+"").getBytes());
    salida.write(myName.getBytes());
    salida.flush();
    salida.close();
    conexion.close();
  }

  //TODO: implementar la recepcion de la solicitud de calculo
  // instanciando CalculateRequest y enviandole por parametro el socket y el pool
  @Override
  public void run() {
	  // TODO Auto-generated method stub
	  CalculateRequest calculateRequest = new CalculateRequest(conexion, pool);
  }
}
