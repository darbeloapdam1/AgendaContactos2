package agenda.interfaz;

import java.io.File;
import java.io.IOException;

import agenda.io.AgendaIO;
import agenda.modelo.AgendaContactos;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
/**
 * 
 * @author Diego Arbeloa - Carla Barberia
 *
 */
public class GuiAgenda extends Application {
	private AgendaContactos agenda;
	private MenuItem itemImportar;
	private MenuItem itemExportarPersonales;
	private MenuItem itemSalir;

	private MenuItem itemBuscar;
	private MenuItem itemFelicitar;

	private MenuItem itemAbout;

	private TextArea areaTexto;

	private RadioButton rbtListarTodo;
	private RadioButton rbtListarSoloNumero;
	private Button btnListar;

	private Button btnPersonalesEnLetra;
	private Button btnPersonalesOrdenadosPorFecha;

	private TextField txtBuscar;

	private Button btnClear;
	private Button btnSalir;

	@Override
	public void start(Stage stage) {
		agenda = new AgendaContactos(); // el modelo

		BorderPane root = crearGui();

		Scene scene = new Scene(root, 1100, 700);
		stage.setScene(scene);
		stage.setTitle("Agenda de contactos");
		scene.getStylesheets().add(getClass().getResource("/application.css")
		                    .toExternalForm());
		stage.show();

	}

	private BorderPane crearGui() {
		BorderPane panel = new BorderPane();
		panel.setTop(crearBarraMenu());
		panel.setCenter(crearPanelPrincipal());
		return panel;
	}

	private BorderPane crearPanelPrincipal() {
		BorderPane panel = new BorderPane();
		panel.setPadding(new Insets(10));
		panel.setTop(crearPanelLetras());

		areaTexto = new TextArea();
		areaTexto.getStyleClass().add("textarea");
		panel.setCenter(areaTexto);

		panel.setLeft(crearPanelBotones());
		return panel;
	}

	private VBox crearPanelBotones() {
		VBox panel = new VBox();
		panel.setPadding(new Insets(10));
		panel.setSpacing(10);
		
		txtBuscar = new TextField("");
		txtBuscar.setPromptText("Buscar");
		txtBuscar.setMinHeight(40);
		VBox.setMargin(txtBuscar, new Insets(0, 0, 40, 0));
		
		rbtListarTodo = new RadioButton("Listar toda la agenda");
		rbtListarTodo.setSelected(true);
		
		rbtListarSoloNumero = new RadioButton("Listar nº contactos");
		
		btnListar = new Button("Listar");
		btnListar.setPrefWidth(250);
		btnListar.setOnAction(event -> listar());
		btnListar.setStyle("-fx-background-color: rgb(222,193,180);\r\n"
				+ "	-fx-border-color:  rgb(128,128,128);\r\n"
				+ "    -fx-border-radius:  4px;\r\n"
				+ "    -fx-font-family: \"Pt Sans\";\r\n"
				+ "    -fx-font-size: 14px;\r\n"
				+ "    -fx-text-fill:  blue;");
		VBox.setMargin(btnListar, new Insets(0, 0, 40, 0));
		
		btnPersonalesEnLetra = new Button("Contactos personales en letra");
		btnPersonalesEnLetra.setPrefWidth(250);
		btnPersonalesEnLetra.setOnAction(event -> contactosPersonalesEnLetra());
		btnPersonalesEnLetra.setStyle("-fx-background-color: rgb(222,193,180);\r\n"
				+ "	-fx-border-color:  rgb(128,128,128);\r\n"
				+ "    -fx-border-radius:  4px;\r\n"
				+ "    -fx-font-family: \"Pt Sans\";\r\n"
				+ "    -fx-font-size: 14px;\r\n"
				+ "    -fx-text-fill:  blue;");
		
		btnPersonalesOrdenadosPorFecha = new Button("Contactos Personales\n ordenados por fecha");
		btnPersonalesOrdenadosPorFecha.setPrefWidth(250);
		btnPersonalesOrdenadosPorFecha.setOnAction(event -> personalesOrdenadosPorFecha());
		btnPersonalesOrdenadosPorFecha.setStyle("-fx-background-color: rgb(222,193,180);\r\n"
				+ "	-fx-border-color:  rgb(128,128,128);\r\n"
				+ "    -fx-border-radius:  4px;\r\n"
				+ "    -fx-font-family: \"Pt Sans\";\r\n"
				+ "    -fx-font-size: 14px;\r\n"
				+ "    -fx-text-fill:  blue;");
		VBox.setVgrow(btnPersonalesOrdenadosPorFecha, Priority.ALWAYS);
		
		btnClear = new Button("Clear");
		btnClear.setPrefWidth(250);
		btnClear.setOnAction(event -> clear());
		btnClear.setStyle("-fx-background-color: rgb(222,193,180);\r\n"
				+ "	-fx-border-color:  rgb(128,128,128);\r\n"
				+ "    -fx-border-radius:  4px;\r\n"
				+ "    -fx-font-family: \"Pt Sans\";\r\n"
				+ "    -fx-font-size: 14px;\r\n"
				+ "    -fx-text-fill:  blue;");
		VBox.setMargin(btnClear, new Insets(40, 0, 0, 0));
		
		btnSalir = new Button("Salir");
		btnSalir.setPrefWidth(250);
		btnSalir.setOnAction(event -> salir());
		btnSalir.setStyle("-fx-background-color: rgb(222,193,180);\r\n"
				+ "	-fx-border-color:  rgb(128,128,128);\r\n"
				+ "    -fx-border-radius:  4px;\r\n"
				+ "    -fx-font-family: \"Pt Sans\";\r\n"
				+ "    -fx-font-size: 14px;\r\n"
				+ "    -fx-text-fill:  blue;");
		
		panel.getChildren().add(txtBuscar);
		panel.getChildren().addAll(rbtListarTodo, rbtListarSoloNumero);
		panel.getChildren().addAll(btnListar, btnPersonalesEnLetra, btnPersonalesOrdenadosPorFecha, btnClear, btnSalir);
		
		return panel;
	}

	private GridPane crearPanelLetras() {
		GridPane panel = new GridPane();
		panel.setGridLinesVisible(false);
		
		panel.setPadding(new Insets(10, 5, 10, 5));
		panel.setVgap(4);
		panel.setHgap(4);
		panel.setAlignment(Pos.CENTER);
		
		for(int i = 0; i < 2; i++) {
			RowConstraints row = new RowConstraints();
			row.setPercentHeight(50);
			panel.getRowConstraints().add(row);
		}
		
		for(int i = 0; i < 14; i++) {
			ColumnConstraints col = new ColumnConstraints();
			col.setPercentWidth(7.14);
			panel.getColumnConstraints().add(col);
		}
		
		String[] valores = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Ñ", "O", 
				"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		int contador = 0;
		
		for(int f = 0; f < 2; f++) {
			for(int c = 0; c < 14; c++) {
				if(!(contador == valores.length)) {
					panel.add(crearBotones(valores[contador]), c, f);
					contador++;
				}
			}
			
		}
		return panel;
	}
	
	private Button crearBotones(String texto) {
		Button boton = new Button(texto);
		boton.setStyle(".botonletra");
		boton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		GridPane.setHgrow(boton, Priority.ALWAYS);
		GridPane.setVgrow(boton, Priority.ALWAYS);
		return boton;
	}

	private MenuBar crearBarraMenu() {
		MenuBar barra = new MenuBar();
		
		Menu archivo = new Menu("Archivo");
		itemImportar = new MenuItem("_Importar agenda");
		itemImportar.setAccelerator(KeyCombination.keyCombination("Ctrl+I"));
		itemImportar.setOnAction(event -> importarAgenda());
		itemExportarPersonales = new MenuItem("_Exportar Personales");
		itemExportarPersonales.setDisable(true);
		itemExportarPersonales.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
		itemExportarPersonales.setOnAction(event -> exportarPersonales());
		itemSalir = new MenuItem("_Salir");
		itemSalir.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		itemSalir.setOnAction(event -> salir());
		archivo.getItems().addAll(itemImportar, itemExportarPersonales, new SeparatorMenuItem(), itemSalir);
		
		
		Menu operacion = new Menu("Operaciones");
		itemBuscar = new MenuItem("_Buscar");
		itemBuscar.setAccelerator(KeyCombination.keyCombination("Ctrl+B"));
		itemBuscar.setOnAction(event -> buscar());
		itemFelicitar = new MenuItem("_Felicitar");
		itemFelicitar.setAccelerator(KeyCombination.keyCombination("Ctrl+F"));
		itemFelicitar.setOnAction(event -> felicitar());
		operacion.getItems().addAll(itemBuscar, itemFelicitar);
		
		Menu help = new Menu("Help");
		itemAbout = new MenuItem("_About");
		itemAbout.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));
		help.getItems().add(itemAbout);
		
		barra.getMenus().addAll(archivo, operacion,help);
		return barra;
	}

	private void importarAgenda() {
		FileChooser selector = new FileChooser();
		selector.setTitle("Abrir fichero csv");
		selector.setInitialDirectory(new File("."));
		selector.getExtensionFilters().addAll(new ExtensionFilter("csv","*.csv"));
		File f = selector.showOpenDialog(null);
		int errores = AgendaIO.importar(agenda, f.getName());
		clear();
		areaTexto.setText("Lineas erroneas: " + errores);
		itemImportar.setDisable(true);
		itemExportarPersonales.setDisable(false);
	}

	private void exportarPersonales() {
		FileChooser selector = new FileChooser();
		selector.setTitle("Exportar contactos personales por relación");
		selector.setInitialDirectory(new File(":"));
		selector.getExtensionFilters().add(new ExtensionFilter("txt", ".txt"));
		try {
			AgendaIO.exportarPersonales(agenda, selector.showSaveDialog(null).getName());
			clear();
			areaTexto.setText("Exportados datos personales");
		} catch (NullPointerException | IOException e) {
			areaTexto.setText("Error en el fichero");
		}
		
	}

	/**
	 *  
	 */
	private void listar() {
		clear();
		// a completar

	}

	private void personalesOrdenadosPorFecha() {
		clear();
		// a completar

	}

	private void contactosPersonalesEnLetra() {
		clear();
		// a completar

	}

	private void contactosEnLetra(char letra) {
		clear();
		// a completar
	}

	private void felicitar() {
		clear();
		// a completar

	}

	private void buscar() {
		clear();
		// a completar

		cogerFoco();

	}

	private void about() {
		// a completar

	}

	private void clear() {
		areaTexto.setText("");
	}

	private void salir() {
		Platform.exit();
	}

	private void cogerFoco() {
		txtBuscar.requestFocus();
		txtBuscar.selectAll();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
