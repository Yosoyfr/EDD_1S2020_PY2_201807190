/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;


/**
 *
 * @author Francisco Suarez
 */
public class Books {

	//Atributos del objeto libro
	private String ISBN = "0";
	private String title;
	private String author;
	private String editorial;
	private String year;
	private String edition;
	private String category;
	private String lenguage;
	private String user;

	//Constructor del objeto libro
	public Books(String ISBN, String title, String author, String editorial, String year, String edition, String category, String lenguage, String user) {
		this.ISBN = ISBN;
		this.title = title;
		this.author = author;
		this.editorial = editorial;
		this.year = year;
		this.edition = edition;
		this.category = category;
		this.lenguage = lenguage;
		this.user = user;
	}

	//Accesores y modificadores de los atributos
	public int getISBN() {
		return Integer.parseInt(ISBN);
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLenguage() {
		return lenguage;
	}

	public void setLenguage(String lenguage) {
		this.lenguage = lenguage;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
