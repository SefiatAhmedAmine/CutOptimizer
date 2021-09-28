package application.views;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import application.Board;
import application.BoardTableLine;
import application.Genome;
import application.Population;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class MainViewController implements Initializable {

    @FXML
    private TableView<BoardTableLine> demande_table;
    @FXML
    private TableColumn<BoardTableLine, Float> demande_hauteur;
    @FXML
    private TableColumn<BoardTableLine, Float> demande_largeur;
    @FXML
    private TableColumn<BoardTableLine, Integer> demande_quantite;
    @FXML
    private TextField add_d_h;
    @FXML
    private TextField add_d_l;
    @FXML
    private TextField add_d_q;
    @FXML
    private Button add_demande;
    @FXML
    private TableView<BoardTableLine> stock_table;
    @FXML
    private TableColumn<BoardTableLine, Float> stock_hauteur;
    @FXML
    private TableColumn<BoardTableLine, Float> stock_largeur;
    @FXML
    private TableColumn<BoardTableLine, Integer> stock_quantite;
    @FXML
    private TextField add_s_h;
    @FXML
    private TextField add_s_l;
    @FXML
    private TextField add_s_q;
    @FXML
    private Button add_stock;
    @FXML
    private Button start_opt_btn;
    @FXML
    private VBox draw_space;
    @FXML
    private Button btntbn;
    
    final Random rn = new Random();
    
	ObservableList<BoardTableLine> list_demande = FXCollections.observableArrayList();
	ObservableList<BoardTableLine> list_stock = FXCollections.observableArrayList();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Demande table configuration
		this.demande_hauteur.setCellValueFactory(new PropertyValueFactory<BoardTableLine, Float>("hauteur"));
		this.demande_largeur.setCellValueFactory(new PropertyValueFactory<BoardTableLine, Float>("largeur"));
		this.demande_quantite.setCellValueFactory(new PropertyValueFactory<BoardTableLine, Integer>("quantite"));
		this.demande_table.setItems(list_demande);
		this.demande_table.setEditable(true);
		demande_table.setOnKeyPressed( new EventHandler<KeyEvent>()
		{
			@Override
			public void handle( final KeyEvent keyEvent )
			{
				final BoardTableLine selectedItem = demande_table.getSelectionModel().getSelectedItem();
				
				if ( selectedItem != null )
				{
					if ( keyEvent.getCode().equals( KeyCode.DELETE ) )
					{
						//Delete or whatever you like:
						demande_table.getItems().remove(selectedItem);
					}
				}
			}
		} );
		
		this.demande_hauteur.setCellFactory(TextFieldTableCell.<BoardTableLine, Float>forTableColumn(new FloatStringConverter()));
		this.demande_hauteur.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<BoardTableLine,Float>>() {
			@Override
			public void handle(CellEditEvent<BoardTableLine, Float> event) {
				BoardTableLine line = event.getRowValue();
				line.setHauteur(event.getNewValue());
			}
		});
		
		this.demande_largeur.setCellFactory(TextFieldTableCell.<BoardTableLine, Float>forTableColumn(new FloatStringConverter()));
		this.demande_largeur.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<BoardTableLine,Float>>() {
			@Override
			public void handle(CellEditEvent<BoardTableLine, Float> event) {
				BoardTableLine line = event.getRowValue();
				line.setLargeur(event.getNewValue());
			}
		});
		
		this.demande_quantite.setCellFactory(TextFieldTableCell.<BoardTableLine, Integer>forTableColumn(new IntegerStringConverter()));
		this.demande_quantite.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<BoardTableLine,Integer>>() {
			@Override
			public void handle(CellEditEvent<BoardTableLine, Integer> event) {
				BoardTableLine line = event.getRowValue();
				line.setQuantite(event.getNewValue());
			}
		});
				
		
		// stock table configuration
		this.stock_hauteur.setCellValueFactory(new PropertyValueFactory<BoardTableLine, Float>("hauteur"));
		this.stock_largeur.setCellValueFactory(new PropertyValueFactory<BoardTableLine, Float>("largeur"));
		this.stock_quantite.setCellValueFactory(new PropertyValueFactory<BoardTableLine, Integer>("quantite"));
		this.stock_table.setItems(list_stock);
		this.stock_table.setEditable(true);
		
		stock_table.setOnKeyPressed( new EventHandler<KeyEvent>()
		{
			@Override
			public void handle( final KeyEvent keyEvent )
			{
				final BoardTableLine selectedItem = stock_table.getSelectionModel().getSelectedItem();

				if ( selectedItem != null )
				{
					if ( keyEvent.getCode().equals( KeyCode.DELETE ) )
					{
						//Delete or whatever you like:
						stock_table.getItems().remove(selectedItem);
					}
				}
			}
		} );
			
		this.stock_hauteur.setCellFactory(TextFieldTableCell.<BoardTableLine, Float>forTableColumn(new FloatStringConverter()));
		this.stock_hauteur.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<BoardTableLine,Float>>() {
			@Override
			public void handle(CellEditEvent<BoardTableLine, Float> event) {
				BoardTableLine line = event.getRowValue();
				line.setHauteur(event.getNewValue());
			}
		});
		
		this.stock_largeur.setCellFactory(TextFieldTableCell.<BoardTableLine, Float>forTableColumn(new FloatStringConverter()));
		this.stock_largeur.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<BoardTableLine,Float>>() {
			@Override
			public void handle(CellEditEvent<BoardTableLine, Float> event) {
				BoardTableLine line = event.getRowValue();
				line.setLargeur(event.getNewValue());
			}
		});
		
		this.stock_quantite.setCellFactory(TextFieldTableCell.<BoardTableLine, Integer>forTableColumn(new IntegerStringConverter()));
		this.stock_quantite.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<BoardTableLine,Integer>>() {
			@Override
			public void handle(CellEditEvent<BoardTableLine, Integer> event) {
				BoardTableLine line = event.getRowValue();
				line.setQuantite(event.getNewValue());
			}
		});
	}
	

	

	public void addDemande() {
		float hauteur = Float.parseFloat(this.add_d_h.getText()), largeur = Float.parseFloat(this.add_d_l.getText());
		int quantite = Integer.parseInt(this.add_d_q.getText());
		BoardTableLine board = new BoardTableLine(hauteur,largeur, quantite);
		this.demande_table.getItems().add(board);
//		list_demande.add(board);
		this.add_d_h.clear();
		this.add_d_l.clear();
		this.add_d_q.clear();
	}
	
	public void addStock() {
		float hauteur = Float.parseFloat(this.add_s_h.getText()), largeur = Float.parseFloat(this.add_s_l.getText());
		int quantite = Integer.parseInt(this.add_s_q.getText());
		BoardTableLine board = new BoardTableLine(hauteur, largeur, quantite);
		this.stock_table.getItems().add(board);
//		list_stock.add(board);
		this.add_s_h.clear();
		this.add_s_l.clear();
		this.add_s_q.clear();
	}
	
	public void onOptimzeClick() {
		ArrayList<Board> stock = new ArrayList<Board>();
		ArrayList<Board> demande = new ArrayList<Board>();
		
		Color color;
		list_demande = this.demande_table.getItems();
		list_stock = this.stock_table.getItems();
		if (list_demande.size()> 0 && list_stock.size()>0) {			
			// get stocks
			for (BoardTableLine line : list_stock) {
				stock.add(new Board(line.getHauteur(), line.getLargeur()));
			}

			// get demande
			int num = 1;
			for (BoardTableLine line : list_demande) {
				color = Color.rgb(rn.nextInt(130) + 70, rn.nextInt(130) + 70, rn.nextInt(130) + 70);
				for (int j = 0 ; j < line.getQuantite(); j++) {
					demande.add(new Board(line.getHauteur(), line.getLargeur(), num, color));
					num++;
				}
			}

			/*  start the optimisation  */
			Population p = new Population(stock, demande, 1000);
			List<Genome> plans = p.fit(500,10,20);

//			for (int i = 0; i < plans.size(); i++){
//				System.out.println( plans.get(i).getFitness() + plans.get(i).toString());
//			}
//			System.out.println("-----------------------------------");
			
			/*  optimisation completed  */
		
			AnchorPane draw_pane;
			/*  draw results  */
			draw_space.getChildren().clear();
			//  set pane height			
			double coeff = (double) 400 / list_stock.get(0).getLargeur();

			float x0 = 10f, y0 = 10f, h = (float) (stock.get(0).getHauteur()*coeff);
			
			Rectangle temp_rect;
			Label width_label, height_label;
			
			for (Genome genome : plans) {
				temp_rect = new Rectangle(x0,y0,
						stock.get(0).getLargeur() * coeff,
						stock.get(0).getHauteur() * coeff);
						temp_rect.setFill(stock.get(0).getColor());

				draw_pane = new AnchorPane();
				draw_pane.getChildren().add(temp_rect);

				for (int i = 0; i < genome.getLinesX().size() ; i++) {
					draw_pane.getChildren().add(new Line(x0 +genome.getLinesX().get(i)[0] * coeff,
							y0 + h-genome.getLinesY().get(i)[0] * coeff,
							x0 + genome.getLinesX().get(i)[1] * coeff,
							y0 + h-genome.getLinesY().get(i)[1] * coeff)
							);
				}

				for (Board rect : genome.getStock_left()) {
					temp_rect = new Rectangle(x0 + rect.getX() * coeff,
							y0 + h-rect.getY() * coeff-rect.getHauteur() * coeff,
							rect.getLargeur() * coeff,
							rect.getHauteur() * coeff
							);
					temp_rect.setFill(Color.WHITE);
					temp_rect.setStyle("-fx-stroke: black;");

					draw_pane.getChildren().add(temp_rect);
				}

				for (Board rect : genome.getDemande()) {
					if (rect.getCut() != 0) {
						temp_rect = new Rectangle(x0 + rect.getX() * coeff,
								y0 + h-rect.getY() * coeff-rect.getHauteur() * coeff,
								rect.getLargeur() * coeff,
								rect.getHauteur() * coeff
								);
						temp_rect.setFill(rect.getColor());
						temp_rect.setStyle("-fx-stroke: black;");
						
						width_label = new Label(String.valueOf(rect.getLargeur()));
						width_label.setLayoutX(x0 + rect.getX() * coeff + rect.getLargeur() * coeff / 2 - 10 );
						width_label.setLayoutY(y0 + h-rect.getY() * coeff-rect.getHauteur() * coeff );
						width_label.setTextFill(Color.GHOSTWHITE);

						height_label = new Label(String.valueOf(rect.getHauteur()));
						height_label.setLayoutX(x0 + rect.getX() * coeff - 5);
						height_label.setLayoutY(y0 + h-rect.getY() * coeff - rect.getHauteur() * coeff /2 -5);
						height_label.setRotate(-90.0);	height_label.setTextFill(Color.GHOSTWHITE);
						
						draw_pane.getChildren().addAll(temp_rect, width_label, height_label);
					}
				}
				draw_space.getChildren().add(draw_pane);
			}
		}
		
	}
	public void saveAsPng() {
		FileChooser dialogue = new FileChooser();
		dialogue.getExtensionFilters().add(new ExtensionFilter("images", "*.png"));
		WritableImage image = draw_space.snapshot(new SnapshotParameters(), null);
		
		File file = dialogue.showSaveDialog(draw_space.getScene().getWindow());
		if (file != null) {
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
			} catch (IOException e) {
				// TODO: handle exception
			}
		}
	}
	
	public void saveAsPDF() {
		
		FileChooser fc = new FileChooser();
		File file = fc.showSaveDialog(draw_space.getScene().getWindow());

		if (file != null) {

			String path = file.getAbsolutePath();

			file.delete();
			PDDocument doc = new PDDocument();
			PDPage page ;
			PDImageXObject pdimage;
			PDPageContentStream content;
			WritableImage image;

			for (Node node : draw_space.getChildren()) {
				image = node.snapshot(new SnapshotParameters(), null);
				file = new File("temp.png");
				if (file != null) {
					try {
						ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
					} catch (IOException e) {
						e.printStackTrace();
						System.exit(-1);
					}
				}

				page = new PDPage();
				try {
					pdimage = PDImageXObject.createFromFile("temp.png", doc);
					content = new PDPageContentStream(doc, page);
					content.drawImage(pdimage, 100, 100);
					content.close();
					doc.addPage(page);

					file.delete();
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-2);
				}

			}
			try {
				doc.save(path);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-3);
			}
		}		
	}
	
//	private void setupColumn(TableColumn<S, T> column) {
//		// sets the cell factory to use EditCell which will handle key presses
//		// and firing commit events
//		column.setCellFactory(
//				EditCell.<PersonTableData, Double>forTableColumn(
//						new MyDoubleStringConverter()));
//		// updates the salary field on the PersonTableData object to the
//		// committed value
//		salaryColumn.setOnEditCommit(event -> {
//			final Double value = event.getNewValue() != null
//					? event.getNewValue() : event.getOldValue();
//			((PersonTableData) event.getTableView().getItems()
//					.get(event.getTablePosition().getRow())).setSalary(value);
//			table.refresh();
//		});
//	}
//	
//	private void setTableEditable(TableView<BoardTableLine> table) {
//		table.setEditable(true);
//		// allows the individual cells to be selected
//		table.getSelectionModel().cellSelectionEnabledProperty().set(true);
//		// when character or numbers pressed it will start edit in editable
//		// fields
//		table.setOnKeyPressed(event -> {
//			if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
//				editFocusedCell();
//			} else if (event.getCode() == KeyCode.RIGHT
//					|| event.getCode() == KeyCode.TAB) {
//				table.getSelectionModel().selectNext();
//				event.consume();
//			} else if (event.getCode() == KeyCode.LEFT) {
//				// work around due to
//				// TableView.getSelectionModel().selectPrevious() due to a bug
//				// stopping it from working on
//				// the first column in the last row of the table
//				selectPrevious();
//				event.consume();
//			}
//		});
//	}
}
