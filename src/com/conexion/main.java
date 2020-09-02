package com.conexion;

import java.sql.SQLException;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ConexionJDBC.crearConexion();
			Inventario inventario = new Inventario();
	        long t1 = System.nanoTime();
	        inventario.readFile();
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
	
}
