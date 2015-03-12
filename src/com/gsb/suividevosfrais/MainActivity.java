package com.gsb.suividevosfrais;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.gsb.donnees.controleurs.ExportDonn;
import com.gsb.donnees.modele.FraisFBDD;
import com.gsb.donnees.modele.FraisHFBDD;
import com.gsb.donnees.modele.IdBDD;

/**
 * MainActivity.java
 * 
 * Classe MainActivity qui hérite de la classe Activity.
 * Elle est associée au layout activity_main.xml.
 * Permet d'afficher le menu principal de l'application
 * 
 * @author 		Cavron Jérémy
 * @version		v 1.0
 */
public class MainActivity extends Activity {

    static Activity thisActivity = null;
    private IdBDD idBdd;
    private FraisFBDD fraisFBdd;
    private FraisHFBDD fraisHBdd;
	
	/**
	 * Création de l'interface Menu Principal.
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idBdd = new IdBDD(this);
        fraisFBdd = new FraisFBDD(this);
        fraisHBdd = new FraisHFBDD(this);
        thisActivity = this;
        
     	
        // chargement des méthodes événementielles
        cmdMenu_clic(((Button)findViewById(R.id.cmdKm)), KmActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdRepas)), RepasActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdNuitee)), NuitActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdEtape)), EtapeActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdHf)), HfActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdHfRecap)), HfRecapActivity.class) ;
        cmdTransfert_clic() ;
    }

    /**
     * Création du Setting Menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    /**
     * Evennement sur le clic du bouton Paramètres identifiant
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	
        case R.id.action_settings:

        	// ouvre l'activité
			Intent intent = new Intent(MainActivity.this, LoginActivity.class) ;
			startActivity(intent);
			finish();
            return true;
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    

    /**
     * Sur la sélection d'un bouton dans l'activité principale ouverture de l'activité correspondante.
     */
    private void cmdMenu_clic(Button button, final Class classe) {
    	button.setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
    			// ouvre l'activité
    			Intent intent = new Intent(MainActivity.this, classe);
    			startActivity(intent);
    			finish();
    		}
    	}) ;
    }
    
    /**
     * Cas particulier du bouton pour le transfert d'informations vers le serveur.
     */
    private void cmdTransfert_clic() {
    	((Button)findViewById(R.id.cmdTransfert)).setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {

                alertMessage();

    		}
    	}) ;    	
    }

    /**
     * Message de confirmation pour l'envoi des données
     */
    public void alertMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // envoi les informations des du mois vers le serveur

                        //--- test de connexion ---
                        ConnectivityManager connManag = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

                        NetworkInfo networkInfo = connManag.getActiveNetworkInfo();

                        //Si la connexion est opérationnelle alors on envoie les données
                        if(networkInfo != null && networkInfo.isConnected()) {
                            ExportDonn exportD = new ExportDonn();
                            exportD.setTimeOut(10000);//Temps de fin de connexion

                            //Initialisation de la liste JSON pour l'envoie.
                            exportD.recupeListes(idBdd, fraisFBdd, fraisHBdd);

                            //Envoi des données
                            exportD.execute("http://nomDuServeur/service.php");

                        }else{
                            //Affichage du message
                            Toast.makeText(getApplicationContext(), "Problème de connexion", Toast.LENGTH_LONG).show();
                        }
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Voulez-vous vraiment envoyer les données ?")
                .setPositiveButton("Oui", dialogClickListener)
                .setNegativeButton("Non", dialogClickListener).show();
    }

    /**
     * Procédure qui permet d'afficher un message qui vient d'un autre thread.
     *
     * @param message : est le message à afficher.
     */
    public static void receptMessage(String message) {
        Toast.makeText(thisActivity, message, Toast.LENGTH_LONG).show();
    }


}
