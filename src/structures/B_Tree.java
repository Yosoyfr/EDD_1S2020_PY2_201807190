/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import nodes.B_Node;
import objects.Books;

/**
 *
 * @author Francisco Suarez
 */
public class B_Tree {

	//Atributos de arbol
	//Raiz del arbol
	private B_Node root;
	//Orden del arbol
	private int order;

	//Constructor del arbol
	public B_Tree(int order) {
		this.order = order;
		this.root = null;
	}

	public boolean isEmpty() {
		return this.root == null;
	}

	public void insert(Books key) {
		if (isEmpty()) {
			this.root = new B_Node(this.order, true);
			this.root.setRoot(true);
			this.root.insert(key);
		} else {
			B_Node aux = this.root;
			while (!aux.isLeaf()) {
				int i = 0;
				while (key.getISBN() > aux.getKey(i).getISBN() && i < aux.getN()) {
					if (aux.getKey(i + 1) == null) {
						i++;
						break;
					}
					i++;
				}
				aux = aux.getChild(i);
			}
			aux.insert(key);
		}
	}

	public void remove(int key) {
		if (!isEmpty()) {
			this.root.remove(key);
		}

	}

	public void print() {
		System.out.println(this.root.getDOT(this.root));
	}

	public B_Node getRoot() {
		return root;
	}

	// Funcion que atraviesa cada nodo del arbol para mostrarlo
	public int traverse() {
		if (!isEmpty()) {
			return this.root.traverse();
		}
		return 0;
	}

	public Simply_Linked_List list(String owner) {
		if (!isEmpty()) {
			return this.root.list(owner);
		}
		return new Simply_Linked_List();
	}
}
