package com.gsb.suividevosfrais;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.gsb.donnees.modele.FraisHFBDD;

/**
 * HfActivity.java
 * 
 * Classe HfActivity qui hérite de la classe Activity.
 * Elle est associée au layout activity_hf.xml.
 * Permet d'afficher la page insertion frais hors forfait.
 * 
 * @author      Cavron Jérémy
 * @version     v 1.0
 */
public class HfActivity extends Activity {

    //--- Déclaration des propriétés privées ---

	private String uneDate;//Date du frais forfait
	private FraisHFBDD fraisHFBdd;// Base de données.
	private FraisHf fraisHf;// Objet Frais hors forfait.

    private String message;//message pour l'affichage

    //--- Constantes ---
    private static String FORMATMOIS = "MM";
    private static String FORMATANNEE = "yyyy";

    //Initialisation du mois actuel
    private static java.text.SimpleDateFormat FORMATERMOIS = new java.text.SimpleDateFormat( FORMATMOIS );
    private static java.util.Date DATEMOIS = new java.util.Date();

    //Initialisation de l'année actuelle
    private static java.text.SimpleDateFormat FORMATERANNEE = new java.text.SimpleDateFormat( FORMATANNEE );
    private static java.util.Date DATEANNEE = new java.util.Date();

    private static int MOISACTU = Integer.parseInt(FORMATERMOIS.format( DATEMOIS ));
    private static int ANNEEACTU = Integer.parseInt(FORMATERANNEE.format( DATEANNEE ));

    //--------------------------------------------
	
	/**
	 * Procédure de création de l'affichage
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hf);
		
		// Instanciation de la base de données
		fraisHFBdd  = new FraisHFBDD(this);
		
        // chargement des méthodes événementielles
		imgReturn_clic() ;
		cmdAjouter_clic() ;
	}



	/**
	 * Sur la selection de l'image : retour au menu principal
	 */
    private void imgReturn_clic() {
    	((ImageView)findViewById(R.id.imgHfReturn)).setOnClickListener(new ImageView.OnClickListener() {
    		public void onClick(View v) {
    			retourActivityPrincipale() ;
    		}
    	}) ;
    }

    /**
     * Sur le clic du bouton ajouter : enregistrement dans la liste
     */
    private void cmdAjouter_clic() {
    	((Button)findViewById(R.id.cmdHfAjouter)).setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {

                //Enregistrement dans la base de données.
                if (enregNewFrais()){
                    //Message de confirmation de la validation
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                    //retour sur le menu principal
                    retourActivityPrincipale();
                }

                //Message de confirmation de la validation
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    		}
    	}) ;
    }
    
	/**
	 * Enregistrement dans la base de données du nouveau frais hors forfait
	 */
	private boolean enregNewFrais() {
		// récupération des informations saisies
		Integer annee = ((DatePicker)findViewById(R.id.datHf)).getYear() ;
		Integer mois = ((DatePicker)findViewById(R.id.datHf)).getMonth() + 1 ;
		Integer jour = ((DatePicker)findViewById(R.id.datHf)).getDayOfMonth() ;
		float montant = Float.parseFloat(((EditText)findViewById(R.id.txtHf)).getText().toString()) ;
		String motif = ((EditText)findViewById(R.id.txtHfMotif)).getText().toString() ;

        if(annee <= ANNEEACTU && mois <= MOISACTU) {
		/*Initialisation de la date: Si les chifres sont supérieur à 9, on envoie ces chiffres
		Sinon on rajoute un 0 devant*/
            uneDate = ((jour > 9) ? jour.toString() : "0" + jour.toString())
                    + "/"
                    + ((mois > 9) ? mois.toString() : "0" + mois.toString())
                    + "/"
                    + annee.toString();

            //Ouverture de la base de données
            fraisHFBdd.ouvre();

            //Valorisation du frais forfaitaire
            fraisHf = new FraisHf(uneDate, montant, motif);


            fraisHFBdd.insertFraisHF(fraisHf);


            fraisHFBdd.ferme();

            fraisHf = null;
            message = "Validation confirmée";
        }else{
            //Message d'erreur si le mois et l'année sont supérieurs au mois et année actuels
            message = "Erreur de date. Vous ne pouvez pas prévoir au delà du mois actuel";
            return false;
        }
        return true;
			
	}

	/**
	 * Retour à l'activité principale (le menu)
	 */
	private void retourActivityPrincipale() {
		Intent intent = new Intent(HfActivity.this, MainActivity.class) ;
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
