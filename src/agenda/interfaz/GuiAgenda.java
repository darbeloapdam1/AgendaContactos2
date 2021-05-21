package agenda.interfaz;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import agenda.io.AgendaIO;
import agenda.modelo.AgendaContactos;
import agenda.modelo.Contacto;
import agenda.modelo.Personal;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
		txtBuscar.setOnAction(event -> buscar());	

		VBox.setMargin(txtBuscar, new Insets(0, 0, 40, 0));
		
		ToggleGroup grupo = new ToggleGroup();
		
		rbtListarTodo = new RadioButton("Listar toda la agenda");
		rbtListarTodo.setSelected(true);
		rbtListarTodo.setToggleGroup(grupo);
		VBox.setVgrow(rbtListarTodo, Priority.ALWAYS);
		
		rbtListarSoloNumero = new RadioButton("Listar nº contactos");
		rbtListarSoloNumero.setToggleGroup(grupo);
		VBox.setVgrow(rbtListarSoloNumero, Priority.ALWAYS);
		
		btnListar = new Button("Listar");
		btnListar.setPrefWidth(250);
		btnListar.setOnAction(event -> listar());
		btnListar.getStyleClass().add("botones");
		VBox.setMargin(btnListar, new Insets(0, 0, 40, 0));
		
		btnPersonalesEnLetra = new Button("Contactos personales en letra");
		btnPersonalesEnLetra.setPrefWidth(250);
		btnPersonalesEnLetra.setOnAction(event -> contactosPersonalesEnLetra());
		btnPersonalesEnLetra.getStyleClass().add("botones");
		VBox.setVgrow(btnPersonalesEnLetra, Priority.ALWAYS);
		
		btnPersonalesOrdenadosPorFecha = new Button("Contactos Personales\n ordenados por fecha");
		btnPersonalesOrdenadosPorFecha.setPrefWidth(250);
		btnPersonalesOrdenadosPorFecha.setOnAction(event -> personalesOrdenadosPorFecha());
		btnPersonalesOrdenadosPorFecha.getStyleClass().add("botones");
		VBox.setVgrow(btnPersonalesOrdenadosPorFecha, Priority.ALWAYS);
		
		btnClear = new Button("Clear");
		btnClear.setPrefWidth(250);
		btnClear.setOnAction(event -> clear());
		btnClear.getStyleClass().add("botones");
		VBox.setMargin(btnClear, new Insets(40, 0, 0, 0));
		
		btnSalir = new Button("Salir ");
		btnSalir.setPrefWidth(250);
		btnSalir.setOnAction(event -> salir());
		btnSalir.getStyleClass().add("botones");
		VBox.setVgrow(btnSalir, Priority.ALWAYS);
		
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
	
	/**
	 * 
	 * @param texto el texto del boton
	 * @return el boton ya creado
	 */
	
	private Button crearBotones(String texto) {
		Button boton = new Button(texto);
		boton.getStyleClass().add("botonletra");
		boton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		boton.setOnAction(event -> contactosEnLetra(boton.getText().charAt(0)));
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
		itemAbout.setOnAction(event -> about());
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
	
	/**
	 * exporta en un fichero txt todos los contactos personales
	 */

	private void exportarPersonales() {
		FileChooser selector = new FileChooser();
		selector.setTitle("Exportar contactos personales por relación");
		selector.setInitialDirectory(new File("."));
		selector.getExtensionFilters().add(new ExtensionFilter("txt", ".txt"));
		try {
			File f = selector.showSaveDialog(null);
			AgendaIO.exportarPersonales(agenda, f.getName());
			clear();
			areaTexto.setText("Exportados datos personales");
		} catch (NullPointerException | IOException e) {
			areaTexto.setText("Error en el fichero");
		}
		
	}

	/**
	 *  lista la agenda dependiendo de que radiobutton este seleccionado
	 */
	private void listar() {
		clear();
		if(agenda.totalContactos() > 0) {
			if(rbtListarTodo.isSelected()) {
				areaTexto.setText(agenda.toString());
			}else {
				areaTexto.setText("Total contactos en la agenda: " + agenda.totalContactos());
			}
		}else {
			areaTexto.setText("Inserte antes la agenda");
		}

	}

	/**
	 * muestra los contactos ordenados por fecha
	 */
	
	private void personalesOrdenadosPorFecha() {
		clear();
		if(agenda.totalContactos() > 0) {
			List<String> opciones = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Ñ", "O", 
				"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
			ChoiceDialog<String> dialogo = new ChoiceDialog<>("A",opciones);
			dialogo.setTitle("Selector de letra");
			dialogo.setHeaderText(null);
			dialogo.setContentText("Elija letra:");
			Optional<String> resul = dialogo.showAndWait();
			if(resul.isPresent()) {
				String opcion = resul.get();
				try {
					List<Personal> contactos = agenda.personalesOrdenadosPorFechaNacimiento(opcion.charAt(0));
					if(contactos.size() > 0) {
					String resulTexto = "Contactos personales ordenados por fecha de nacimiento\n\n" + opcion + "\n";
					for(Personal per : contactos) {
						resulTexto += per.toString();
					}
					areaTexto.setText(resulTexto);
					}
				}catch(NullPointerException e) {
					areaTexto.setText("No hay contactos personales");
				}
			}			
		}else {
			areaTexto.setText("Inserte antes la agenda");
		}
	}

	/**
	 * muestra los contactos personales en una letra seleccionada 
	 */
	
	private void contactosPersonalesEnLetra() {
		clear();
		if(agenda.totalContactos() > 0) {
			List<String> opciones = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Ñ", "O", 
					"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
				ChoiceDialog<String> dialogo = new ChoiceDialog<>("A",opciones);
				dialogo.setTitle("Selector de letra");
				dialogo.setContentText("Elija letra:");
				Optional<String> resul = dialogo.showAndWait();
				if(resul.isPresent()) {
					List<Personal> contactos = agenda.personalesEnLetra(resul.get().charAt(0));
					String resulTexto = "Contactos personales en la letra " + resul.get() + "(" + contactos.size() + " contactos/a)\n";
					for (Personal con : contactos) {
						resulTexto += con.toString();
					}
					areaTexto.setText(resulTexto);
				}else {
					areaTexto.setText("No hay contactos");
				}
		}else {
			areaTexto.setText("Inserte antes la agenda");
		}

	}
	
	/**
	 * muestra los contactos en una letra
	 * @param letra la letra por la que empiezan los contactos
	 */

	private void contactosEnLetra(char letra) {
		clear();
		String textoResul = "";
		if(agenda.totalContactos() > 0) { 
			try {
				Set<Contacto> contactos = agenda.contactosEnLetra(letra);
				textoResul = "Contactos en la letra " + letra;
				for (Contacto con : contactos) {
					textoResul += con.toString();
				}
			}catch(NullPointerException e) {
				textoResul += "\nNo hay contactos";
			}
			areaTexto.setText(textoResul);
		}else {
			areaTexto.setText("Inserte antes la agenda");
		}
	}

	/**
	 * muestra los contactos que hay que felicitar
	 */
	
	private void felicitar() {
		clear();
		LocalDate ld = LocalDate.now();
		List<Personal> felicitar = agenda.felicitar();
		if(agenda.totalContactos() > 0) {
			String str = ("Hoy es " + ld);
			for (Contacto con : felicitar) {
				str += con.toString();
			}
			areaTexto.setText(str);
		}else {
			areaTexto.setText("Inserte antes la agenda");
		}

	}

	/**
	 * busca los contactos que tengan un determinado texto
	 */
	
	private void buscar() {
		clear();
		if(agenda.totalContactos() > 0) {
			String texto = txtBuscar.getText();
			List<Contacto> contactos = agenda.buscarContactos(texto);
			if(contactos.size() > 0) {
				String textoResul = "Contactos en la agenda que contienen '" + texto + "'\n";
				for(Contacto con : contactos) {
					textoResul += con.toString();
				}
				areaTexto.setText(textoResul);
			}else {
				areaTexto.setText("No hay contactos en la agendo con ese texto");
			}
		}else {
			areaTexto.setText("Inserte antes la agenda");
		}
		cogerFoco();

	}

	/**
	 * saca informacion del la agenda
	 */
	
	private void about() {
		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
		alerta.setTitle("About agenda de contactos");
		alerta.setHeaderText(null);
		alerta.setContentText("Mi agenda de contactos");
		DialogPane dialogPane = alerta.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		alerta.showAndWait();
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
