package com.gsb.suividevosfrais;


import java.util.ArrayList;

/**
 * FraisHf.java
 * 
 * Classe FraisHf. Permet de créer l'objet FraisHf.
 * Elle hérite de la classe Frais.
 * 
 * @author      Cavron Jérémy
 * @version     v 1.0
 */
public class FraisHf  extends Frais {

	//--- Déclaration des propriétés ---
	
	private float montant ;//Montant du frais hors forfait
	private String descriptif;//Descriptif du forfait hors forfait.
    private ArrayList<FraisHf> lesFraisHf ;
	
	/**
	 * Constructeur 1 de la classe Frais hors forfait.
	 */
	public FraisHf() {
		
	}
	
	/**
	 * Constructeur 2 de la classe Frais hors forfait.
	 * 
	 * @param date : est la date du frais
	 * @param montant : est le montant du frais hors forfait.
	 * @param descriptif : est le descriptif du frais hors forfait.
	 */
	public FraisHf(String date, float montant, String descriptif) {
		this.date = date;
		this.montant = montant ;
		this.descriptif = descriptif ; 
	}
	
	//--- Les Getters ---

	public float getMontant() {
		return montant;
	}

	public String getDescriptif() {
		return descriptif;
	}
	
	
	//--- Les Setters ---
	
	public void setMontant(float montant) {
		this.montant = montant;
	}
	
	public void setDescriptif(String descriptif) {
		this.descriptif = descriptif;
	}

    public void nouvListe(){
        lesFraisHf = new ArrayList<FraisHf>();
    }

    public void supprimefraisListe(int index) {
        lesFraisHf.remove(index) ;
    }
}
