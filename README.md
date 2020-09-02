# CargueInventarioDistribuidos
Trabajo sistema de cargue de inventario - sistemas distribuidos - Juan Giraldo

Buenas noches!
Para la conexion a la base de datos - Clase Conexion JDBC

  static final String URL = "jdbc:postgresql://localhost:5432/(NOMBRE_BASE_DE_DATOS)";
	static final String USER = "(USUARIO)";
	static final String PASS = "(CONTRASEÑA)";
 
Para la configuración del archivo por bloques - Clase Inventario

  En el metodo readFile(), en la variable size indica el tamaño del arreglo para almacenar el archivo, 
  en este caso está para cargarlo de a 2000 registros e ir enviando.
  
  Para la parte del bloque y pool, en la misma clase Inventario, variable estatica CHUNK_SIZE y en el constructor, el pool de hilos.
  
Envío también script de BD, pero esta se crea al crear la conexion a la base de datos

*------------SCRIPT BASE DE DATE----------------------

DROP TABLE IF EXISTS product_stores;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS store;

CREATE TABLE category
(
	category_id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
	category_name varchar(250) unique,
	primary key (category_id)
);

CREATE TABLE store
(
	store_id int,
	store_name varchar(250),
	primary key (store_id)
);

CREATE TABLE products
(
	product_id int primary key,
	product_name varchar(250),
	category_id int,
	CONSTRAINT fk_category
    FOREIGN KEY(category_id) 
	REFERENCES category(category_id)
);

CREATE TABLE product_stores
(
	product_id int,
	product_price varchar(250),
	store_id int,
	CONSTRAINT fk_product
      FOREIGN KEY(product_id) 
	  REFERENCES products(product_id),
	CONSTRAINT fk_store
      FOREIGN KEY(store_id) 
	  REFERENCES store(store_id)
);

*-------------------------------------


NOTA: Quedo con una duda profe con el ejercicio, me gustaría solucionarlo una vez calificado, no deseo quedarme con el error

Gracias!
