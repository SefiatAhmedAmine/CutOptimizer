package application;
	
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	public static Stage primaryStage;
	private Scene primaryScene;
	private BorderPane mainLayout;
	public static int demande_counter = 1;

	static List<Board> copy_list(List<Board> stock) {
		ArrayList<Board> copy = new ArrayList<Board>();
		for (Board rect : stock) {
			copy.add(new Board(rect));
		}
		return copy;
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("views/Main_layout.fxml"));
		this.mainLayout = loader.load();
		this.primaryStage.setTitle("Nour Cut Optimizer");		
		this.primaryScene = new Scene(this.mainLayout);
		this.primaryStage.setScene(primaryScene);
		this.primaryStage.show();		
	}
	
	
	
	public static void main(String[] args) {
		launch(args);

//		ArrayList<Board> stock = new ArrayList<Board>();
//		ArrayList<Board> demande = new ArrayList<Board>();
//		stock.add(new Board(255, 183));
//		demande.add(new Board(185, 28, 1));
//		demande.add(new Board(185, 28, 2));
//		demande.add(new Board(78, 28, 3));
//		demande.add(new Board(253, 28, 4));
//		demande.add(new Board(176, 28, 5));
//		demande.add(new Board(15, 28, 6));
//		demande.add(new Board(15, 28, 7));
//		demande.add(new Board(15, 28, 8));
//		demande.add(new Board(253, 7, 9));
//		demande.add(new Board(253, 7, 10));
//		demande.add(new Board(200, 7, 11));
//		demande.add(new Board(200, 7, 12));
//		demande.add(new Board(200, 7, 13));
//		demande.add(new Board(200, 7, 14));
//		demande.add(new Board(85, 7, 15));
//		demande.add(new Board(85, 7, 16));
//		demande.add(new Board(176, 7, 17));
//		demande.add(new Board(176, 7, 18));
//		demande.add(new Board(253, 10, 25));
//		demande.add(new Board(100, 100, 26));
//		demande.add(new Board(80, 80, 27));
//		
//		Population p = new Population(stock, demande);
//		List<Genome> plans = p.fit(10000,10,20);
//
//		for (int i = 0; i < plans.size(); i++){
//			System.out.println( plans.get(i).getFitness() + plans.get(i).toString());
//		}		
//		System.out.println("demaned:" + p.getDemande().size());


		
	}

	
}
