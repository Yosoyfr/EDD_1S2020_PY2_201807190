/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodes;

import java.io.Serializable;

/**
 *
 * @author Francisco Suarez
 */
public class Simple_Node implements Serializable{
	//El dato que contendra el nodo
	private Object data;
	//Enlace siguiente del nodo
	private Simple_Node next;

	//Constructor vacio del nodo
	public Simple_Node() {
	}

	public Simple_Node(Object data) {
		this.data = data;
		this.next = null;
	}

	public Simple_Node(Object data, Object type) {
		this.data = data;
		this.next = this;
	}
	
	
	//Accesores y modificadores de los atributos del nodo
	public Object getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public Simple_Node getNext() {
		return next;
	}

	public void setNext(Simple_Node next) {
		this.next = next;
	}
}
