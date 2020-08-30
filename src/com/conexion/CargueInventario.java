package com.conexion;

import java.sql.Connection;
import java.sql.SQLException;

public class CargueInventario implements Runnable{
	
	private String[] registros;
	
	
	
	public CargueInventario(String[] registros) {
		super();
		this.registros = registros;
	}


	private void insert(String registros[]) {
		
		for (int i = 0; i < registros.length; i++) {
			
			String campos[] = registros[i].split(",");
			
			int product_id = Integer.parseInt(campos[0]);
			String product_name = campos[1];
			String product_price = campos[2];
			String category_name = campos[3];
			int store_id = Integer.parseInt(campos[5]);
			String store_name = campos[6];

			int category_id = obtenerCategoria(category_name);
			
			if(category_id==0)
				category_id = insertCategory(category_name);
			
			insertStore(store_id, store_name);

			insertProducts(product_id, product_name, category_id);
			
			insertProductStore(product_id, product_price, store_id);
			
		}
		
		try {
			Thread.sleep((long)Math.random()*1000);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	

	private int insertCategory(String category_name) {
		int category_id=0;
		try {
			ConexionJDBC.crearConexion();
			
			String query = "INSERT INTO category (category_name) VALUES ('"+category_name+"') RETURNING category_id";
//		    ConexionJDBC.sentencia.execute(query);
			ConexionJDBC.resultado = ConexionJDBC.sentencia.executeQuery(query);
			while(ConexionJDBC.resultado.next()) {
				category_id = ConexionJDBC.resultado.getInt("category_id");
			}
			
		    ConexionJDBC.cerrarConexion();
		    
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return category_id;
	}
	
	private void insertProductStore(int product_id, String product_price, int store_id) {
		try {
			ConexionJDBC.crearConexion();
			
			String query = "INSERT INTO product_stores VALUES ("+product_id+", '"+product_price+"', "+store_id+")";
		    ConexionJDBC.sentencia.execute(query);
		    
		    ConexionJDBC.cerrarConexion();
		    
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void insertProducts(int product_id, String product_name, int category_id) {
		try {
			ConexionJDBC.crearConexion();
			
			String query = "INSERT INTO products VALUES ("+product_id+",'"+product_name+"', "+category_id+")";
		    ConexionJDBC.sentencia.execute(query);
		    		    
		    ConexionJDBC.cerrarConexion();
		    
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void insertStore(int store_id, String store_name) {
		try {
			ConexionJDBC.crearConexion();
			
			String query = "INSERT INTO store VALUES ("+store_id+",'"+store_name+"')";
		    ConexionJDBC.sentencia.execute(query);
		    
		    ConexionJDBC.cerrarConexion();
		    
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int obtenerCategoria(String category_name) {
		int category_id = 0;
		try {
			Connection conexion = ConexionJDBC.crearConexion();
			ConexionJDBC.sentencia = conexion.createStatement();
			String query = "SELECT category_id FROM category where category_name='"+category_name+"'";
			ConexionJDBC.resultado = ConexionJDBC.sentencia.executeQuery(query);
			
			while(ConexionJDBC.resultado.next()) {
				category_id =  ConexionJDBC.resultado.getInt("category_id");
			}
			
			ConexionJDBC.cerrarConexion();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return category_id;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		insert(registros);
	}
}
