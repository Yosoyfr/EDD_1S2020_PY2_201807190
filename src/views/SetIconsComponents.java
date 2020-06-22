/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Francisco Suarez
 */
public class SetIconsComponents {

	private static SetIconsComponents instance;

	public static SetIconsComponents getInstance() {
		if (instance == null) {
			instance = new SetIconsComponents();
		}
		return instance;
	}

	//Funcion para colocar imagenes
	public void setImages(String route, JLabel lbl) {
		ImageIcon image = new ImageIcon(getClass().getResource(route));
		Icon icon = new ImageIcon(image.getImage().getScaledInstance(lbl.getWidth(), lbl.getHeight(), Image.SCALE_DEFAULT));
		lbl.setIcon(icon);
	}

	public void setImages(String route, JButton btn) {
		ImageIcon image = new ImageIcon(getClass().getResource(route));
		Icon icon = new ImageIcon(image.getImage().getScaledInstance(btn.getWidth(), btn.getHeight(), Image.SCALE_DEFAULT));
		btn.setIcon(icon);
	}

}
