package com.gsb.suividevosfrais;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import com.gsb.donnees.modele.FraisHFBDD;

/**
 * FraisHfAdapter.java
 * 
 * Classe FraisHfAdapter. Permet de créer l'objet FraisHfAdapter.
 * Elle hérite de la classe BaseAdapter. Permet de créer une liste hors forfait
 * avec les boutons supprimés.
 * 
 * @author      Cavron Jérémy
 * @version     v 1.0
 */
public class FraisHfAdapter extends BaseAdapter {


    //--- Déclaration des propriétés ---
	ArrayList<FraisHf> lesFrais;// liste des frais du mois
    public FraisHf fraisHf;
	LayoutInflater inflater;
	String uneDate;// mois et année
	public Context context;// contexte pour gérer la sérialisation
    public FraisHFBDD fraisHFBDD;//Objet de manipulation sur la base de données.
	
	
	/**
	 * Constructeur de l'adapter pour valoriser les propriétés
	 * @param context
	 * @param lesFrais
	 */
	public FraisHfAdapter(Context context, ArrayList<FraisHf> lesFrais, String uneDate) {

        inflater = LayoutInflater.from(context);
        this.lesFrais = lesFrais;
        this.uneDate = uneDate;
        this.context = context;
        fraisHFBDD = new FraisHFBDD(this.context);

	}
	
	/**
	 * retourne le nombre d'éléments de la listview
	 */
	@Override
	public int getCount() {
		return lesFrais.size();
	}

    public Context getContext() {
        return this.context;
    }
	/**
	 * retourne l'item de la listview à un index précis
	 */
	@Override
	public Object getItem(int index) {
		return lesFrais.get(index);
	}

	/**
	 * retourne l'index de l'élément actuel
	 */
	@Override
	public long getItemId(int index) {
		return index;
	}

	/**
	 * structure contenant les éléments d'une ligne
	 */
	private class ViewHolder {
		TextView txtListJour;
		TextView txtListMontant;
		TextView txtListMotif;
		ImageView imgSupp;
	}
	
	/**
	 * Affichage dans la liste
	 */
	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		ViewHolder holder ;
		
		if (convertView == null) {
			holder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.layout_liste, null);
			holder.txtListJour = (TextView)convertView.findViewById(R.id.txtListJour);
			holder.txtListMontant = (TextView)convertView.findViewById(R.id.txtListMontant);
			holder.txtListMotif = (TextView)convertView.findViewById(R.id.txtListMotif);
			holder.imgSupp = (ImageView)convertView.findViewById(R.id.imgSupp);// Bouton pour la suppression des frais hors forfait
			
			convertView.setTag(holder);
			
			
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.txtListJour.setText(lesFrais.get(index).getJour());
		holder.txtListMontant.setText(Float.toString(lesFrais.get(index).getMontant())) ;
		holder.txtListMotif.setText(lesFrais.get(index).getDescriptif());
		holder.imgSupp.setId(index);
		holder.imgSupp.setClickable(true);
		holder.imgSupp.setOnClickListener(new imageViewClickListener(index));
		
	
		return convertView;
	}
	
	/**
	 * Action sur l'appuis du bouton suppression
	 * 
	 * @author Cavron Jérémy
	 *
	 */
	private class imageViewClickListener implements OnClickListener {
		int position;

		public imageViewClickListener( int pos) {
		            this.position = pos;
		}

		public void onClick(View v) {

            //Récupérer l'index du frais.
            int index = lesFrais.get(this.position).getId();

            //On supprime le frais hors forfait de la liste vue
            lesFrais.remove(getItem(this.position));

            //Ouverture de la base de données
            fraisHFBDD.ouvre();

            //Suppression du frais dans la base de données.
            fraisHFBDD.supprimeFraisHF(index);


            //Fermeture de la base de données
            fraisHFBDD.ferme();

		    //On réactualise la liste dans la vue
		    FraisHfAdapter.this.notifyDataSetChanged();
		    	
		    //Message après suppression
		    Toast.makeText(context, "Supression effectuée", Toast.LENGTH_SHORT).show();
		}
	}

	
}
