package com.gsb.suividevosfrais;


/**
 * Identifiant.java
 * 
 * Classe Identifiant. Permet de créer l'objet Identifiant.
 * 
 * @author      Cavron Jérémy
 * @version     v 1.0
 */
public class Identifiant {

	private int id;
	private String mdp; // Mot de passe hashé
	private String login; //Login du visiteur
	
	
	
	/**
	 * Constructeur 1 de la classe Identifiant.
	 */
	public Identifiant() {
		
	}
	
	/**
	 * Constructeur 2 de la classe SerLogin.
	 * 
	 * @param login : est le login visiteur.
	 * @param mdp : est le mot de apsse visiteur hashé.
	 */
	public Identifiant(String login, String mdp){
		this.login = login;
		this.mdp = mdp;
	}
	
	// --- Les Setters ---
	
	/**
	 * Procédure qui permet d'initialiser l'id.
	 * 
	 * @param id est l'id de l'identifiant.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Procédure qui permet d'attribuer la valeur
	 * mdp à l'attribut mdp de la classe.
	 * 
	 * @param mdp : est le mot de passe hashé.
	 */
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
	/**
	 * Procédure qui permet d'attribuer la valeur
	 * login à l'attribut login de la classe.
	 * 
	 * @param login : est le login du visiteur.
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	
	// --- Les Getters ---
	
	/**
	 * Fonction qui retourne l'id de l'identifiant.
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Fonction qui retourne le login.
	 * 
	 * @return login : est le login du visiteur.
	 */
	public String getLogin() {
		return login;
	}
	
	
	/**
	 * Fonction qui retourne le mot de passe.
	 * 
	 * @return mdp : est le mot de passe.
	 */
	public String getMdp() {
		return mdp;
	}
	

}
