package com.gsb.donnees.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.gsb.suividevosfrais.FraisHf;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Classe de gestion de la table Frais hors forfait.
 * Hérite de la classe OUtilsBdd.
 * 
 * @author 		Cavron Jérémy
 * @version		v 1.0
 */
public class FraisHFBDD extends OutilsBdd {

	private static final String BDD_NOM = "frais.db";//Nom de la base de données
	private static final int BDD_VERSION = 1;//Version de la base de données
	
	public static final String TABLE_FRAISHF = "table_fraisHf";//Table frais qui contiendra les frais forfait et hors forfait
	
	// Description des colonnes frais hors forfait
	public static final String COL_IDHF = "id";//Colonne pour l'id Frais
	public static final int NUM_COL_IDHF = 0;//Numéro de colonne pour l'id Frais
		
	public static final String COL_DATEHF = "dateHf";//Colonne date jj/MM/aaaa
	public static final int  NUM_COL_DATEHF = 1;//Numéro de colonne date
		
	public static final String COL_MONTANT = "montant";//Colonne pour le montant hors forfait
	public static final int NUM_COL_MONTANT = 2;//Numéro de colonne pour le montant hors forfait
		
	public static final String COL_COMMENT = "comment";//Colonne pour le commentaire hors forfait
	public static final int NUM_COL_COMMENT = 3;//Numéro de colonne pour le commentaire hors forfait

	private ArrayList<FraisHf> listeFraisHf;
	
	
	/**
	 * Constructeur de la classe.
	 * 
	 * @param context
	 */
	public FraisHFBDD(Context context) {
		bddGsbApp = new BddGsbApp(context, BDD_NOM, null, BDD_VERSION);
	}
	

	
	/**
	 * Fonction qui permet d'insérer un Frais hors forfait
	 * dans la base de données.
	 * 
	 * @param fraisHf : est l'objet frais hors forfait
	 * @return bdd.insert(TABLE_FRAISHF, null, values)
	 */
	public long insertFraisHF(FraisHf fraisHf) {
		ContentValues values = new ContentValues();
		
		values.put(COL_DATEHF, fraisHf.getDate());
		values.put(COL_MONTANT, fraisHf.getMontant());
		values.put(COL_COMMENT, fraisHf.getDescriptif());
		
		return bdd.insert(TABLE_FRAISHF, null, values);
	}
	
	/**
	 * Fonction qui permet de mettre à jours
	 * un frais hors forfait.
	 * 
	 * @param id : est l'id du frais
	 * @param fraisHf : est l'objet frais hors forfait
	 * @return bdd.update(TABLE_FRAISHF, values, COL_IDHF + " = " + id, null)
	 */
	public int miseAJourFraisHF(int id, FraisHf fraisHf) {
		ContentValues values = new ContentValues();
		
		values.put(COL_DATEHF, fraisHf.getDate());
		values.put(COL_MONTANT, fraisHf.getMontant());
		values.put(COL_COMMENT, fraisHf.getDescriptif());
		
		return bdd.update(TABLE_FRAISHF, values, COL_IDHF + " = " + id, null);
	}
	
	/**
	 * Fonction qui supprime un frais hors forfait
	 * dans la base de données.
	 * @param id : est l'id du frais hors forfait.
	 * @return bdd.delete(TABLE_FRAISHF, COL_IDHF + " = " + id, null)
	 */
	public int supprimeFraisHF(int id) {
		return bdd.delete(TABLE_FRAISHF, COL_IDHF + " = " + id, null);
	}
	
	
	
	/**
	 * Fonction qui convertit le curseur en
	 * objet FraisHf et retourne le dernier frais
	 * d'une catégorie.
	 * 
	 * @param c : est le curseur.
	 * @return fraisHf : est l'objet FraisHF
	 */
	public ArrayList<FraisHf> curseurEnFraisHF(Cursor c) {
		if(c.getCount() == 0)
			return null;

		listeFraisHf = new ArrayList<FraisHf>();

        //tant qu'on trouve un résultat dans le curseur, on passe à la ligne suivante.
        while (c.moveToNext()) {

            FraisHf fraisHf = new FraisHf();

            fraisHf.setId(c.getInt(NUM_COL_IDHF));
            fraisHf.setDate(c.getString(NUM_COL_DATEHF));
            fraisHf.setMontant(c.getFloat(NUM_COL_MONTANT));
            fraisHf.setDescriptif(c.getString(NUM_COL_COMMENT));

            //On ajout le frais hors forfait dans la liste.
            listeFraisHf.add(fraisHf);
        }

		//fermeture du curseur
		c.close();

		return listeFraisHf;
	}
	
	/**
	 * Fonction qui retourne les frais hors forfait
	 * d'une catégorie passée en paramètre.
	 * 
	 * @param date : est la date du frais hors forfait.
	 * @return curseurEnFraisF(c)
	 */
	public ArrayList<FraisHf> getListeFraisHF(String date) {

		Cursor c = bdd.query(TABLE_FRAISHF, new String[] { COL_IDHF, COL_DATEHF, COL_MONTANT, COL_COMMENT }, 
				COL_DATEHF + " LIKE '%" + date + "'", null, null, null, null);
		return curseurEnFraisHF(c);
	}

    //--- JSONArray ---
    /**
     * Fonction qui convertit le curseur en
     * objet liste JSON FraisHf.
     * d'une catégorie.
     *
     * @param c : est le curseur.
     * @return fraisHf : est l'objet FraisHF
     */
    public JSONArray curseurEnFraisHFJSON(Cursor c) {
        if(c.getCount() == 0)
            return null;

        //Déclaration des propriétés
        JSONArray listeFraisHfJSON;
        JSONObject fraisHfJSON;

        listeFraisHfJSON = new JSONArray();

        //tant qu'on trouve un résultat dans le curseur, on passe à la ligne suivante.
        while (c.moveToNext()) {

            //Initialisation de l'objet JSON frais hors forfait
            fraisHfJSON = new JSONObject();

            try {
                fraisHfJSON.put("idFraisHf", c.getInt(NUM_COL_IDHF));
                fraisHfJSON.put("dateFraisHf", c.getString(NUM_COL_DATEHF));
                fraisHfJSON.put("montFraisHf", c.getFloat(NUM_COL_MONTANT));
                fraisHfJSON.put("comFraisHf", c.getString(NUM_COL_COMMENT));

                //On ajout le frais hors forfait dans la liste.
                listeFraisHfJSON.put(fraisHfJSON);


            }catch (Throwable e){
                e.printStackTrace();
            }
        }

        //fermeture du curseur
        c.close();

        return listeFraisHfJSON;
    }

    /**
     * Fonction qui retourne les frais hors forfait en format JSONArray
     *
     * @param date : est la date du frais hors forfait
     * @return curseurEnFraisHFJSON(c)
     */
    public JSONArray getListeFraisHFJSON(String date) {

        date = date.substring(3);

        Cursor c = bdd.query(TABLE_FRAISHF, new String[] { COL_IDHF, COL_DATEHF, COL_MONTANT, COL_COMMENT },
                COL_DATEHF + " LIKE '%" + date + "'", null, null, null, null);
        return curseurEnFraisHFJSON(c);
    }

    /**
     * Fonction qui récupère et renvoie la date
     * du dernier frais hors forfait créé.
     *
     * @return laDerniereDate : est la dernière date trouvée.
     */
    public String getDerniereDate(){

        //Déclaration des propriétés
        String laDerniereDate = null;

        //Déclaration de la requête
        String requete = "SELECT MAX(dateHf) FROM table_fraisHf";

        //Déclaration et initialisation du curseur
        Cursor c = bdd.rawQuery(requete , null);


        //Si le curseur ne contient pas de données alors on retourne null sinon la dernière date
        if(c.getCount() == 0){
            return null;
        }else{

            //On se positionne sur le premier résultat. Il ne doit en avoir qu'un seul.
            c.moveToFirst();

            //Initialisation de la propriété laDerniereDate avec la dernière date trouvée
            laDerniereDate = c.getString(0);

            c.close();//Fermeture du curseur
        }

        //retourne la dernière date
        return laDerniereDate;
    }
	
}
