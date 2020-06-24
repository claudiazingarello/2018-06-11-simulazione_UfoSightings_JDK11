package it.polito.tdp.ufo.model;

import java.time.Year;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {

	SightingsDAO dao;
	List<AnnoAvvistamenti> listAnnoAvvistamenti;

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
		Graphs.addAllVertices(grafo, dao.getStatiForYear(anno));

		//Aggiungi gli archi
		//Ordine temporale degli avvistamenti
		for(Adiacenza a : this.dao.getAdiacenze(anno)) {
			if(!a.getS1().equals(a.getS2()))
				grafo.addEdge(a.getS1(), a.getS2());
		}
		
		System.out.println("GRAFO CREATO!\n# VERTICI: "+ grafo.vertexSet().size()+"\n#ARCHI: " +grafo.edgeSet().size());
	}
}
