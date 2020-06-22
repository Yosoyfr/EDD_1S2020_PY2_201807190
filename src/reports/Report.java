/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports;

import java.io.FileWriter;

/**
 *
 * @author Francisco Suarez
 */
public class Report {

	//Constructor vacio
	public Report() {
	}

	//Funcion para crear el reporte 
	public String generateTB(String res, String lbl) {
		String graph = "digraph G{\n "
				+ "rankdir=TB;\n"
				+ "labelloc = \"t\";\n"
				+ "node [shape=record];\n";

		graph += res;

		graph += "graph[label=\"" + lbl + ".\"];\n"
				+ "}";
		return graph;
	}

	public String generateLR(String res, String lbl) {
		String graph = "digraph G{\n "
				+ "rankdir=LR;\n"
				+ "labelloc = \"t\";\n"
				+ "node [shape=record];\n";

		graph += res;

		graph += "graph[label=\"" + lbl + ".\"];\n"
				+ "}";
		return graph;
	}

	//Metodo para generar la imagen
	public void generateImage(String graph, String title) {
		FileWriter file = null;
		try {
			String name = title + ".dot";
			file = new FileWriter(name);
			file.write(graph);
			file.close();
			String image = title + ".png";
			Runtime rt = Runtime.getRuntime();
			rt.exec("dot -Tpng " + name + " -o " + image);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
