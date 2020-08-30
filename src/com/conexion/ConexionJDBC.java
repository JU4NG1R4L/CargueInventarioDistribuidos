package com.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionJDBC {

	static final String URL = "jdbc:postgresql://localhost:5432/Inventario";
	static final String USER = "ju4ng1r4l";
	static final String PASS = "salosamu";
	static Statement sentencia = null;
	static Connection conexion = null;
	static ResultSet resultado = null;
	
	public static Connection crearConexion() throws ClassNotFoundException, SQLException{
	Class.forName("org.postgresql.Driver");
	conexion = DriverManager.getConnection(URL, USER, PASS);
	if (conexion != null){
	sentencia = conexion.createStatement();
	
	return conexion;
	}
	return null;
	}
	
	public static void cerrarConexion() {
		conexion = null;
		if(resultado!=null) {
			try {
				resultado.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		if(sentencia!=null) {
			try {
				sentencia.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		if(conexion!=null) {
			try {
				conexion.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();

			}
		}
	}
	
}
