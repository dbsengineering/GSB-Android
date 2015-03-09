package com.gsb.suividevosfrais;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Classe qui permet de hasher une chaîne de caractères.
 * 
 * @author 		Cavron Jérémy
 * @version		v 1.0
 */
public class Encrypte {
	
	
	/**
	 * Constructeur de la classe
	 */
	public Encrypte() {
		
	}
	
	
	/**
     * Fonction de hash qui permet de hasher une chaîne
     * et de retourner son hash.
     * 
     * @param motDePasse : est le mot de passe à hasher.
     * 
     * @return hashString.toString() : est le résultat hashé.
     */
	public String getEncodePassword(String motDePasse) { 
 
		byte[] uniqueKey = motDePasse.getBytes(); 
		byte[] hash = null; 
 
		try { 
			hash = MessageDigest.getInstance("SHA-1").digest(uniqueKey); //MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512
 
		} 
		catch (NoSuchAlgorithmException e) { 
			throw new Error("Pas de support SHA-1"); 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
 
		StringBuffer hashString = new StringBuffer(); 
		for ( int i = 0; i < hash.length; ++i ) { 
			String hex = Integer.toHexString(hash[i]); 
			if ( hex.length() == 1 ) { 
				hashString.append('0'); 
				hashString.append(hex.charAt(hex.length()-1)); 
			} else { 
				hashString.append(hex.substring(hex.length()-2)); 
			} 
		}
		return hashString.toString(); 
    }
	
}
