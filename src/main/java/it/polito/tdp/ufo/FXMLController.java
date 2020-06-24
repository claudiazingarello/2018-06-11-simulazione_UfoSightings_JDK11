package it.polito.tdp.ufo;

import java.net.URL;
import java.time.Year;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.AnnoAvvistamenti;
import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<AnnoAvvistamenti> boxAnno;
    @FXML
    private Button btnAnalizza;

    @FXML
    private Button btnSequenza;
    
    @FXML
    private ComboBox<String> boxStato;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleAnalizza(ActionEvent event) {
    	txtResult.clear();
    	if(model.getStati() == null) {
    		txtResult.appendText("ERRORE: il grafo non ha vertici");
    		return;
    	}
    	
    	String stato = boxStato.getValue();
    	
    	if (stato == null) {
    		txtResult.appendText("ERRORE: devi selezionare uno stato!");
    		return;
    	}
    	
    	List<String> statiPrecedenti = model.getStatiPrecedenti(stato);
    	txtResult.appendText("Stati PRECEDENTI a "+stato+":\n");
    	txtResult.appendText(statiPrecedenti.toString()+"\n");
    	
    	List<String> statiSuccessivi = model.getStatiSuccessivi(stato);
    	txtResult.appendText("\nStati SUCCESSIVI a "+stato+":\n");
    	txtResult.appendText(statiSuccessivi.toString()+"\n");
    	
    	List<String> statiRaggiungibili = model.getStatiRaggiungibili(stato);
    	txtResult.appendText("\nStati RAGGIUNGIBILI da "+stato+":\n");
    	txtResult.appendText(statiRaggiungibili.toString()+"\n");
    	}

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	txtResult.clear();
    	
    	AnnoAvvistamenti aa = boxAnno.getValue();
    	
    	if (aa == null) {
    		txtResult.appendText("ERRORE: devi selezionare un anno!");
    		return;
    	}
    	
    	Year anno = aa.getAnno();
    	
    	model.creaGrafo(anno);
    	btnAnalizza.setDisable(false);  
    	
    	txtResult.appendText("Grafo creato!");
    	boxStato.getItems().addAll(model.getStati());
    	btnSequenza.setDisable(false);
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	
    	String stato = boxStato.getValue();
    	if(stato == null) {
    		txtResult.appendText("ERRORE: devi selezionare uno stato!");
    		return;
    	}
    	
    	List<String> sequenza = model.getPercorsoMassimo(stato);
    	txtResult.appendText("\nStato di partenza: "+stato+"\n");
    	txtResult.appendText("Percorso massimo: "+ sequenza+"\n");
    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSequenza != null : "fx:id=\"btnSequenza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
		boxAnno.getItems().addAll(model.listAnnoAvvistamenti());
		btnAnalizza.setDisable(true);
		btnSequenza.setDisable(true);
		
	}
}
