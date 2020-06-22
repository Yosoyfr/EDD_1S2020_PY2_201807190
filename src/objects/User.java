/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.Main;
import security.HashPassword;

/**
 *
 * @author Francisco Suarez
 */
public class User {

	//Atributos del objeto usuario
	private int carnet;
	private String name;
	private String surname;
	private String career;
	private String password;
	private String pass;

	//Constructor del objeto usuario
	public User(int carnet, String name, String surname, String career, String password) {
		this.carnet = carnet;
		this.name = name;
		this.surname = surname;
		this.career = career;
		try {
			this.password = HashPassword.getInstance().encrypt(password);
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
		this.pass = password;
	}

	//Constructor del objeto usuario para cuando se requiera editar
	public User(int carnet, String name, String surname, String career, String password, String pass) {
		this.carnet = carnet;
		this.name = name;
		this.surname = surname;
		this.career = career;
		this.password = password;
		this.pass = pass;
	}

	//Accesores y modificadores de los atributos
	public int getCarnet() {
		return carnet;
	}

	public void setCarnet(int carnet) {
		this.carnet = carnet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (!this.password.equals(password)) {
			try {
				this.password = HashPassword.getInstance().encrypt(password);
				this.pass = password;
			} catch (NoSuchAlgorithmException ex) {
				Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public String getPass() {
		return pass;
	}

	public boolean isPassword(String password) {
		try {
			return this.password.equals(HashPassword.getInstance().encrypt(password));
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

}
