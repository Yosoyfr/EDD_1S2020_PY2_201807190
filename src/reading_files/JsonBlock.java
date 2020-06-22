/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reading_files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import nodes.Simple_Node;
import objects.Books;
import objects.User;
import org.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import security.HashPassword;
import structures.Simply_Linked_List;

/**
 *
 * @author Francisco Suarez
 */
public class JsonBlock {

	private static JsonBlock instance;

	public static JsonBlock getInstance() {
		if (instance == null) {
			instance = new JsonBlock();
		}
		return instance;
	}

	//lista que contendra las operaciones de los bloques
	Simply_Linked_List data_block = new Simply_Linked_List();

	//Constructor vacio
	public JsonBlock() {
	}

	//Metodo para leer archivos que existan en la carpeta de la instancia
	public void readJsonFile() {
		try {
			File json = new File("C:/app" + Main.ipInstance + "_" + Main.portInstance + "/blockchain.json");
			if (json.exists()) {
				JSONParser parser = new JSONParser();
				Object object = parser.parse(new BufferedReader(new InputStreamReader(new FileInputStream(json), "UTF-8")));
				jsonBlock_(object.toString());
			}
		} catch (Exception e) {
			System.out.println("Error en la lectura de la carpeta");
		}
	}

	//Metodo para leer el bloque completo
	public void jsonBlock_(String dataBlock) {
		try {
			JSONParser parser = new JSONParser();
			Object objects = parser.parse(dataBlock);
			JSONObject jsonObject = (JSONObject) objects;
			//Atributos del bloque
			//Obtenemos el hash obtenido
			String hash = jsonObject.get("HASH").toString();
			//Obtenemos el index
			String index = jsonObject.get("INDEX").toString();
			//Se lo asignamos a nuestras variables
			this.id_block = Integer.parseInt(index) + 1;
			this.previousHash = hash;
			//Obtenemos la data y la leemos para agregar a a nuestra app
			JSONArray data = (JSONArray) jsonObject.get("DATA");
			readingDataBlock(data);
			//Replicamos el objeto json en nuestra carpeta de esta instancia
			try (FileWriter file = new FileWriter("C:\\app" + Main.ipInstance + "_" + Main.portInstance + "\\blockchain.json")) {
				file.write(jsonObject.toJSONString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			Main.blockchainInstance.add(jsonObject.toString());
		} catch (Exception e) {
			System.out.println("Error en la lectura del archivo de configuracion " + e);
		}
	}

	//Lectura de la data en bloque
	public void readingDataBlock(JSONArray data) {
		try {

			for (int i = 0; i < data.size(); i++) {
				JSONObject blocksData = (JSONObject) data.get(i);
				JSONArray createUser = (JSONArray) blocksData.get("CREAR_USUARIO");
				JSONArray editUser = (JSONArray) blocksData.get("EDITAR_USUARIO");
				JSONArray deleteUser = (JSONArray) blocksData.get("ELIMINAR_USUARIO");
				JSONArray createCategory = (JSONArray) blocksData.get("CREAR_CATEGORIA");
				JSONArray deleteCategory = (JSONArray) blocksData.get("ELIMINAR_CATEGORIA");
				JSONArray createBook = (JSONArray) blocksData.get("CREAR_LIBRO");
				JSONArray deleteBook = (JSONArray) blocksData.get("ELIMINAR_LIBRO");

				//Dependiendo el JSONArray encontrado lo enviamos a la funcion definida
				if (createUser != null) {
					readCreateUser(createUser);
				}
				if (editUser != null) {
					readEditUser(editUser);
				}
				if (deleteUser != null) {
					readDeleteUser(deleteUser);
				}
				if (createCategory != null) {
					readCreateCategory(createCategory);
				}
				if (deleteCategory != null) {
					readDeleteCategory(deleteCategory);
				}
				if (createBook != null) {
					readCreateBook(createBook);
				}
				if (deleteBook != null) {
					readDeleteBook(deleteBook);
				}
			}

		} catch (Exception e) {
			System.out.println("Error en la lectura del archivo de data " + e);
		}
	}

	//Parte de la lectura del blockchain
	//Leer Operacion: Crear usuarios
	public void readCreateUser(JSONArray data) {
		JSONObject obj = (JSONObject) data.get(0);
		String carnet = obj.get("Carnet").toString();
		String name = obj.get("Nombre").toString();
		String surname = obj.get("Apellido").toString();
		String career = obj.get("Carrera").toString();
		String password = obj.get("Password").toString();
		User user = new User(Integer.parseInt(carnet), name, surname, career, password);
		Main.hashTable.insert(user);
	}

	//Leer Operacion: Editar Usuarios
	public void readEditUser(JSONArray data) {
		JSONObject obj = (JSONObject) data.get(0);
		String carnet = obj.get("Carnet").toString();
		String name = obj.get("Nombre").toString();
		String surname = obj.get("Apellido").toString();
		String career = obj.get("Carrera").toString();
		String password = obj.get("Password").toString();
		User user = new User(Integer.parseInt(carnet), name, surname, career, password);
		Main.hashTable.edit(user);
	}

	//Leer Operacion: Eliminar usuarios
	public void readDeleteUser(JSONArray data) {
		JSONObject obj = (JSONObject) data.get(0);
		String carnet = obj.get("Carnet").toString();
		Main.hashTable.remove(Integer.parseInt(carnet));
	}

	//Leer Operacion: Crear libro
	public void readCreateBook(JSONArray data) {
		JSONObject obj = (JSONObject) data.get(0);
		String ISBN = obj.get("ISBN").toString();
		String year = obj.get("Año").toString();
		String lenguage = obj.get("Idioma").toString();
		String title = obj.get("Titulo").toString();
		String editorial = obj.get("Editorial").toString();
		String author = obj.get("Autor").toString();
		String edition = obj.get("Edicion").toString();
		String category = obj.get("Categoria").toString();
		String owner = obj.get("Propietario").toString();
		Books b = new Books(ISBN, title, author, editorial, year, edition, category, lenguage, owner);
		Main.treeAVL.insert(b);
	}

	//Leer Operacion: Eliminar libro
	public void readDeleteBook(JSONArray data) {
		JSONObject obj = (JSONObject) data.get(0);
		String ISBN = obj.get("ISBN").toString();
		String category = obj.get("Categoria").toString();
		Main.treeAVL.removeBook(category, Integer.parseInt(ISBN));
	}

	//Leer Operacion: Crear categoria
	public void readCreateCategory(JSONArray data) {
		JSONObject obj = (JSONObject) data.get(0);
		String category = obj.get("Nombre").toString();
		String owner = obj.get("Propietario").toString();
		Main.treeAVL.insert(new Books("-1", "", "", "", "", "", category, "", owner));
	}

	//Leer Operacion: Eliminar categoria
	public void readDeleteCategory(JSONArray data) {
		JSONObject obj = (JSONObject) data.get(0);
		String category = obj.get("Nombre").toString();
		Main.treeAVL.remove(category);
	}

	//Parte de la data del Block chain
	//Operacion: Crear usuarios
	public void createUser(String[] user) {
		JSONObject obj = new JSONObject();
		obj.put("Carnet", Integer.parseInt(user[0]));
		obj.put("Nombre", user[1]);
		obj.put("Apellido", user[2]);
		obj.put("Carrera", user[3]);
		obj.put("Password", user[4]);

		JSONArray list = new JSONArray();
		list.add(obj);

		JSONObject data = new JSONObject();
		data.put("CREAR_USUARIO", list);

		data_block.addLast(data);
	}

	//Operacion: Editar Usuarios
	public void editUser(String[] user) {
		JSONObject obj = new JSONObject();
		obj.put("Carnet", Integer.parseInt(user[0]));
		obj.put("Nombre", user[1]);
		obj.put("Apellido", user[2]);
		obj.put("Carrera", user[3]);
		obj.put("Password", user[4]);

		JSONArray list = new JSONArray();
		list.add(obj);

		JSONObject data = new JSONObject();
		data.put("EDITAR_USUARIO", list);

		data_block.addLast(data);
	}

	//Operacion: Eliminar usuarios
	public void deleteUser(String[] user) {
		JSONObject obj = new JSONObject();
		obj.put("Carnet", Integer.parseInt(user[0]));

		JSONArray list = new JSONArray();
		list.add(obj);

		JSONObject data = new JSONObject();
		data.put("ELIMINAR_USUARIO", list);

		data_block.addLast(data);
	}

	//Operacion: Crear libro
	public void createBook(Books book) {
		JSONObject obj = new JSONObject();
		obj.put("ISBN", book.getISBN());
		obj.put("Año", Integer.parseInt(book.getYear()));
		obj.put("Idioma", book.getLenguage());
		obj.put("Titulo", book.getTitle());
		obj.put("Editorial", book.getEditorial());
		obj.put("Autor", book.getAuthor());
		obj.put("Edicion", book.getEdition());
		obj.put("Categoria", book.getCategory());
		obj.put("Propietario", Integer.parseInt(book.getUser()));

		JSONArray list = new JSONArray();
		list.add(obj);

		JSONObject data = new JSONObject();
		data.put("CREAR_LIBRO", list);

		data_block.addLast(data);
	}

	//Operacion: Eliminar libro
	public void deleteBook(Books book) {
		JSONObject obj = new JSONObject();
		obj.put("ISBN", book.getISBN());
		obj.put("Titulo", book.getTitle());
		obj.put("Categoria", book.getCategory());

		JSONArray list = new JSONArray();
		list.add(obj);

		JSONObject data = new JSONObject();
		data.put("ELIMINAR_LIBRO", list);

		data_block.addLast(data);
	}

	//Operacion: Crear categoria
	public void createCategory(String category, String owner) {
		JSONObject obj = new JSONObject();
		obj.put("Nombre", category);
		obj.put("Propietario", Integer.parseInt(owner));
		JSONArray list = new JSONArray();
		list.add(obj);

		JSONObject data = new JSONObject();
		data.put("CREAR_CATEGORIA", list);

		data_block.addLast(data);
	}

	//Operacion: Eliminar categoria
	public void deleteCategory(String category) {
		JSONObject obj = new JSONObject();
		obj.put("Nombre", category);
		JSONArray list = new JSONArray();
		list.add(obj);

		JSONObject data = new JSONObject();
		data.put("ELIMINAR_CATEGORIA", list);

		data_block.addLast(data);
	}

	//Creacion de la DATA
	public Object createDATA() {
		JSONArray list = new JSONArray();
		for (Simple_Node obj = data_block.getFirst(); obj != null; obj = obj.getNext()) {
			list.add(obj.getData());
		}
		return list;
	}

	public int id_block = 0;
	public String previousHash = "0000";

	//Creacion del block
	public void createBlock() {
		Calendar cal = Calendar.getInstance();
		String timestamp = new SimpleDateFormat("dd-MM-yyyy::HH:mm:ss").format(cal.getTime());
		String data = createDATA().toString();
		int nonce = 0;
		String hash = HashPassword.getInstance().hashBlock(id_block + timestamp + previousHash + data + nonce);
		while (!hash.startsWith("0000")) {
			timestamp = new SimpleDateFormat("dd-MM-yyyy::HH:mm:ss").format(cal.getTime());
			hash = HashPassword.getInstance().hashBlock(id_block + timestamp + previousHash + data + nonce);
			nonce++;
		}
		System.out.println("Nonce: " + nonce + " Hash valido: " + hash);

		JSONObject obj = new JSONObject();
		obj.put("INDEX", id_block);
		obj.put("TIMESTAMP", timestamp);
		obj.put("DATA", createDATA());
		obj.put("NONCE", nonce);
		obj.put("PREVIOUSHASH", previousHash);
		obj.put("HASH", hash);

		try (FileWriter file = new FileWriter("C:\\app" + Main.ipInstance + "_" + Main.portInstance + "\\blockchain.json")) {
			file.write(obj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Main.blockchainInstance.add(obj.toString());
		previousHash = hash;
		id_block++;
	}

}
