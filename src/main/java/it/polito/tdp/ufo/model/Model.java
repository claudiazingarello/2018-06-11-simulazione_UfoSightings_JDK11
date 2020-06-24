package it.polito.tdp.ufo.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {

	private SightingsDAO dao;
	private List<AnnoAvvistamenti> listAnnoAvvistamenti;
	private List<String> stati;
	private List<String> statiPrecedenti;
	private List<String> statiSuccessivi;

	//per la ricorsione
	List<String> statiConsecutivi;
	List<String> percorsoBest;

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

	public List<String> getStatiRaggiungibili(String stato) {
		List<String> raggiungibili = new ArrayList<>();

		GraphIterator<String, DefaultEdge> bfv = new BreadthFirstIterator<>(grafo, stato);
		//se metto null parte da un vertice a caso

		//		bfv.next(); se non voglio salvare il primo
		while(bfv.hasNext()) {
			raggiungibili.add( bfv.next() ) ;
		}

		//rimuovo lo stato da cui partiamo
		raggiungibili.remove(0);
		return raggiungibili ;
	}

	// SOLUZIONE PUNTO 2

	public List<String> getPercorsoMassimo(String stato) {
		this.percorsoBest = new ArrayList<String>();

		List<String> parziale = new ArrayList<String>();
		parziale.add(stato);

		cerca(parziale);


		return percorsoBest;
	}

	private void cerca(List<String> parziale) {

		//Genera nuove soluzioni
		String ultimo = parziale.get(parziale.size() -1);

		List<String> vicini = Graphs.successorListOf(grafo, ultimo);

		for(String prossimo : vicini) {
			if(!parziale.contains(prossimo)) {
				
					parziale.add(prossimo);
					cerca(parziale);

					//oppure parziale.size()-1
					parziale.remove(parziale.size()-1); //rimuovo quello in posizione size()-1
				
			}
		}

		//valuta caso terminale
			if(parziale.size() > percorsoBest.size()) {
				percorsoBest = new ArrayList<String>(parziale); //clona il best
		}
	}

}

