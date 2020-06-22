/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 *
 * @author Francisco Suarez
 */
public class ValidateJTextField {

	public void containsNumbers(JTextField txt) {
		txt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if ((!Character.isDigit(c))) {
					e.consume();
				}
			}
		});
	}

	public void containsLetters(JTextField txt) {
		txt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (Character.isDigit(c)) {
					e.consume();
				}
			}
		});
	}

	public void limitNumbers(JTextField campo, int numero) {
		campo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				int cantidad = campo.getText().length();
				if (cantidad >= numero) {
					e.consume();
				}
			}
		});
	}

	public void formatIP(JTextField txt) {
		txt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if ((!Character.isDigit(c)) && c != '.') {
					e.consume();
				}
			}
		});
	}
}
