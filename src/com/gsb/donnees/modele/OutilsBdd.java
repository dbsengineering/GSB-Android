package com.gsb.donnees.modele;

import android.database.sqlite.SQLiteDatabase;

/**
 * Classe d'outils. Ouvre et ferme la base de données.
 * 
 * @author 		Cavron Jérémy
 * @version		v 1.0
 */
public class OutilsBdd {

    protected SQLiteDatabase bdd;
    protected BddGsbApp bddGsbApp;

    public OutilsBdd() {

    }

    /**
     * Procédure d'ouverture de la base de données
     * en écriture.
     */
    public void ouvre() {
        bdd = bddGsbApp.getWritableDatabase();
    }

    public void ouvreLecture(){
        bdd = bddGsbApp.getReadableDatabase();
    }

    /**
     * Procédure de fermeture de la base de données.
     */
    public void ferme() {
        bdd.close();
    }

    /**
     * Fonction qui retourne la base de données.
     * @return bdd
     */
    public SQLiteDatabase getBdd() {
        return bdd;
    }
}
