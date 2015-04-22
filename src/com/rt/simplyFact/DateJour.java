package com.rt.simplyFact;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.Serializable;

public class DateJour implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 566135531132569665L;



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((jour == null) ? 0 : jour.hashCode());
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
		DateJour other = (DateJour) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (jour != other.jour)
			return false;
		return true;
	}
	private Jour jour;
	private Date date;

	
	
	public DateJour(){
		date=new Date();
		jour=Jour.matin;
	}
	public DateJour(Date date,Jour jour){
		this.date=date;
		this.jour=jour;
	}
	public DateJour(Date date){
		this.date=date;
		jour=Jour.matin;
	}
	public DateJour(String d){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date=sdf.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		jour=Jour.matin;
	}
	public void setJour(Jour j){
		jour=j;
	}
	public void setDate(String d){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date=sdf.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
/*
	public int getDateMois(){
		SimpleDateFormat sdf = new SimpleDateFormat("M");
		String str=sdf.format(date);
		int mois;
		try 
		{ 
			mois=Integer.parseInt(str);
		}
		catch (NumberFormatException nfe) {
			mois=0;
		}
				
		return mois;
	}
*/
	public String getDateAnnee(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String str=sdf.format(date);
		return str;
	}
	public String getDateMois(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		String str=sdf.format(date);
		return str;
	}
	public String getDateJour(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String str=sdf.format(date);
		return str;
	}
	public String getDateStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);
	}
	public Jour getJour(){
		return this.jour;
	}
	public Date getDate(){
		return this.date;
	}
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date)+" "+jour;
	}
}
