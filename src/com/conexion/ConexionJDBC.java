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
	crearTablas();
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
	
	private static void crearTablas() {
		try {
			
			String query = "DROP TABLE IF EXISTS product_stores;\n" + 
					"DROP TABLE IF EXISTS products;\n" + 
					"DROP TABLE IF EXISTS category;\n" + 
					"DROP TABLE IF EXISTS store;\n" + 
					"\n" + 
					"CREATE TABLE category\n" + 
					"(\n" + 
					"	category_id INT NOT NULL GENERATED ALWAYS AS IDENTITY,\n" + 
					"	category_name varchar(250) unique,\n" + 
					"	primary key (category_id)\n" + 
					");\n" + 
					"\n" + 
					"CREATE TABLE store\n" + 
					"(\n" + 
					"	store_id int,\n" + 
					"	store_name varchar(250),\n" + 
					"	primary key (store_id)\n" + 
					");\n" + 
					"\n" + 
					"CREATE TABLE products\n" + 
					"(\n" + 
					"	product_id int primary key,\n" + 
					"	product_name varchar(250),\n" + 
					"	category_id int,\n" + 
					"	CONSTRAINT fk_category\n" + 
					"    FOREIGN KEY(category_id) \n" + 
					"	REFERENCES category(category_id)\n" + 
					");\n" + 
					"\n" + 
					"CREATE TABLE product_stores\n" + 
					"(\n" + 
					"	product_id int,\n" + 
					"	product_price varchar(250),\n" + 
					"	store_id int,\n" + 
					"	CONSTRAINT fk_product\n" + 
					"      FOREIGN KEY(product_id) \n" + 
					"	  REFERENCES products(product_id),\n" + 
					"	CONSTRAINT fk_store\n" + 
					"      FOREIGN KEY(store_id) \n" + 
					"	  REFERENCES store(store_id)\n" + 
					");";
			ConexionJDBC.sentencia.execute(query);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
