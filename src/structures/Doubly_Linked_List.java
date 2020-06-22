/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import nodes.Double_Node;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Francisco Suarez
 */
public class Doubly_Linked_List {

	private Double_Node first;
	private Double_Node last;
	private int size;

	public Doubly_Linked_List() {
		this.first = this.last = null;
		this.size = 0;
	}

	//Verifica si la lista esta vacia 
	public boolean isEmpty() {
		return this.first == null;
	}

	//Insertar datos en la lista
	public void add(Object data) {
		Double_Node n = new Double_Node(data);
		if (isEmpty()) {
			this.first = this.last = n;
		} else {
			this.last.setNext(n);
			n.setPrevious(this.last);
			this.last = n;
		}
		this.size++;
	}

	//Buscar un nodo en la lista
	Double_Node search(Object element) {
		Double_Node index;
		for (index = this.first; index != null; index = index.getNext()) {
			if (element == index.getData()) {
				return index;
			}
		}
		return null;
	}

	//Remover objeto de la lista
	public Object remove(Object element) {
		Double_Node aux = search(element);
		Object obj = null;
		if (aux != null) {
			obj = aux.getData();
			if (aux == this.first) {
				if (this.first.getNext() != null) {
					this.first = this.first.getNext();
					this.first.setPrevious(null);
				} else {
					this.first = null;
				}
			} else if (aux == this.last) {
				this.last = this.last.getPrevious();
				this.last.setNext(null);
			} else {
				Double_Node prev = aux.getPrevious();
				prev.setNext(aux.getNext());
				aux.getNext().setPrevious(prev);
			}
			this.size--;
		}
		return obj;
	}

	//Imprime los datos de la lista en consola
	public void print() {
		Double_Node aux = this.first;
		while (aux != null) {
			String p = "";
			if (aux.getPrevious() != null) {
				p += "El anterior es: " + aux.getPrevious().getData();
			} else {
				p += "El anterior es: null";
			}
			p += " El dato es: " + aux.getData();
			if (aux.getNext() != null) {
				p += " El siguiente es: " + aux.getNext().getData();
			} else {
				p += " El siguiente es: null";
			}
			System.out.println(p);
			aux = aux.getNext();
		}
	}

	//Accesores de los atributos
	public Double_Node getFirst() {
		return first;
	}

	public Double_Node getLast() {
		return last;
	}

	public int getSize() {
		return size;
	}

	//Metodo para obtener el dot de los bloques generados
	public String getBlocksDOT() {
		String graph = "";
		try {
			int i = 0;
			for (Double_Node aux = this.first; aux != null; aux = aux.getNext(), i++) {
				String blocks = (String) aux.getData();
				JSONParser parser = new JSONParser();
				Object objects = parser.parse(blocks);
				JSONObject obj = (JSONObject) objects;
				//Obtenemos los atributos del jsonObject
				String hash = obj.get("HASH").toString();
				String index = obj.get("INDEX").toString();
				String timestamp = obj.get("TIMESTAMP").toString();
				String previousHash = obj.get("PREVIOUSHASH").toString();
				String nonce = obj.get("NONCE").toString();
				graph += "block" + i + "[label =\"" + "INDEX: " + index + "\\n TIMESTAMP: " + timestamp + "\\n NONCE: " + nonce + "\\n PREVIOUSHASH: " + previousHash + "\\n HASH: " + hash + "\"];\n";
			}
			for (int j = 0; j < i - 1; j++) {
				graph += "block" + j + "->block" + (j + 1) + ";\n";
				graph += "block" + (j + 1) + "->block" + j + ";\n";
			}
		} catch (Exception e) {
			System.out.println("Error al realizar el dot de Bloques");
		}

		return graph;
	}

}
