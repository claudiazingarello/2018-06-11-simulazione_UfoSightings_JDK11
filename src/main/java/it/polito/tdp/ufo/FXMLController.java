package it.polito.tdp.ufo;

import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.AnnoAvvistamenti;
import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private ComboBox<?> boxStato;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleAnalizza(ActionEvent event) {

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
    	
    }

    @FXML
    void handleSequenza(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
		boxAnno.getItems().addAll(model.listAnnoAvvistamenti());
	}
}
