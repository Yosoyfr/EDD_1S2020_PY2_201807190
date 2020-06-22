/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodes;

import objects.Books;
import reports.Report;
import structures.B_Tree;
import structures.Simply_Linked_List;

/**
 *
 * @author Francisco Suarez
 */
public class AVL_Node extends Tree_Node {

	//Balance
	private int balance;
	//Arbol B
	private B_Tree treeB;
	//Propietario de la categoria
	private String owner;

	public AVL_Node(Object data) {
		super(data);
		this.balance = 0;
		treeB = new B_Tree(5);
	}

	//Accesores y modificadores de los atributos
	public int getBalance() {
		return balance;
	}

	public B_Tree getTreeB() {
		return treeB;
	}

	public void setTreeB(B_Tree treeB) {
		this.treeB = treeB;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	//Inserta el libro en la categoria
	public void insertBook(Books data) {
		this.treeB.insert(data);
	}

	//Obitene el reporte del arbol b en el nodo actual
	public String getReportTreeB() {
		if (!this.treeB.isEmpty()) {
			return this.treeB.getRoot().getDOT(this.treeB.getRoot());
		} else {
			System.out.println("\"Esta categoria aun no contiene libros\"");
			return "Esta categoria aun no contiene libros";
		}
	}

	//Metodo que me retorna todos los libros en el arbol avl
	public Simply_Linked_List allBooks(AVL_Node r, String owner) {
		Simply_Linked_List aux = new Simply_Linked_List();
		if (r != null) {
			aux.merge(allBooks((AVL_Node) r.getLeft(), owner));
			aux.merge(r.treeB.list(owner));
			aux.merge(allBooks((AVL_Node) r.getRight(), owner));
		}
		return aux;
	}

	//Metodo que me retorna la lista de libros en este nodo
	public Simply_Linked_List listBooks(String owner) {
		return treeB.list(owner);
	}

	//Metodo para obtener las categorias
	public Simply_Linked_List getCategories(Tree_Node r) {
		Simply_Linked_List aux = new Simply_Linked_List();
		if (r != null) {
			aux.merge(getCategories(r.getLeft()));
			aux.addLast(r.getData());
			aux.merge(getCategories(r.getRight()));
		}
		return aux;
	}

	//Metodo para obtener las categorias
	public Simply_Linked_List getCategories(AVL_Node r, String owner) {
		Simply_Linked_List aux = new Simply_Linked_List();
		if (r != null) {
			aux.merge(getCategories((AVL_Node) r.getLeft(), owner));
			if (r.owner.equals(owner)) {
				aux.addLast(r.getData());
			}
			aux.merge(getCategories((AVL_Node) r.getRight(), owner));
		}
		return aux;
	}

	//Variable que me indica si se encontro el libro
	private boolean find = false;

	//Accesores y modificadores
	public boolean isFind() {
		return find;
	}

	public void setFind(boolean find) {
		this.find = find;
	}

	//Busca si ya existe el isbn en alguna categoria
	public void contains(AVL_Node aux, int data) {
		if (aux != null) {
			//System.out.println("La categoria es: " + aux.getData());
			boolean found = false;
			if (!aux.treeB.isEmpty()) {
				found = found(aux, data);
			}
			if (found) {
				find = true;
				return;
			} else {
				contains((AVL_Node) aux.getLeft(), data);
				contains((AVL_Node) aux.getRight(), data);
			}
		}
	}

	//Recorridos
	//Recorrido del arbol en preorden
	public static Simply_Linked_List preOrder(AVL_Node r) {
		Simply_Linked_List aux = new Simply_Linked_List();
		if (r != null) {
			aux.addLast(r);
			aux.merge(preOrder((AVL_Node) r.getLeft()));
			aux.merge(preOrder((AVL_Node) r.getRight()));
		}
		return aux;
	}

	//Recorrido del arbol inorden
	public static Simply_Linked_List inOrder(AVL_Node r) {
		Simply_Linked_List aux = new Simply_Linked_List();
		if (r != null) {
			aux.merge(inOrder((AVL_Node) r.getLeft()));
			aux.addLast(r);
			aux.merge(inOrder((AVL_Node) r.getRight()));
		}
		return aux;
	}

	//Recorrido del arbol postorden
	public static Simply_Linked_List postOrder(AVL_Node r) {
		Simply_Linked_List aux = new Simply_Linked_List();
		if (r != null) {
			aux.merge(postOrder((AVL_Node) r.getLeft()));
			aux.merge(postOrder((AVL_Node) r.getRight()));
			aux.addLast(r);
		}
		return aux;
	}

	//Funcion que devuelve true si existe el isbn en el arbol recorrido
	boolean found(AVL_Node aux, int data) {
		return aux.treeB.getRoot().search(data) != null;
	}

	//Funcion para remover si es qeu existe el libro en el arbol de este nodo
	public void removeBook(int key) {
		this.treeB.remove(key);
	}

	//Funcion que me devuelve un libro
	public Books getBook(int key) {
		int i = 0;
		while (key > this.treeB.getRoot().search(key).getKey(i).getISBN()) {
			i++;
		}
		return this.treeB.getRoot().search(key).getKey(i);
	}

	//Funcion que muestra todos los nodos del arbol b
	public int traverse() {
		return this.treeB.traverse();
	}

	//Accesor del propietario
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}
