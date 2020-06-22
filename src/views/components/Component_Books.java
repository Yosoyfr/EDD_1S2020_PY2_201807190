/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.components;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Francisco Suarez
 */
public class Component_Books extends JPanel {

	private JLabel cover;
	private Label_Custom lbl_info;
	private int width = 325;
	private int heigth = 525;

	public Component_Books() {
		this.setBounds(0, 0, width, heigth);
		this.setLayout(null);
		this.setBackground(new Color(18, 30, 49));
		String info = "<html>"
				+ "<p>Selecciona un libro :D</p>"
				+ "</html>";
		this.lbl_info = new Label_Custom(info, 36);
		this.lbl_info.setBounds(5, 0, width, heigth);
		this.add(this.lbl_info);
	}

	public Component_Books(int x, int y, String isbn, String title, String author, String editorial, String year, String edition, String lenguage, String user) {
		this.setBounds(x, y, width, heigth);
		this.setLayout(null);
		components(isbn, title, author, editorial, year, edition, lenguage, user);
		//this.setBackground(new Color(64, 74, 84));
		this.setBackground(new Color(18, 30, 49));
		//setBack();
	}

	public void components(String isbn, String title, String author, String editorial, String year, String edition, String lenguage, String user) {
		this.cover = new JLabel();
		this.cover.setText("");
		//Etiqueta tipo Imagen
		ImageIcon book = new ImageIcon(getClass().getResource("/views/imgs/img.jpg"));
		this.cover.setBounds(7, 7, width - 14, 193);
		this.cover.setIcon(new ImageIcon(book.getImage().getScaledInstance(this.cover.getWidth(), this.cover.getHeight(), Image.SCALE_SMOOTH)));
		this.add(this.cover);

		//Informacion del libro
		String info = "<html>"
				+ "<p><b>#ISBN: </b> " + isbn + "</p>"
				+ "<p><b>#TITULO: </b> " + title + "</p>"
				+ "<p><b>#AUTOR: </b> " + author + "</p>"
				+ "<p><b>#EDITORIAL: </b> " + editorial + "</p>"
				+ "<p><b>#EDICION: </b> " + edition + " - <b>#AÑO: </b> " + year + "</p>"
				+ "<p><b>#IDIOMA: </b> " + lenguage + "</p>"
				+ "<p><b>#USUARIO: </b> " + user + "</p>"
				+ "</html>";
		//Labels
		this.lbl_info.setText("");
		this.lbl_info = new Label_Custom(info, 19);
		this.lbl_info.setBounds(10, 200, width - 15, heigth - 200);
		this.add(this.lbl_info);
	}

	public void setBack() {
		ImageIcon bg = new ImageIcon(getClass().getResource("/views/imgs/bg.jpg"));
		JLabel background = new JLabel();
		background.setText("");
		background.setBounds(0, 0, width, heigth);
		background.setIcon(new ImageIcon(bg.getImage().getScaledInstance(background.getWidth(), background.getHeight(), Image.SCALE_SMOOTH)));
		this.add(background);
	}
}
