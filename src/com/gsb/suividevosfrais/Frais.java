package com.gsb.suividevosfrais;


/**
 * Frais.java
 * 
 * Classe Frais. Permet de créer l'objet Frais mère.
 * C'et la classe mère des frais.
 * 
 * @author      Cavron Jérémy
 * @version     v 1.0
 */
public class Frais {

	//--- Déclaration des propriétés ---
	private int id;//L'id du frais
	protected String date;//La date du frais en jj/MM/aaaa
	private String jour;
	private String mois;
	private String annee;
	
	
	/**
	 * Constructeur de la classe.
	 */
	public Frais() {
		
	}
	
	//--- Les Setters ---
	
	/**
	* Procédure pour initialiser l'id du frais forfait.
	* 
	* @param id : est l'id du frais forfait.
	*/
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	* Procédure pour initialiser la date du frais forfait.
	* 
	* @param date : est la date du frais forfait en jj/MM/aaaa
	*/
	public void setDate(String date) {
		this.date = date;
	}
	
	/**
	 * Procédure d'initialisation du jour
	 * avec la date.
	 * 
	 * @param date
	 */
	public void setJour(String date) {
		this.jour = date.substring(0,2);
	}
	
	/**
	 * Procédure d'initialisation du mois
	 * avec la date.
	 * 
	 * @param date
	 */
	public void setMois(String date) {
		this.mois = date.substring(3,2);
	}
	
	/**
	 * Procédure d'initialisation de l'année
	 * avec la date.
	 * 
	 * @param date
	 */
	public void setAnnee(String date) {
		this.annee = date.substring(6,4);
	}
	
	
	//--- Les Getters ---
	
	/**
	 * Fonction qui retourne l'id du frais forfait.
	 * 
	 * @return id : est l'id du frais forfait.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Fonction qui retourne la date du frais forfait.
	 * 
	 * @return date : est la date du frais forfait en jj/MM/aaaa.
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * Fonction qui renvoie le jour de la date du frais.
	 * 
	 * @return jour : est le jour du frais.
	 */
	public String getJour() {
		this.jour = this.date.substring(0,2);
		return this.jour;
	}
	
	/**
	 * Fonction qui renvoie le mois de la date du frais.
	 * 
	 * @return mois : est le mois du frais.
	 */
	public String getMois() {
		this.mois = this.date.substring(3,5);
		return this.mois;
	}
	
	/**
	 * Fonction qui renvoie l'année de la date du frais.
	 * 
	 * @return annee : est l'année du frais.
	 */
	public String getAnnee() {
		this.annee = this.date.substring(6,10);
		return this.annee;
	}
}
