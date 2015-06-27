package com.rt.simplyFact;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;


public class Patient implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2127747358010185111L;
	private String nom,prenom,adresse,tel;
	private boolean selected;
	private boolean visible;
	private List<Acte> facturation=new ArrayList<Acte>();
	private List<Ordonnance> ordos=new ArrayList<Ordonnance>();
	private List<String> muts=new ArrayList<String>();
	private List<String> medecins=new ArrayList<String>();
	private List<Cotation> defCot=new ArrayList<Cotation>();
	private Cotation defCotMatin=new Cotation();
	private Cotation defCotMidi=new Cotation();
	private Cotation defCotSoir=new Cotation();
	private MrMme civilite;
	
	public Patient(){
		this.nom="";
		this.tel="";
		this.prenom="";
		this.adresse="";
		this.selected=false;
		this.visible=true;
		this.civilite=null;
	}
	public Patient(String nom){
		this.nom="";
		this.prenom="";
		this.adresse="";
		this.selected=false;
		this.visible=true;
		this.tel="";
		this.civilite=null;
		
		String[] nameParts=nom.trim().split(" ");
		int startSearch =0;
		
		if ((nameParts[0].equals("Mme"))||(nameParts[0].equals("Mr"))){
			if (nameParts[0].equals("Mme")) {
				civilite=MrMme.Mme;
			} else {
				civilite=MrMme.Mr;
			}
			startSearch=1;
		}
		this.nom=nameParts[startSearch].trim().toUpperCase();
		for (int i=startSearch+1;i<nameParts.length;i++){
			if(!nameParts[i].trim().equals("")){
				this.nom=this.nom+" "+nameParts[i].trim().toUpperCase();
			}
		}
			
	}
	public Patient(String nom,String prenom){
		this.nom="";
		this.prenom=formatPrenom(prenom);
		adresse="";
		selected=false;
		visible=true;
		tel="";
		civilite=null;
		String nomStr="";
		String[] nameParts=nom.trim().split(" ");
		int startSearch =0;

		if ((nameParts[0].equals("Mme"))||(nameParts[0].equals("Mr"))){
			if (nameParts[0].equals("Mme")) {
				civilite=MrMme.Mme;
			} else {
				civilite=MrMme.Mr;
			}
			startSearch=1;
		}
		nomStr=nameParts[startSearch].trim().toUpperCase();
		for (int i=startSearch+1;i<nameParts.length;i++){
			if(!nameParts[i].trim().equals("")){
				nomStr=nomStr+" "+nameParts[i].trim().toUpperCase();
			}
		}
		this.nom=nomStr;
	}
	public void addCotation(Cotation cot){
		if (!defCot.contains(cot)){
			defCot.add(cot);
		}
	}
	public void addActe(Acte act){
		facturation.add(act);
		this.addCotation(act.cotation);
		this.sortDefCot();
	}
	public void addActe(Date date,int order){
		this.removeActe(date);
		if (!this.defCotMatin.toString().equals("")) {
			DateJour dj=new DateJour(date,Jour.matin);
			Acte act=new Acte(defCotMatin,dj);
			act.setOrder(order);
			facturation.add(act);
			this.addCotation(defCotMatin);
			//System.out.println("ajout cotation : "+defCotMatin);
		}
		if (!this.defCotMidi.toString().equals("")) {
			DateJour dj=new DateJour(date,Jour.midi);
			Acte act=new Acte(defCotMidi,dj);
			act.setOrder(order);
			facturation.add(act);
			this.addCotation(defCotMidi);
			//System.out.println("ajout cotation : "+defCotMidi);
		}
		if (!this.defCotSoir.toString().equals("")) {
			DateJour dj=new DateJour(date,Jour.soir);
			Acte act=new Acte(defCotSoir,dj);
			act.setOrder(order);
			facturation.add(act);
			this.addCotation(defCotSoir);
			//System.out.println("ajout cotation : "+defCotSoir);
		}
			
		
		this.sortDefCot();
	}
	public void clearAllActes(){
		facturation.clear();
	}
	public void clearUnusedCot(){
		List<Cotation> usedCot=new ArrayList<Cotation>();
		for(int i=0;i<facturation.size();i++){
			Acte act=facturation.get(i);
			if (!usedCot.contains(act.getCotation())){
				usedCot.add(act.getCotation());
			}
		}
		for(int i=0;i<this.getCotList().size();i++){
			Cotation cot=this.getCotList().get(i);
			if (!usedCot.contains(cot)){
				System.out.println(this.toString()+" : removing unused cotation : "+cot);
				this.getCotList().remove(i--);
			}
		}
	}
	public void addActe(Date date,String soignant,int order){
		
		String otherSoignant=this.hasOtherActe(date, soignant);
		int confirm=1;
		if (!otherSoignant.equals("")){
		} 
		if ((confirm==0)||(confirm==1)){
			if (confirm==0) {
				this.removeActe(date);
			} else {
				this.removeActe(date,soignant);
			}
			
			if (!this.defCotMatin.toString().equals("")) {
				DateJour dj=new DateJour(date,Jour.matin);
				Acte act=new Acte(defCotMatin,dj,soignant);
				act.setOrder(order);
				facturation.add(act);
				this.addCotation(defCotMatin);
				//System.out.println("ajout cotation : "+defCotMatin);
			}
			if (!this.defCotMidi.toString().equals("")) {
				DateJour dj=new DateJour(date,Jour.midi);
				Acte act=new Acte(defCotMidi,dj,soignant);
				act.setOrder(order);
				facturation.add(act);
				this.addCotation(defCotMidi);
				//System.out.println("ajout cotation : "+defCotMidi);
			}
			if (!this.defCotSoir.toString().equals("")) {
				DateJour dj=new DateJour(date,Jour.soir);
				Acte act=new Acte(defCotSoir,dj,soignant);
				act.setOrder(order);
				facturation.add(act);
				this.addCotation(defCotSoir);
				//System.out.println("ajout cotation : "+defCotSoir);
			}


			this.sortDefCot();
		}
	}
	public void setAdresse(String adresse){
		this.adresse=adresse;
	}
	public void setCivilite(MrMme civilite){
		this.civilite=civilite;
	}
	
	public void setCivilite(String civilite){
		this.civilite=null;
		if (civilite.toLowerCase().equals("mr"))
			this.civilite=MrMme.Mr;
		if (civilite.toLowerCase().equals("mme"))
			this.civilite=MrMme.Mme;
		
	}
	
	public void setNom(String nom){
		this.nom=nom.toUpperCase().trim();
	}
	public void setPrenom(String prenom){
		this.prenom=formatPrenom(prenom).trim();
	}
	
	public String formatPrenom(String prenom){
		String rtnStr="";
		
		if (prenom.contains(" ")){
			String[] str=prenom.split(" ");
			rtnStr=capitalize(str[0]);
			for (int i=1;i<str.length;i++){
				if (str[i]!="") {
					rtnStr=rtnStr+"-"+capitalize(str[i]);
				}
			}
		} else if (prenom.contains("-")){
			String[] str=prenom.split("-");
			rtnStr=capitalize(str[0]);
			for (int i=1;i<str.length;i++){
				if (str[i]!="") {
					rtnStr=rtnStr+"-"+capitalize(str[i]);
				}
			}
		} else {
			rtnStr=capitalize(prenom);
		}
		return rtnStr;
	}
	public static String capitalize(String nom){
		String str="";
		if (!nom.equals("")){
			str=nom.substring(0,1).toUpperCase();
			if (nom.length()>1){
				str=str+nom.substring(1,nom.length()).toLowerCase();
			}
		}
		return str;
		
		
	}
	public void setTel(String tel){
		this.tel=tel;
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
	public void setCotList(List<Cotation> defCot){
		this.defCot=defCot;
	}
	public void setVisible(boolean visible){
		this.visible=visible;
	}
	public void select(){
		selected=true;
	}
	public void deselect(){
		selected=false;
	}
	public boolean isSelected(){
		return selected;
	}
	public void addOrdo(Ordonnance ord){
		ordos.add(ord);
	}
	public void delOrdo(Ordonnance ord){
		ordos.remove(ord);
	}
	public void archiveOrdo(Ordonnance ord){
		Ordonnance ordo;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for(int i=0;i<ordos.size();i++){
			
			if (ordos.get(i).equals(ord)){
				ordo=ordos.get(i);
				ordos.get(i).archive(true);
				String fileName=ordo.getFileName();
				File newFile=new File(fileName);
				
				String newFileName=newFile.getName();
				String[] newFileStr=newFileName.split("_");
				String subDir=newFileStr[2]+"_"+newFileStr[3];
				File dir=new File("Archives Ordos");
				if(!dir.exists()){
					dir.mkdir();
				}
				
				dir=new File("Archives Ordos\\"+subDir);
				if(!dir.exists()){
					dir.mkdir();
				}
				newFileName="Archives Ordos\\"+subDir+"\\"+newFileName;
				File fileDest=new File(newFileName);
				JOptionPane.showMessageDialog(null,"Ordo de :"+this.nom+"\nEst arriv�e a expiration ("+sdf.format(ordo.getDateFin())+"). Archivage du fichier dans :\n"+newFileName);
				newFile.renameTo(fileDest);
				ordos.get(i).setFileName(newFileName);
				break;
			}
		}
	}
	public void addMedecin(String medecin){
		medecins.add(medecin);
	}
	public void setMedecins(List<String> medecins){
		this.medecins=medecins;
	}
	public void setOrdos(List<Ordonnance> ordos){
		this.ordos=ordos;
	}
	public void addMut(String mut){
		muts.add(mut);
	}
	
	public void removeActe(Acte act){
		this.facturation.remove(act);
	}	
	public void removeActe(Date date,String soignant){
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		for (int i=0;i<this.facturation.size();i++){
			Acte act=this.facturation.get(i);
			if ((sdf.format(act.dateJour.getDate()).equals(sdf.format(date)))&&(act.getSoignant().equals(soignant))){
				this.facturation.remove(i--);
				
			}
		}
		
		
	}	
	public void removeActe(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		for (int i=0;i<this.facturation.size();i++){
			Acte act=this.facturation.get(i);
			if (sdf.format(act.dateJour.getDate()).equals(sdf.format(date))){
				this.facturation.remove(i--);
				
			}
		}
		
		
	}	
	public void removeCotation(Cotation cot){
		this.defCot.remove(cot);
	}	
	public void removeOrdo(String ord){
		this.ordos.remove(ord);
	}	
	public void removeMut(String mut){
		this.muts.remove(mut);
	}	
	public MrMme getCivilite(){
		return this.civilite;
	}	
	public String getNom(){
		return this.nom;
	}	
	public String getPrenom(){
		return this.prenom;
	}	
	public String getTel(){
		return this.tel;
	}
	public String getAdresse(){
		return this.adresse;
	}
	public Boolean getVisible(){
		return this.visible;
	}
	public List<Acte> getActe(){
		return this.facturation;
	}
	public List<Acte> getActe(String mois,String annee){
		List<Acte> factDate=new ArrayList<Acte>();
		for(int i=0;i<this.facturation.size();i++){
			Acte act=this.facturation.get(i);
			DateJour dja=act.getDateJour();
			
			if ((dja.getDateMois().equals(mois))&&(dja.getDateAnnee().equals(annee))){
				factDate.add(act);
			}
		}
		return factDate;
		
	}	
	public List<Acte> getActe(String mois,String annee,String soig){
		List<Acte> factDate=new ArrayList<Acte>();
		for(int i=0;i<this.facturation.size();i++){
			Acte act=this.facturation.get(i);
			DateJour dja=act.getDateJour();
			
			if ((act.getSoignant().equals(soig))&&(dja.getDateMois().equals(mois))&&(dja.getDateAnnee().equals(annee))){
				factDate.add(act);
			}
		}
		return factDate;
		
	}	
	public List<Acte> getActe(Date crtDate,String soig){
		SimpleDateFormat sdfM = new SimpleDateFormat("MM");
		SimpleDateFormat sdfA = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdfJ = new SimpleDateFormat("dd");
		String mois=sdfM.format(crtDate);
		String annee=sdfA.format(crtDate);
		String jour=sdfJ.format(crtDate);
		List<Acte> factDate=new ArrayList<Acte>();
		for(int i=0;i<this.facturation.size();i++){
			Acte act=this.facturation.get(i);
			DateJour dja=act.getDateJour();

			if ((act.getSoignant().equals(soig))&&(dja.getDateMois().equals(mois))&&(dja.getDateAnnee().equals(annee))&&(dja.getDateJour().equals(jour))){
				factDate.add(act);			}
		}
		return factDate;

	}	
	public List<Acte> getActe(Date crtDate){
		SimpleDateFormat sdfM = new SimpleDateFormat("MM");
		SimpleDateFormat sdfA = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdfJ = new SimpleDateFormat("dd");
		String mois=sdfM.format(crtDate);
		String annee=sdfA.format(crtDate);
		String jour=sdfJ.format(crtDate);
		List<Acte> factDate=new ArrayList<Acte>();
		for(int i=0;i<this.facturation.size();i++){
			Acte act=this.facturation.get(i);
			DateJour dja=act.getDateJour();

			if ((dja.getDateMois().equals(mois))&&(dja.getDateAnnee().equals(annee))&&(dja.getDateJour().equals(jour))){
				factDate.add(act);			}
		}
		return factDate;

	}	
	public int getNbActesJour(Date date,String soignant){
		int nbActes=0;
		SimpleDateFormat sdfM=new SimpleDateFormat("MM");
		SimpleDateFormat sdfA=new SimpleDateFormat("yyyy");
		SimpleDateFormat sdfJ=new SimpleDateFormat("dd");
		String jour=sdfJ.format(date);
		String mois=sdfM.format(date);
		String annee=sdfA.format(date);
		List<Acte> factDate=new ArrayList<Acte>();
		for(int i=0;i<this.facturation.size();i++){
			Acte act=this.facturation.get(i);
			if (act.getSoignant().equals(soignant)){
				DateJour dja=act.getDateJour();

				if ((dja.getDateMois().equals(mois))&&(dja.getDateAnnee().equals(annee))&&(dja.getDateJour().equals(jour))){
					nbActes++;
				}
			}
		}
		return nbActes;
		
	}	
	public int getNbActesMois(Date date,String soignant){
		int nbActes=0;
		SimpleDateFormat sdfM=new SimpleDateFormat("MM");
		SimpleDateFormat sdfA=new SimpleDateFormat("yyyy");
		SimpleDateFormat sdfJ=new SimpleDateFormat("dd");
		String jour=sdfJ.format(date);
		String mois=sdfM.format(date);
		String annee=sdfA.format(date);
		List<Acte> factDate=new ArrayList<Acte>();
		for(int i=0;i<this.facturation.size();i++){
			Acte act=this.facturation.get(i);
			if (act.getSoignant().equals(soignant)){
				DateJour dja=act.getDateJour();

				if ((dja.getDateMois().equals(mois))&&(dja.getDateAnnee().equals(annee))){
					nbActes++;
				}
			}
		}
		return nbActes;
		
	}	
	public double getFactJour(Date date,String soignant){
		double fact=0;
		SimpleDateFormat sdfM=new SimpleDateFormat("MM");
		SimpleDateFormat sdfA=new SimpleDateFormat("yyyy");
		SimpleDateFormat sdfJ=new SimpleDateFormat("dd");
		String jour=sdfJ.format(date);
		String mois=sdfM.format(date);
		String annee=sdfA.format(date);
		List<Acte> factDate=new ArrayList<Acte>();
		for(int i=0;i<this.facturation.size();i++){
			Acte act=this.facturation.get(i);
			if (act.getSoignant().equals(soignant)){
				DateJour dja=act.getDateJour();

				if ((dja.getDateMois().equals(mois))&&(dja.getDateAnnee().equals(annee))&&(dja.getDateJour().equals(jour))){
					fact=fact+act.getCotation().getCotation();
				}
			}
		}
		return fact;
		
	}	
	public double getFactMois(Date date, String soignant){
		double fact=0;
		SimpleDateFormat sdfM=new SimpleDateFormat("MM");
		SimpleDateFormat sdfA=new SimpleDateFormat("yyyy");
		SimpleDateFormat sdfJ=new SimpleDateFormat("dd");
		String jour=sdfJ.format(date);
		String mois=sdfM.format(date);
		String annee=sdfA.format(date);
		List<Acte> factDate=new ArrayList<Acte>();
		for(int i=0;i<this.facturation.size();i++){
			Acte act=this.facturation.get(i);
			if (act.getSoignant().equals(soignant)){
				DateJour dja=act.getDateJour();

				if ((dja.getDateMois().equals(mois))&&(dja.getDateAnnee().equals(annee))){
					fact=fact+act.getCotation().getCotation();
				}
			}
		}
		return fact;
		
	}	
	public boolean hasActe(){
		if(this.facturation.size()>0){
			return true;
		} else {return false;}
		
	}	
	public boolean hasActe(Date d){
		int nbActes=0;
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		for(int i=0;i<this.facturation.size();i++){
			if (sdf.format(this.facturation.get(i).dateJour.getDate()).equals(sdf.format(d))){
				nbActes++;
				break;
			}
		}
		if (nbActes>0){return true;} else {return false;}
		
	}	
	public boolean hasActe(Date d,String soignant){
		int nbActes=0;
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		for(int i=0;i<this.facturation.size();i++){
			if ((sdf.format(this.facturation.get(i).dateJour.getDate()).equals(sdf.format(d)))&&(this.facturation.get(i).getSoignant().equals(soignant))){
				nbActes++;
				break;
			}
		}
		if (nbActes>0){return true;} else {return false;}		
	}	
	public String hasOtherActe(Date d,String soignant){
		String otherSoignant="";
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		for(int i=0;i<this.facturation.size();i++){
			if ((sdf.format(this.facturation.get(i).dateJour.getDate()).equals(sdf.format(d)))&&(!this.facturation.get(i).getSoignant().equals(soignant))){
				otherSoignant=this.facturation.get(i).getSoignant();
				break;
			}
		}
		return otherSoignant;
		
	}	
	public boolean hasActe(Acte act){
		int nbActes=0;
		for(int i=0;i<this.facturation.size();i++){
			if (this.facturation.get(i).equals(act)){
				nbActes++;
			}
		}
		if (nbActes>0){return true;} else {return false;}
		
	}	
	public boolean hasActe(String mois, String annee){
		int nbActes=0;
		for(int i=0;i<this.facturation.size();i++){
			if ((this.facturation.get(i).dateJour.getDateMois().equals(mois))&&(this.facturation.get(i).dateJour.getDateAnnee().equals(annee))){
				nbActes++;
				
			}
		}
		if (nbActes>0){return true;} else {return false;}
		
	}
	public boolean hasActe(String mois, String annee,String soignant){
		int nbActes=0;
		for(int i=0;i<this.facturation.size();i++){
			if ((this.facturation.get(i).dateJour.getDateMois().equals(mois))&&(this.facturation.get(i).dateJour.getDateAnnee().equals(annee))&&(this.facturation.get(i).getSoignant().equals(soignant))){
				nbActes++;
				
			}
		}
		if (nbActes>0){return true;} else {return false;}
		
	}
	
//	public String getDefCotation(){
//		String str="";
//		if (defCot.size()>0){
//			str= this.defCot.get(0).toString();
//		} 
//		return str;
//		
//	}
	public boolean hasDefCot(){
		boolean result=true;
		if ((this.getDefCotMatin().toString().equals(""))&&(this.getDefCotMidi().toString().equals(""))&&(this.getDefCotSoir().toString().equals(""))){
			result=false;
		}
		return 	result;	
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
	public String getDefIK(){
		String str="0";
		if (defCot.size()>0){
			for (int i=0;i<defCot.size();i++) {
				if (this.defCot.get(i).getIk()>0) {
					str= String.valueOf(this.defCot.get(i).getIk());
					break;

				}
			}
					} 
		return str;
		
	}
	public void sortDefCot(){
		List<Cotation> cotList=new ArrayList<Cotation>();
		List<Integer> cotListNb=new ArrayList<Integer>();
		for(int i=0;i<this.facturation.size();i++){
			Acte act=this.facturation.get(i);
			Cotation cot=act.cotation;
	 		if (!cotList.contains(cot)){
	 			cotList.add(cot);
	 			cotListNb.add(1);
	 		} else {
	 			int idx=cotList.indexOf(cot);
	 			int nbCot=cotListNb.get(idx);
				nbCot++;
	 			cotListNb.set(idx,nbCot);
	 		}
			
		}
		/*
		for(int i=0;i<cotList.size();i++){
			System.out.println("cot : "+cotList.get(i).toString()+" nb occurrences :"+cotListNb.get(i));
		}
		System.out.println("Default cot :");
		for(int i=0;i<defCot.size();i++){
			System.out.println("cot : "+defCot.get(i));
		}
		*/
		int biggest=-1;
		int biggestIdx=-1;
		for(int j=0;j<cotList.size();j++){
			if(cotListNb.get(j)>biggest){
				biggest=cotListNb.get(j);
				biggestIdx=j;
			}
			
		}
		if (biggestIdx>=0){
			int cotInDefCotIdx=defCot.indexOf(cotList.get(biggestIdx));
			if(cotInDefCotIdx>0){
				
				defCot.remove(cotInDefCotIdx);
				defCot.add(0,cotList.get(biggestIdx));
			}
		}
		/*
		System.out.println("Default cot list:");
		for(int i=0;i<defCot.size();i++){
			System.out.println("cot : "+defCot.get(i));
		}
		*/
	}
	
	public List<Cotation> getCotList(){
		return this.defCot;
	}
	public List<Ordonnance> getOrdos(){
		return this.ordos;
	}
	public Ordonnance getOrdo(String date,String motif,String medecin){
		Ordonnance ordoTmp=new Ordonnance(date,motif,medecin);
		Ordonnance ordo=new Ordonnance();
		for (int i=0;i<ordos.size();i++ ){
			if(ordos.get(i).equals(ordoTmp)){
				return ordos.get(i);
			}
		}
		ordo.setMotif("failed to find ordo");
		return ordo;
	}
	public List<String> getMedecins(){
		return this.medecins;
	}
	public CotationsMois getCotListMois(String mois, String annee){
		CotationsMois cotMois=new CotationsMois();
		
		List<Cotation> cotList=new ArrayList<Cotation>();
		List<Integer> cotListNb=new ArrayList<Integer>();
		for(int i=0;i<this.facturation.size();i++){
			Acte act=this.facturation.get(i);
			Cotation cot=act.cotation;
			//String day=act.dateJour.getDateJour();
			String imois=act.dateJour.getDateMois();
			String iannee=act.dateJour.getDateAnnee();
			if (imois.equals(mois)&&iannee.equals(annee)){
				//System.out.println("cot du jour : "+day+" :"+cot);
	 			if (!cotList.contains(cot)){
	 				cotList.add(cot);
	 				cotListNb.add(1);
	 			} else {
	 				int idx=cotList.indexOf(cot);
	 				int nbCot=cotListNb.get(idx);
					nbCot++;
	 				cotListNb.set(idx,nbCot);
	 			}
			}
		}
		/*
		for(int i=0;i<cotList.size();i++){
			System.out.println("cot : "+cotList.get(i).toString()+" nb occurrences :"+cotListNb.get(i));
		}
		*/
		for(int i=0;i<cotList.size();i++){
			int biggest=cotListNb.get(0);
			int biggestIdx=0;
			for(int j=0;j<cotList.size();j++){
				if(cotListNb.get(j)>biggest){
					biggest=cotListNb.get(j);
					biggestIdx=j;
				}
				
			}
			cotMois.addCotList(cotList.get(biggestIdx));
			cotMois.addCotListNb(cotListNb.get(biggestIdx));
			cotListNb.set(biggestIdx,0);
		}
		for(int i=0;i<cotMois.cotList.size();i++){
			//System.out.println("puis : "+cotMois.cotList.get(i).toString()+" "+cotMois.cotList.get(i).getComment());
			Cotation crtCot=cotMois.cotList.get(i);
			String cotStr=crtCot.toString();
			String cotComment=crtCot.getComment();
			List<String> dateListMatin=new ArrayList<String>();
			List<String> dateListMidi=new ArrayList<String>();
			List<String> dateListSoir=new ArrayList<String>();
			
			for(int j=0;j<this.facturation.size();j++){
	 			Acte act=this.facturation.get(j);
	 			Cotation cot=act.cotation;
				DateJour dj=act.dateJour;
				Jour jour=dj.getJour();
	 			String imois=dj.getDateMois();
	 			String iannee=dj.getDateAnnee();
				int dayNb;
				try {
					dayNb=Integer.parseInt(dj.getDateJour());
				} catch (NumberFormatException e) {
					dayNb=0;
				}
	 			if (cot.equals(crtCot)&&imois.equals(mois)&&iannee.equals(annee)){
	 				if (jour.equals(Jour.matin)){
						dateListMatin.add(dj.getDateJour());
						if (dayNb>0){cotMois.setCotDuJourMatin(i,dayNb);}
					}
	 				if (jour.equals(Jour.midi)){
						dateListMidi.add(dj.getDateJour());
						if (dayNb>0){cotMois.setCotDuJourMidi(i,dayNb);}
					}
	 				if (jour.equals(Jour.soir)){
						dateListSoir.add(dj.getDateJour());
						if (dayNb>0){cotMois.setCotDuJourSoir(i,dayNb);}
					}
	 			}
			}
			if (dateListMatin.size()>0){
				if (dateListMatin.size()>1){
	 				cotStr=cotStr+" les ";
				} else {
	 				cotStr=cotStr+" le ";
				}
	 			for(int k=0;k<dateListMatin.size();k++){
	 				cotStr=cotStr+dateListMatin.get(k);
					if (k+1<dateListMatin.size()){cotStr=cotStr+",";}
	 			}
				cotStr=cotStr+" matin";
			}
			if (dateListMidi.size()>0){
				if (dateListMatin.size()>0){cotStr=cotStr+" et";}
				if (dateListMidi.size()>1){
	 				cotStr=cotStr+" les ";
				} else {
	 				cotStr=cotStr+" le ";
				}
	 			for(int k=0;k<dateListMidi.size();k++){
	 				cotStr=cotStr+dateListMidi.get(k);
					if (k+1<dateListMidi.size()){cotStr=cotStr+",";}
	 			}
				cotStr=cotStr+" midi";
			}
			if (dateListSoir.size()>0){
				if (dateListMatin.size()>0||dateListMidi.size()>0){cotStr=cotStr+" et";}
				if (dateListSoir.size()>1){
	 				cotStr=cotStr+" les ";
				} else {
	 				cotStr=cotStr+" le ";
				}
	 			for(int k=0;k<dateListSoir.size();k++){
	 				cotStr=cotStr+dateListSoir.get(k);
					if (k+1<dateListSoir.size()){cotStr=cotStr+",";}
	 			}
				cotStr=cotStr+" soir";
			}
			if (i==0){
	 			cotMois.addCotStr(cotMois.cotList.get(0).toString());
			} else {
	 			cotMois.addCotStr(cotStr+" "+cotComment);
			}
		}
		/*
		if (cotListMois.size()==0){
			if (this.defCot.size()>0) {
				cotListMois.add(this.getDefCotation());
			}
		}
		*/
		return cotMois;
	}
	
	
	public CotationsMois getCotListMois(String mois, String annee, String sgt){
		CotationsMois cotMois=new CotationsMois();
		
		List<Cotation> cotList=new ArrayList<Cotation>();
		List<Integer> cotListNb=new ArrayList<Integer>();
		for(int i=0;i<this.facturation.size();i++){
			Acte act=this.facturation.get(i);
			Cotation cot=act.cotation;
			//String day=act.dateJour.getDateJour();
			String imois=act.dateJour.getDateMois();
			String iannee=act.dateJour.getDateAnnee();
			String isgt=act.getSoignant();
			if (imois.equals(mois)&&iannee.equals(annee)&&isgt.equals(sgt)){
				//System.out.println("cot du jour : "+day+" :"+cot);
	 			if (!cotList.contains(cot)){
	 				cotList.add(cot);
	 				cotListNb.add(1);
	 			} else {
	 				int idx=cotList.indexOf(cot);
	 				int nbCot=cotListNb.get(idx);
					nbCot++;
	 				cotListNb.set(idx,nbCot);
	 			}
			}
		}
		/*
		for(int i=0;i<cotList.size();i++){
			System.out.println("cot : "+cotList.get(i).toString()+" nb occurrences :"+cotListNb.get(i));
		}
		*/
		for(int i=0;i<cotList.size();i++){
			int biggest=cotListNb.get(0);
			int biggestIdx=0;
			for(int j=0;j<cotList.size();j++){
				if(cotListNb.get(j)>biggest){
					biggest=cotListNb.get(j);
					biggestIdx=j;
				}
				
			}
			cotMois.addCotList(cotList.get(biggestIdx));
			cotMois.addCotListNb(cotListNb.get(biggestIdx));
			cotListNb.set(biggestIdx,0);
		}
		for(int i=0;i<cotMois.cotList.size();i++){
			//System.out.println("puis : "+cotMois.cotList.get(i).toString()+" "+cotMois.cotList.get(i).getComment());
			Cotation crtCot=cotMois.cotList.get(i);
			String cotStr=crtCot.toString();
			String cotComment=crtCot.getComment();
			List<String> dateListMatin=new ArrayList<String>();
			List<String> dateListMidi=new ArrayList<String>();
			List<String> dateListSoir=new ArrayList<String>();
			
			for(int j=0;j<this.facturation.size();j++){
	 			Acte act=this.facturation.get(j);
	 			Cotation cot=act.cotation;
				DateJour dj=act.dateJour;
				Jour jour=dj.getJour();
	 			String imois=dj.getDateMois();
	 			String iannee=dj.getDateAnnee();
				int dayNb;
				try {
					dayNb=Integer.parseInt(dj.getDateJour());
				} catch (NumberFormatException e) {
					dayNb=0;
				}
	 			if (cot.equals(crtCot)&&imois.equals(mois)&&iannee.equals(annee)){
	 				if (jour.equals(Jour.matin)){
						dateListMatin.add(dj.getDateJour());
						if (dayNb>0){cotMois.setCotDuJourMatin(i,dayNb);}
					}
	 				if (jour.equals(Jour.midi)){
						dateListMidi.add(dj.getDateJour());
						if (dayNb>0){cotMois.setCotDuJourMidi(i,dayNb);}
					}
	 				if (jour.equals(Jour.soir)){
						dateListSoir.add(dj.getDateJour());
						if (dayNb>0){cotMois.setCotDuJourSoir(i,dayNb);}
					}
	 			}
			}
			if (dateListMatin.size()>0){
				if (dateListMatin.size()>1){
	 				cotStr=cotStr+" les ";
				} else {
	 				cotStr=cotStr+" le ";
				}
	 			for(int k=0;k<dateListMatin.size();k++){
	 				cotStr=cotStr+dateListMatin.get(k);
					if (k+1<dateListMatin.size()){cotStr=cotStr+",";}
	 			}
				cotStr=cotStr+" matin";
			}
			if (dateListMidi.size()>0){
				if (dateListMatin.size()>0){cotStr=cotStr+" et";}
				if (dateListMidi.size()>1){
	 				cotStr=cotStr+" les ";
				} else {
	 				cotStr=cotStr+" le ";
				}
	 			for(int k=0;k<dateListMidi.size();k++){
	 				cotStr=cotStr+dateListMidi.get(k);
					if (k+1<dateListMidi.size()){cotStr=cotStr+",";}
	 			}
				cotStr=cotStr+" midi";
			}
			if (dateListSoir.size()>0){
				if (dateListMatin.size()>0||dateListMidi.size()>0){cotStr=cotStr+" et";}
				if (dateListSoir.size()>1){
	 				cotStr=cotStr+" les ";
				} else {
	 				cotStr=cotStr+" le ";
				}
	 			for(int k=0;k<dateListSoir.size();k++){
	 				cotStr=cotStr+dateListSoir.get(k);
					if (k+1<dateListSoir.size()){cotStr=cotStr+",";}
	 			}
				cotStr=cotStr+" soir";
			}
			if (i==0){
	 			cotMois.addCotStr(cotMois.cotList.get(0).toString());
			} else {
	 			cotMois.addCotStr(cotStr+" "+cotComment);
			}
		}
		/*
		if (cotListMois.size()==0){
			if (this.defCot.size()>0) {
				cotListMois.add(this.getDefCotation());
			}
		}
		*/
		return cotMois;
	}
	
	
	
	
	public String toString(){
		if (this.nom.equals("")){return "pas de patient";}
		String str;
		if (this.civilite==null) {
			str=this.nom;
		} else {
			str=civilite+" "+this.nom;
		}
		if (!this.prenom.equals("")){str=str+" "+this.prenom;}
//		
//		str=str+"\n";
//		if (!this.adresse.equals("")){
//			str=str+"Habite a :"+this.adresse+"\n";
//		}
//		if (this.defCot.size()>0){
//			str=str+"Cotations :\n";
//	 		for(int i = 0; i < this.defCot.size(); i++) {
//	 			Cotation cot =this.defCot.get(i);
//	 
//	 			str=str+cot+"\n";
//	 		}
//		}
//		if (this.facturation.size()>0){
//			str=str+"Facturation :\n";
//	 		for(int i = 0; i < this.facturation.size(); i++) {
//	 			Acte act =this.facturation.get(i);
//	 
//	 			str=str+act+"\n";
//	 		}
//		}
//		if (this.selected) {
//			str=str+"est selectionné\n";
//		} else {
//			str=str+"n'est pas selectionné\n";
//		}
		return str;
	}
	
	
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((civilite == null) ? 0 : civilite.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
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
		Patient other = (Patient) obj;
		if (civilite != other.civilite)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.trim().equals(other.nom.trim()))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.trim().equals(other.prenom.trim()))
			return false;
		return true;
	}
}
