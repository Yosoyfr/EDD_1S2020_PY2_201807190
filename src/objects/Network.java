/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.Serializable;

/**
 *
 * @author Francisco Suarez
 */
public class Network implements Serializable{

	//Direccion ip
	private String address;
	//Puerto de conexion
	private int port;

	//Constructor del objeto
	public Network(String address, int port) {
		this.address = address;
		this.port = port;
	}

	//Reporte
	public String getInfo() {
		return "IP: " + this.address + ", Port: " + this.port + ".";
	}

	//Accesores y modificadores de los atributos
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
