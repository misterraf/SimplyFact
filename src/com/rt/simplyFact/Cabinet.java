package com.rt.simplyFact;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Cabinet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7516082903037512815L;
	protected List<Patient> patients=new ArrayList<Patient>();
	protected List<String> soignants=new ArrayList<String>();
	protected Double version;
	protected List<UidSyncPath> syncPathList=new ArrayList<UidSyncPath>();
	protected boolean modified;
	
	public Cabinet(){
		this.version=1.0;
		
	}
	
	public void addSyncPath(String syncPath){
		UidSyncPath sync=new UidSyncPath();
		String UID=sync.getUID();
		Boolean found=false;
//		System.out.println("Trying to set syncpath of UID :"+UID+" to "+syncPath);
		if (this.syncPathList!=null){
			for (int i=0;i<syncPathList.size();i++){
//				System.out.println("syncpath of UID :"+UID+" ("+i+"):"+syncPathList.get(i).getUID());
				if (syncPathList.get(i).getUID().equals(UID)) {
					found=true;
					syncPathList.get(i).setSyncPath(syncPath);
//					System.out.println("Replacing syncpath of UID :"+UID+" by "+syncPath);
					break;

				}
			}
		}
		if (!found){
			sync.setSyncPath(syncPath);
			this.syncPathList.add(sync);
		}
		
	}
	public void addPatient(Patient pat){
		if (getPatientIdx(pat)<0){
			patients.add(pat);
			//System.out.println("ajout patient"+pat.getNom());
		} else {
			if (!pat.getDefCotMatin().toString().equals("")){
				this.patients.get(getPatientIdx(pat)).setDefCotMatin(pat.getDefCotMatin());
				this.patients.get(getPatientIdx(pat)).addCotation(pat.getDefCotMatin());
				//System.out.println("add patient ajout cotation matin");
			}
			if (!pat.getDefCotMidi().toString().equals("")){
				this.patients.get(getPatientIdx(pat)).setDefCotMidi(pat.getDefCotMidi());
				this.patients.get(getPatientIdx(pat)).addCotation(pat.getDefCotMidi());
			}
			if (!pat.getDefCotSoir().toString().equals("")){
				this.patients.get(getPatientIdx(pat)).setDefCotSoir(pat.getDefCotSoir());
				this.patients.get(getPatientIdx(pat)).addCotation(pat.getDefCotSoir());
				//System.out.println("add patient ajout cotation soir");
			}
			for (int i=0;i<pat.getActe().size();i++){
				if(!this.patients.get(getPatientIdx(pat)).hasActe(pat.getActe().get(i))){
					this.patients.get(getPatientIdx(pat)).addActe(pat.getActe().get(i));
				}
			}
			// gestion des conflits quand 2 actes <> pour la meme dateJour ? 
			for (int i=0;i<pat.getCotList().size();i++){
				if(!this.patients.get(getPatientIdx(pat)).getCotList().contains(pat.getCotList().get(i))){
					this.patients.get(getPatientIdx(pat)).addCotation(pat.getCotList().get(i));
				}
			}
			if (pat.getVisible()==true){
				this.patients.get(getPatientIdx(pat)).setVisible(true);
			}
			
		}
	}
	public List<UidSyncPath> getSyncPathList() {
		return this.syncPathList;
	}
	public String getSyncPath(){
		String path="";
		UidSyncPath us=new UidSyncPath();
		String UID=us.getUID();
		for (int i=0;i<this.getSyncPathList().size();i++){
			if (this.getSyncPathList().get(i).getUID().equals(UID)){
				path=this.getSyncPathList().get(i).getSyncPath();
			}
		}
		return path;

		
	}
	public int getPatientIdx(Patient pat){
		int idx=-1;
		for (int i=0;i<patients.size();i++){
			if(patients.get(i).equals(pat)){idx= i;}
		}
		return idx;
	}
	public int getSize(){
		int size=0;
		for (int i=0;i<patients.size();i++){
			if (patients.get(i).getVisible()) size++;
		}
			
		return size;
	}
	public int getNbActesJour(Date date){
		int nbActes=0;
		for (int i=0;i<patients.size();i++){
			nbActes=nbActes+patients.get(i).getNbActesJour(date);
		}
		return nbActes;
	}
	public int getNbActesMois(Date date){
		int nbActes=0;
		for (int i=0;i<patients.size();i++){
			nbActes=nbActes+patients.get(i).getNbActesMois(date);
		}
		return nbActes;
	}
	public int getNbActesAnnee(Date date){
		int nbActes=0;
		return nbActes;
	}
	public double getFactJour(Date date){
		double fact=0;
		for (int i=0;i<patients.size();i++){
			fact=fact+patients.get(i).getFactJour(date);
		}
		return fact;
	}
	public double getFactMois(Date date){
		double fact=0;
		for (int i=0;i<patients.size();i++){
			fact=fact+patients.get(i).getFactMois(date);
		}
		return fact;
	}
	public double getFactAnnee(Date date){
		double fact=0;
		return fact;
	}
	public double getFactMois(Patient pat,Date date){
		double fact=0;
		return fact;
	}
	
	
	public Double getVersion(){
		return this.version;
	}
	public boolean isModified(){
		return this.modified;
	}
	public void setVersion(Double version){
		this.version=version;
	}
	public void setModified(boolean modified){
		this.modified=modified;
	}
	public int getPatientIdx(String fullName){
		Patient pat=new Patient();
		String nom1="";
		String nom2="";
		String prenom="";
		MrMme civilite=null;
		String[] nameParts=fullName.trim().split(" ");
		int startSearch =0;
		
		if ((nameParts[0].equals("Mme"))||(nameParts[0].equals("Mr"))){
			if (nameParts[0].equals("Mme")) {
				civilite=MrMme.Mme;
			} else {
				civilite=MrMme.Mr;
			}
			startSearch=1;
		}
		nom1=nameParts[startSearch];
		nom2=nameParts[startSearch];
		for (int i=startSearch+1;i<nameParts.length;i++){
			nom1=nom1+" "+nameParts[i];
		}
		for (int i=startSearch+1;i<nameParts.length-1;i++){
			nom2=nom2+" "+nameParts[i];
		}
		prenom=nameParts[nameParts.length-1];
		pat.setNom(nom1);
		pat.setPrenom("");
		pat.setCivilite(civilite);
		int idx=-1;
		for (int i=0;i<patients.size();i++){
			if(patients.get(i).equals(pat)){idx= i;}
		}
		//System.out.println("patient essai 1 :"+pat.toString());
		if (idx<0){
			pat.setNom(nom2);
			pat.setPrenom(prenom);
			pat.setCivilite(civilite);
			//System.out.println("patient essai 2 :"+pat.toString());
				for (int i=0;i<patients.size();i++){
				if(patients.get(i).equals(pat)){idx= i;}
			}
				
		}
		return idx;
	}
	public String toString(){
		String str="";
		if (soignants.size()>0){str=str+"Soignants:\n";}
		for(int i=0;i<soignants.size();i++){
			str=str+soignants.get(i);
		}
		if (patients.size()>0){str=str+"Patients:\n";}
		for(int i=0;i<patients.size();i++){
			str=str+patients.get(i).toString()+"\n";
		}
		return str;
	}
	public void writeCsv(){
		Date crtDate=new Date();
		System.out.println("exporting liste :");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyy");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fileName= "liste_"+sdf.format(crtDate)+".CSV";
		System.out.println(fileName);
		BufferedWriter bw=null;

		try {
			File dir=new File("Listes");
			if (!dir.exists()) {
				dir.mkdir();
			}
			File file = new File("Listes\\"+fileName);
			
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw=new FileWriter(file.getAbsoluteFile());
			bw=new BufferedWriter(fw);
			String line="Date;"+sdf2.format(crtDate)+"\n";
			bw.write(line);
			line="Civilite;Nom;Prenom;Adresse;Tel;MMS;Nb;Cotation;Coefficient;Nb;Cotation;Coefficient;Nb;Cotation;Coefficient;MAU;MajDim;MajNuit;IFD;IK;commentaire\n";
			bw.write(line);
			
			for (int i=0;i<patients.size();i++) {
				Patient pat=patients.get(i);
				if (pat.isSelected()) {
					line="";
					if (pat.getCivilite()!=null) {
					line=line+pat.getCivilite()+";";
					} else { 
						line=line+";";
					}
					if (pat.getNom()!=null) {
					line=line+pat.getNom()+";";
					} else { 
						line=line+";";
					}
					if (pat.getPrenom()!=null) {
					line=line+pat.getPrenom()+";";
					} else { 
						line=line+";";
					}
					if (pat.getAdresse()!=null) {
					line=line+pat.getAdresse()+";";
					} else { 
						line=line+";";
					}
					if (pat.getTel()!=null) {
					line=line+pat.getTel()+";";
					} else { 
						line=line+";";
					}
						
					if (!pat.getDefCotMatin().toString().equals("")){
						Cotation cot=pat.getDefCotMatin();
						if (!cot.gettypeActe1().equals(TypeActe.aucun)){
							line=line+"matin";
						} else {
							line=line+";;;;;;;;;;;;;;;";
						}
						line=line+"\n";
						bw.write(line);
					}
					
				}
			}
			JOptionPane.showMessageDialog(null, "Liste : "+fileName+" exportée avec succès");


		}	catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString(),"Erreur",JOptionPane.ERROR_MESSAGE);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void importCsv(){
		String line="";
		BufferedReader br=null;
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"fichier .csv", "csv");
		chooser.setFileFilter(filter);
		chooser.setCurrentDirectory(new File("."));
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			String fileName=chooser.getSelectedFile().getAbsolutePath();
			System.out.println("You chose to open this file: " +fileName );
			try {
				//patients.clear();
				br = new BufferedReader(new FileReader(fileName));
				patients.clear();
				line = br.readLine();
				Boolean beginSection=false;
				
				while ((line = br.readLine()) != null) {

					String[] item=line.toUpperCase().split(";",21);
					if (item[0].equals("CIVILITE")) {
						beginSection=true;
					}
					if ((!item[0].equals("CIVILITE"))&&beginSection) {

						Patient pat=new Patient(item[1],item[2]);
						if(!item[0].equals("")){pat.setCivilite(item[0]);}

						if(!item[3].equals("")){pat.setAdresse(item[3]);}
						if(!item[4].equals("")){pat.setTel(item[4]);}
						Cotation cot=new Cotation();
						String mms="MATIN";
						if(!item[7].equals("")){

							cot.setK11(Double.parseDouble(item[6]));
							cot.setK12(Double.parseDouble(item[8]));
							switch (item[7]){
							case "AMI":
								cot.settypeActe1(TypeActe.AMI);
								break;
							case "AIS":
								cot.settypeActe1(TypeActe.AIS);
								break;
							}
							if(!item[5].equals("")){
								mms=item[5];
							}
							if(!item[10].equals("")){
								cot.setK21(Double.parseDouble(item[9]));
								cot.setK22(Double.parseDouble(item[11]));
								switch (item[10]){
								case "AMI":
									cot.settypeActe2(TypeActe.AMI);
									break;
								case "AIS":
									cot.settypeActe2(TypeActe.AIS);
									break;
								}
								if(!item[13].equals("")){
									cot.setK31(Double.parseDouble(item[12]));
									cot.setK32(Double.parseDouble(item[14]));
									switch (item[13]){
									case "AMI":
										cot.settypeActe3(TypeActe.AMI);
										break;
									case "AIS":
										cot.settypeActe3(TypeActe.AIS);
										break;
									}
								}
							}
							if(!item[15].equals("")){
								if(item[15]=="0"||item[15]=="false"){
									cot.setMau(false);
								} else {
									cot.setMau(true);
								}
							}
							if(!item[16].equals("")){
								if(item[16]=="0"||item[16]=="false"){
									cot.setMajDim(false);
								} else {
									cot.setMajDim(true);
								}
							}
							if(!item[17].equals("")){
								if(item[17]=="0"||item[17]=="false"){
									cot.setMajNuit(false);
								} else {
									cot.setMajNuit(true);
								}
							}
							if(!item[18].equals("")){
								if(item[18]=="0"||item[18]=="false"){
									cot.setIfd(false);
								} else {
									cot.setIfd(true);
								}
							}
							if(!item[19].equals("")){
								cot.setIk(Integer.parseInt(item[19]));
							}
							if(!item[20].equals("")){
								cot.setComment(item[20]);
							}
							pat.addCotation(cot);
							switch (mms){
								case "MATIN":{
										pat.setDefCotMatin(cot);
										//System.out.println("ajout matin");
										break;
								}
								case "MIDI":{
										pat.setDefCotMidi(cot);
										break;
								}
								case "SOIR":{
									//System.out.println("ajout soir");
									pat.setDefCotSoir(cot);
										break;
								}
							}
							//System.out.println("matin : "+pat.getDefCotMatin());
							//System.out.println("soir : "+pat.getDefCotSoir());
						}
						this.addPatient(pat);
					}
				}

			} catch (FileNotFoundException e) {
				System.out.println(fileName+" n'existe pas !");
				JOptionPane.showMessageDialog(null, fileName+" n'existe pas !","Erreur",JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.toString(),"Erreur",JOptionPane.ERROR_MESSAGE);

			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}


}
