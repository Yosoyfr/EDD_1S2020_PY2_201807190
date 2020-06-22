/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reading_files;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import nodes.AVL_Node;
import objects.Books;
import objects.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.Main;

/**
 *
 * @author Francisco Suarez
 */
public class JsonReading {
	
	private static JsonReading instance;

	public static JsonReading getInstance() {
		if (instance == null) {
			instance = new JsonReading();
		}
		return instance;
	}

	//Constructor vacio
	public JsonReading() {
	}

	//Metodo para leer los usuarios
	public void jsonUsers(String route) {
		try {
			JSONParser parser = new JSONParser();
			Object objects = parser.parse(new BufferedReader(new InputStreamReader(new FileInputStream(route), "UTF-8")));
			JSONObject jsonObject = (JSONObject) objects;
			JSONArray books = (JSONArray) jsonObject.get("Usuarios");
			for (int i = 0; i < books.size(); i++) {
				JSONObject obj = (JSONObject) books.get(i);
				String carnet = obj.get("Carnet").toString();
				String name = obj.get("Nombre").toString();
				String surname = obj.get("Apellido").toString();
				String career = obj.get("Carrera").toString();
				String password = obj.get("Password").toString();
				User user = new User(Integer.parseInt(carnet), name, surname, career, password);
				Main.hashTable.insert(user);
			}
		} catch (Exception e) {
			System.out.println("Error en la lectura del archivo de configuracion " + e);
		}
		//System.out.println(Main.hashTable.getDOT());
	}

	//Metodo para leer los libros
	public void jsonBooks(String route) {
		try {
			JSONParser parser = new JSONParser();
			Object objects = parser.parse(new BufferedReader(new InputStreamReader(new FileInputStream(route), "UTF-8")));
			JSONObject jsonObject = (JSONObject) objects;
			JSONArray users = (JSONArray) jsonObject.get("libros");
			for (int i = 0; i < users.size(); i++) {
				JSONObject obj = (JSONObject) users.get(i);
				String ISBN = obj.get("ISBN").toString();
				String year = obj.get("Año").toString();
				String lenguage = obj.get("Idioma").toString();
				String title = obj.get("Titulo").toString();
				String editorial = obj.get("Editorial").toString();
				String author = obj.get("Autor").toString();
				String edition = obj.get("Edicion").toString();
				String category = obj.get("Categoria").toString();
				Books b = new Books(ISBN, title, author, editorial, year, edition, category, lenguage, Main.user);
				Main.treeAVL.insert(b);
			}
		} catch (Exception e) {
			System.out.println("Error en la lectura del archivo de configuracion " + e);
		}
	}
}
