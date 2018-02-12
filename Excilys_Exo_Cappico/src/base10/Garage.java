package base10;

import java.util.ArrayList;

public class Garage {
		
	private ArrayList<Voiture>  voitures;
	
	
	public Garage(){
		voitures= new ArrayList<Voiture>();
		Voiture v = new Voiture();
		v.marque = "Toyota";
		v.modele = "Mazda";
		v.prix = 8000;
		Voiture v1 = new Voiture();
		v1.marque = "Renault";
		v1.modele = "R9";
		v1.prix = 800;
		 voitures.add(v1);
	}
	
	/*  méthodes **/
	
	public void rentreVoiture(Voiture v){
		
		voitures.add(v);
		
	}
	
	public void sortVoiture(){
		
		if(voitures.size()>0){
			voitures.remove(0);
		}
		else{ System.out.println("plus de voitures dans le garage ");}
	}
	
	
}
