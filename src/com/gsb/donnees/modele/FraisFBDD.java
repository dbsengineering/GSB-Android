package com.gsb.donnees.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.gsb.suividevosfrais.FraisF;

/**
 * Classe de gestion de la table Frais forfait.
 * Hérite de la classe OUtilsBdd.
 * 
 * @author 		Cavron Jérémy
 * @version		v 1.0
 */
public class FraisFBDD extends OutilsBdd {

	
	
	private static final String BDD_NOM = "frais.db";//Nom de la base de données
	private static final int BDD_VERSION = 1;//Version de la base de données
	
	public static final String TABLE_FRAISF = "table_fraisF";//Table frais qui contiendra les frais forfait
	
	// Description des colonnes frais forfait
	public static final String COL_IDF = "id";//Colonne pour l'id Frais
	public static final int NUM_COL_IDF = 0;//Numéro de colonne pour l'id Frais
	
	public static final String COL_DATEF = "dateF";//Colonne date jj/MM/aaaa
	public static final int  NUM_COL_DATEF = 1;//Numéro de colonne date
	
	public static final String COL_TYPEF = "type_forfait";//Colonne pour le type de forfait (Km, repas, nuit, etape)
	public static final int  NUM_COL_TYPEF = 2;//Numéro de colonne type_forfait
	
	public static final String COL_QTE = "quantite";//Colonne pour la quantité forfait
	public static final int NUM_COL_QTE = 3;//Numéro de colonne pour la quantité forfait

	
	/**
	 * Constructeur de la classe.
	 * 
	 * @param context
	 */
	public FraisFBDD(Context context) {
		bddGsbApp = new BddGsbApp(context, BDD_NOM, null, BDD_VERSION);
	}
	
	/**
	 * Fonction qui permet d'insérer un Frais forfait
	 * dans la base de données.
	 * 
	 * @param fraisF : est l'objet frais forfait
	 * @return bdd.insert(TABLE_FRAISF, null, values)
	 */
	public long insertFraisF(FraisF fraisF) {
		ContentValues values = new ContentValues();
		
		values.put(COL_DATEF, fraisF.getDate());
		values.put(COL_TYPEF, fraisF.getTypeF());
		values.put(COL_QTE, fraisF.getQte());
		
		return bdd.insert(TABLE_FRAISF, null, values);
	}
	
	/**
	 * Fonction qui permet de mettre à jours
	 * un frais forfait.
	 * 
	 * @param id : est l'id du frais
	 * @param fraisF : est l'objet frais forfait
	 * @return bdd.update(TABLE_FRAISF, values, COL_IDF + " = " + id, null)
	 */
	public int miseAJourFraisF(int id, FraisF fraisF) {
		ContentValues values = new ContentValues();
		
		values.put(COL_DATEF, fraisF.getDate());
		values.put(COL_TYPEF, fraisF.getTypeF());
		values.put(COL_QTE, fraisF.getQte());
		
		return bdd.update(TABLE_FRAISF, values, COL_IDF + " = " + id, null);
	}
	
	/**
	 * Fonction qui supprime un frais forfait
	 * dans la base de données.
	 * @param id : est l'id du frais forfait.
	 * @return 
	 */
	public int supprimeFraisF(int id) {
		return bdd.delete(TABLE_FRAISF, COL_IDF + " = " + id, null);
	}
	
	
	
	/**
	 * Fonction qui convertit le curseur en
	 * objet FraisF et retourne le dernier frais
	 * d'une catégorie.
	 * 
	 * @param c : est le curseur.
	 * @return fraisF : est l'objet FraisF
	 */
	public FraisF curseurEnFraisF(Cursor c) {
		if(c.getCount() == 0)
			return null;
		
		//on se positionne sur le dernier frais d'une catégorie.
		c.moveToLast();
		
		FraisF fraisF = new FraisF();
		fraisF.setId(c.getInt(NUM_COL_IDF));
		fraisF.setDate(c.getString(NUM_COL_DATEF));
		fraisF.setTypeF(c.getString(NUM_COL_TYPEF));
		fraisF.setQte(c.getInt(NUM_COL_QTE));
		
		//fermeture du curseur
		c.close();
		
		return fraisF;
	}
	
	/**
	 * Fonction qui retourne les frais forfait
	 * d'une catégorie passée en paramètre.
	 * 
	 * @param typeF : est le type de frais forfait (km, etape, repas, nuit)
	 * @return curseurEnFraisF(c)
	 */
	public FraisF getDernierFraisF(String typeF) {
		Cursor c = bdd.query(TABLE_FRAISF, new String[] { COL_IDF, COL_DATEF, COL_TYPEF, COL_QTE }, 
				COL_TYPEF + " = '" + typeF + "'", null, null, null, null);
		return curseurEnFraisF(c);
	}
	
	/**
	 * Fonction qui convertit le curseur en
	 * objet FraisF et retourne le frais
	 * d'une catégorie et d'une date.
	 * 
	 * @param c : est le curseur.
	 * @return fraisF : est l'objet FraisF
	 */
	public FraisF curseurEnFraisFDate(Cursor c) {
		if(c.getCount() == 0)
			return null;
		
		//on se positionne sur le premier frais d'une catégorie.
		c.moveToFirst();

        //Initialisation des propriétés de l'objet
		FraisF fraisF = new FraisF();
		fraisF.setId(c.getInt(NUM_COL_IDF));
		fraisF.setDate(c.getString(NUM_COL_DATEF));
		fraisF.setTypeF(c.getString(NUM_COL_TYPEF));
		fraisF.setQte(c.getInt(NUM_COL_QTE));
				
		//fermeture du curseur
		c.close();
				
		return fraisF;
	}
	
	/**
	 * Fonction qui retourne le frais forfait
	 * d'une catégorie et d'une date passées en paramètre.
	 * 
	 * @param typeF : est le type de frais forfait (km, etape, repas, nuit)
	 * @param date : est la date du frais.
	 * @return curseurEnFraisF(c)
	 */
	public FraisF getFraisFDate(String typeF, String date) {
		Cursor c = bdd.query(TABLE_FRAISF, new String[] { COL_IDF, COL_DATEF, COL_TYPEF, COL_QTE}, 
				COL_TYPEF + " = '" + typeF + "' AND " + COL_DATEF + " = '" + date + "'", null, null, null, null);
		return curseurEnFraisF(c);
	}
	
	
}
