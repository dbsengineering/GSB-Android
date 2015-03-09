package com.gsb.donnees.modele;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe de création, de mise à jours et suppression
 * de la base de données et ses tables.
 * 
 * @author 		Cavron Jérémy
 * @version		v 1.0
 */
public class BddGsbApp extends SQLiteOpenHelper {
	
	//--- Déclaration des propriétés ---
	
	
	//Table frais
	public static final String TABLE_FRAISF = "table_fraisF";//Table frais qui contiendra les frais forfait
	
	// Description des colonnes frais forfait
	public static final String COL_IDF = "id";//Colonne pour l'id Frais
	
	public static final String COL_DATEF = "dateF";//Colonne date jj/MM/aaaa
	
	public static final String COL_TYPEF = "type_forfait";//Colonne pour le type de forfait (Km, repas, nuit, etape)
	
	public static final String COL_QTE = "quantite";//Colonne pour la quantité forfait
	
	
	//Table Frais hors forfait
	public static final String TABLE_FRAISHF = "table_fraisHf";//Table frais qui contiendra les frais forfait et hors forfait
	
	// Description des colonnes frais hors forfait
	public static final String COL_IDHF = "id";//Colonne pour l'id Frais
	
	public static final String COL_DATEHF = "dateHf";//Colonne date jj/MM/aaaa
	
	public static final String COL_MONTANT = "montant";//Colonne pour le montant hors forfait
	
	public static final String COL_COMMENT = "comment";//Colonne pour le commentaire hors forfait
	
	
	
	//Table identifiant
	public static final String TABLE_IDENT = "table_ident";//Table pour l'identifiant
	
	// Description des colonnes identifiant
	public static final String COL_IDI = "id";//Colonne pour l'id Identifiant
	
	public static final String COL_LOGIN = "login";//Colonne pour le login Identifiant
	
	public static final String COL_MDP = "mdp";//Colonne pour le mot de passe Identifiant
	
	
	//Requête de création de la table TABLE_FRAISF
	private static final String CREER_TABLEF = "CREATE TABLE " + TABLE_FRAISF 
			+ " (" + COL_IDF + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_DATEF + " TEXT NOT NULL, " 
			+ COL_TYPEF + " TEXT NOT NULL, "
			+ COL_QTE + " INTEGER NULL);";
	
	//Requête de création de la table TABLE_FRAISHF
	private static String CREER_TABLEHF = "CREATE TABLE " + TABLE_FRAISHF
			+ " (" + COL_IDHF + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_DATEHF + " TEXT NOT NULL, "
			+ COL_MONTANT + " FLOAT NULL, "
			+ COL_COMMENT + " TEXT NULL);";
	
	//Requête de création de la table TABLE_IDENT
	private static String CREER_TABLEI = "CREATE TABLE " + TABLE_IDENT
			+ " (" + COL_IDI + " INTEGER PRIMARY KEY, "
			+ COL_LOGIN + " TEXT NULL, "
			+ COL_MDP + " TEXT NULL);";
	
	//------------------------------------------------------------------------
	//------------------------------------------------------------------------
	//------------------------------------------------------------------------
	
	/**
	 * Constructeur de la classe BddGsbApp.
	 * 
	 * @param context : est le context.
	 * @param nom
	 * @param factory
	 * @param version
	 */
	public BddGsbApp(Context context, String nom, CursorFactory factory, int version) {
		super(context, nom, factory, version);
	}
	
	/**
	 * Création de la base de données
	 * avec les tables.
	 * 
	 * @param db : est la base de données.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREER_TABLEF);//Création de la table Frais
		db.execSQL(CREER_TABLEHF);//Création de la table Frais
		db.execSQL(CREER_TABLEI);//Création de la table Identifiant.
		
	}

	/**
	 * Procédure de mise à jours de la base de données.
	 * 
	 * @param db : est la base de données.
	 * @param ancienVers : est le numéro de l'ancienne version.
	 * @param nouvelVers : est le numéro de la nouvelle version.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int ancienVers, int nouvelVers) {
		if (nouvelVers > ancienVers) {
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRAISF);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRAISHF);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_IDENT);
		    onCreate(db);
		}
	}
}
