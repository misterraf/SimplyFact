package com.rt.simplyFact;

public class ActeJour {
	protected DateJour dateJour;
	protected String cotation;
	protected String soignant;
	protected String nomPatient;
	protected String prenom;
	protected String duplicate;
	protected int order;
	
	public ActeJour(DateJour dateJour,String soignant,String nomPatient,String prenom,String cotation,int order){
		this.cotation=cotation;
		this.dateJour=dateJour;
		this.soignant=soignant;
		this.nomPatient=nomPatient;
		this.prenom=prenom;
		this.duplicate="";
		this.order=order;
	}
	public String getNomPatient(){
		return this.nomPatient;
	}
	public String getPrenom(){
		return this.prenom;
	}
	public int getOrder(){
		return this.order;
	}
	public void setOrder(int order){
		this.order=order;
	}
	public void setDuplicate(String duplicate){
		this.duplicate=duplicate;
	}
	public String getDuplicate(){
		return this.duplicate;
	}
	public String getSoignant(){
		return this.soignant;
	}
	public DateJour getDateJour(){
		return this.dateJour;
	}
	public String getCotation(){
		return this.cotation;
	}
	@Override
	public String toString() {
		return soignant+";"+dateJour.getJour()+";" +nomPatient+";"+prenom+";"+cotation;
				
	}
	
}
