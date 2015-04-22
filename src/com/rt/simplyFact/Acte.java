package com.rt.simplyFact;
import java.io.Serializable;
public class Acte implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8867512702442748475L;
	protected DateJour dateJour;
	protected Cotation cotation;
	protected String soignant;
	protected int order;



	public Acte(){
		cotation=new Cotation();
		dateJour=new DateJour();
		soignant="";
		order=0;
	}
	public Acte(Cotation cot){
		cotation=cot;
		dateJour=new DateJour();
		soignant="";
		order=0;
	}
	public Acte(DateJour dj){
		cotation=new Cotation();
		dateJour=dj;
		soignant="";
		order=0;
	}
	public Acte(Cotation cot,DateJour dj){
		cotation=cot;
		dateJour=dj;
		soignant="";
		order=0;
	}
	public Acte(Cotation cot,DateJour dj,String sgt){
		cotation=cot;
		dateJour=dj;
		soignant=sgt;
		order=0;
	}
	public void setDateJour(DateJour j){
		dateJour=j;
	}
	public void setOrder(int order){
		this.order=order;
	}
	public DateJour getDateJour(){
		return dateJour;
	}
	public void setCotation(Cotation cot){
		cotation=cot;
	}
	public Cotation getCotation(){
		return cotation;
	}
	public int getOrder(){
		return this.order;
	}
	public String toString (){
		String str=cotation+" le "+dateJour;
		if (!soignant.equals("")) {
			str=str+" par "+soignant;
		}
		return str;
	}
	public void setSoignant(String sgt){
		soignant=sgt;
	}
	public String getSoignant(){
		return soignant;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cotation == null) ? 0 : cotation.hashCode());
		result = prime * result
				+ ((dateJour == null) ? 0 : dateJour.hashCode());
		result = prime * result
				+ ((soignant == null) ? 0 : soignant.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Acte other = (Acte) obj;
		if (cotation == null) {
			if (other.cotation != null)
				return false;
		} else if (!cotation.equals(other.cotation))
			return false;
		if (dateJour == null) {
			if (other.dateJour != null)
				return false;
		} else if (!dateJour.equals(other.dateJour))
			return false;
		if (soignant == null) {
			if (other.soignant != null)
				return false;
		} else if (!soignant.equals(other.soignant))
			return false;
		return true;
	}
}
