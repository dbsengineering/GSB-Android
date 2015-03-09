package com.gsb.suividevosfrais;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.gsb.donnees.modele.FraisFBDD;

/**
 * EtapeActivity.java
 * 
 * Classe EtapeActivity qui hérite de la classe Activity.
 * Elle est associée au layout activity_etape.xml.
 * Permet d'afficher et de rentrer les frais étape
 * du mois en cours.
 * 
 * @author 		Cavron Jérémy
 * @version		v 1.0
 */
public class EtapeActivity extends Activity {

    //--- Déclaration des propriétés privées ---

	private Integer annee;// Variable pour récupérer l'année
	private Integer mois;// Variable pour récupérer le mois
	private Integer qte;// Variable pour récupérer la quantité d'étapes (unitaire)
	private Integer id;// l'id du frais forfait
	private String uneDate;//Date du frais forfait
    private String message;//message pour l'affichage
	
	private FraisFBDD fraisFBdd;// Base de données.
	private FraisF fraisF;// Objet Frais forfaitaire.

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_etape);
		
		// Modification de l'affichage du DatePicker
		Global.changeAfficheDate((DatePicker) findViewById(R.id.datEtape));
		
		// Instanciation de la base de données
		fraisFBdd  = new FraisFBDD(this);
		
		// Valorisation des propriétés
		valoriseProprietes();
		
        // Chargement des méthodes événementielles
		imgReturn_clic();
		cmdValider_clic();
		cmdPlus_clic();
		cmdMoins_clic();
		dat_clic();
		
		//Désactive la saisie dans le input Text kilomètres
		((EditText)findViewById(R.id.txtEtape)).setInputType(InputType.TYPE_NULL);
	}
	
	/**
	 * Création du Setting menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.etape, menu);
		return true;
	}

	/**
	 * Valorisation des propriétés avec les informations affichées
	 */
	private void valoriseProprietes() {
		annee = ((DatePicker)findViewById(R.id.datEtape)).getYear();
		mois = ((DatePicker)findViewById(R.id.datEtape)).getMonth() + 1;
		qte = 0;
		id = null;

         /*Si le mois et l'année sont plus grands au mois en cours
        alors on afffiche un message d'avertissement */
        if(annee > ANNEEACTU || mois > MOISACTU) {
            //Message d'erreur si le mois et l'année sont supérieurs au mois et année actuels
            Toast.makeText(getApplicationContext(), "Attention vous avez choisi une date supérieur à celle actuelle.", Toast.LENGTH_LONG).show();
        }
		
		//Initialisation de la date
		uneDate = "01/" 
				+ ((mois > 9) ? mois.toString() : "0" + mois.toString()) 
				+ "/" 
				+ annee.toString();
						
		//Instance d'un objet Frais forfait
		fraisF = new FraisF();
						
		//On ouvre la base de données
		fraisFBdd.ouvre();
						
		//On r�cup�re le frais forfaitaire etape à la date sélectionnée
		fraisF = fraisFBdd.getFraisFDate("etape", uneDate);

		//Si l'objet Frais forfait n'est pas null alors on Initialise les composants d'affichages
		if(fraisF != null){
			id = fraisF.getId();//Initialisation de l'id avec celui trouvé dans la base de données
			qte = fraisF.getQte();//Initialisation de la quantité avec celle trouvée dans la base de données.
		}
		
		//Initialisation du TextBox de la quantité
		((EditText)findViewById(R.id.txtEtape)).setText(qte.toString());
		
		//On ferme la base de données
		fraisFBdd.ferme();
				
		fraisF = null;
	}
	
	/**
	 * Sur la sélection de l'image : retour au menu principal
	 */
    private void imgReturn_clic() {
    	((ImageView)findViewById(R.id.imgEtapeReturn)).setOnClickListener(new ImageView.OnClickListener() {
    		public void onClick(View v) {
    			retourActivityPrincipale() ;  		
    		}
    	}) ;
    }

    /**
     * Sur le clic du bouton valider : sérialisation
     */
    private void cmdValider_clic() {
    	((Button)findViewById(R.id.cmdEtapeValider)).setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {

                //Enregistrement dans la base de données.
                if (enregNewQte()){
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
     * Sur le clic du bouton plus : ajout de 1 dans la quantité
     */
    private void cmdPlus_clic() {
    	((Button)findViewById(R.id.cmdEtapePlus)).setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
    			qte+=1;//Ajout de 1 etape

    			//Ajout la quantit� dans le TextBox quantité
    			((EditText)findViewById(R.id.txtEtape)).setText(qte.toString());
    		}
    	}) ;    	
    }
    
    /**
     * Sur le clic du bouton moins : enl�ve 1 dans la quantit� si c'est possible
     */
    private void cmdMoins_clic() {
    	((Button)findViewById(R.id.cmdEtapeMoins)).setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
   				qte = Math.max(0, qte-1);// Enlève 1 etape

   				//Enlève la quantité dans le TextBox quantité
    			((EditText)findViewById(R.id.txtEtape)).setText(qte.toString());
     		}
    	}) ;    	
    }
    
    /**
     * Sur le changement de date : mise à jour de l'affichage de la qte
     */
    private void dat_clic() {   	
    	final DatePicker uneDate = (DatePicker)findViewById(R.id.datEtape) ;
    	uneDate.init(uneDate.getYear(), uneDate.getMonth(), uneDate.getDayOfMonth(), new OnDateChangedListener(){
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				valoriseProprietes() ;				
			}
    	});       	
    }

	/**
	 * Enregistrement dans la zone de texte et dans la liste de la nouvelle qte, à la date choisie
	 */
	private boolean enregNewQte() {

        if(annee <= ANNEEACTU && mois <= MOISACTU) {
		/*Initialisation de la date: Si le mois est supérieur à 9, on envoie ce mois
		sinon on rajoute un 0 devant*/
            uneDate = "01/"
                    + ((mois > 9) ? mois.toString() : "0" + mois.toString())
                    + "/"
                    + annee.toString();

            //Ouverture de la base de données
            fraisFBdd.ouvre();

            //Valorisation du frais forfaitaire
            fraisF = new FraisF(uneDate, "etape", qte);

            if (id != null) {

                // enregistrement du frais dans la base de données
                fraisFBdd.miseAJourFraisF(id, fraisF);

            } else {
                fraisFBdd.insertFraisF(fraisF);
            }

            fraisFBdd.ferme();

            fraisF = null;
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
		Intent intent = new Intent(EtapeActivity.this, MainActivity.class) ;
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
