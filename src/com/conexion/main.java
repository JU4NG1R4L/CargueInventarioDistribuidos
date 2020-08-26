package com.conexion;

import java.sql.Connection;
import java.sql.SQLException;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Connection conexion = ConexionJDBC.crearConexion();
			
			String query = "INSERT INTO category (category_name) VALUES ('prueba')";
			ConexionJDBC.sentencia.execute(query);
			System.out.println("GUARDADO EXITOSO");
			
			ConexionJDBC.cerrarConexion();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
}
