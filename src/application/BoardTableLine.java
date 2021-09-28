package application;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class BoardTableLine {

	SimpleFloatProperty hauteur, largeur;
	SimpleIntegerProperty quantite;
	public BoardTableLine(float hauteur, float largeur, int quantite) {
		super();
		this.hauteur = new SimpleFloatProperty(hauteur);
		this.largeur = new SimpleFloatProperty(largeur);
		this.quantite = new SimpleIntegerProperty(quantite);
	}
	public float getHauteur() {
		return hauteur.get();
	}
	public float getLargeur() {
		return largeur.get();
	}
	public int getQuantite() {
		return quantite.get();
	}
	public void setHauteur(float hauteur) {
		this.hauteur.set(hauteur);
	}
	public void setLargeur(float largeur) {
		this.largeur.set(largeur);
	}
	public void setQuantite(int quantite) {
		this.quantite.set(quantite);
	}
	@Override
	public String toString() {
		return "BoardTableLine [hauteur=" + hauteur + ", largeur=" + largeur + ", quantite=" + quantite + "]";
	}
	
	
}
