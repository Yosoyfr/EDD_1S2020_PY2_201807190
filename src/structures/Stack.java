/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import nodes.Simple_Node;

/**
 *
 * @author Francisco Suarez
 */
public class Stack {

	private Simple_Node peek;

	//Constructor de la pila
	public Stack() {
		this.peek = null;
	}

	//Verifica si la pila esta vacia
	public boolean isEmpty() {
		return peek == null;
	}

	//Inserta datos a la pila
	public void push(Simple_Node data) {
		Simple_Node n = new Simple_Node(data);
		n.setNext(peek);
		peek = n;
	}

	//Desapila la cima de la pila
	public Object pop() {
		if (!isEmpty()) {
			Object aux = peek.getData();
			peek = peek.getNext();
			return aux;
		}
		return null;
	}

	//Obtiene la cima de la pila
	public Object top() {
		if (!isEmpty()) {
			return peek.getData();
		}
		return null;
	}
}
