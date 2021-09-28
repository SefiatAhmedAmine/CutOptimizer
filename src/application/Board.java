package application;

import javafx.scene.paint.Color;

public class Board {

	private float hauteur;
	private float largeur;
	private float area;
	private int num = 0;
	private float x = .0f;
	private float y = .0f;
	private int cut = 0;
	private Color color = Color.rgb(255, 255, 255);
	
	public Board(float hauteur, float largeur) {
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.area = hauteur * largeur;
	}
	
	public Board(float hauteur, float largeur, int num) {
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.area = hauteur * largeur;
		this.num = num;
	}
	
	public Board(float hauteur, float largeur, int num, Color c) {
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.area = hauteur * largeur;
		this.num = num;
		this.color = c;

	}

	public Board(float hauteur, float largeur, float x, float y, int num) {
		this.hauteur = hauteur;
		this.largeur = largeur; 
		this.area = hauteur * largeur;
		this.x = x;
		this.y = y;
		this.num = num;			
	}
	
	public Board(Board origin) {
		this.hauteur = origin.hauteur;
		this.largeur = origin.largeur;
		this.area = origin.area;
		this.x = origin.x;
		this.y = origin.y;
		this.num = origin.num;
		this.cut = origin.cut;
		this.color = origin.getColor();
	}

	public float getHauteur() {
		return hauteur;
	}

	public void setHauteur(float hauteur) {
		this.hauteur = hauteur;
	}

	public float getLargeur() {
		return largeur;
	}

	public void setLargeur(float largeur) {
		this.largeur = largeur;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public int getCut() {
		return cut;
	}

	public void setCut(int cut) {
		this.cut = cut;
	}

	public float getArea() {
		return area;
	}

	public void setArea(float area) {
		this.area = area;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String toString() {
		String s = "{board:" + String.valueOf(this.num) 
					+ ", W:" + this.largeur + ", H:" + this.hauteur
					+ ", Area:" + this.area
					+ ", X:" + this.x + ", Y:" + this.y 
					+ ", Cut:" + String.valueOf(this.cut)
					+ ", Color:("+color.getRed()+","+color.getGreen()+","+color.getBlue()+")  }\n";
		return s;
	}
	
}