package it.polito.tdp.ufo.model;

import java.time.Year;

public class AnnoAvvistamenti {
	private Year anno;
	private int numeroAvvistamenti;
	
	public AnnoAvvistamenti(Year anno, int numeroAvvistamenti) {
		super();
		this.anno = anno;
		this.numeroAvvistamenti = numeroAvvistamenti;
	}
	
	public Year getAnno() {
		return anno;
	}


	public void setAnno(Year anno) {
		this.anno = anno;
	}

	public int getNumeroAvvistamenti() {
		return numeroAvvistamenti;
	}
	public void setNumeroAvvistamenti(int numeroAvvistamenti) {
		this.numeroAvvistamenti = numeroAvvistamenti;
	}
	@Override
	public String toString() {
		return anno + " (" + numeroAvvistamenti + ")";
	}
	
	
}
