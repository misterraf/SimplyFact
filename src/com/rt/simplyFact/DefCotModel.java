package com.rt.simplyFact;

import java.util.ArrayList;
import java.util.List;

public class DefCotModel {
	private List<Cotation> listCot=new ArrayList<Cotation>();
	private Cotation defCotMatin=new Cotation();
	private Cotation defCotMidi=new Cotation();
	private Cotation defCotSoir=new Cotation();
	private boolean isDefault=false;
	private boolean isCancelled=false;
	

	public DefCotModel(){
		this.isDefault=false;
		this.isCancelled=false;
	}
	public DefCotModel(List<Cotation> listCot,Cotation defCotMatin,Cotation defCotMidi,Cotation defCotSoir){
		this.isDefault=false;
		this.isCancelled=false;
		this.defCotMatin=defCotMatin;
		this.defCotMidi=defCotMidi;
		this.defCotSoir=defCotSoir;
		this.listCot=listCot;
	}
	public void setDefault(){
		this.isDefault=true;
	}
	public void setCancelled(){
		this.isCancelled=true;
	}
	public boolean getCancelled(){
		return this.isCancelled;
	}
	public boolean getDefault(){
		return this.isDefault;
	}
	public void setDefCotMatin(Cotation cot){
		this.defCotMatin=cot;
	}
	public void setDefCotSoir(Cotation cot){
		this.defCotSoir=cot;
	}
	public void setDefCotMidi(Cotation cot){
		this.defCotMidi=cot;
	}
	public Cotation getDefCotMatin(){
		return 	this.defCotMatin;	
	}
	public Cotation getDefCotMidi(){
		return 	this.defCotMidi;
	}
	public Cotation getDefCotSoir(){
		return 	this.defCotSoir;	
	}
	public List<Cotation> getListCot(){
		return this.listCot;
	}
	public void addCotation(Cotation cot){
		if (!this.listCot.contains(cot)){
			this.listCot.add(cot);
		}
	}
	public String toString(){
		String str="Cotations:\n";
		for(Cotation value:listCot) str=str+value+"\n";
		return str;
	}
}
