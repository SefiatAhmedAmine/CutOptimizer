package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Genome {
	private List<Board> stock = new ArrayList<Board>();
	private List<Board> demande = new ArrayList<Board>();
	private List<Board> stock_left = new ArrayList<Board>();
	private int len = 0;
	private float area = .0f;
	private List<Float[]> linesX = new ArrayList<Float[]>();
	private List<Float[]> linesY = new ArrayList<Float[]>();
	private Random rn = new Random();
	
	public Genome(List<Board> stock, List<Board> demande) {
		this.stock = Main.copy_list(stock);
		this.demande = Main.copy_list(demande);  Collections.shuffle(this.demande);
		this.stock_left.add(new Board(0, 0));
		this.len = demande.size();
		make_genome();
	}
	
	public Genome(Genome g) {
		this.stock = Main.copy_list(g.stock);
		this.demande = Main.copy_list(g.demande);
		this.stock_left.add(new Board(0, 0));
		this.len = this.demande.size();
		make_genome();
	}
	
	public void make_genome() {
		List<Board> curr_stock = Main.copy_list(stock);
		float lost_space = 0f;
		int cut;
		boolean cut_possible;
		for (int d = 0 ; d < this.len ; d++ ) {
			cut = rn.nextInt(3);
			if (cut == 0) continue;
			else {
				if (cut == 1) {
					cut_possible = this.cut_hv(curr_stock, d);
					if (cut_possible == false) {
						cut_possible = this.cut_vh(curr_stock, d);
						if (cut_possible) 	demande.get(d).setCut(2);
					}
					else demande.get(d).setCut(1);
				}
				else {
					cut_possible = this.cut_vh(curr_stock, d);
					if (cut_possible == false) {
						cut_possible = this.cut_hv(curr_stock, d);
						if (cut_possible) demande.get(d).setCut(1);
					}
					else demande.get(d).setCut(2);
				}
			}
//			System.out.println(cut_possible);
			if (cut_possible) {
				lost_space += demande.get(d).getArea() ;
			}
		}
		this.area = lost_space;
		

	}
	
	public boolean cut_hv(List<Board> curr_stock, int board_index) {
		boolean cut_possible = false;
		Collections.sort(curr_stock, (b1, b2) ->{
			if (((Board) b1).getArea() == ((Board) b2).getArea()) {
				if (((Board) b1).getHauteur() == ((Board) b2).getHauteur()) {
					return Float.compare(((Board) b1).getLargeur(), ((Board) b2).getLargeur());
				}
				else {
					return Float.compare(((Board) b1).getHauteur(), ((Board) b2).getHauteur());
				}
			}
			else {
				return Float.compare(((Board)b1).getArea() , ((Board) b2).getArea());
			}
		});
		
		for (Board rect : curr_stock) {
			if (this.demande.get(board_index).getLargeur() <= rect.getLargeur() && this.demande.get(board_index).getHauteur() <= rect.getHauteur()) {
				curr_stock.remove(rect);
				
				// metre a jour les coordonnees du rect demande
				this.demande.get(board_index).setX(rect.getX());
				this.demande.get(board_index).setY(rect.getY());
				
				// piece cute à droite
				if (rect.getLargeur() > this.demande.get(board_index).getLargeur()) {
					curr_stock.add(new Board(this.demande.get(board_index).getHauteur(),
											rect.getLargeur() - this.demande.get(board_index).getLargeur(),
											rect.getX() + this.demande.get(board_index).getLargeur(),
											rect.getY(),
											0));
					this.linesX.add(new Float[] {rect.getX() + this.demande.get(board_index).getLargeur(), rect.getX() + this.demande.get(board_index).getLargeur()});
					this.linesY.add(new Float[] {rect.getY(), rect.getY() + this.demande.get(board_index).getHauteur()});
				}
				
				// piece chute en haut
				if (rect.getHauteur() > this.demande.get(board_index).getHauteur()) {
					curr_stock.add(new Board(rect.getHauteur() - this.demande.get(board_index).getHauteur(),
											rect.getLargeur(),
											rect.getX(),
											rect.getY() + this.demande.get(board_index).getHauteur(),
											0));
					this.linesX.add(new Float[] {rect.getX(), rect.getX() + rect.getLargeur()});
					this.linesY.add(new Float[] {rect.getY() + this.demande.get(board_index).getHauteur(), rect.getY() + this.demande.get(board_index).getHauteur()});
				}
				cut_possible = true;
				break;
			}
		}
		return cut_possible;
	}

	public boolean cut_vh(List<Board> curr_stock, int board_index) {
		boolean cut_possible = false;
		Collections.sort(curr_stock, (b1, b2) ->{
			if (((Board) b1).getArea() == ((Board) b2).getArea()) {
				if (((Board) b1).getLargeur() == ((Board) b2).getLargeur()) {
					return Float.compare(((Board) b1).getHauteur(), ((Board) b2).getHauteur());
				}
				else {
					return Float.compare(((Board) b1).getLargeur(), ((Board) b2).getLargeur());
				}
			}
			else {
				return Float.compare(((Board)b1).getArea() , ((Board) b2).getArea());
			}
		});
		
		for (Board rect : curr_stock) {
			if (this.demande.get(board_index).getLargeur() <= rect.getLargeur() && this.demande.get(board_index).getHauteur() <= rect.getHauteur()) {
				curr_stock.remove(rect);
				
				// mettre a jour les cordonnees du rect demande
				this.demande.get(board_index).setX(rect.getX());
				this.demande.get(board_index).setY(rect.getY());
				// piece a droite
				if (rect.getLargeur() > this.demande.get(board_index).getLargeur()) {
					curr_stock.add(new Board(rect.getHauteur(),
											rect.getLargeur() - this.demande.get(board_index).getLargeur(),
											rect.getX() + this.demande.get(board_index).getLargeur(),
											rect.getY(),
											0));
					this.linesX.add(new Float[] {rect.getX() + this.demande.get(board_index).getLargeur(), rect.getX() + this.demande.get(board_index).getLargeur()});
					this.linesY.add(new Float[] {rect.getY(), rect.getY()+ rect.getHauteur()});
				}
				// piece en haut
				if (rect.getHauteur() > this.demande.get(board_index).getHauteur()) {
					curr_stock.add(new Board(rect.getHauteur() - this.demande.get(board_index).getHauteur(),
											this.demande.get(board_index).getLargeur(),
											rect.getX(),
											rect.getY() + this.demande.get(board_index).getHauteur(),
											0));
					this.linesX.add(new Float[] {rect.getX(), rect.getX() + this.demande.get(board_index).getLargeur()});
					this.linesY.add(new Float[] {rect.getY() + this.demande.get(board_index).getHauteur(), rect.getY() + this.demande.get(board_index).getHauteur()});
				}
				cut_possible = true;
				break;
			}
		}

		return cut_possible;
	}

	public void fitness() {
		this.linesX.clear();	this.linesY.clear();
		this.area = 0;
		List<Board> curr_stock = Main.copy_list(stock);
		boolean demande_fit, assemblable;
		
		for (int d = 0; d < this.len ; d++) {
			demande_fit = false;
			if (this.demande.get(d).getCut() == 0) continue;
			else if (this.demande.get(d).getCut() == 1) {
				demande_fit = cut_hv(curr_stock, d);
			}
			else if (this.demande.get(d).getCut() == 2) {
				demande_fit = cut_vh(curr_stock, d);
			}
			if (demande_fit == false) {
				this.area = 0f;
				return;
			}
						
			assemblable = true;
			while (assemblable) {
				assemblable = false;
				for (int i = 0 ; i < curr_stock.size() ; i++) {
					for (int j = i+1 ; j < curr_stock.size() ; j++) {
						if (curr_stock.get(i).getX() == curr_stock.get(j).getX() 
								&& curr_stock.get(i).getLargeur() == curr_stock.get(j).getLargeur()) {
							if (curr_stock.get(i).getY() + curr_stock.get(i).getHauteur() == curr_stock.get(j).getY()) {
								assemblable = true;
								curr_stock.add(new Board(curr_stock.get(i).getHauteur() + curr_stock.get(j).getHauteur(),
														curr_stock.get(i).getLargeur(), 
														curr_stock.get(i).getX(),
														curr_stock.get(i).getY(),
														0));
								curr_stock.remove(j); curr_stock.remove(i);
								break;
							}
							else if (curr_stock.get(j).getY() + curr_stock.get(j).getHauteur() == curr_stock.get(i).getY()) {
								assemblable = true;
								curr_stock.add(new Board(curr_stock.get(j).getHauteur() + curr_stock.get(i).getHauteur(),
														curr_stock.get(j).getLargeur(), 
														curr_stock.get(j).getX(),
														curr_stock.get(j).getY(),
														0));
								curr_stock.remove(j); curr_stock.remove(i);
								break;
							}
						}
						if (curr_stock.get(i).getY() == curr_stock.get(j).getY() 
								&& curr_stock.get(i).getHauteur() == curr_stock.get(j).getHauteur()) {
							if (curr_stock.get(i).getX() + curr_stock.get(i).getLargeur() == curr_stock.get(j).getX()) {
								assemblable = true;
								curr_stock.add(new Board(curr_stock.get(i).getHauteur(),
														curr_stock.get(i).getLargeur() + curr_stock.get(j).getLargeur(), 
														curr_stock.get(i).getX(),
														curr_stock.get(i).getY(),
														0));
								curr_stock.remove(j); curr_stock.remove(i);
								break;
							}
							else if (curr_stock.get(j).getX() + curr_stock.get(j).getLargeur() == curr_stock.get(i).getY()) {
								assemblable = true;
								curr_stock.add(new Board(curr_stock.get(j).getHauteur(),
										curr_stock.get(j).getLargeur() + curr_stock.get(i).getLargeur(),
														curr_stock.get(j).getX(),
														curr_stock.get(j).getY(),
														0));
								curr_stock.remove(j); curr_stock.remove(i);
								break;
							}
						}
					}
					if (assemblable) break;
				}
			}
			this.area += this.demande.get(d).getArea();
		}
		if (curr_stock.size() > 0) {
			this.stock_left = Main.copy_list(curr_stock);
			Collections.sort(this.stock_left, (b1, b2)->{
				return Float.compare(((Board) b1).getArea(), ((Board) b2).getArea());
			});
		}
	}


	public void mutate(int size, float probability) {
		for (int i = 0 ; i < size ; i++) {
			int index = rn.nextInt(this.len);
			if (rn.nextFloat() >= probability)
				this.demande.get(index).setCut((rn.nextInt(2)+1 + this.demande.get(index).getCut())%3);
		}
	}
	
	public void mutate() {
		this.mutate(1, 0.5f);
	}
	
	public float getFitness() {
		return 100 * this.area / this.stock.get(0).getArea();
	}
	
	public String toString() {
		String s = "{\n";
		for (Board b : this.demande) {
			s+= b.toString();
		}
		return s + "}";
	}
	
	public List<Board> getStock() {
		return stock;
	}

	public void setStock(List<Board> stock) {
		this.stock = Main.copy_list(stock);
	}

	public List<Board> getDemande() {
		return demande;
	}

	public void setDemande(List<Board> demande) {
		this.demande = Main.copy_list(demande);
	}

	public List<Board> getStock_left() {
		return stock_left;
	}

	public void setStock_left(List<Board> stock_left) {
		this.stock_left = Main.copy_list(stock_left);
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public float getArea() {
		return area;
	}

	public void setArea(float area) {
		this.area = area;
	}

	public List<Float[]> getLinesX() {
		return linesX;
	}

	public void setLinesX(List<Float[]> linesX) {
		this.linesX = new ArrayList<Float[]>(linesX);
	}

	public List<Float[]> getLinesY() {
		return linesY;
	}

	public void setLinesY(List<Float[]> linesY) {
		this.linesY = new ArrayList<Float[]>(linesY);
	}


}
