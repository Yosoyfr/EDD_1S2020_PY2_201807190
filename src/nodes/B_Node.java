/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodes;

import objects.Books;
import reading_files.JsonBlock;
import structures.Simply_Linked_List;

/**
 *
 * @author Francisco Suarez
 */
public class B_Node {

	//Atributos del aux
	//Orden del arbol
	private int order;
	//Arreglo de valores (claves) en el aux
	private Books keys[];
	//Numero actual de valores (claves)
	private int n;
	//Arreglo de auxs childs
	private B_Node childs[];
	//Nodo parent 
	private B_Node parent;
	//Boolean que definira si es hoja o no
	private boolean leaf;
	//Boolean que define si es la raiz o no
	private boolean root;

	//Constructor del aux
	public B_Node(int order, boolean leaf) {
		this.order = order;
		this.childs = new B_Node[order + 1];
		this.keys = new Books[order];
		this.n = 0;
		this.leaf = leaf;
		this.root = false;
	}

	//Metodo para insertar
	public void insert(Books key) {
		int i = n;
		//Insertamos la nueva clave en el nodo
		while (i > 0 && keys[i - 1].getISBN() > key.getISBN()) {
			//Corremos las claves para insertar ordenadamente la nueva
			keys[i] = keys[i - 1];
			i--;
		}
		//Se inserta la clave la posicion ordenada
		keys[i] = key;
		//Se le suma una clave mas
		n++;
		//Si el numero de claves actuales es igual al orden establecido, se debe realizar el split (en este caso se usar orden 5)
		if (n == order) {
			this.split();
		}
		JsonBlock.getInstance().createBook(key);
	}

	//Metodo para realizar el split (particion del nodo)
	public void split() {
		//Si es la raiz, vamos a partirla a la mitad y obtener el dato del medio
		if (root) {
			//Se crean dos nuevos nodos auxiliares (un izquierdo y otro derecho)
			B_Node auxL = new B_Node(order, leaf);
			B_Node auxR = new B_Node(order, leaf);
			//Se asignaran los respectivas claves e childs a cada nuevo nodo
			int i;
			int j = order / 2 + 1;
			for (i = 0; i < order / 2; i++, j++) {
				//A los de la izquierda se le asigna la mitad de izquierda
				//El mismo proceso es para los childs
				auxL.keys[i] = keys[i];
				auxL.childs[i] = childs[i];
				//A los de la derecha se le asigna la mitad de derecha
				auxR.keys[i] = keys[j];
				auxR.childs[i] = childs[j];
				//Se verifica si tiene hoja, si en dado caso no tiene se le asigna como parent a los childs de los nuevos nodos creados
				if (!leaf) {
					auxL.childs[i].parent = auxL;
					auxR.childs[i].parent = auxR;
				}
				//Se sube la cuenta de claves actuales en para cada nodo
				auxL.n++;
				auxR.n++;
			}
			//Se realiza una vez mas el proceso de asignar los nodos childs
			auxL.childs[i] = childs[i];
			auxR.childs[i] = childs[j];
			if (!leaf) {
				auxL.childs[i].parent = auxL;
				auxR.childs[i].parent = auxR;
			}
			//Ahora el nuevo nodo queda con el nodo qeu queda en medio de la particion y se le asignara a la primera cassila
			keys[0] = keys[order / 2];
			//Se le asigna como childs los dos nuevos nodos creados
			childs[0] = auxL;
			childs[1] = auxR;
			//Se le asigna como parent a los childs creados
			auxL.parent = this;
			auxR.parent = this;
			//Este nodo dejara de ser la hoja de insercion ahora
			leaf = false;
			//Se setea el valor de claves actuales
			n = 1;
		} else {
			//Si no es la raiz, es un hijo de la raiz donde se debe insertar el dato
			//Se crea un nodo auxiliar ingresar los childs y las claves de la mitad mas a la derecha
			//(Este mismo proceso se realiza como el de arriba)
			B_Node aux = new B_Node(order, leaf);
			int i = order / 2 + 1;
			int j;
			for (j = 0; j < order / 2; j++, i++) {
				aux.keys[j] = keys[i];
				aux.childs[j] = childs[i];
				if (!leaf) {
					aux.childs[j].parent = aux;
				}
				aux.n++;
			}
			aux.childs[j] = childs[i];
			if (!leaf) {
				aux.childs[j].parent = aux;
			}
			//Asignamos el parent de este auxiliar
			aux.parent = parent;
			n = order / 2;
			int z = parent.n;
			//Ahora recorremos los childs del parent de este nodo, para correrlos hasta la posicion donde encaje
			while (z > 0 && keys[order / 2].getISBN() < parent.keys[z - 1].getISBN()) {
				parent.childs[z + 1] = parent.childs[z];
				z--;
			}

			parent.childs[z + 1] = aux;
			//Se le recurre a insertar con la clave del medio en la nueva hoja establecida
			parent.insert(keys[order / 2]);
		}
	}

	//Metodo para buscar una clave en el arbol
	public B_Node search(int key) {
		int i = 0;
		while (i < n && key > keys[i].getISBN()) {
			i++;
		}
		if (keys[i] != null) {
			if (keys[i].getISBN() == key) {
				return this;
			}
		}
		if (leaf) {
			return null;
		}
		return childs[i].search(key);
	}

	//Metodo para remover una clave del nodo
	public void remove(int key) {
		B_Node aux = search(key);
		if (aux != null) {
			int i = 0;
			while (key != aux.keys[i].getISBN()) {
				i++;
			}
			Books del_Book = aux.keys[i];
			if (!aux.leaf) {
				B_Node temp = aux.childs[i + 1];
				if (!temp.leaf) {
					temp = temp.childs[0];
				}
				aux.keys[i] = temp.keys[0];
				temp.n--;
				int j;
				for (j = 0; j < temp.n; j++) {
					temp.keys[j] = temp.keys[j + 1];
				}
				aux = temp;
			} else {
				while (i < aux.n) {
					aux.keys[i] = aux.keys[i + 1];
					i++;
				}
				aux.n--;

			}
			while (!aux.root && aux.n < order / 2) {
				int j = 0;
				while (aux.parent.childs[j] != aux) {
					j++;
				}
				if (aux.n >= order / 2) {
					return;
				} else if (j != aux.parent.n && aux.parent.childs[j + 1].n > order / 2) {
					aux.allocateLeft(j + 1);
				} else if (j != 0 && aux.parent.childs[j - 1].n > order / 2) {
					aux.allocateRigth(j - 1);
				} else if (aux.parent.root) {
					if (aux.parent.n == 1) {
						aux.mergeRoot();
					} else {
						if (j != aux.parent.n) {
							aux.mergeLeft(j + 1);
						} else {
							aux.mergeRight(j - 1);
						}
					}
					return;
				} else {
					if (j != aux.parent.n) {
						aux.mergeLeft(j + 1);
					} else {
						aux.mergeRight(j - 1);
					}
				}
				aux = aux.parent;
			}
			JsonBlock.getInstance().deleteBook(del_Book);
		}
	}

	//Metodo para reubicar las claves y los nodos cuando se elimina alguna clave de un nodo en la izquierda
	void allocateLeft(int j) {
		keys[n] = parent.keys[j - 1];
		n++;

		childs[n] = parent.childs[j].childs[0];
		if (!leaf) {
			childs[n].parent = this;
		}

		parent.keys[j - 1] = parent.childs[j].keys[0];
		parent.childs[j].n--;
		int i;
		for (i = 0; i < parent.childs[j].n; i++) {
			parent.childs[j].keys[i] = parent.childs[j].keys[i + 1];
			parent.childs[j].childs[i] = parent.childs[j].childs[i + 1];
		}
		parent.childs[j].childs[i] = parent.childs[j].childs[i + 1];
	}

	//Metodo para reubicar las claves y los nodos cuando se elimina alguna clave de un nodo en la derecha
	void allocateRigth(int j) {
		int i = n;

		for (n = i; i > 0; i--) {
			keys[i] = keys[i - 1];
			childs[i + 1] = childs[i];
		}
		childs[i + 1] = childs[i];

		keys[0] = parent.keys[j];
		childs[0] = parent.childs[j].childs[parent.childs[j].n];
		if (!leaf) {
			childs[0].parent = this;
		}
		n++;

		parent.keys[j] = parent.childs[j].keys[parent.childs[j].n - 1];
		parent.childs[j].n--;
	}

	//Metodo para reubicar nodos, al eliminar un nodo que tenia childs en la izquierda
	void mergeLeft(int merge) {
		keys[n] = parent.keys[merge - 1];
		n++;
		int z;
		for (z = 0; z < order / 2; z++) {
			keys[order / 2 + z] = parent.childs[merge].keys[z];
			childs[order / 2 + z] = parent.childs[merge].childs[z];
			if (!leaf) {
				childs[order / 2 + z].parent = this;
			}
			n++;
		}

		childs[order / 2 + z] = parent.childs[merge].childs[z];
		if (!leaf) {
			childs[order / 2 + z].parent = this;
		}

		for (int i = 0; i < parent.n - merge; i++) {
			parent.keys[merge - 1 + i] = parent.keys[merge + i];
			parent.childs[merge + i] = parent.childs[merge + i + 1];
		}
		parent.n--;

	}

	//Metodo para reubicar nodos, al eliminar un nodo que tenia childs en la derecha
	void mergeRight(int merge) {
		int i = order - 2;
		int k = n - 1;

		childs[i + 1] = childs[k + 1];
		for (k = n - 1; k >= 0; i--, k--) {
			keys[i] = keys[k];
			childs[i] = childs[k];
		}

		keys[order / 2] = parent.keys[merge];
		n++;

		int z = 0;
		childs[order / 2] = parent.childs[merge].childs[parent.childs[merge].n];
		if (!leaf) {
			childs[order / 2 + z].parent = this;
		}

		for (z = 0; z < order / 2; z++) {
			keys[order / 2 - 1 - z] = parent.childs[merge].keys[parent.childs[merge].n - 1 - z];
			childs[order / 2 - 1 - z] = parent.childs[merge].childs[parent.childs[merge].n - 1 - z];
			if (!leaf) {
				childs[order / 2 - 1 - z].parent = this;
			}
			n++;
		}

		for (int j = 0; j < parent.n - merge; j++) {
			parent.keys[merge + j] = parent.keys[j + 1 + merge];
			parent.childs[merge + j] = parent.childs[j + 1 + merge];
		}
		parent.n--;
	}

	//Metodo para reubicar nodos, cuando se elimina desde la raiz
	void mergeRoot() {
		if (keys[0].getISBN() < parent.keys[0].getISBN()) {
			mergeLRoot();
		} else {
			mergeRRoot();
		}
	}

	//Reubicca nodos, cuando se elimina en la raiz para reasignar sus childs por la izquierda
	void mergeLRoot() {
		parent.leaf = leaf;
		parent.keys[order / 2 - 1] = parent.keys[0];
		B_Node aux = parent.childs[1];
		int i;
		for (i = 0; i < n; i++) {
			parent.keys[i] = keys[i];
			parent.childs[i] = childs[i];
			if (!parent.leaf) {
				parent.childs[i].parent = parent;
			}
			parent.n++;
		}
		parent.childs[i] = childs[i];
		if (!parent.leaf) {
			parent.childs[i].parent = parent;
		}
		int z;
		for (z = 0; z < aux.n; z++) {
			parent.keys[order / 2 + z] = aux.keys[z];
			parent.childs[order / 2 + z] = aux.childs[z];
			if (!parent.leaf) {
				parent.childs[order / 2 + z].parent = parent;
			}
			parent.n++;
		}
		parent.childs[order / 2 + z] = aux.childs[z];
		if (!parent.leaf) {
			parent.childs[order / 2 + z].parent = parent;
		}

	}

	//Reubicca nodos, cuando se elimina en la raiz para reasignar sus childs por la derecha
	void mergeRRoot() {
		parent.leaf = leaf;
		parent.keys[order / 2] = parent.keys[0];
		B_Node aux = parent.childs[0];
		int i;
		for (i = 0; i < n; i++) {
			parent.keys[order / 2 + 1 + i] = keys[i];
			parent.childs[order / 2 + 1 + i] = childs[i];
			if (!parent.leaf) {
				parent.childs[order / 2 + 1 + i].parent = parent;
			}
			parent.n++;
		}

		parent.childs[order / 2 + 1 + i] = childs[i];
		if (!parent.leaf) {
			parent.childs[order / 2 + 1 + i].parent = parent;
		}

		int z;
		for (z = 0; z < aux.n; z++) {
			parent.keys[z] = aux.keys[z];
			parent.childs[z] = aux.childs[z];
			if (!parent.leaf) {
				parent.childs[z].parent = parent;
			}
			parent.n++;
		}
		parent.childs[z] = aux.childs[z];
		if (!parent.leaf) {
			parent.childs[z].parent = parent;
		}

	}

	// Funcion que atraviesa cada nodo del arbol
	public int traverse() {
		int count = 0;
		int i = 0;
		for (i = 0; i < this.n; i++) {
			if (!this.leaf) {
				count += childs[i].traverse();
			}
			count++;
			//System.out.print(keys[i].getISBN() + " ");
		}
		if (!leaf) {
			count += childs[i].traverse();
		}
		return count;
	}

	public Simply_Linked_List list(String owner) {
		Simply_Linked_List aux = new Simply_Linked_List();
		int i = 0;
		if (owner.equals("")) {
			for (i = 0; i < this.n; i++) {
				if (!this.leaf) {
					aux.merge(childs[i].list(""));
				}
				aux.addLast(keys[i]);
			}
			if (!leaf) {
				aux.merge(childs[i].list(""));
			}
		} else {
			for (i = 0; i < this.n; i++) {
				if (!this.leaf) {
					aux.merge(childs[i].list(owner));
				}
				if (owner.equals(keys[i].getUser())) {
					aux.addLast(keys[i]);
				}
			}
			if (!leaf) {
				aux.merge(childs[i].list(owner));
			}
		}

		return aux;
	}

	//Metodo para obtener el codigo dot del arbol
	public String getDOT(B_Node aux) {
		StringBuilder graph = new StringBuilder();
		graph.append("\"").append(keys[0].getISBN()).append("\"");
		graph.append("[label=\"");

		int i;
		for (i = 0; i < n; i++) {
			if (!leaf) {
				graph.append("<C").append(i).append(">|");
			}
			Books temp = keys[i];
			graph.append("ISBN: ").append(temp.getISBN()).append(" \\n ").append("Title: ").append(temp.getTitle());
			if (i < n - 1) {
				graph.append("|");
			}
		}

		if (!leaf) {
			graph.append("|<C").append(n).append(">");
		}

		graph.append("\"");
		if (aux == this) {
			graph.append(", color=\"black\"");
		}
		graph.append("];\n");

		for (int j = 0; j < n; j++) {
			if (!leaf) {
				graph.append(childs[j].getDOT(aux));
			}
		}

		if (!leaf) {
			graph.append(childs[n].getDOT(aux));
		}

		for (int k = 0; k < n + 1; k++) {
			if (!leaf) {
				graph.append("\"").append(keys[0].getISBN()).append("\":C").append(k).append("->\"").append(childs[k].keys[0].getISBN()).append("\";\n");
			}
		}
		return graph.toString();
	}

	//Acceseores y modificadores
	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public Books getKey(int index) {
		return keys[index];
	}

	public B_Node getChild(int index) {
		return childs[index];
	}

	public void setChild(int index, B_Node value) {
		this.childs[index] = value;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

}
