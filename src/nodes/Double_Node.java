/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodes;

/**
 *
 * @author Francisco Suarez
 */
public class Double_Node {

	//El dato que contendra el nodo
	private Object data;
	//Enlace siguiente del nodo
	private Double_Node next;
	//Enlace anterior del nodo
	private Double_Node previous;

	//Constructor vacio del nodo
	public Double_Node() {
	}

	public Double_Node(Object data) {
		this.next = this.previous = null;
		this.data = data;
	}

	public Double_Node(Object data, Object type) {
		this.next = this;
		this.previous = this;
		this.data = data;
	}

	//Accesores y modificadores de los atributos del nodo
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Double_Node getNext() {
		return next;
	}

	public void setNext(Double_Node next) {
		this.next = next;
	}

	public Double_Node getPrevious() {
		return previous;
	}

	public void setPrevious(Double_Node previous) {
		this.previous = previous;
	}

}
