package com.conexion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ConexionJDBC.crearConexion();
			Inventario inventario = new Inventario();
	        long t1 = System.nanoTime();
	        inventario.particionar(getArray());
	        long t = System.nanoTime() - t1;

	        System.out.println("t(ms) = "+t/1000000 + " ms");
			
	        //ConexionJDBC.cerrarConexion();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	
	private static String[] getArray() {
		BufferedReader bufferLectura = null;
		String[]campos=new String[10000];
        try {
            bufferLectura = new BufferedReader(new FileReader("/Users/ju4ng1r4l/eclipse-workspace/CargueInventarioDistribuido/src/recursos/inventario.csv"));
            String linea = bufferLectura.readLine();
            linea = bufferLectura.readLine();
            int i = 0;
            
            while (linea != null) {
                campos[i] = linea;
                i=i+1;
                linea = bufferLectura.readLine();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferLectura != null) {
                try {
                    bufferLectura.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return campos;
	}
}
