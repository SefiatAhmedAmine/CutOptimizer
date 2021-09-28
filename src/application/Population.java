package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Population {
	private List<Genome> population = new ArrayList<Genome>();
	private List<Board> stock = new ArrayList<Board>();
	private List<Board> demande = new ArrayList<Board>();
	public Comparator<Genome> comparator = new Comparator<Genome>() {
		@Override
		public int compare(Genome a, Genome b) {

			if (Float.compare(a.getArea(), b.getArea()) == 0) {
				if ( a.getStock_left().size() == b.getStock_left().size() ) {
					int size = a.getStock_left().size();
					if (size == 0) return 0;
					return Float.compare(b.getStock_left().get(size - 1).getArea(), a.getStock_left().get(size - 1).getArea());
				}
				else return a.getStock_left().size() - b.getStock_left().size();
			}
			else return Float.compare(b.getArea(), a.getArea());
//			return Float.compare(a.getArea(), b.getArea());
		};
	};
	private int size = 100;
	
	public Population(List<Board> stock, List<Board> demande) {
		this.setStock(stock);
		this.setDemande(demande);
	}
	
	public Population(List<Board> stock, List<Board> demande, int size) {
		this.setStock(stock);
		this.setDemande(demande);
		this.size = size;
	}

	public void generatePopulation(List<Board> stock, List<Board> demande){
		this.population.clear();
		for (int i = 0; i < this.size; i++) {
			this.population.add(new Genome(stock, demande));
		}
	}
	
	public void generatePopulation(){
		this.generatePopulation(this.stock, this.demande);
	}
	
	public Genome[] pair_selection() {
		return this.pair_selection(this.population);
	}
	
	public Genome[] pair_selection(List<Genome> population) {
		float weight = 0f;
		Genome[] selected = new Genome[2];
		int choices [] = new int[2], choice_index = 0;
		
		for (int i = 0; i < population.size(); i++) {
			weight += population.get(i).getArea() /(i+1);
		}
		
		final Random rn = new Random();
		float proba1 = rn.nextFloat() * weight, proba2 = rn.nextFloat() * weight;
		if (proba1 > proba2) {
			float temp = proba1; proba1 = proba2; proba2 = temp;
		}
		float choice_weight = population.get(0).getArea();
		for (int i = 1; i < population.size(); i++) {
			if (choice_weight < proba1 && 
					choice_weight + population.get(i).getArea() /(i+1) > proba1) {
				choices[choice_index++] = i;
				proba1 = proba2;
			}
			
			else if (choice_index == 0 && choice_weight > proba1) {
				choices[choice_index++] = i;
				proba1 = proba2;
			}
			choice_weight += population.get(i).getArea() /(i+1) ;
			if (choice_index == 2) {
				break;
			}
		}
		while (choices[0] == choices[1]) {
			choices[1] = rn.nextInt(10);
		}
		selected[0] = new Genome(population.get(choices[0]));
		selected[1] = new Genome(population.get(choices[1]));
		
		return selected;
	}
	
	public Genome[] crossover (Genome a, Genome b) {
		Genome[] children = new Genome[2];
		Genome a1 = new Genome(a), b1 = new Genome(b);
		
		if (a.getLen() < 2) {
			children[0] = a1;	children[1] = b1;
			return children;
		}
		
		int p = new Random().nextInt(a.getLen()-1) + 1;
		HashSet<Integer> numsA = new HashSet<Integer>(), numsB = new HashSet<Integer>();
		for (int i = p ; i < a.getLen() ; i++)	numsA.add(a.getDemande().get(i).getNum());
		for (int i = p ; i < a.getLen() ; i++)	numsB.add(b.getDemande().get(i).getNum());

		for (int i = p; i < a1.getLen(); i++) {
			for (int j = 0; j < b.getLen(); j++) {
				if ( numsA.contains(b.getDemande().get(j).getNum())) {
//					System.out.println("i:" + i + ", size:" + a1.getDemande().size());
					a1.getDemande().set(i, new Board(b.getDemande().get(j)));
					numsA.remove(b.getDemande().get(j).getNum());
					break;
				}
			}
		}
		for (int i = p; i < b1.getLen(); i++) {
			for (int j = 0; j < a.getLen(); j++) {
				if ( numsB.contains(a.getDemande().get(j).getNum())) {
					b1.getDemande().set(i, new Board(a.getDemande().get(j)));
					numsB.remove(a.getDemande().get(j).getNum());
					break;
				}
			}
		}
		
		children[0] = a1;	children[1] = b1;
//		System.out.println(p + a.toString()+a1.toString() + b.toString()+b1.toString());
//		System.exit(0);

		return children;
	}

//	public Genome[] crossover (Genome a, Genome b) {
//		Genome a1 = new Genome(a), b1 = new Genome(b);
//		
//		if (a.getLen() < 2) {
//			return new Genome[] {a1, b1};
//		}
//		
//		for (int i = 0; i < a.getLen() ; i+= 3) {
//			for (int j = 0 ; j < b.getLen() ; j++) {
//				if (b.getDemande().get(j).getNum() == a1.getDemande().get(i).getNum()) {
//					a1.getDemande().set(i, new Board(b.getDemande().get(j)));
//				}
//				if (a.getDemande().get(j).getNum() == b1.getDemande().get(i).getNum()) {
//					b1.getDemande().set(i, new Board(a.getDemande().get(j)));
//				}
//			}
//		}
//		return new Genome[] {a1, b1};
//	}
		
	
	public List<Genome> fit_(int generation_limit, int search_limit){
		int saturation = 0;
		float best_fit = 0f, last_best = 0f;
		List<Genome> population = new ArrayList<Genome>();
		for (Genome g : this.population)	population.add(new Genome(g));
		
		for (int i =0; i < generation_limit; i++) {
			for (Genome g : population)	g.fitness();
//			System.out.println("Generation: " + i);
//			Collections.sort(population);
			Collections.sort(population, comparator);
			
			last_best = population.get(0).getFitness();
			if (last_best > best_fit) {
				saturation = 0;	best_fit = last_best;
//				System.out.println("best: "+ best_fit + "//" + population.get(0).toString());
			}
			else {
				saturation++;
//				System.out.println("//:: " + population.get(0).getFitness());
			}
			
			if (saturation == search_limit) return population;
			
			List<Genome> next_generation = new ArrayList<Genome> (population.subList(0, 5));
			for (int j = 0; j < this.size / 8; j++) next_generation.add(new Genome(this.stock, this.demande));
			for (int j = 5; j < 5 + this.size / 8; j++) next_generation.get(j).fitness();
			
//			System.out.println(next_generation.get(0).getFitness() + next_generation.get(0).toString());
//			System.exit(0);
			
			for (int j = 0; j < this.size / 2; j++) {
				Collections.sort(next_generation, comparator);
				Genome[] parents = this.pair_selection(next_generation);
				Genome[] children = this.crossover(parents[0], parents[1]);
				children[0].mutate();	children[1].mutate();
				children[0].fitness();	children[1].fitness();
				next_generation.add(children[0]);	next_generation.add(children[1]);
			}
			population = next_generation;
		}
		
		for (int j = 0; j < population.size(); j++) population.get(j).fitness();
		Collections.sort(population, comparator);
//		Collections.sort(population);
		return population;		
	}
	
	public List<Genome> fit_(){
		int generation_limit = 100, search_limit = 10;
		return this.fit_(generation_limit, search_limit);
	}
	
	public List<Genome> fit(int generation_limit, int existace_search_limit, int search_limit) {
		List<Genome> plans = new ArrayList<Genome>();
		boolean is_fulfiled = true;
		int existance_limit = 0;
		
		while (is_fulfiled) {
			this.generatePopulation();
//			System.out.println(this.population.get(0).toString() + this.population.get(2).toString() + "\n--------------------");
			List<Genome> population = this.fit_(generation_limit, search_limit);
			if (population.get(0).getFitness() == 0) {
				existance_limit++;
				if (existance_limit > existace_search_limit) return plans;
				continue;
			}
			existance_limit = 0;
			plans.add(population.get(0));
			for (Board rect : plans.get(plans.size()-1).getDemande()) {
				if (rect.getCut() != 0) {
					for (int i = 0; i < this.demande.size(); i++) {
						if (this.demande.get(i).getNum() == rect.getNum()) {
							this.demande.remove(i);
							break;
						}
					}
				}
			}
			if (this.demande.size() == 0) is_fulfiled = false;
		}
		
		return plans;
	}
	
	public List<Genome> fit(){
		int generation_limit = 100, existance_search_limit = 10, search_limit = 10;
		return this.fit(generation_limit, existance_search_limit, search_limit);
	}
	
	public List<Genome> getPopulation() {
		return this.population;
	}

	public void setPopulation(List<Genome> population) {
		this.population.clear();
		for (Genome g : population) {
			this.population.add(new Genome(g));
		}
	}

	public List<Board> getStock() {
		return this.stock;
	}

	public void setStock(List<Board> stock2) {
		this.stock.clear();
		for (Board rect : stock2) {
			this.stock.add(new Board(rect));
		}
	}

	public List<Board> getDemande() {
		return this.demande;
	}

	public void setDemande(List<Board> demande2) {
		this.demande.clear();
		for (Board rect : demande2) {
			this.demande.add(new Board(rect));
		}
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}
