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
	protected int crtSoignant ;
	
	public Cabinet(){
		this.version=1.3;
		this.crtSoignant=-1;		
	}
	
	public void addSoignant(String soignant){
		if(!soignants.contains(soignant)){
			if (soignants.size()==0) {crtSoignant++;}
			soignants.add(soignant);
		} else {
			JOptionPane.showMessageDialog(null, "Ce nom existe déjà ","Erreur",JOptionPane.ERROR_MESSAGE);
		}
	}
	public void setCrtSoignant(int crtSoignant){
		this.crtSoignant=crtSoignant;
	}
	public void delCrtSoignant(){
		if(this.crtSoignant+1==soignants.size()){
			soignants.remove(this.crtSoignant);
			this.crtSoignant--;
		} else {
			soignants.remove(this.crtSoignant);
		}
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
	public int getCrtSoignant(){
		return this.crtSoignant;
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
	public int getNbActesJour(Date date,String soignant){
		int nbActes=0;
		for (int i=0;i<patients.size();i++){
			nbActes=nbActes+patients.get(i).getNbActesJour(date,soignant);
		}
		return nbActes;
	}
	public int getNbActesMois(Date date,String soignant){
		int nbActes=0;
		for (int i=0;i<patients.size();i++){
			nbActes=nbActes+patients.get(i).getNbActesMois(date,soignant);
		}
		return nbActes;
	}
	public double getFactJour(Date date, String soignant){
		double fact=0;
		for (int i=0;i<patients.size();i++){
			fact=fact+patients.get(i).getFactJour(date,soignant);
		}
		return fact;
	}
	public double getFactMois(Date date, String soignant){
		double fact=0;
		for (int i=0;i<patients.size();i++){
			fact=fact+patients.get(i).getFactMois(date,soignant);
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
		nom1=nameParts[startSearch].trim();
		nom2=nameParts[startSearch].trim();
		for (int i=startSearch+1;i<nameParts.length;i++){
			if(!nameParts[i].trim().equals("")){
				nom1=nom1+" "+nameParts[i].trim();
			}
		}
		for (int i=startSearch+1;i<nameParts.length-1;i++){
			if(!nameParts[i].trim().equals("")){
				nom2=nom2+" "+nameParts[i].trim();
			}
		}
		prenom=nameParts[nameParts.length-1].trim();
		pat.setNom(nom1);
		pat.setPrenom("");
		pat.setCivilite(civilite);
		int idx=-1;
		for (int i=0;i<patients.size();i++){
			if(patients.get(i).equals(pat)){idx= i;break;}
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
	public String checkIfNoEmptyDefCot(){
		String patFailList="";
		for (int i=0;i<patients.size();i++) {
			if(patients.get(i).getVisible()){
				String cotMat=patients.get(i).getDefCotMatin().toString();
				String cotMid=patients.get(i).getDefCotMidi().toString();
				String cotSoir=patients.get(i).getDefCotSoir().toString();
				if((cotMat.equals(""))&&(cotMid.equals(""))&&(cotSoir.equals(""))){
					System.out.println("Patient "+patients.get(i).getNom()+" n'a pas de cotation par defaut");
					if(patFailList.equals("")){
						patFailList=patFailList+patients.get(i).getNom();
					} else {
						patFailList=patFailList+", "+patients.get(i).getNom();
					}
				}
			}
		}
		return patFailList;
	}
	public void writeCsv(Date crtDate){
		//Date crtDate=new Date();
		String test=checkIfNoEmptyDefCot();
		if(!test.equals("")){
			int confirm = JOptionPane.showOptionDialog(null, test+" n'ont pas de cotation par défaut\n(et ne seront donc pas exportés dans les listes).\nVoulez-vous exporter la liste ?", "Avertissement", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (confirm != 0) {return;}

		}
		System.out.println("exporting liste : (test :"+test+")");
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyy");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fileName= "liste_"+sdf.format(crtDate)+".CSV";
		String fileNameTxt= "liste_"+sdf.format(crtDate)+".txt";
		System.out.println(fileName);
		BufferedWriter bw=null;
		BufferedWriter bwTxt=null;

		try {
			File dir=new File("Listes");
			if (!dir.exists()) {
				dir.mkdir();
			}
			File file = new File("Listes\\"+fileName);
			File fileTxt = new File("Listes\\"+fileNameTxt);
			
			if (!file.exists()) {
				file.createNewFile();
			}
			if (!fileTxt.exists()) {
				fileTxt.createNewFile();
			}
			FileWriter fw=new FileWriter(file.getAbsoluteFile());
			FileWriter fwTxt=new FileWriter(fileTxt.getAbsoluteFile());
			bw=new BufferedWriter(fw);
			bwTxt=new BufferedWriter(fwTxt);
			String line="Date;"+sdf2.format(crtDate)+"\n";
			bw.write(line);
			bwTxt.write("Listes du :"+sdf2.format(crtDate)+System.getProperty("line.separator"));
			
			String soignant;
			for (int s=0;s<soignants.size();s++){
				soignant=soignants.get(s);
				line="\n\"Liste pour :\";"+soignant+"\n";
				bw.write(line);
				bwTxt.write(System.getProperty("line.separator")+"Liste pour : "+soignant+System.getProperty("line.separator"));
				line="Civilite;Nom;Prenom;Adresse;Tel;MMS;Nb;Cotation;Coefficient;Nb;Cotation;Coefficient;Nb;Cotation;Coefficient;MAU;MCI;MajDim;MajNuit;IFD;IK;commentaire\n";
				bw.write(line);

				for (int i=0;i<patients.size();i++) {
					Patient pat=patients.get(i);
					if (pat.hasActe(crtDate, soignant)) {
						List<Acte> actList=pat.getActe(crtDate, soignant);
						for (int j=0;j<actList.size();j++){
							Acte crtAct=actList.get(j);
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
							line=line+crtAct.getDateJour().getJour().toString()+";";
							line=line+String.valueOf(crtAct.getCotation().getK11())+";";
							line=line+crtAct.getCotation().gettypeActe1().toString()+";";
							line=line+String.valueOf(crtAct.getCotation().getK12())+";";
							if (crtAct.getCotation().gettypeActe2().equals(TypeActe.aucun)){
								line=line+";;;";
							} else {
								line=line+String.valueOf(crtAct.getCotation().getK21())+";";
								line=line+crtAct.getCotation().gettypeActe2().toString()+";";
								line=line+String.valueOf(crtAct.getCotation().getK22())+";";
							}
							if (crtAct.getCotation().gettypeActe3().equals(TypeActe.aucun)){
								line=line+";;;";
							} else {
								line=line+String.valueOf(crtAct.getCotation().getK31())+";";
								line=line+crtAct.getCotation().gettypeActe3().toString()+";";
								line=line+String.valueOf(crtAct.getCotation().getK32())+";";
							}
							if (crtAct.getCotation().getMau()){
								line=line+"oui;";
							} else {
								line=line+";";
							}
							if (crtAct.getCotation().getMci()){
								line=line+"oui;";
							} else {
								line=line+";";
							}
							if (crtAct.getCotation().getMajDim()){
								line=line+"oui;";
							} else {
								line=line+";";
							}
							if (crtAct.getCotation().getMajNuit()){
								line=line+"oui;";
							} else {
								line=line+";";
							}
							if (crtAct.getCotation().getIfd()){
								line=line+"oui;";
							} else {
								line=line+";";
							}
							line=line+crtAct.getCotation().getIk()+";";
							line=line+crtAct.getCotation().getComment()+";";
							line=line+"\n";
							bw.write(line);
							bwTxt.write(pat.toString()+" "+crtAct.getDateJour().getJour().toString()+":"+crtAct.getCotation().toString()+System.getProperty("line.separator"));
						}

					}
				}
			}
			JOptionPane.showMessageDialog(null, "Liste : "+fileNameTxt+" exportée avec succès");


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
			if (bwTxt != null) {
				try {
					bwTxt.close();
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
				"fichier .txt", "txt");
		chooser.setFileFilter(filter);
		chooser.setCurrentDirectory(new File(".\\Listes"));
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			String fileName=chooser.getSelectedFile().getAbsolutePath();
			System.out.println("You chose to open this file: " +fileName );
			try {
				//patients.clear();
				br = new BufferedReader(new FileReader(fileName));

				Date crtDate=new Date();
				String soignant="";
				while ((line = br.readLine()) != null) {
					if(line.contains("Listes du")){
						String[] lineSpl=line.split(":");
						String dateStr=lineSpl[lineSpl.length-1].trim();
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						try {
							crtDate=sdf.parse(dateStr);
						} catch (ParseException e) {
							e.printStackTrace();
						}

					} else if(line.contains("Liste pour")){
						String[] lineSpl=line.split(":");
						soignant=lineSpl[lineSpl.length-1].trim();
						if(!this.soignants.contains(soignant)){
							this.addSoignant(soignant);
						}

					} else if (!line.equals("")){

						String[] lineSpl=line.split(" ");
						int cotIdx=0;
						for (int i=0;i<lineSpl.length;i++){
							if(lineSpl[i].contains(":")){
								cotIdx=i;
								break;
							}
						}
						String nomStr=lineSpl[0];
						String comment="";
						if(lineSpl.length>cotIdx+1){
							for(int i=cotIdx+1;i<lineSpl.length;i++){
								comment=comment+" "+lineSpl[i];
							}
						}
						String actStr=lineSpl[cotIdx];
						for(int i=1;i<cotIdx;i++){
							nomStr=nomStr+" "+lineSpl[i];
						}
					
						int patIdx=this.getPatientIdx(nomStr);
						Patient pat=new Patient();
						if(patIdx<0){

							String nom="";
							String prenom="";
							MrMme civilite=null;
							String[] nameParts=nomStr.trim().split(" ");
							int startSearch =0;

							if ((nameParts[0].equals("Mme"))||(nameParts[0].equals("Mr"))){
								if (nameParts[0].equals("Mme")) {
									civilite=MrMme.Mme;
								} else {
									civilite=MrMme.Mr;
								}
								startSearch=1;
							}
							if(nameParts.length-startSearch>1){
								prenom=nameParts[nameParts.length-1];
								nom=nameParts[startSearch].trim();
								for (int i=startSearch+1;i<nameParts.length-1;i++){
									if(!nameParts[i].trim().equals("")){
										nom=nom+" "+nameParts[i].trim();
									}
								}

							} else {
								nom=nameParts[nameParts.length-1];
							}
							pat.setNom(nom);
							pat.setCivilite(civilite);
							pat.setPrenom(prenom);
							System.out.println("Creation nouveau patient :"+pat);

						} else {
							pat=this.patients.get(patIdx);
						}

						String[] cotSpl=actStr.split(":");
						String cotJour=cotSpl[0];
						String cotStr=cotSpl[1];

						Acte act=new Acte();
						DateJour dj=new DateJour(crtDate);
						if (cotJour.equals("matin")) {
							dj.setJour(Jour.matin);
						} else if (cotJour.equals("midi")) {
							dj.setJour(Jour.midi);
						} else if (cotJour.equals("soir")) {
							dj.setJour(Jour.soir);
						}
						act.setDateJour(dj);
						Cotation cot=new Cotation(cotStr);
						if (!pat.getCotList().contains(cot)){
							pat.addCotation(cot);
						}
						act.setCotation(cot);
						act.setSoignant(soignant);
						if(!pat.hasActe(crtDate)){
							
							pat.addActe(act);
							System.out.println("ajout patient :"+pat);
							System.out.println("ajout cotation :"+cot);
							System.out.println("ajout acte :"+act);
						}
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
