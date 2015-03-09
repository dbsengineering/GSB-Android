package com.gsb.donnees.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.gsb.suividevosfrais.Identifiant;

/**
 * Classe de gestion de la table Identifiant.
 * Hérite de la classe OUtilsBdd.
 * 
 * @author 		Cavron Jérémy
 * @version		v 1.0
 */
public class IdBDD extends OutilsBdd {

    private static final String BDD_NOM = "frais.db";//Nom de la base de données
    private static final int BDD_VERSION = 1;//Version de la base de données

    public static final String TABLE_IDENT = "table_ident";//Table pour l'identifiant

    // Description des colonnes identifiant
    public static final String COL_IDI = "id";//Colonne pour l'id Identifiant
    public static final int NUM_COL_IDI = 0;//Numéro de colonne pour l'id Identifiant

    public static final String COL_LOGIN = "login";//Colonne pour le login Identifiant
    public static final int NUM_COL_LOGIN = 1;//Numéro de colonne pour le login Identifiant

    public static final String COL_MDP = "mdp";//Colonne pour le mot de passe Identifiant
    public static final int NUM_COL_MDP = 2;//Numéro de colonne pour le mot de passe Identifiant



    /**
     * Constructeur de la classe.
     *
     * @param context
     */
    public IdBDD(Context context) {
        bddGsbApp = new BddGsbApp(context, BDD_NOM, null, BDD_VERSION);
    }

    /**
     * Fonction qui permet d'insérer un identifiant
     * dans la base de données.
     *
     * @param identifiant : est l'objet identifiant
     * @return bdd.insert(TABLE_IDENT, null, values)
     */
    public long insertIdentifiant(Identifiant identifiant) {
        ContentValues values = new ContentValues();

        if (this.getIdentifiant() != null){

            bdd.delete(TABLE_IDENT, COL_IDI + " = " + 1, null);
        }

        values.put(COL_IDI, 1);
        values.put(COL_LOGIN, identifiant.getLogin());
        values.put(COL_MDP, identifiant.getMdp());

        return bdd.insert(TABLE_IDENT, null, values);
    }

    /**
     * Fonction qui permet de mettre à jour un identifiant
     *
     * @param identifiant : est l'objet identifiant.
     */
    public long updateIdentifiant(Identifiant identifiant) {
        ContentValues values = new ContentValues();

        values.put(COL_IDI, 1);
        values.put(COL_LOGIN, identifiant.getLogin());
        values.put(COL_MDP, identifiant.getMdp());

        return bdd.update(TABLE_IDENT, values, "COL_IDI=0", null);
    }

    /**
     * Fonction qui convertit le curseur en
     * objet Identifiant et retourne le l'identifiant.
     *
     * @param c : est le curseur.
     * @return identifiant : est l'objet Identifiant
     */
    public Identifiant curseurEnId(Cursor c) {
        if(c.getCount() == 0)
            return null;

        //on se positionne sur le seul Identifiant que contient la base de données.
        c.moveToFirst();

        //Instanciation d'un nouvel objet identifiant.
        Identifiant identifiant = new Identifiant();

        //Initialisation des propriétés de l'objet
        identifiant.setId(c.getInt(NUM_COL_IDI));
        identifiant.setLogin(c.getString(NUM_COL_LOGIN));

        //fermeture du curseur
        c.close();

        return identifiant;
    }

    /**
     *
     * @return
     */
    public Identifiant getIdentifiant() {
        Cursor c = bdd.query(TABLE_IDENT, new String[] { COL_IDI, COL_LOGIN},
                null, null, null, null, null);

        return curseurEnId(c);
    }


    /**
     * Fonction qui récupère les informations identifiant
     * et retourne l'identifiant.
     *
     * @return identifiant
     */
    public Identifiant getIdentifiantMdp() {

        //Instanciation d'un nouvel objet identifiant.
        Identifiant identifiant = new Identifiant();

        Cursor c = bdd.query(TABLE_IDENT, new String[] { COL_IDI, COL_LOGIN, COL_MDP },
                null, null, null, null, null);

        if(c.getCount() == 0){
            return null;
        }else{
            //on se positionne sur le seul Identifiant que contient la base de données.
            c.moveToFirst();

            identifiant.setId(c.getInt(NUM_COL_IDI));

            identifiant.setLogin(c.getString(NUM_COL_LOGIN));

            identifiant.setMdp(c.getString(NUM_COL_MDP));

            //fermeture du curseur
            c.close();
        }

        return identifiant;
    }

}
