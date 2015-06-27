package com.rt.simplyFact;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ordonnance  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2938496921283017065L;
	protected Date datePresc;
	protected Date dateDebut;
	protected Date dateFin;
	protected String motif;
	protected String medecin;
	protected boolean sent;
	protected String fileName;
	protected int dureeN;
	protected String dureeStr;
	protected boolean archived;
	
	public Ordonnance(){
		this.datePresc=new Date();
		this.dateDebut=new Date();
		this.dateFin=new Date();
		this.motif="";
		this.dureeN=0;
		this.dureeStr="jours";
		this.medecin="";
		this.sent=false;
		this.archived=false;
		this.fileName="";
		
	}
	public Ordonnance(String date,String motif,String medecin){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try{
			this.datePresc=sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.dateDebut=new Date();
		this.dateFin=new Date();
		this.motif=motif;
		this.dureeN=0;
		this.dureeStr="jours";
		this.medecin=medecin;
		this.sent=false;
		this.archived=false;
		this.fileName="";
		
	}
	public Date getDatePresc(){
		return this.datePresc;
	}
	public boolean isArchived(){
		return this.archived;
	}
	public Date getDateDebut(){
		return this.dateDebut;
	}
	public Date getDateFin(){
		return this.dateFin;
	}
	public boolean isSent(){
		return this.sent;
	}
	public String getMedecin(){
		return this.medecin;
	}
	public String getMotif(){
		return this.motif;
	}
	public String getFileName(){
		return this.fileName;
	}
	public int getDureeN(){
		return this.dureeN;
	}
	public String getDureeStr(){
		return this.dureeStr;
	}
	public void setDatePresc(Date datePresc){
		this.datePresc=datePresc;
	}
	public void setDateDebut(Date dateDebut){
		this.dateDebut=dateDebut;
	}
	public void setDateFin(Date dateFin){
		this.dateFin=dateFin;
	}
	public void setMotif(String motif){
		this.motif=motif;
	}
	public void setMedecin(String medecin){
		this.medecin=medecin;
	}
	public void setFileName(String fileName){
		this.fileName=fileName;
	}
	public void send(boolean sent){
		this.sent=sent;
	}
	public void archive(boolean archived){
		this.archived=archived;
	}
	public void setDureeN(int dureeN){
		this.dureeN=dureeN;
	}
	public void setDureeStr(String dureeStr){
		this.dureeStr=dureeStr;
	}
	@Override
	public String toString() {
		return "Ordonnance [datePresc=" + datePresc + ", dateDebut="
				+ dateDebut + ", dateFin=" + dateFin + ", motif=" + motif
				+ ", medecin=" + medecin + ", sent=" + sent + ", fileName="
				+ fileName + ", dureeN=" + dureeN + ", dureeStr=" + dureeStr
				+ "]"+archived+" (is archived)";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((datePresc == null) ? 0 : datePresc.hashCode());
		result = prime * result + ((medecin == null) ? 0 : medecin.hashCode());
		result = prime * result + ((motif == null) ? 0 : motif.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ordonnance other = (Ordonnance) obj;
		if (datePresc == null) {
			if (other.datePresc != null)
				return false;
		} else if (!datePresc.equals(other.datePresc))
			return false;
		if (medecin == null) {
			if (other.medecin != null)
				return false;
		} else if (!medecin.equals(other.medecin))
			return false;
		if (motif == null) {
			if (other.motif != null)
				return false;
		} else if (!motif.equals(other.motif))
			return false;
		return true;
	}
		
	
	
}
