package com.gsb.suividevosfrais;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.gsb.donnees.modele.IdBDD;

/**
 * LoginActivity.java
 * 
 * Classe LoginActivity qui hérite de la classe Activity.
 * Elle est associée au layout activity_login.xml.
 * Permet d'afficher et de rentrer ses identifiants.
 * 
 * @author 		Cavron Jérémy
 * @version		v 1.0
 */
public class LoginActivity extends Activity {

	//--- Déclaration des variables privées ---
	String login;//Est le login.
	String mdp;//Est le mot de passe.
	EditText edTLog;//est l'objet EditTexte login.
	EditText edTMdp;//est l'objet EditTexte mot de passe.

    private IdBDD idBdd;// Base de données.
	
	Encrypte encrypte = new Encrypte();//Instance de la classe Encrypte qui permet de hasher le mot de passe.
	
	
	/**
	 * Création de l'interface paramètres identifiants
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		edTLog = (EditText)findViewById(R.id.txtLogin);
		
		edTMdp = (EditText)findViewById(R.id.txtMdp);

        //Instanciation de la base de données
        idBdd = new IdBDD(this);

        //Intanciation de l'objet Identifiant
        Identifiant identifiant = new Identifiant();

        //Ouverture de la base de données.
        idBdd.ouvre();

        identifiant = idBdd.getIdentifiant();

        //Si l'identifiant existe déjà, on affiche le login dans le textBox login
        if(identifiant != null){
            edTLog.setText(identifiant.getLogin());
        }
		

        //Fermeture de la base de données
		idBdd.ferme();
		
		 // Chargement des méthodes événementielles
		imgReturn_clic() ;
		cmdValider_clic() ;
	}
	
	/**
	 * Sur la sélection de l'image : retour au menu principal
	 */
    private void imgReturn_clic() {
    	((ImageView)findViewById(R.id.imgLoginReturn)).setOnClickListener(new ImageView.OnClickListener() {
    		public void onClick(View v) {
    			retourActivityPrincipale() ;
    		}
    	}) ;
    }

    /**
     * Sur le clic du bouton valider : enregistrement dans la base de données
     */
    private void cmdValider_clic() {
    	((Button)findViewById(R.id.cmdLoginValider)).setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {

                Identifiant identifiant = new Identifiant();
    	        //Initialisation de la variable login avec le textBox login.
    		    login = edTLog.getText().toString();
    			
    		    //Initialisation de la variable mdp avec le textBox mot de passe.
    		    mdp = edTMdp.getText().toString();
    			
    		    mdp = fuslogMdp(login, mdp);
    			
    		    // --- enregistrement du login et mot de passe hashés. ---

                //Ouverture de la base de données
                idBdd.ouvre();

                //identifiant = idBdd.getIdentifiant();
    			
                if(!login.equals("") || !mdp.equals("")) {

                    //On insert l'identifiant dans la base de données
                    idBdd.insertIdentifiant(new Identifiant(login, mdp));

                    //Message de confirmation de la validation
                    Toast.makeText(getApplicationContext(), "Identifiant pris en compte", Toast.LENGTH_SHORT).show();

                    //retour sur le menu principal
                    retourActivityPrincipale();

                }else{
                    Toast.makeText(getApplicationContext(), "Login ou mot de passe incorrects", Toast.LENGTH_LONG).show();
                }
                //Fermeture de la base de données
                idBdd.ferme();
    		}
    	});
    }
    
    /**
     * Fonction qui fusionne le login et le mot de passe, hash 
     * et retourne le résultat
     * 
     * @param login : est le login utilisateur.
     * @param mdp : est le mot de passe utilisateur.
     * @return mdp : est le mot de passe final.
     */
    private String fuslogMdp(String login, String mdp){
    	
    	//Concaténation du login avec le mot de passe.
    	mdp = login + mdp;
    	
    	mdp = encrypte.getEncodePassword(mdp);//Hash le mot de passe.
    	
    	return mdp;
    }
    

	/**
	 * Retour à l'activité principale (le menu)
	 */
	private void retourActivityPrincipale() {
		Intent intent = new Intent(LoginActivity.this, MainActivity.class) ;
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
