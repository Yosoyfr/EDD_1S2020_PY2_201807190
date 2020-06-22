/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Suarez
 */
public class HashPassword {

	private static HashPassword instance;

	public static HashPassword getInstance() {
		if (instance == null) {
			instance = new HashPassword();
		}
		return instance;
	}

	//Forma uno de realizar HashMd5
	public String encrypt(String key) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(StandardCharsets.UTF_8.encode(key));
		return String.format("%032x", new BigInteger(1, md.digest()));
	}

	//Forma dos de realizar HashMd5
	public String hashPassword(String key) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(key.getBytes());
		byte[] b = md.digest();
		StringBuilder sb = new StringBuilder();
		for (byte m : b) {
			sb.append(Integer.toHexString(m & 0xff));
		}
		return sb.toString();
	}
	
	//Hash del blockchain SHA256
	public String hashBlock(String key){
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(StandardCharsets.UTF_8.encode(key));
			return String.format("%064x", new BigInteger(1, md.digest()));
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(HashPassword.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

}
