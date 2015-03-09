package com.gsb.donnees.controleurs;

import android.content.Context;
import android.os.AsyncTask;
import com.gsb.suividevosfrais.MainActivity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.gsb.donnees.modele.FraisFBDD;
import com.gsb.donnees.modele.FraisHFBDD;
import com.gsb.donnees.modele.IdBDD;

/**
 * ExportDonn.java
 *
 * Classe qui permet l'export des frais du dernier mois.
 * Cette classe hérite de la classe AsyncTask qui permet
 * d'executer l'export en tâche de fond pour éviter que
 * l'application se fige le temps du transfert.
 *
 * @author 		Cavron Jérémy
 * @version		v 1.0
 */
public class ExportDonn extends AsyncTask<String, Void, String> {


    private int timeOut = 10000;//Temps de connexion.
    private JSONObject json = new JSONObject();
    private Context context;


    @Override
    protected void onPostExecute(String result) {
        //Envoi du message dans le toast de l'activité principale
        MainActivity.receptMessage(result);

    }

    /**
     * Procédure qui modifie le temps de connexion.
     *
     * @param timeOut : est le temps de connexion.
     */
    public void setTimeOut(int timeOut){
        this.timeOut = timeOut;
    }

    /**
     * Procédure qui envoie la liste des frais forfait
     * et la liste des frais hors forfait du mois en cours.
     *
     * @param message
     */
    @Override
    protected String doInBackground(String... message) {


        try {

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, timeOut);
            HttpConnectionParams.setSoTimeout(httpParams, timeOut);



            HttpClient client = new DefaultHttpClient();//Instanciantion d'un nouveau client HttpClient

            HttpPost post = new HttpPost(message[0]);//message [0] est l'url envoyée en paramètre

            post.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF8")));


            post.setHeader("json", json.toString());

            HttpResponse reponse = client.execute(post);

            HttpEntity entite = reponse.getEntity();

            if(entite != null) {
                InputStream inS = entite.getContent();

                String result = convertStreamToString(inS);

                inS.close();
                
                return result;
            }


        }catch (Throwable e){
            e.printStackTrace();
        }
        return "Problème d'envoi";

    }

    /**
     * Procédure qui récupère les frais forfaits, frais hors forfaits et identifiant pour
     * ensuite les préparer à  l'envoie.
     *
     * @param idBdd
     * @param fraisFBdd
     * @param fraisHBdd
     */
    public void recupeListes(IdBDD idBdd, FraisFBDD fraisFBdd, FraisHFBDD fraisHBdd){


        try{
            //Ouverture de la base de données pour la table identifiant
            idBdd.ouvre();

            //Ajout dans la liste, l'objet identifiant avec login et mot de passe
            json.put("login", idBdd.getIdentifiantMdp().getLogin());
            json.put("mdp", idBdd.getIdentifiantMdp().getMdp());

            //fermeture de la base de données pour la tble identifiant
            idBdd.ferme();

            //Ouverture de la base de données pour les frais forfaitaires
            fraisFBdd.ouvre();

            //Date des km qui sert pour la date générale de tous les frais
            String laDerniereDate = fraisFBdd.getDernierFraisF("km").getDate();

            //Ajout dans la liste, l'objet frais forfait de toute catégories du mois en cours.
            json.put("qteKm", fraisFBdd.getDernierFraisF("km").getQte());
            json.put("dateKm", laDerniereDate);


            json.put("qteEtape", fraisFBdd.getDernierFraisF("etape").getQte());
            json.put("dateEtape", fraisFBdd.getDernierFraisF("etape").getDate());

            json.put("qteNuit", fraisFBdd.getDernierFraisF("nuit").getQte());
            json.put("dateNuit", fraisFBdd.getDernierFraisF("nuit").getDate());

            json.put("qteRepas", fraisFBdd.getDernierFraisF("repas").getQte());
            json.put("dateRepas", fraisFBdd.getDernierFraisF("repas").getDate());


            //Fermeture de la base de données pour les frais forfaitaires
            fraisFBdd.ferme();

            //Ouverture de la base de données pour les frais hors forfaits
            fraisHBdd.ouvre();



            //Ajout dans l'objet JSON, la liste JSON frais hors forfait du dernier mois saisi.
            json.put("listeFHf", fraisHBdd.getListeFraisHFJSON(laDerniereDate));



            //Fermeture de la base de données pour les frais hors forfaits
            fraisHBdd.ferme();

        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    /**
     * Fonction qui converti un objet Stream en chaîne.
     *
     * @param inS : est l'objet stream
     * @return sb.toString() : est la chaîne retournée.
     */
    private String convertStreamToString(InputStream inS) {

        StringBuilder sb = new StringBuilder();
        try {
            //Pas d'encodage utf8 car il est géré sur la page php du serveur
            BufferedReader lire = new BufferedReader(new InputStreamReader(inS));


            String ligne = null;


            while ((ligne = lire.readLine()) != null) {
                sb.append(ligne + "\n");
            }
        }catch(IOException e) {
            System.out.println("Problème de conversion");
            e.printStackTrace();
        }finally {
            try {
                inS.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
