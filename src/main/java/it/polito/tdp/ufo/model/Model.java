package it.polito.tdp.ufo.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {

	SightingsDAO dao;
	List<AnnoAvvistamenti> listAnnoAvvistamenti;
	List<String> stati;
	List<String> statiPrecedenti;
	List<String> statiSuccessivi;
	
	Graph<String, DefaultEdge> grafo;

	public Model() {
		dao = new SightingsDAO();
	}

	public List<AnnoAvvistamenti> listAnnoAvvistamenti(){
		listAnnoAvvistamenti = dao.listAnnoAvvistamenti();
		return listAnnoAvvistamenti;
	}

	public void creaGrafo(Year anno) {
		grafo = new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

		//Aggiungi i vertici
		stati = dao.getStatiForYear(anno);
		Graphs.addAllVertices(grafo, stati);


		//Aggiungi gli archi
		//Ordine temporale degli avvistamenti
		for(Adiacenza a : this.dao.getAdiacenze(anno)) {
			if(!a.getS1().equals(a.getS2()))
				grafo.addEdge(a.getS1(), a.getS2());
		}

		System.out.println("GRAFO CREATO!\n# VERTICI: "+ grafo.vertexSet().size()+"\n#ARCHI: " +grafo.edgeSet().size());
	}

	public List<String> getStati(){
		return stati;
	}

	public List<String> getStatiPrecedenti(String stato) {
		statiPrecedenti = Graphs.predecessorListOf(grafo, stato);
		return statiPrecedenti;
	}

	public List<String> getStatiSuccessivi(String stato) {
		statiSuccessivi = Graphs.successorListOf(grafo, stato);
		return statiSuccessivi;
	}
}
