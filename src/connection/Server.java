/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Suarez
 */
public class Server extends Observable implements Runnable {

	//Puerto donde se levantara el servidor
	private int port;

	//Constructor del servidor
	public Server(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		//Variables para el server, el socket para el server y la entrada de objetos al servidor 
		ServerSocket server = null;
		Socket socket = null;
		ObjectInputStream in = null;
		
		try {
			//Se crea el socket del servidor a partir del puerto indicado
			server = new ServerSocket(port);
			System.out.println("Servidor inicializado en el puerto: " + port);
			
			//Se mantiene escuchando las peticiones
			while(true){
				//Se espera a que algun cliente se conecte
				socket = server.accept();
				System.out.println("Cliente conectado al servidor: " + port);
				//Obtenemos el objeto que no envia el cliente
				in = new ObjectInputStream(socket.getInputStream());
				//Capturamos ese objeto
				Object obj = in.readObject();
				
				//Avisa que algo va a cambiar en la informacion
				this.setChanged();
				//Notifica a los observables que algo entro y se hizo el cambio
				this.notifyObservers(obj);
				//Limpia el cambio realizado
				this.clearChanged();
				
				//El cliente se desconecta
				socket.close();
				System.out.println("Cliente desconectado");
			}
		} catch (Exception e) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
		}
	}

}
