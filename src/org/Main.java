/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org;

import structures.AVL_Tree;
import structures.Doubly_Linked_List;
import structures.HashTable;
import structures.Simply_Linked_List;
import views.Settings_Form;

/**
 *
 * @author Francisco Suarez
 */
public class Main {

	//Estructuras
	public static AVL_Tree treeAVL = new AVL_Tree();
	public static HashTable hashTable = new HashTable(45);
	//Bloques creados por este nodo
	public static Doubly_Linked_List blockchainInstance = new Doubly_Linked_List();
	//Nodos de la red
	public static Simply_Linked_List networkNodes = new Simply_Linked_List();
	//Variables del logueo global
	public static String user = "";
	//Ip de esta instacia
	public static String ipInstance = "";
	//Puerto de esta instacia
	public static int portInstance = 0;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		Settings_Form set = new Settings_Form();
		set.setVisible(true);
	}

}
