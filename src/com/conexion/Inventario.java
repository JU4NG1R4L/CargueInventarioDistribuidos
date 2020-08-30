package com.conexion;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Inventario {

	private static final int CHUNK_SIZE=2;
	
	private ExecutorService pool;

	public Inventario() {
		super();
		this.pool = Executors.newFixedThreadPool(5);
	}
	
	public void particionar(String registros[]) {
		
		int chunks = (int) Math.floor(registros.length /CHUNK_SIZE);

        for(int c = 0; c<chunks; c++){
        	String nRegistro [] = Arrays.copyOfRange(registros,c*CHUNK_SIZE, (c + 1)*CHUNK_SIZE );
        	CargueInventario ci = new CargueInventario(nRegistro);
            pool.submit(ci);
        }
    }
}
