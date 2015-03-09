package com.gsb.suividevosfrais;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import com.gsb.donnees.modele.FraisHFBDD;

/**
 * HfRecapActivity.java
 * 
 * Classe HfRecapActivity qui hérite de la classe Activity.
 * Elle est associée au layout activity_hf_recap.xml.
 * Permet d'afficher la page de récapitulation des frais hors forfait.
 * 
 * @author      Cavron Jérémy
 * @version     v 1.0
 */
public class HfRecapActivity extends Activity {

	private Integer id;// l'id du frais forfait
	private String laDate;//Date du frais forfait
	private FraisHFBDD fraisHFBdd;// Base de données.

    private ArrayList<FraisHf> liste;

	//private FraisHf fraisHf;// Objet Frais hors forfait.
	
	/**
	 * Procédure de création de l'affichage
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_hf_recap);
		
		// Instanciation de la base de données
		fraisHFBdd  = new FraisHFBDD(this);
		
		// modification de l'affichage du DatePicker
		Global.changeAfficheDate((DatePicker) findViewById(R.id.datHfRecap));
		
		// valorisation des propriétés
		afficheListe();
		
        // chargement des méthodes événementielles
		imgReturn_clic();
		
		//Procédure sur le clic des dates
		dat_clic();
	}


	/**
	 * Affiche la liste des frais hors forfaits de la date sélectionnée
	 */
	private void afficheListe() {
        ListView listView = (ListView) findViewById(R.id.lstHfRecap);
        FraisHfAdapter adapter;
		Integer annee = ((DatePicker)findViewById(R.id.datHfRecap)).getYear();
		Integer mois = ((DatePicker)findViewById(R.id.datHfRecap)).getMonth() + 1;
		
		//Initialisation de la date
		laDate = ((mois > 9) ? mois.toString() : "0" + mois.toString())
				+ "/" 
				+ annee.toString();

        //Instance d'une liste d'objets Frais hors forfait
        liste = new ArrayList<FraisHf>();
			
		//On ouvre la base de données
		fraisHFBdd.ouvre();

		liste = fraisHFBdd.getListeFraisHF(laDate);

        /*Si la liste de frais hors forfait n'est pas vide, on initialise la listView
        Sinon on efface la listView */
        if(liste != null) {
            adapter = new FraisHfAdapter(HfRecapActivity.this, liste, laDate);

            listView.setAdapter(adapter);
        }else{
            listView.setAdapter(null);
        }

		//ferme la base de données.
		fraisHFBdd.ferme();


        liste = null;
		
	}
	
	/**
	 * Sur la selection de l'image : retour au menu principal
	 */
    private void imgReturn_clic() {
    	((ImageView)findViewById(R.id.imgHfRecapReturn)).setOnClickListener(new ImageView.OnClickListener() {
    		public void onClick(View v) {
    			retourActivityPrincipale();
    		}
    	}) ;
    }

    /**
     * Sur le changement de date : mise à jour de l'affichage de la liste
     */
    private void dat_clic() {   	
    	final DatePicker uneDate = (DatePicker)findViewById(R.id.datHfRecap) ;
    	uneDate.init(uneDate.getYear(), uneDate.getMonth(), uneDate.getDayOfMonth(), new OnDateChangedListener(){
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                afficheListe();
			}
        });
    }
    

	/**
	 * Retour à l'activité principale (le menu)
	 */
	private void retourActivityPrincipale() {
		Intent intent = new Intent(HfRecapActivity.this, MainActivity.class) ;
		startActivity(intent) ;
		
		finish();
	}
	
	
	/**
	 * Retour au menu principal si appuis sur la touche retour.
	 */
	@Override
	public void onBackPressed() {
		Log.d("CDA", "onBackPressed Called");
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);

		retourActivityPrincipale();
	}
}
