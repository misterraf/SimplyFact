package com.rt.simplyFact;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddOrdoDialog extends JDialog {
	private boolean sendData;
	private ImageIcon btnIcon3 = new ImageIcon(getClass().getResource("/Accept-icon.png"));
	private ImageIcon btnIcon4 = new ImageIcon(getClass().getResource("/add.png"));
	private ImageIcon btnIcon11 = new ImageIcon(getClass().getResource("/Calendar.png"));
	private ImageIcon btnIcon1 = new ImageIcon(getClass().getResource("/close.png"));
	private ImageIcon btnIcon2 = new ImageIcon(getClass().getResource("/findSmall.png"));
	private ImageIcon btnIcon5 = new ImageIcon(getClass().getResource("/Documents-icon.png"));
	private JButton btnOK=new JButton("OK",btnIcon3);
	private JButton btnCancel=new JButton("Fermer",btnIcon1);
	private JButton btnCal1=new JButton(btnIcon11);
	private JButton btnCal2=new JButton(btnIcon11);
	private JButton btnAddMed=new JButton(btnIcon4);
	private JButton btnSelFile=new JButton(btnIcon5);
	private JButton btnViewFile=new JButton(btnIcon2);
	private JComboBox comboJjMm=new JComboBox();
	private JComboBox comboMed=new JComboBox();
	private JTextField datePrTF =new JTextField();
	private JTextField dateDebTF =new JTextField();
	private JTextField dureeNTF =new JTextField();
	private JTextField motifTF =new JTextField();
	private JTextField fileTF =new JTextField();
	private JLabel dateFinLb=new JLabel();
	
	private List<String> meds=new ArrayList<String>();
	private Ordonnance ordo=new Ordonnance();
	private String patName;
	private boolean dataOk;
	
	public AddOrdoDialog(JFrame parent, String title, boolean modal){
		super(parent, title, modal);
		this.setSize(400, 400);
		this.setLocation(800,150);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent();	
		
	}
	public void initComponent(){
		BorderLayout b = new BorderLayout();
		
		this.setLayout(b);
		JPanel btnPanel=new JPanel();
		this.getContentPane().add(btnPanel,BorderLayout.SOUTH);
		btnPanel.add(btnOK);
		btnPanel.add(btnCancel);
		Box box=new Box(BoxLayout.Y_AXIS);
		this.getContentPane().add(box,BorderLayout.CENTER);
		JPanel panDates=new JPanel(new BorderLayout());
		JPanel panMed=new JPanel();
		JPanel panMotif=new JPanel();
		JPanel panFile=new JPanel();
		panDates.setBorder(BorderFactory.createTitledBorder("Dates"));
		panMed.setBorder(BorderFactory.createTitledBorder("Médecin Traitant"));
		panMotif.setBorder(BorderFactory.createTitledBorder("Motif"));
		panFile.setBorder(BorderFactory.createTitledBorder("Fichier Associé"));
		box.add(panDates);
		box.add(panMed);
		box.add(panMotif);
		box.add(panFile);
		Box boxFileX=new Box(BoxLayout.X_AXIS);
		boxFileX.add(fileTF);
		btnSelFile.setPreferredSize(new Dimension(25,25));
		btnViewFile.setPreferredSize(new Dimension(25,25));
		boxFileX.add(btnSelFile);
		boxFileX.add(btnViewFile);
		panFile.add(boxFileX);
		fileTF.setPreferredSize(new Dimension(300,30));
		panMotif.add(motifTF);
		motifTF.setPreferredSize(new Dimension(380,30));
		Box boxMedX=new Box(BoxLayout.X_AXIS);
		boxMedX.add(comboMed);
		boxMedX.add(btnAddMed);
		panMed.add(boxMedX);
		comboMed.setPreferredSize(new Dimension(300,30));
		Box boxDateY=new Box(BoxLayout.Y_AXIS);
		JPanel dateY1=new JPanel(new BorderLayout());
		JPanel dateY2=new JPanel(new BorderLayout());
		JPanel dateY3=new JPanel(new BorderLayout());
		JPanel dateY4=new JPanel(new BorderLayout());
		Box dateY1Box=new Box(BoxLayout.X_AXIS);
		Box dateY2Box=new Box(BoxLayout.X_AXIS);
		Box dateY3Box=new Box(BoxLayout.X_AXIS);
		Box dateY4Box=new Box(BoxLayout.X_AXIS);
		dateY1Box.add(new JLabel("Date de Prescription :"));
		dateY1Box.add(datePrTF);
		dateY1Box.add(btnCal1);
		//dateY1Box.setAlignmentX(0);
		dateY2Box.add(new JLabel("Date de Prise d'effet : "));
		dateY2Box.add(dateDebTF);
		dateY2Box.add(btnCal2);
		dateY3Box.add(new JLabel("Durée : "));
		dateY3Box.add(dureeNTF);
		dateY3Box.add(new JLabel(" "));
		dateY3Box.add(comboJjMm);
		dateFinLb.setText("Date fin :");
		dateY4Box.add(dateFinLb);
		dateY1.add(dateY1Box,BorderLayout.CENTER);
		dateY2.add(dateY2Box,BorderLayout.CENTER);
		dateY3.add(dateY3Box,BorderLayout.CENTER);
		dateY4.add(dateY4Box,BorderLayout.CENTER);
		boxDateY.add(dateY1);
		boxDateY.add(dateY2);
		boxDateY.add(dateY3);
		boxDateY.add(dateY4);
		panDates.add(boxDateY,BorderLayout.CENTER);
		
		
		
		
		comboMed.removeAllItems();
		comboMed.addItem("");
		comboJjMm.addItem("jour(s)");
		comboJjMm.addItem("mois");
		comboJjMm.addActionListener(new JjMmComboChange());
		btnCancel.addActionListener(new CloseBtnListener());
		btnOK.addActionListener(new OkBtnListener());
		btnAddMed.addActionListener(new AddMedBtnListener());
		btnCal1.addActionListener(new CalBtnListener());
		btnCal2.addActionListener(new CalBtnListener());
		btnSelFile.addActionListener(new SelFileBtnListener());
		btnViewFile.addActionListener(new ViewFileBtnListener());
		
		datePrTF.addFocusListener(new DataFocusListener());
		dateDebTF.addFocusListener(new DataFocusListener());
		dureeNTF.addFocusListener(new DataFocusListener());
		motifTF.addFocusListener(new DataFocusListener());
		fileTF.addFocusListener(new DataFocusListener());
		comboMed.addFocusListener(new DataFocusListener());

	}
	public void setData(List<String> meds,String patName){
		this.meds=meds;
		this.patName=patName;
		comboMed.removeAllItems();
		comboMed.addItem("");
		if(meds!=null){
			for (int i=0;i<meds.size();i++){comboMed.addItem(meds.get(i));}
		}
	}
	public Ordonnance showAddOrdo(){
		this.sendData = false;
		this.setVisible(true);      
		return this.ordo;      
	}
	public String addStr(String a,String b){
		String res="0";
		int ai=Integer.parseInt(a);
		int bi=Integer.parseInt(b);
		int resi=ai+bi;
		res=String.valueOf(resi);
		return res;
	}
	public String getDateFin(){
		String dateFin="Date fin :";
		if (!(dateDebTF.getText().equals(""))&&!(dureeNTF.getText().equals(""))){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdfM = new SimpleDateFormat("MM");
			SimpleDateFormat sdfJ = new SimpleDateFormat("dd");
			SimpleDateFormat sdfA = new SimpleDateFormat("yyyy");
			
			try {
				Date debDate=sdf.parse(dateDebTF.getText());
				String debDateJStr=sdfJ.format(debDate);
				String debDateMStr=sdfM.format(debDate);
				String debDateAStr=sdfA.format(debDate);
				if (comboJjMm.getSelectedItem().equals("mois")){
					debDateMStr=addStr(debDateMStr,dureeNTF.getText());
				} else {
					debDateJStr=addStr(debDateJStr,dureeNTF.getText());
				}
				String dateFinStr=debDateJStr+"/"+debDateMStr+"/"+debDateAStr;
				try {
					Date finDate=sdf.parse(dateFinStr);
					dateFin=dateFin+" "+sdf.format(finDate);
					ordo.setDateFin(finDate);
				} catch (ParseException e) {
				}
				
			} catch (ParseException e) {
			}

		}
		
		return dateFin;
	}
	public void viewFile(String fileName){
		try {
		Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " +fileName);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	public void validData(Boolean all){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		datePrTF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		dateDebTF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		dureeNTF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		comboMed.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		motifTF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		fileTF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		
		dataOk=true;

		if (datePrTF.getText().equals("")) {
			dataOk=false;
		} else {
			try {
				Date debPr=sdf.parse(datePrTF.getText());
				ordo.setDatePresc(debPr);
			} catch (ParseException e) {
				dataOk=false;
			}

		}
		if (!dataOk){
			dateFinLb.setText("Veuillez entrer une date de prescription valide (jj/mm/aaaa)");
			datePrTF.requestFocus();
			datePrTF.setBorder(BorderFactory.createLineBorder(Color.red, 2));
		}
		if (dataOk) {
			if (dateDebTF.getText().equals("")) {
				dataOk=false;
			} else {
				try {
					Date debPr=sdf.parse(dateDebTF.getText());
					ordo.setDateDebut(debPr);
				} catch (ParseException e) {
					dataOk=false;
				}
			}

			if (!dataOk){
				dateFinLb.setText("Veuillez entrer une date de début valide (jj/mm/aaaa)");
				dateDebTF.requestFocus();
				dateDebTF.setBorder(BorderFactory.createLineBorder(Color.red, 2));
			}
		}
		if (dataOk) {
			if (dureeNTF.getText().equals("")) {
				dataOk=false;
			} else {
				try {
					int dureeN = Integer.parseInt(dureeNTF.getText());
					ordo.setDureeN(dureeN);
					ordo.setDureeStr(comboJjMm.getSelectedItem().toString());
				} catch (NumberFormatException  e) {
					dataOk=false;
				}

			}
			if (!dataOk) {
				dateFinLb.setText("Veuillez entrer une valeur entiere pour le nombre de "+comboJjMm.getSelectedItem());
				dureeNTF.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				dureeNTF.requestFocus();
			}
		}
		if (all) {
			if (dataOk) {
				if (comboMed.getSelectedItem().equals("")){
					dateFinLb.setText("Veuillez sélectionner un médecin traitant");
					comboMed.requestFocus();
					comboMed.setBorder(BorderFactory.createLineBorder(Color.red, 2));
					dataOk=false;
				} else {
					ordo.setMedecin(comboMed.getSelectedItem().toString());
					dateFinLb.setText(getDateFin());
					comboMed.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
				}
			}
			if (dataOk) {
				if (motifTF.getText().equals("")){
					dateFinLb.setText("Veuillez entrer une courte description de l'ordonnance");
					motifTF.requestFocus();
					motifTF.setBorder(BorderFactory.createLineBorder(Color.red, 2));
					dataOk=false;
				} else {
					dateFinLb.setText(getDateFin());
					ordo.setMotif(motifTF.getText());
					motifTF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));

				}
			}
			if (dataOk) {
				if (fileTF.getText().equals("")){
					dateFinLb.setText("Veuillez associer un fichier a cette ordonnance");
					fileTF.requestFocus();
					fileTF.setBorder(BorderFactory.createLineBorder(Color.red, 2));
					dataOk=false;
				} else {
					dateFinLb.setText(getDateFin());
					fileTF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
				}
			}
		}

	}
	class CloseBtnListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			ordo.setMotif("cancelled");
			dispose();
		}
	}	
	class CalBtnListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			if(event.getSource().equals(btnCal1)){
				CalendarSel cal=new CalendarSel(null,"Choisir la date",true);
				SimpleDateFormat sdfm = new SimpleDateFormat("MM");
				SimpleDateFormat sdfa = new SimpleDateFormat("yyy");
				Date crtDate=new Date();
				String mois=sdfm.format(crtDate);
				String annee=sdfa.format(crtDate);
				
				
				cal.setCrtMY(mois,annee);
				Date selDate=cal.showCalendarSel();
				crtDate=selDate;
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
				datePrTF.setText(sdf.format(crtDate));
			}
			if(event.getSource().equals(btnCal2)){
				CalendarSel cal=new CalendarSel(null,"Choisir la date",true);
				SimpleDateFormat sdfm = new SimpleDateFormat("MM");
				SimpleDateFormat sdfa = new SimpleDateFormat("yyy");
				Date crtDate=new Date();
				String mois=sdfm.format(crtDate);
				String annee=sdfa.format(crtDate);
				
				
				cal.setCrtMY(mois,annee);
				Date selDate=cal.showCalendarSel();
				crtDate=selDate;
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
				dateDebTF.setText(sdf.format(crtDate));
			}
		}
	}	

	class DataFocusListener implements FocusListener{
		public void focusLost(FocusEvent event) {
			validData(false);
		}
		public void focusGained(FocusEvent event) {

		}
	}


	class OkBtnListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			validData(true);
			if(dataOk) {
				boolean fcopyOk=true;
				String fileName=fileTF.getText();
				String fileExt=fileName.substring(fileName.length()-4, fileName.length());
				File fOrdo=new File(fileName);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
				String motifSpl[]=ordo.getMotif().split(" ");
				String motif="";
				for (int i=0;i<motifSpl.length;i++){
					if (motifSpl[i].length()>1) {
						motif=motif+motifSpl[i].substring(0,1).toUpperCase()+motifSpl[i].substring(1,motifSpl[i].length()).toLowerCase();
					} else {
						motif=motif+motifSpl[i].toUpperCase();
					}
				}
				
				String newFileName=".\\Ordos En Cours\\Ordo_"+patName+"_"+sdf.format(ordo.datePresc)+"_"+motif+fileExt;
				File fOrdoArch=new File(newFileName);
				if (!fOrdo.exists()){
					JOptionPane.showMessageDialog(null, "Le fichier "+fileTF.getText()+" n'existe pas. Veuillez selectionner un fichier existant","Erreur",JOptionPane.ERROR_MESSAGE);
					fcopyOk=false;
				} else {
					File dir=new File("Ordos En Cours");
					if (!dir.exists()){
						dir.mkdir();
					}
					System.out.println("Copie du fichier "+fileName+" vers "+newFileName);
					fcopyOk=copyFile(fOrdo,fOrdoArch);
				}
				if (fcopyOk){
					ordo.setFileName(newFileName);
					JOptionPane.showMessageDialog(null, "Copie du fichier "+fileName+"\nvers : \n"+newFileName+"\nréussie","Message",JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
			}
		}
	}	
	public static boolean copyFile(File source, File dest){
		try{
			// Declaration et ouverture des flux
			java.io.FileInputStream sourceFile = new java.io.FileInputStream(source);
	 
			try{
				java.io.FileOutputStream destinationFile = null;
	 
				try{
					destinationFile = new FileOutputStream(dest);
	 
					// Lecture par segment de 0.5Mo 
					byte buffer[] = new byte[512 * 1024];
					int nbLecture;
	 
					while ((nbLecture = sourceFile.read(buffer)) != -1){
						destinationFile.write(buffer, 0, nbLecture);
					}
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.toString(),"Erreur",JOptionPane.ERROR_MESSAGE);
				}	finally {
					destinationFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.toString(),"Erreur",JOptionPane.ERROR_MESSAGE);
			} finally {
				sourceFile.close();
			}
		} catch (IOException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString(),"Erreur",JOptionPane.ERROR_MESSAGE);
			return false; // Erreur
		}
	 
		return true; // Résultat OK  
	}
	class AddMedBtnListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			JOptionPane d = new JOptionPane();
			String newMed = d.showInputDialog(null,"Médecin Traitant :","Ajouter un nouveau médecin traitant",JOptionPane.OK_CANCEL_OPTION);
			if((newMed!=null) && !newMed.equals("")){
				if (!meds.contains(newMed)){
					meds.add(newMed);
					setData(meds,patName);
					comboMed.setSelectedItem(newMed);
					//for (int i=0;i<meds.size();i++) System.out.println("med : "+meds.get(i));
				}
				
			}
			
		}
	}	
	class JjMmComboChange implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			dateFinLb.setText(getDateFin());
		}
	}	
	class SelFileBtnListener  implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"fichier .jpg / .pdf", "pdf", "jpg");
			chooser.setFileFilter(filter);
			chooser.setCurrentDirectory(new File("."));
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				String fileName=chooser.getSelectedFile().getAbsolutePath();
				fileTF.setText(fileName);
			}
		}
	}	
	class ViewFileBtnListener  implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			File fileSel=new File(fileTF.getText());
			if (!fileSel.exists()){
				JOptionPane.showMessageDialog(null, "Le fichier "+fileTF.getText()+" n'existe pas. Veuillez selectionner un fichier existant","Erreur",JOptionPane.ERROR_MESSAGE);
				
			} else {
				viewFile(fileTF.getText());
			}
			
		}
	}	
}
