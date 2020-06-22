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
public class Tree_Node {

	//El dato que contendra el nodo
	private Object data;
	//Enlace siguiente del nodo
	private Tree_Node left;
	//Enlace anterior del nodo
	private Tree_Node right;

	//Constructor del nodo
	public Tree_Node(Object data) {
		this.data = data;
		this.left = this.right = null;
	}

	//Accesores y modificadores de los atributos del nodo
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Tree_Node getLeft() {
		return left;
	}

	public void setLeft(Tree_Node left) {
		this.left = left;
	}

	public Tree_Node getRight() {
		return right;
	}

	public void setRight(Tree_Node right) {
		this.right = right;
	}

	//Funcion para obtener el dot
	public String getDOT() {
		String graph = "";
		if (left == null && right == null) {
			graph = "\"" + getData().toString() + "\" [label = \"" + getData().toString() + "\\n" + "Libros: " + ((AVL_Node) this).traverse() + "\"];\n";
		} else {
			graph = "\"" + getData().toString() + "\" [label = \"<C0>|" + getData().toString() + "\\n" + "Libros: " + ((AVL_Node) this).traverse() + "|<C1>\"];\n";
		}

		if (left != null) {
			graph += left.getDOT() + "\"" + getData() + "\":C0->\"" + left.getData().toString() + "\";\n";
		}
		if (right != null) {
			graph += right.getDOT() + "\"" + getData() + "\":C1->\"" + right.getData().toString() + "\";\n";
		}
		return graph;
	}
	
	//Recorrido del arbol en preorden
	public static void preOrder(Tree_Node r) {
		if (r != null) {
			r.visit();
			preOrder(r.getLeft());
			preOrder(r.getRight());
		}
	}

	//Recorrido del arbol inorden
	public static void inOrder(Tree_Node r) {
		if (r != null) {
			inOrder(r.getLeft());
			r.visit();
			inOrder(r.getRight());
		}
	}

	//Recorrido del arbol postorden
	public static void postOrder(Tree_Node r) {
		if (r != null) {
			postOrder(r.getLeft());
			postOrder(r.getRight());
			r.visit();
		}
	}

	void visit() {
		System.out.println("<" + getData().toString() + "> ");
	}

	String visitDOT() {
		return getData().toString();
	}
}
