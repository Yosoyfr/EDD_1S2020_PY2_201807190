/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import nodes.Simple_Node;
import objects.User;
import reading_files.JsonBlock;

/**
 *
 * @author Francisco Suarez
 */
public class HashTable {

	//Tamaño de la tabla
	private int size;
	//Tabla de disperion
	private Simply_Linked_List[] table;

	//Constructor de la tabla
	public HashTable(int size) {
		this.size = size;
		this.table = new Simply_Linked_List[size];
	}

	//Funcion hash que me devuelve la posicion a insertar
	private int hashIndex(int key) {
		return (key & 0x7ffffff) % this.size;
	}

	//metodo para insertar
	public boolean insert(User user) {
		int index = hashIndex(user.getCarnet());
		if (isEmpty(index)) {
			table[index] = new Simply_Linked_List();
		}
		if (!contains(user.getCarnet())) {
			this.table[index].addLast(user);
			JsonBlock.getInstance().createUser(new String[]{user.getCarnet() + "", user.getName(), user.getSurname(), user.getCareer(), user.getPass()});
			return true;
		}
		return false;
	}

	//Funcion que me dira si esa posicion esta vacia
	private boolean isEmpty(int index) {
		return this.table[index] == null;
	}

	//Funcion que verifica si se contiene o no el dato a insertar
	public boolean contains(int key) {
		return search(key) != null;
	}

	//Funcion para buscar usuarios en la hash
	public User search(int key) {
		int index = hashIndex(key);
		if (!isEmpty(index)) {
			for (Simple_Node aux = this.table[index].getFirst(); aux != null; aux = aux.getNext()) {
				if (key == ((User) aux.getData()).getCarnet()) {
					return ((User) aux.getData());
				}
			}
		}
		return null;
	}

	//Funcion para editar usuario
	public void edit(User user) {
		int index = hashIndex(user.getCarnet());
		if (!isEmpty(index)) {
			for (Simple_Node aux = this.table[index].getFirst(); aux != null; aux = aux.getNext()) {
				if (user.getCarnet() == ((User) aux.getData()).getCarnet()) {
					((User) aux.getData()).setCareer(user.getCareer());
					((User) aux.getData()).setName(user.getName());
					((User) aux.getData()).setPassword(user.getPassword());
					((User) aux.getData()).setSurname(user.getSurname());
					JsonBlock.getInstance().editUser(new String[]{user.getCarnet() + "", user.getName(), user.getSurname(), user.getCareer(), user.getPass()});
				}
			}
		}
	}

	//Metodo para eliminar de la hash
	public boolean remove(int key) {
		User user = search(key);
		if (user != null) {
			int index = hashIndex(key);
			this.table[index].remove(user);
			return true;
		}
		return false;
	}

	//Funcion que me realiza el dot de la tabla hash
	public String getDOT() {
		String graph = "";
		for (int i = 0; i < this.table.length; i++) {
			graph += "node" + i + "[label =\"" + i + "\"; group = 0];\n";
			if (!isEmpty(i)) {
				Simple_Node aux = table[i].getFirst();
				while (aux != null) {
					graph += ((User) aux.getData()).getCarnet() + "[label=\""
							+ "Carnet: " + ((User) aux.getData()).getCarnet() + "\\n"
							+ "Nombre: " + ((User) aux.getData()).getName() + "\\n"
							+ "Apellido: " + ((User) aux.getData()).getSurname() + "\\n"
							+ "Carrera: " + ((User) aux.getData()).getCareer() + "\\n"
							+ "Password: " + ((User) aux.getData()).getPassword() + "\\n"
							+ "\"];\n";
					aux = aux.getNext();
				}
			}
		}

		for (int i = 0; i < this.table.length; i++) {
			if (i < this.table.length - 1) {
				graph += "\"node" + i + "\" -> " + "\"node" + (i + 1) + "\";\n";
			}
			graph += "{rank=same;node" + i;
			if (!isEmpty(i)) {
				Simple_Node aux = table[i].getFirst();
				while (aux != null) {
					graph += ";" + ((User) aux.getData()).getCarnet();
					aux = aux.getNext();
				}
			}
			graph += "};\n";
		}
		return graph;
	}
}
