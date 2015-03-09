package com.gsb.suividevosfrais;

/**
 * FraisF.java
 * 
 * Classe FraisF. Permet de créer l'objet FraisF.
 * Elle hérite de la classe Frais.
 * 
 * @author      Cavron Jérémy
 * @version     v 1.0
 */
public class FraisF extends Frais {
	
	//--- Déclaration des propriétés ---
	private int qte;//La quantité du frais.
	private String typeF;//type de forfait.
	
	/**
	 * Constructeur 1 de la classe FraisF.
	 */
	public FraisF() {
		
	}
	
	/**
	 * Constructeur 2 de la classe FraisF. (Avec paramètres).
	 * 
	 * @param date : est la date du frais.
	 * @param typeF : est le type de frais forfaitaire.
	 * @param qte : est la quantité du frais.
	 */
	public FraisF(String date, String typeF, int qte) {
		this.date = date;
		this.typeF = typeF;
		this.qte = qte;
	}
	
	//--- Les Setters ---
	
	/**
	 * Procédure pour initialiser le type du frais forfait.
	 * 
	 * @param typeF : est le type du frais forfait.
	 */
	public void setTypeF(String typeF) {
		this.typeF = typeF;
	}
	
	/**
	 * Procédure pour initialiser la quantité du frais forfait.
	 * 
	 * @param qte : est la quantité du frais forfait.
	 */
	public void setQte(int qte) {
		this.qte = qte;
	}
	
	
	//--- Les Getters ---
	
	/**
	 * Fonction qui retourne le type du frais forfait.
	 * 
	 * @return typeF : est le type du frais forfait.
	 */
	public String getTypeF() {
		return typeF;
	}
	
	/**
	 * Fonction qui retourne la quantité du frais forfait.
	 * 
	 * @return qte : est la quantité du frais forfait.
	 */
	public int getQte() {
		return qte;
	}
}
