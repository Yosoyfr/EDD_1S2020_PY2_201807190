/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import nodes.AVL_Node;
import nodes.Simple_Node;
import objects.Books;
import reading_files.JsonBlock;

/**
 *
 * @author Francisco Suarez
 */
public class AVL_Tree {

	private AVL_Node root;

	public AVL_Tree() {
		this.root = null;
	}

	//Verifica si esta vacio
	public boolean isEmpty() {
		return this.root == null;
	}

	private AVL_Node rotationLL(AVL_Node aux, AVL_Node aux1) {
		aux.setLeft(aux1.getRight());
		aux1.setRight(aux);

		//Se actualiza el balanceo
		if (aux1.getBalance() == -1) {
			aux.setBalance(0);
			aux1.setBalance(0);
		} else {
			aux.setBalance(-1);
			aux1.setBalance(1);
		}
		return aux1;
	}

	private AVL_Node rotationRR(AVL_Node aux, AVL_Node aux1) {
		aux.setRight(aux1.getLeft());
		aux1.setLeft(aux);

		//Se actualiza el balanceo
		if (aux1.getBalance() == 1) {
			aux.setBalance(0);
			aux1.setBalance(0);
		} else {
			aux.setBalance(1);
			aux1.setBalance(-1);
		}
		return aux1;
	}

	private AVL_Node rotationLR(AVL_Node aux, AVL_Node aux1) {
		AVL_Node aux2 = (AVL_Node) aux1.getRight();
		aux.setLeft(aux2.getRight());
		aux2.setRight(aux);
		aux1.setRight(aux2.getLeft());
		aux2.setLeft(aux1);

		//Se actualiza el balanceo
		if (aux2.getBalance() == 1) {
			aux1.setBalance(-1);
		} else {
			aux1.setBalance(0);
		}

		if (aux2.getBalance() == -1) {
			aux.setBalance(1);
		} else {
			aux.setBalance(0);
		}
		aux2.setBalance(0);
		return aux2;
	}

	private AVL_Node rotationRL(AVL_Node aux, AVL_Node aux1) {
		AVL_Node aux2 = (AVL_Node) aux1.getLeft();
		aux.setRight(aux2.getLeft());
		aux2.setLeft(aux);
		aux1.setLeft(aux2.getRight());
		aux2.setRight(aux1);

		//Se actualiza el balanceo
		if (aux2.getBalance() == 1) {
			aux.setBalance(-1);
		} else {
			aux.setBalance(0);
		}

		if (aux2.getBalance() == -1) {
			aux1.setBalance(1);
		} else {
			aux1.setBalance(0);
		}
		aux2.setBalance(0);
		return aux2;
	}

	//Metodo para insertar
	public boolean insert(Books data) {
		this.existCategory = false;
		if (!contains(data.getISBN())) {
			Logical log = new Logical(false);
			this.root = insert(this.root, data, log);
			return !existCategory;
		} else {
			System.out.println("Ya existe el libro en la biblioteca " + data.getISBN() + " ----- " + data.getTitle());
			return false;
		}
	}

	//Varible que me dice si la categoria existe o no
	boolean existCategory = false;

	//Metodo que inserta
	AVL_Node insert(AVL_Node aux, Books data, Logical log) {
		AVL_Node aux1;
		if (aux == null) {
			aux = new AVL_Node(data.getCategory());
			aux.setOwner(data.getUser());
			log.setLog(true);
			JsonBlock.getInstance().createCategory(data.getCategory(), data.getUser());
			if (data.getTitle() != "") {
				aux.insertBook(data);
			}
		} else {
			if (data.getCategory().compareTo((String) aux.getData()) < 0) {
				AVL_Node left_Aux = insert((AVL_Node) aux.getLeft(), data, log);
				aux.setLeft(left_Aux);
				if (log.isLog()) {
					//Modificamos los balances
					switch (aux.getBalance()) {
						case 1:
							aux.setBalance(0);
							log.setLog(false);
							break;
						case 0:
							aux.setBalance(-1);
							break;
						case -1:
							aux1 = (AVL_Node) aux.getLeft();
							if (aux1.getBalance() == -1) {
								aux = rotationLL(aux, aux1);
							} else {
								aux = rotationLR(aux, aux1);
							}
							log.setLog(false);
							break;
					}
				}
			} else if (data.getCategory().compareTo((String) aux.getData()) > 0) {
				AVL_Node right_Aux = insert((AVL_Node) aux.getRight(), data, log);
				aux.setRight(right_Aux);
				if (log.isLog()) {
					//Modificamos los balances
					switch (aux.getBalance()) {
						case 1:
							aux1 = (AVL_Node) aux.getRight();
							if (aux1.getBalance() == 1) {
								aux = rotationRR(aux, aux1);
							} else {
								aux = rotationRL(aux, aux1);
							}
							log.setLog(false);
							break;
						case 0:
							aux.setBalance(1);
							break;
						case -1:
							aux.setBalance(0);
							log.setLog(false);
							break;
					}
				}
			} else {
				//Esto para evitar que no se inserte una categoria que ya existia
				if (data.getTitle() != "") {
					aux.insertBook(data);
				} else {
					this.existCategory = true;
					System.out.println("Esta categoria ya existe: " + data.getCategory());
				}
			}
		}
		return aux;
	}

	//Accesor de la raix
	public AVL_Node getRoot() {
		return root;
	}

	//Reporte
	public String getDOT() {
		if (!isEmpty()) {
			//rep.getDOT(this.root.getDOT(), "Arbol AVL");
			return this.root.getDOT();
		} else {
			System.out.println("Aun no se encuentra registrada ninguna categoria");
			return "\"Aun no se encuentra registrada ninguna categoria\"";
		}
	}

	//Metodo para buscar algun nodo del arbol
	public AVL_Node search(AVL_Node aux, String data) {
		if (aux == null) {
			return null;
		} else if (data.compareTo((String) aux.getData()) == 0) {
			return aux;
		} else if (data.compareTo((String) aux.getData()) < 0) {
			return search((AVL_Node) aux.getLeft(), data);
		} else {
			return search((AVL_Node) aux.getRight(), data);
		}
	}

	//Recorridos del arbol
	public String getPreOrder() {
		return getNodes(AVL_Node.preOrder(this.root));
	}

	public String getInOrder() {
		return getNodes(AVL_Node.inOrder(this.root));
	}

	public String getPostOrder() {
		return getNodes(AVL_Node.postOrder(this.root));
	}

	public String getNodes(Simply_Linked_List travels) {
		String graph = "";
		for (Simple_Node node = travels.getFirst(); node != null; node = node.getNext()) {
			graph += "\"" + ((AVL_Node) node.getData()).getData().toString() + "\" [label = \"" + ((AVL_Node) node.getData()).getData().toString() + "\\n" + "Libros: " + ((AVL_Node) node.getData()).traverse() + "\"];\n";
			if (node.getNext() != null) {
				graph += "\"" + ((AVL_Node) node.getData()).getData().toString() + "\"" + " -> " + "\"" + ((AVL_Node) node.getNext().getData()).getData().toString() + "\"" + "\n";
			}
		}
		return graph;
	}

	//Metodo para buscar isbn
	public boolean contains(int key) {
		if (!isEmpty()) {
			this.root.setFind(false);
			this.root.contains(this.root, key);
			return this.root.isFind();
		}
		return false;
	}

	//Metodo que me retorna todos los libros en el arbol avl
	public Simply_Linked_List allBooks(String owner) {
		if (!isEmpty()) {
			return this.root.allBooks(this.root, owner);
		}
		return new Simply_Linked_List();
	}

	//Metodo que me retorna todas las categorias
	public Simply_Linked_List getCategories() {
		if (!isEmpty()) {
			return this.root.getCategories(this.root);
		}
		return new Simply_Linked_List();
	}

	//Metodo que me retorna todas las categorias de un usuario
	public Simply_Linked_List getCategories(String owner) {
		if (!isEmpty()) {
			return this.root.getCategories(this.root, owner);
		}
		return new Simply_Linked_List();
	}

	//Metodo para eliminar un libro que se encuentre en el arbol B de algun nodo
	public void removeBook(String category, int key) {
		AVL_Node aux = search(this.root, category);
		if (aux != null) {
			aux.removeBook(key);
		} else {
			System.out.println("Categoria no encontrada para eliminar el libro");
		}
	}

	//Metodo que me retorna un libro
	public Books getBook(String category, int key) {
		return search(this.root, category).getBook(key);
	}

	//Metodo para eliminar un nodo del arbol AVL
	public void remove(String data) {
		Logical log = new Logical(false);
		this.root = remove(this.root, data, log);
	}

	AVL_Node remove(AVL_Node aux, String data, Logical log) {
		if (aux == null) {
			System.out.println("Categoria no encontrado");
		} else if (data.compareTo((String) aux.getData()) < 0) {
			AVL_Node left_Aux = remove((AVL_Node) aux.getLeft(), data, log);
			aux.setLeft(left_Aux);
			if (log.isLog()) {
				aux = setBalanceL(aux, log);
			}
		} else if (data.compareTo((String) aux.getData()) > 0) {
			AVL_Node right_Aux = remove((AVL_Node) aux.getRight(), data, log);
			aux.setRight(right_Aux);
			if (log.isLog()) {
				aux = setBalanceR(aux, log);
			}
		} else {
			//Este sera el nodo que debemos quitar
			AVL_Node tmp = aux;
			String cat = (String) aux.getData();
			if (tmp.getLeft() == null) {
				aux = (AVL_Node) tmp.getRight();
				log.setLog(true);
			} else if (tmp.getRight() == null) {
				aux = (AVL_Node) tmp.getLeft();
				log.setLog(true);
			} else {
				//Contiene ambas ramas
				AVL_Node node = replace(aux, (AVL_Node) aux.getLeft(), log);
				aux.setLeft(node);
				if (log.isLog()) {
					aux = setBalanceL(aux, log);
				}
			}
			System.out.println("Se elimino el nodo del AVL ------------------------------------- " + data);
			JsonBlock.getInstance().deleteCategory(cat);
			tmp = null;
		}
		return aux;
	}

	AVL_Node replace(AVL_Node tmp, AVL_Node aux, Logical log) {
		if (aux.getRight() != null) {
			AVL_Node right_Aux = replace(tmp, (AVL_Node) aux.getRight(), log);
			aux.setRight(right_Aux);
			if (log.isLog()) {
				aux = setBalanceR(aux, log);
			}
		} else {
			tmp.setData(aux.getData());
			tmp.setTreeB(aux.getTreeB());
			tmp.setOwner(aux.getOwner());
			tmp = aux;
			aux = (AVL_Node) aux.getLeft();
			tmp = null;
			log.setLog(true);
		}
		return aux;
	}

	AVL_Node setBalanceL(AVL_Node aux, Logical log) {
		AVL_Node tmp;
		switch (aux.getBalance()) {
			case -1:
				aux.setBalance(0);
				break;
			case 0:
				aux.setBalance(1);
				log.setLog(false);
				break;
			case 1:
				tmp = (AVL_Node) aux.getRight();
				if (tmp.getBalance() >= 0) {
					if (tmp.getBalance() == 0) {
						log.setLog(false);
					}
					aux = rotationRR(aux, tmp);
				} else {
					aux = rotationRL(aux, tmp);
				}
				break;
		}
		return aux;
	}

	AVL_Node setBalanceR(AVL_Node aux, Logical log) {
		AVL_Node tmp;
		switch (aux.getBalance()) {
			case -1:
				tmp = (AVL_Node) aux.getLeft();
				if (tmp.getBalance() <= 0) {
					if (tmp.getBalance() == 0) {
						log.setLog(false);
					}
					aux = rotationLL(aux, tmp);
				} else {
					aux = rotationLR(aux, tmp);
				}
				break;
			case 0:
				aux.setBalance(-1);
				log.setLog(false);
				break;
			case 1:
				aux.setBalance(0);
				break;
		}
		return aux;
	}

}

class Logical {

	private boolean log;

	//Constructor
	public Logical(boolean log) {
		this.log = log;
	}

	//Accesores y modificadores
	public boolean isLog() {
		return log;
	}

	public void setLog(boolean log) {
		this.log = log;
	}

}
