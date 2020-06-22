/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.components;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

/**
 *
 * @author Francisco Suarez
 */
public class Label_Custom extends JLabel {

	public Label_Custom(String text, int size) {
		this.setFont(new Font("Berlin Sans FB", Font.PLAIN, size));
		this.setText(text);
		this.setForeground(Color.white);
	}
	
}
