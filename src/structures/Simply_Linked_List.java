/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import java.io.Serializable;
import nodes.Simple_Node;

/**
 *
 * @author Francisco Suarez
 */
public class Simply_Linked_List implements Serializable{

	private Simple_Node first;
	private Simple_Node last;
	private int size;

	//Constructor vacio de la lista
	public Simply_Linked_List() {
		this.first = this.last = null;
		this.size = 0;
	}

	//Verifica si la lista esta vacia 
	public boolean isEmpty() {
		return this.first == null;
	}

	//Inserta datos a la lista, por la cabeza
	public void addFirst(Object data) {
		Simple_Node n = new Simple_Node(data);
		if (isEmpty()) {
			this.first = this.last = n;
		} else {
			n.setNext(this.first);
			this.first = n;
		}
		this.size++;
	}
	
	//Inserta datos a la lista, por el final
	public void addLast(Object data) {
		Simple_Node n = new Simple_Node(data);
		if (isEmpty()) {
			this.first = this.last = n;
		} else {
			this.last.setNext(n);
			this.last = n;
		}
		this.size++;
	}

	//Insertar datos en orden (Modificar este metodo a modo de que los datos se ordenen dependiendo el factor que se busca) && En este caso esta de mayor a menor
	//No se tomara en cuenta el ultimo nodo (Se trabajara solo con el primero)
	//Este caso estara hecho para enteros
	public void insertInOrder(int data) {
		Simple_Node n = new Simple_Node(data);
		if (isEmpty()) {
			this.first = n;
		} else if (data > (int) this.first.getData()) {
			n.setNext(this.first);
			this.first = n;
		} else {
			Simple_Node aux, prev;
			aux = prev = this.first;

			while (aux.getNext() != null && data < (int) aux.getData()) {
				prev = aux;
				aux = aux.getNext();
			}
			if (data < (int) aux.getData()) {
				prev = aux;
			}
			n.setNext(prev.getNext());
			prev.setNext(n);
		}
		this.size++;
	}

	//Busqueda en la lista (Retorna el nodo)
	public Simple_Node search(Object element) {
		Simple_Node index;
		for (index = this.first; index != null; index = index.getNext()) {
			if (element == index.getData()) {
				return index;
			}
		}
		return null;
	}

	//Busqueda de la posicion del elemento en la lista
	int indexOf(Object element) {
		Simple_Node aux = this.first;
		int index = 0;
		while (aux != null) {
			if (aux.getData() == element) {
				return index;
			}
			index++;
			aux = aux.getNext();
		}
		return -1;
	}

	//Metodos para eliminar de la lista
	public void remove(Object element) {
		Simple_Node aux = this.first;
		Simple_Node prev = null;
		boolean found = false;

		//Buscamos el nodo que deseamos encontrar y su anterior
		while ((aux != null) && !found) {
			found = aux.getData().equals(element);
			if (!found) {
				prev = aux;
				aux = aux.getNext();
			}
		}

		//Si se encontro el nodo a eliminiar
		if (aux != null) {
			if (aux == this.first) {
				if (this.first == this.last) {
					this.first = this.last = null;
				} else {
					this.first = aux.getNext();
				}
			} else {
				prev.setNext(aux.getNext());
			}
		}
	}

	//Imprime los datos de la lista en consola
	public void print() {
		Simple_Node aux = this.first;
		while (aux != null) {
			System.out.println("El dato es: " + aux.getData());
			aux = aux.getNext();
		}
	}

	//Metodo para unir una lista con la actul
	public void merge(Simply_Linked_List list) {
		Simple_Node aux = list.first;
		while (aux != null) {
			this.addLast(aux.getData());
			aux = aux.getNext();
		}
	}

	//Retorna el tamaño de la lista
	public int getSize() {
		return this.size;
	}

	//Retorna la cabeza
	public Simple_Node getFirst() {
		return this.first;
	}

}
