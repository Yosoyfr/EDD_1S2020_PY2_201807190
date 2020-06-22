/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Suarez
 */
public class Client implements Runnable {

	//Direccion donde se aloja el cliente
	private String address;
	//Puerto donde se aloja el cliente
	private int port;
	//Objeto que nos enviara este cliente
	private Object obj;
	
	//Constructor del cliente
	public Client(String address, int port, Object obj) {
		this.address = address;
		this.port = port;
		this.obj = obj;
	}

	@Override
	public void run() {
		//Socket de conexion del cliente y la data que nos tratara de enviar
		Socket socket = null;
		ObjectOutputStream out = null;
		try {
			//Se inicialia el socket para conectarnos con el cliente
			socket = new Socket(address, port);
			//Enviamos el objeto al cliente
			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(this.obj);
			//Cerramos la conexion
			socket.close();
		} catch (Exception e) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
		}
	}

}
