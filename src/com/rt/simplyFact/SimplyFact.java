package com.rt.simplyFact;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;


public class SimplyFact extends JFrame {
	private JTable tableau;
	private ImageIcon btnIcon1 = new ImageIcon(getClass().getResource("/find.png"));
	private ImageIcon btnIcon2 = new ImageIcon(getClass().getResource("/save.png"));
	private ImageIcon btnIcon3 = new ImageIcon(getClass().getResource("/exportPDF.png"));
	private ImageIcon btnIcon4 = new ImageIcon(getClass().getResource("/add.png"));
	private ImageIcon btnIcon5 = new ImageIcon(getClass().getResource("/list.png"));
	private ImageIcon btnIcon6 = new ImageIcon(getClass().getResource("/importCsv.png"));
	private ImageIcon btnIcon7 = new ImageIcon(getClass().getResource("/Gear-icon.png"));
	private ImageIcon btnIcon8 = new ImageIcon(getClass().getResource("/delete.png"));
	private ImageIcon btnIcon9 = new ImageIcon(getClass().getResource("/left.png"));
	private ImageIcon btnIcon10 = new ImageIcon(getClass().getResource("/right.png"));
	private ImageIcon btnIcon11 = new ImageIcon(getClass().getResource("/Calendar.png"));
	private ImageIcon btnIcon12 = new ImageIcon(getClass().getResource("/chart.png"));
	private ImageIcon btnIcon13 = new ImageIcon(getClass().getResource("/delete.png"));
	private ImageIcon btnIcon14 = new ImageIcon(getClass().getResource("/document-yellow-icon.png"));
	//private JButton importCsvBtn = new JButton(btnIcon6);
	private JButton saveBtn = new JButton(btnIcon2);
	private JButton expPdfBtn = new JButton(btnIcon3);
	private JButton expXlsBtn = new JButton(btnIcon5);
	private JButton statsBtn = new JButton(btnIcon12);
	private JButton clearBtn = new JButton(btnIcon8);
	private JButton leftArrowBtn = new JButton(btnIcon9);
	private JButton rightArrowBtn = new JButton(btnIcon10);
	private JButton calendarBtn = new JButton(btnIcon11);
	private JButton addSoignantBtn = new JButton(btnIcon4);
	private JButton delSoignantBtn = new JButton(btnIcon13);
	private JButton ordoListBtn = new JButton(btnIcon14);
	private JTextField dateEntry=new JTextField();
	private String crtSoignant="";
	private Patient crtPat=null;
	private Date crtDate=new Date();
	private FichePat fp;
	private FicheList fl;
	private FicheStats fs;
	private FicheOrdos FO;
	private JMenuBar menuBar=new JMenuBar();
	private JMenu menu1=new JMenu("Fichier");
	private JMenu menu2=new JMenu("Patients");
	private JMenu menu3=new JMenu("Aide");
	private JMenuItem item1_1=new JMenuItem("Lire Base de Donnee");
	private JMenuItem item1_2=new JMenuItem("Sauvegarder Donnees");
	private JMenuItem item1_3=new JMenuItem("Importer Liste");
	private JMenuItem item1_4=new JMenuItem("Exporter Liste");
	private JMenuItem item1_5=new JMenuItem("Exporter Fiches de Liaison");
	private JMenuItem item1_7=new JMenuItem("Sélectionner Repertoire de Synchronisation");
	private JMenuItem item1_6=new JMenuItem("Quitter");
	private JMenuItem item2_1=new JMenuItem("Effacer Tout");
	private JMenuItem item2_6=new JMenuItem("Effacer Tous Les Actes");
	private JMenuItem item2_7=new JMenuItem("Effacer Les Cotations Inutilisees");
	private JMenuItem item2_2=new JMenuItem("Effacer Patients Selectionnes");
	private JMenuItem item2_4=new JMenuItem("Effacer Patients Inactifs");
	private JMenuItem item2_3=new JMenuItem("Retrouver Patients Effaces");
	private JMenuItem item2_5=new JMenuItem("Affecter Actes passés");
	private JMenuItem item3_1=new JMenuItem("A Propos");
	private JMenuItem item3_2=new JMenuItem("Stats");
	private JMenuItem item3_3=new JMenuItem("Debug : liste actes du jour");
	private JComboBox soignantCB=new JComboBox();
	private String version="2.3";
	private Cabinet cab=new Cabinet();
	private List<ActeJour> listActesJour=new ArrayList<ActeJour>();
	private int crtDayOrder=0;
	
	private Splash spl=new Splash(null,"A Propos...",true);
	//public NewPatDialog newFichePat; 

	private int nbPatSelected=0;


	public SimplyFact(){
		this.setLocation(100,50);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("SimplyFact'");
		this.setSize(400, 700);
		
		this.menu1.add(item1_1);
		this.menu1.add(item1_2);
		this.menu1.add(item1_3);
		this.menu1.add(item1_4);
		this.menu1.add(item1_5);
		this.menu1.add(item1_7);
		this.menu1.add(item1_6);
		this.menu2.add(item2_1);
		this.menu2.add(item2_6);
		this.menu2.add(item2_7);
		this.menu2.add(item2_2);
		this.menu2.add(item2_4);
		this.menu2.add(item2_3);
		this.menu2.add(item2_5);
		this.menu3.add(item3_1);
		this.menu3.add(item3_2);
		this.menu3.add(item3_3);
		this.menuBar.add(menu1);
		this.menuBar.add(menu2);
		this.menuBar.add(menu3);
		this.setJMenuBar(menuBar);
		item1_7.setEnabled(false);
		createWin();
	}
	private void createWin(){
		BorderLayout b = new BorderLayout();
		JPanel btnPanel=new JPanel();
		JPanel selSoignantPanel=new JPanel();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");

		this.setLayout(b);
		//JOptionPane.showMessageDialog(null, getClass().getResource("/find.png")+ " "+btnIcon1.toString().substring(0,btnIcon1.toString().length()),"Erreur",JOptionPane.ERROR_MESSAGE);
		//System.out.println(btnIcon1.toString().substring(6,btnIcon1.toString().length()));
		//importCsvBtn.setPreferredSize(new Dimension(50,35));
		clearBtn.setPreferredSize(new Dimension(50,35));
		saveBtn.setPreferredSize(new Dimension(50,35));
		expPdfBtn.setPreferredSize(new Dimension(50,35));
		expXlsBtn.setPreferredSize(new Dimension(50,35));
		statsBtn.setPreferredSize(new Dimension(50,35));
		ordoListBtn.setPreferredSize(new Dimension(50,35));
		//btnPanel.add(importCsvBtn);
		btnPanel.add(clearBtn);
		btnPanel.add(saveBtn);
		btnPanel.add(expPdfBtn);
		btnPanel.add(ordoListBtn);
		btnPanel.add(expXlsBtn);
		btnPanel.add(statsBtn);
		selSoignantPanel.add(new JLabel("Utilisateur:"));

		selSoignantPanel.add(soignantCB);
		selSoignantPanel.add(addSoignantBtn);
		selSoignantPanel.add(delSoignantBtn);
		addSoignantBtn.setPreferredSize(new Dimension(24,24));
		delSoignantBtn.setPreferredSize(new Dimension(24,24));
		soignantCB.setPreferredSize(new Dimension(250,24));
		soignantCB.addActionListener(new SelComboChange());
		addSoignantBtn.addActionListener(new AddSoignantListener());
		delSoignantBtn.addActionListener(new DelSoignantListener());
		ordoListBtn.addActionListener(new OrdoListListener());
		//importCsvBtn.setToolTipText("Import Excel .CSV");
		clearBtn.setToolTipText("Effacer les données");
		saveBtn.setToolTipText("Sauvegarder les données");
		expPdfBtn.setToolTipText("Export facturation .PDF du mois en cours");
		expXlsBtn.setToolTipText("Liste des patients du jour");
		statsBtn.setToolTipText("Statistiques");
		ordoListBtn.setToolTipText("Liste des ordonnances");
		
		this.getContentPane().add(btnPanel,BorderLayout.SOUTH);
		this.getContentPane().add(selSoignantPanel,BorderLayout.NORTH);
		Box box=new Box(BoxLayout.Y_AXIS);
		Box box2=new Box(BoxLayout.X_AXIS);
		JPanel listePanel=new JPanel(new BorderLayout());
		JPanel datePanel=new JPanel();
		datePanel.add(box2);
		box2.add(leftArrowBtn);
		box2.add(dateEntry);
		box2.add(rightArrowBtn);
		box2.add(calendarBtn);
		dateEntry.setPreferredSize(new Dimension(100,16));
		dateEntry.setEditable(false);
		listePanel.setBorder(BorderFactory.createTitledBorder(""));
		datePanel.setBorder(BorderFactory.createTitledBorder("Date"));
		box.add(listePanel);
		box.add(datePanel);
		dateEntry.setText(sdf.format(crtDate));

		Object[][] data={{btnIcon4,"",false,null}};
		String title[] = {"Voir", "Nom", "Sél.","Cot°"};
		ZModel zModel = new ZModel(data, title);
		this.tableau = new JTable(zModel);
		initTable();
		updateListSelection();
		this.tableau.setRowHeight(26);
		this.tableau.getColumn("Voir").setPreferredWidth(24);
		this.tableau.getColumn("Sél.").setPreferredWidth(24);
		this.tableau.getColumn("Cot°").setPreferredWidth(24);
		this.tableau.getColumn("Nom").setPreferredWidth(250);
		this.tableau.getColumn("Voir").setCellRenderer(new ButtonRenderer());
		this.tableau.getColumn("Cot°").setCellRenderer(new ButtonRenderer());
		this.tableau.getColumn("Voir").setCellEditor(new ButtonEditor(new JCheckBox()));
		this.tableau.getColumn("Cot°").setCellEditor(new ButtonEditor(new JCheckBox()));
		this.tableau.setCellSelectionEnabled(true);
		ListSelectionModel cellSelectionModel = tableau.getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				//System.out.println("change...");
				Boolean selectedData = null;
				int[] selectedRow = tableau.getSelectedRows();
				int[] selectedColumns = tableau.getSelectedColumns();
				for (int i = 0; i < selectedRow.length; i++) {
					for (int j = 0; j < selectedColumns.length; j++) {
						if(selectedColumns[j]==2){
							selectedData = (Boolean) tableau.getValueAt(selectedRow[i], selectedColumns[j]);
							Patient pat=new Patient();
							if (selectedRow[i]>0) {

								if (selectedData==false){
									// idx : cab.getPatientIdx(tableau.getValueAt(selectedRow[i], 1).toString())
									pat=cab.patients.get(cab.getPatientIdx(tableau.getValueAt(selectedRow[i], 1).toString()));
									crtDayOrder++;
									if (cab.getCrtSoignant()>=0){
										pat.addActe(crtDate,cab.soignants.get(cab.getCrtSoignant()),crtDayOrder);
									} else {
										pat.addActe(crtDate,crtDayOrder);
									}
									pat.select();
									System.out.println(crtDate+" selecting : "+pat.toString());
									//									System.out.println("selection patient : "+cab.patients.get(selectedRow[i]-1).isSelected());
									//									System.out.println("selection patient : "+tableau.getValueAt(selectedRow[i], 1).toString());
									//									System.out.println("selection patient idx : "+cab.getPatientIdx(tableau.getValueAt(selectedRow[i], 1).toString()));
									nbPatSelected++;
									cab.setModified(true);
									refreshData(pat);
									refreshListActes();
									


								} else {
									pat=cab.patients.get(cab.getPatientIdx(tableau.getValueAt(selectedRow[i], 1).toString()));
									if (cab.getCrtSoignant()>=0){
										pat.removeActe(crtDate,cab.soignants.get(cab.getCrtSoignant()));
									} else {
										pat.removeActe(crtDate);
									}
									pat.deselect();
									System.out.println("deselecting : "+pat.toString());
									//									System.out.println("selection patient : "+cab.patients.get(selectedRow[i]-1).isSelected());
									nbPatSelected--;
									cab.setModified(true);
									refreshData(pat);
									refreshListActes();
									
								}
							} else {
								if (selectedData==false){
									nbPatSelected=0;
									for (int k=1;k<tableau.getRowCount();k++){
										tableau.setValueAt(true, k, 2);
										pat=cab.patients.get(cab.getPatientIdx(tableau.getValueAt(k, 1).toString()));
										pat.select();
										crtDayOrder++;
										if (cab.getCrtSoignant()>=0){
											pat.addActe(crtDate,cab.soignants.get(cab.getCrtSoignant()),crtDayOrder);
										} else {
											pat.addActe(crtDate,crtDayOrder);
										}
										//										
										//cab.patients.get(k-1).select();
										nbPatSelected++;
										//tableau.setValueAt("Nb Patients Sélectionnés : "+nbPatSelected+"/"+cab.getSize(), 0, 1);

									}
									refreshData(null);
									refreshListActes();

								} else {
									nbPatSelected=0;
									for (int k=1;k<tableau.getRowCount();k++) {
										tableau.setValueAt(false, k, 2);
										pat=cab.patients.get(cab.getPatientIdx(tableau.getValueAt(k, 1).toString()));
										if (cab.getCrtSoignant()>=0){
											pat.removeActe(crtDate,cab.soignants.get(cab.getCrtSoignant()));
										} else {
											pat.removeActe(crtDate);
										}
										pat.deselect();

										//cab.setModified(true);
										//tableau.setValueAt("Nb Patients Sélectionnés : "+nbPatSelected+"/"+cab.getSize(), 0, 1);

									}
									cab.setModified(true);
									refreshData(null);
									refreshListActes();

								}
								
							}

						}

					}
				}
				tableau.clearSelection();
				updateListSelection();
				
			}	
			
		});
		listePanel.add(new JScrollPane(tableau));
		this.getContentPane().add(box, BorderLayout.CENTER);
		saveBtn.addActionListener(new SaveBtnListener());
		clearBtn.addActionListener(new ClearBtnListener());
		expPdfBtn.addActionListener(new ExpPdfBtnListener());
		expXlsBtn.addActionListener(new ListBtnListener());
		//importCsvBtn.addActionListener(new ImpCsvBtnListener());
		leftArrowBtn.addActionListener(new LeftBtnListener()); 
		rightArrowBtn.addActionListener(new RightBtnListener());
		calendarBtn.addActionListener(new CalBtnListener());
		statsBtn.addActionListener(new StatsBtnListener());

		WindowListener exitListener = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (cab.isModified()){
					cab.setModified(false);
					//boolean test=cab.isModified();
					int confirm = JOptionPane.showOptionDialog(null, "Voulez-vous enregistrer vos modifications avant de quitter ?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if (confirm == 0) {
						Date date=new Date();
						File dir=new File("Archives");
						if (!dir.exists()){
							dir.mkdir();
						}
						String fileName="Archives\\archive_";
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm");
						fileName=fileName+sdf.format(date)+".fdb";
						writeArchive(cab,fileName,false);
						File f=new File("archive_crt.fdb");
						if (f.exists()){f.delete();}
						writeArchive(cab,"archive_crt.fdb",false);
					}
				}
				System.exit(0);

			}
		};
		this.addWindowListener(exitListener);
		item1_1.addActionListener(new ImpFdbMenuListener());
		item1_2.addActionListener(new SaveBtnListener());
		item1_3.addActionListener(new ImpCsvBtnListener());
		item1_4.addActionListener(new ListBtnListener());
		item1_5.addActionListener(new ExpPdfBtnListener());
		item1_6.addActionListener(new ExitMenuListener());
		item1_7.addActionListener(new SyncMenuListener());
		item2_1.addActionListener(new ClearAllMenuListener());
		item2_6.addActionListener(new ClearAllActesMenuListener());
		item2_7.addActionListener(new ClearUnusedCotMenuListener());
		item2_2.addActionListener(new ClearSelMenuListener());
		item2_4.addActionListener(new ClearInactMenuListener());
		item2_3.addActionListener(new RecoverPatMenuListener());
		item2_5.addActionListener(new AssignActesMenuListener());
		item3_1.addActionListener(new AboutMenuListener());
		item3_2.addActionListener(new StatsBtnListener());
		item3_3.addActionListener(new DebugListener());
	}

	class RecoverPatMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent arg){
			List<String> listeNoms=new ArrayList<String>();
			for(int i=0;i<cab.patients.size();i++){
				if (!cab.patients.get(i).getVisible()){
					listeNoms.add(cab.patients.get(i).toString());
				}
			}
			RecoveryList rcrList=new RecoveryList(null,"Liste Patients Effaces",true);
			rcrList.setRecoveryInfo(listeNoms);
			List<String> delList=new ArrayList<String>();
			delList=rcrList.showRecoveryList();
			int opt=Integer.valueOf(delList.get(delList.size()-1));
//			System.out.println("mode :"+opt+" patients effaces :");
			if (opt>0){
				for(int i=0;i<delList.size()-1;i++){
					if (opt==1) { 
//						System.out.println("Recovering :"+delList.get(i));
						cab.patients.get(cab.getPatientIdx(delList.get(i))).setVisible(true);
					
					} else if (opt==2) {
						cab.patients.remove(cab.getPatientIdx(delList.get(i)));
					}
				}
				refreshList();
				cab.setModified(true);
				refreshData(null);
			}
		}

	}

	class AssignActesMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent arg){
			assignActesToCrtSoignant();
		}

	}

	class ExitMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent arg){
			if (cab.isModified()){
				cab.setModified(false);
				//boolean test=cab.isModified();
				int confirm = JOptionPane.showOptionDialog(null, "Voulez-vous enregistrer vos modifications avant de quitter ?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (confirm == 0) {
					Date date=new Date();
					File dir=new File("Archives");
					if (!dir.exists()){
						dir.mkdir();
					}
					String fileName="Archives\\archive_";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm");
					fileName=fileName+sdf.format(date)+".fdb";
					writeArchive(cab,fileName,false);
					File f=new File("archive_crt.fdb");
					if (f.exists()){f.delete();}
					writeArchive(cab,"archive_crt.fdb",false);
				}
			}
			System.exit(0);
		}

	}
	class SyncMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent arg){
			
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					
			chooser.setCurrentDirectory(new File("."));
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				String syncPath=chooser.getSelectedFile().getAbsolutePath();
				cab.addSyncPath(syncPath);
				cab.setModified(true);
			}
			
		}

	}
	class SelComboChange implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			//System.out.println("sel:"+soignantCB.getSelectedIndex());
			if (soignantCB.getSelectedIndex()>=0){
				cab.setCrtSoignant(soignantCB.getSelectedIndex());
				crtSoignant=cab.soignants.get(cab.getCrtSoignant()).toString();
				disableControlsOnSoignant();
				updateListSelection();
				refreshData(crtPat);
			}
		}
	}

	public class ButtonEditor extends DefaultCellEditor {

		protected JButton button;
		private ButtonListener bListener = new ButtonListener();

		public ButtonEditor(JCheckBox checkBox) {
			//Par défaut, ce type d'objet travaille avec un JCheckBox
			super(checkBox);
			//On crée Ã  nouveau notre bouton
			button = new JButton();
			button.setOpaque(true);
			//On lui attribue un listener
			button.addActionListener(bListener);
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
			//On définit le numéro de ligne Ã  notre listener
			bListener.setRow(row);
			//Idem pour le numéro de colonne
			bListener.setColumn(column);
			//On passe aussi en paramètre le tableau pour des actions potentielles
			bListener.setTable(table);
			//On réaffecte le libellé au bouton
			//ImageIcon icon=(value==null)?new ImageIcon(""):new ImageIcon(value.toString().substring(6,value.toString().length()));
			button.setIcon((ImageIcon)value);
			//On renvoie le bouton
			return button;
		}

		class ButtonListener implements ActionListener {

			private int column, row;
			private JTable table;
			private int nbre = 0;
			private JButton button;
			public void setColumn(int col){this.column = col;}
			public void setRow(int row){this.row = row;}
			public void setTable(JTable table){this.table = table;}
			public JButton getButton(){return this.button;}

			public void actionPerformed(ActionEvent event) {
				if (row==0) {
					NewPatDialog npd=new NewPatDialog(null,"Nouveau Patient",true);
					Patient newPat=new Patient();
					newPat=npd.showNewPatDialog();
					if(!newPat.toString().equals("pas de patient")){
						//System.out.println("nouveau patient"+newPat.toString());
						cab.addPatient(newPat);
						Object[] newdonnee = new Object[]	{btnIcon1,newPat.toString(),new Boolean(false),btnIcon7};
						((ZModel)tableau.getModel()).addRow(newdonnee);
						cab.setModified(true);
						refreshData(newPat);
					} else {
						//System.out.println("cancelled");
					}


				} else {
					//System.out.println("coucou du bouton : "+row+":"+column+"\n"+cab.patients.get(row-1).toString());
					SimpleDateFormat sdfM = new SimpleDateFormat("MM");
					SimpleDateFormat sdfA = new SimpleDateFormat("yyyy");
					String mois=sdfM.format(crtDate);
					String annee=sdfA.format(crtDate);
					if (column==0){
						if (fp!=null) {
							fp.dispose();
						}

						Patient pat=cab.patients.get(cab.getPatientIdx(tableau.getValueAt(row, 1).toString()));
						//DateJour dj=new DateJour("02/01/2015");
						//Acte tmpAct=new Acte(pat.getDefCotMatin(),dj);
						//tmpAct.setSoignant("Melissa");
						//pat.addActe(tmpAct);

						//printPat(pat);



						fp=new FichePat();
						fp.setFichePatInfo(pat, mois, annee, crtSoignant);
						fp.setVisible(true);
						fp.changeBtn.addActionListener(new ChangeBtnListener());
						fp.addOrdoBtn.addActionListener(new AddOrdoBtnListener());
						fp.delOrdoBtn.addActionListener(new DelOrdoBtnListener());

					}	else {
						if (column==3){
							int idx =cab.getPatientIdx(tableau.getValueAt(row, 1).toString());
							Patient pat=cab.patients.get(idx);
							if ((!pat.hasActe(crtDate, crtSoignant))&&(pat.hasDefCot())){
								JOptionPane.showMessageDialog(null, "Erreur : Pas d'acte defini pour ce patient.\nVeuillez d'abord selectionner le patient\navant de redefinir sa cotation","Erreur",JOptionPane.ERROR_MESSAGE);
							} else {
								DefCotDialog dcd = new DefCotDialog(null, "Cotations par défaut de "+pat.toString(), true);
								DefCotModel dcm=new DefCotModel(pat.getCotList(),pat.getDefCotMatin(),pat.getDefCotMidi(),pat.getDefCotSoir());
								dcd.setDialogInfo(dcm);
								DefCotModel newDefCot=new DefCotModel();
								newDefCot = dcd.showDefCotDialog(); 
								if(!dcm.getCancelled()){
									String soignant="";
									cab.patients.get(idx).setCotList(dcm.getListCot());
									if (cab.getCrtSoignant()>=0){
										soignant=cab.soignants.get(cab.getCrtSoignant());
										cab.patients.get(idx).removeActe(crtDate,soignant);
									} else {
										cab.patients.get(idx).removeActe(crtDate);
									}
									
									if (dcm.getDefault()){
										cab.patients.get(idx).setDefCotMatin(dcm.getDefCotMatin());
										cab.patients.get(idx).setDefCotMidi(dcm.getDefCotMidi());
										cab.patients.get(idx).setDefCotSoir(dcm.getDefCotSoir());
									}

									if (!dcm.getDefCotMatin().toString().equals("")){
										DateJour dj=new DateJour(crtDate,Jour.matin);
										Acte newAct;
										if (soignant.equals("")){
											newAct =new Acte(dcm.getDefCotMatin(),dj);
										} else {
											newAct =new Acte(dcm.getDefCotMatin(),dj,soignant);
										}
										crtDayOrder++;
										newAct.setOrder(crtDayOrder);
										cab.patients.get(idx).addActe(newAct);

									}
									if (!dcm.getDefCotMidi().toString().equals("")){
										DateJour dj=new DateJour(crtDate,Jour.midi);
										Acte newAct;
										if (soignant.equals("")){
											newAct=new Acte(dcm.getDefCotMidi(),dj);
										} else {
											newAct=new Acte(dcm.getDefCotMidi(),dj,soignant);
										}
										crtDayOrder++;
										newAct.setOrder(crtDayOrder);
										cab.patients.get(idx).addActe(newAct);

									}
									if (!dcm.getDefCotSoir().toString().equals("")){
										DateJour dj=new DateJour(crtDate,Jour.soir);
										Acte newAct;
										if (soignant.equals("")){
											newAct=new Acte(dcm.getDefCotSoir(),dj);
										} else {
											newAct=new Acte(dcm.getDefCotSoir(),dj,soignant);
										}
										crtDayOrder++;
										newAct.setOrder(crtDayOrder);
										cab.patients.get(idx).addActe(newAct);

									}
									cab.setModified(true);
									refreshData(pat);
									refreshListActes();
									updateListSelection();

								}
							}
						}
					}
				}
			}
		}
	}
	class AddOrdoBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Patient pat=cab.patients.get(cab.getPatientIdx(fp.pat));
			AddOrdoDialog newOrdoDial=new AddOrdoDialog(null,"Nouvelle Ordonnance pour"+pat.toString(),true);
			
			String patName="";
			if(pat.getCivilite()!=null) patName=pat.getCivilite().toString();
			patName=patName+pat.getNom();
			if((pat.getPrenom()!=null)&&!(pat.getPrenom().equals(""))) patName=patName+pat.getPrenom();
			newOrdoDial.setData(pat.getMedecins(),patName);
			Ordonnance newOrdo=newOrdoDial.showAddOrdo();
			if(!newOrdo.getMotif().equals("cancelled")){
				pat.addOrdo(newOrdo);
				//fp.pat.addOrdo(newOrdo);
				if (!pat.getMedecins().contains(newOrdo.getMedecin())){
					pat.addMedecin(newOrdo.getMedecin());
				}
				fp.refreshOrdoList();
				cab.setModified(true);
			}

		}
	}
	class DelOrdoBtnListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			Patient pat=cab.patients.get(cab.getPatientIdx(fp.pat));
			if (fp.listOrdos.getSelectedRow()>=0){
				String date= fp.listOrdos.getValueAt(fp.listOrdos.getSelectedRow(),0).toString();
				String motif= fp.listOrdos.getValueAt(fp.listOrdos.getSelectedRow(),1).toString();
				if(!date.equals("")){
					Ordonnance ordo=pat.getOrdo(date,motif);
					fp.pat.delOrdo(ordo);
					System.out.println("deleting :"+ordo.toString());
					fp.refreshOrdoList();
					cab.setModified(true);

				}
			}
		}
	}	

	class ChangeBtnListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			NewPatDialog npd=new NewPatDialog(null,"Modifier Patient",true);
			//NewPatDialog SimplyFact.newFichePat=new NewPatDialog(null,"Modifier Patient",true);
			Patient newPat=new Patient();
			Patient oldPat=cab.patients.get(cab.getPatientIdx(fp.pat));
			//oldPat.setNom(pat.getNom());
			//oldPat.setPrenom(pat.getPrenom());
//			oldPat.setNom(pat.getNom());
//			oldPat.setNom(pat.getNom());
//			oldPat.setNom(pat.getNom());
			System.out.println("ancien patient :"+oldPat.toString());
			npd.nomTF.setText(oldPat.getNom());
			npd.prenomTF.setText(oldPat.getPrenom());
			npd.addrTF.setText(oldPat.getAdresse());
			npd.telTF.setText(oldPat.getTel());
			System.out.println("ancien patient :"+oldPat.toString());
			newPat=npd.showNewPatDialog();
			if(!newPat.toString().equals("pas de patient")){
				//pat.setNom(newPat.getNom());
				System.out.println("nouveau patient :"+newPat.toString());
				System.out.println("patient :"+fp.pat.toString()+" "+cab.getPatientIdx(fp.pat));
				oldPat.setCivilite(newPat.getCivilite());
				oldPat.setNom(newPat.getNom());
				oldPat.setPrenom(newPat.getPrenom());
				oldPat.setAdresse(newPat.getAdresse());
				oldPat.setTel(newPat.getTel());
				cab.setModified(true);
				refreshData(oldPat);
				refreshList();
				//fillData(mois,annee);
			} else {
				//System.out.println("cancelled");
			}
			
		}
	}	
	
	class StatsBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (fs!=null) fs.dispose();

			fs=new FicheStats();
			fs.setFicheStatsInfo(cab);
			fs.setVisible(true);

		}
	}	
	class DebugListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			SimpleDateFormat sdfM = new SimpleDateFormat("MM");
			String mois=sdfM.format(crtDate);
			SimpleDateFormat sdfA = new SimpleDateFormat("yyyy");
			String annee=sdfA.format(crtDate);
			System.out.println("Liste:\n------");
			for (int i=0;i<cab.patients.size();i++){
				Patient pat=cab.patients.get(i);
				for (int j=0;j<pat.getActe(mois, annee).size();j++){
					System.out.println(pat.toString()+":"+pat.getActe(mois,annee).get(j)+" ordre : "+pat.getActe(mois,annee).get(j).getOrder());
				}
			}
		}
	}	
	class SaveBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Date date=new Date();
			String fileNameArch="Archives\\archive_";
			String fileName="archive_crt.fdb";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmm");
			//String fileNamePost=fileName+sdf.format(date)+".postModif.fdb";
			//String fileNamePatch=fileName+sdf.format(date)+".patched.fdb";
			fileNameArch=fileNameArch+sdf.format(date)+".fdb";
			File fArch = new File(fileNameArch); 
			File f = new File(fileName); 
			File dir=new File("Archives");
			if (!dir.exists()) {
				dir.mkdir();
			}
			if (!f.exists()) {
				f.delete();
			}
			writeArchive(cab,fileName,false);
			
			if (!fArch.exists()){
				writeArchive(cab,fileNameArch,true);

			} else {
				int option = JOptionPane.showConfirmDialog(null, "Attention :"+fileName+" existe déjà ... Voulez-vous l'écraser ?","Warning",JOptionPane.YES_NO_OPTION);
				if(option == 0){
					writeArchive(cab,fileNameArch,true);
				}
			}
		}
	}	
	class AboutMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			spl.setVersion(version, cab.getVersion());
			spl.showSplash();
		}
	}	
	class ClearBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			boolean hasSelection=false;
			for (int i=0;i<cab.patients.size();i++){
				if (cab.patients.get(i).isSelected()) hasSelection=true;
			}
			if (hasSelection){
				int option = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment effacer les patients selectionnés de la liste ?","Warning",JOptionPane.YES_NO_OPTION);
				if(option == 0){
					clearSelectedList();

				}
			} else {

				int option = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment effacer toutes les données ?","Warning",JOptionPane.YES_NO_OPTION);
				if(option == 0){
					clearList();
					cab.patients.clear();
					cab.setModified(true);
					refreshData(null);
				}

			}

			refreshList();

		}
	}	
	class ClearAllMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			int option = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment effacer toutes les données ?","Warning",JOptionPane.YES_NO_OPTION);
			if(option == 0){
				clearList();
				cab.patients.clear();
				cab.setModified(true);
				refreshData(null);
			}


			refreshList();

		}
	}	
	class ClearAllActesMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			int option = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment effacer tous les actes de la base de données ?","Warning",JOptionPane.YES_NO_OPTION);
			if(option == 0){
				clearAllActes();
				cab.setModified(true);
				
			}


			refreshList();

		}
	}	
	class ClearUnusedCotMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			int option = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment effacer tous les cotations inutiles de chaque patient ?","Warning",JOptionPane.YES_NO_OPTION);
			if(option == 0){
				clearUnusedCot();
				cab.setModified(true);
				
			}


			refreshList();

		}
	}	
	class ClearSelMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			boolean hasSelection=false;
			for (int i=0;i<cab.patients.size();i++){
				if (cab.patients.get(i).isSelected()) hasSelection=true;
			}
			if (hasSelection){
				int option = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment effacer les patients selectionnés de la liste ?","Warning",JOptionPane.YES_NO_OPTION);
				if(option == 0){
					clearSelectedList();

				}
			} else {

				JOptionPane.showMessageDialog(null, "Erreur : Pas de patient selectionné","Erreur",JOptionPane.ERROR_MESSAGE);

			}

			refreshList();

		}
	}	
	class ClearInactMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			int option = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment effacer les patients inactifs de la liste ?","Warning",JOptionPane.YES_NO_OPTION);
			if(option == 0){
				clearInactifList();

			}

			refreshList();

		}
	}	
	class ExpPdfBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			//System.out.println("coucou du bouton exp");
			//System.exit(0);
			SimpleDateFormat sdfM = new SimpleDateFormat("MM");
			String mois=sdfM.format(crtDate);
			SimpleDateFormat sdfA = new SimpleDateFormat("yyyy");
			String annee=sdfA.format(crtDate);
			ArrayList<Patient> patList=new ArrayList<Patient>();
			for(int i=0;i<cab.patients.size();i++){
				if(cab.patients.get(i).hasActe(mois,annee,crtSoignant)){
					patList.add(cab.patients.get(i));
				}
			}
			if (patList.size()>0) {
				PdfWrite doc=new PdfWrite();
				doc.exportPatListPdf(patList,mois,annee,crtSoignant);
			} else {
				JOptionPane.showMessageDialog(null, "Erreur : aucun acte pour le mois de "+mois+"/"+annee,"Erreur",JOptionPane.ERROR_MESSAGE);
			}
		}
	}	
	class ListBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			//cab.writeCsv(crtDate);
			refreshListActes();
			if (fl!=null) fl.dispose();
			fl=new FicheList();
			fl.setVisible(true);
			fl.impListBtn.addActionListener(new ImpCsvBtnListener());
			fl.expListBtn.addActionListener(new ExpListBtnListener());
			fl.downListBtn.addActionListener(new DownListBtnListener());
			fl.upListBtn.addActionListener(new UpListBtnListener());
			fl.setFicheListInfo(listActesJour,0);

		}
	}	
	class DownListBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			int lineSel;
			lineSel=fl.getSelectedLine();
			//System.out.println("Ligne selectionnée :"+lineSel);
			int size=listActesJour.size()-1;
			if ((lineSel==size)||(lineSel<0)){
				System.out.println("Bottom of list");
			} else {
				ActeJour crtAJ=listActesJour.get(lineSel);
				ActeJour nextAJ=listActesJour.get(lineSel+1);
				if ((crtAJ.getSoignant().equals(nextAJ.getSoignant()))&&
						(crtAJ.getDateJour().toString().equals(nextAJ.getDateJour().toString()))
						) {
					
					cab.setActeJourOrder(crtAJ.getDateJour(), crtAJ.getNomPatient(), nextAJ.getOrder());
					cab.setActeJourOrder(nextAJ.getDateJour(), nextAJ.getNomPatient(), crtAJ.getOrder());
					listActesJour.get(lineSel).setOrder(nextAJ.getOrder());
					listActesJour.get(lineSel+1).setOrder(crtAJ.getOrder());
					refreshListActes();
					fl.setSelectedLine(lineSel+1);
				} else {
					System.out.println("Down impossible");
				}
			}
		}
	}
	class UpListBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {

			int lineSel;
			lineSel=fl.getSelectedLine();
			//System.out.println("Ligne selectionnée :"+lineSel);
			if (lineSel<=0){
				System.out.println("top of list");
			} else {
				ActeJour crtAJ=listActesJour.get(lineSel);
				ActeJour prevAJ=listActesJour.get(lineSel-1);
				if ((crtAJ.getSoignant().equals(prevAJ.getSoignant()))&&
						(crtAJ.getDateJour().toString().equals(prevAJ.getDateJour().toString()))
						) {
					
					cab.setActeJourOrder(crtAJ.getDateJour(), crtAJ.getNomPatient(), prevAJ.getOrder());
					cab.setActeJourOrder(prevAJ.getDateJour(), prevAJ.getNomPatient(), crtAJ.getOrder());
					listActesJour.get(lineSel).setOrder(prevAJ.getOrder());
					listActesJour.get(lineSel+1).setOrder(crtAJ.getOrder());
					refreshListActes();
					fl.setSelectedLine(lineSel-1);
				} else {
					System.out.println("up impossible");
				}
			}

		}
	}

	class ExpListBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			writeCsv(crtDate);

		}
	}	
	class ImpCsvBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			//System.out.println("taille liste :"+tableau.getRowCount());
			importCsv();
			refreshList();
			cab.setModified(true);
			refreshData(null);
			refreshSoignant();
			refreshListActes();

		}
	}	
	class ImpFdbMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			int option = JOptionPane.showConfirmDialog(null, "Voulez-vous effacer toutes les données avant import ?","Warning",JOptionPane.YES_NO_CANCEL_OPTION);
			if(option!=2){
				String line="";
				BufferedReader br=null;
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"fichier .fdb", "fdb");
				chooser.setFileFilter(filter);
				chooser.setCurrentDirectory(new File("."));
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					String fileName=chooser.getSelectedFile().getAbsolutePath();
					System.out.println("You chose to open this file: " +fileName );
					if(option == 0){
						clearList();
						cab.patients.clear();

					}
					Cabinet cabTmp=new Cabinet();
					cabTmp=readArchive(fileName);
					Patient pat=new Patient();
					for (int i=0;i<cabTmp.patients.size();i++){
						pat=cabTmp.patients.get(i);
						cab.addPatient(pat);
					}
					String soignant;
					for (int i=0;i<cabTmp.soignants.size();i++){
						soignant=cabTmp.soignants.get(i);
						cab.addSoignant(soignant);
					}
					cab.setVersion(cabTmp.getVersion());
					cab.setModified(true);
					refreshList();
					refreshSoignant();
				}
			}
		}
	}

	class LeftBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(crtDate);
			cal.add(Calendar.DATE, -1);
			crtDate=cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
			dateEntry.setText(sdf.format(crtDate));
			getCrtDayOrder();
			updateListSelection();
			refreshListActes();
		}
	}	
	class RightBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(crtDate);
			cal.add(Calendar.DATE, 1);
			crtDate=cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
			dateEntry.setText(sdf.format(crtDate));
			getCrtDayOrder();
			updateListSelection();
			refreshListActes();


		}
	}	
	class AddSoignantListener  implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			JOptionPane d = new JOptionPane();
			String newSoignant = d.showInputDialog(null,"Prenom Nom du nouveau soignant :","Ajouter un nouveau soignant au cabinet",JOptionPane.OK_CANCEL_OPTION);
			//System.out.println("nouveau soignant :"+newSoignant );
			if(newSoignant!=null){
					String[] str=newSoignant.split(" ");
					String newSoignantMod=Patient.capitalize(newSoignant);
					
					if (str.length>1){
						String nom=str[str.length-1].toUpperCase();
						String prenom=Patient.capitalize(str[0]);
						for (int i=1;i<str.length-1;i++){
							prenom=prenom+"-"+Patient.capitalize(str[i]);
						}
						newSoignantMod=prenom+" "+nom;
					} 
					cab.addSoignant(newSoignantMod);
					refreshSoignant();
					cab.setModified(true);
				
			}
		}
	}	
	class DelSoignantListener  implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(cab.getCrtSoignant()>=0){
				cab.delCrtSoignant();
				cab.setModified(true);
				
				refreshSoignant();
				
			}
		}
	}	
	class OrdoListListener  implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (FO==null){
				FO=new FicheOrdos();
			}
			FO.setVisible(true);
			FO.fillData(cab);
			FO.viewBtn.addActionListener(new ViewBtnListener());
			FO.sendOrdosBtn.addActionListener(new SendBtnListener());
			FO.undoSendBtn.addActionListener(new UndoSendBtnListener());
		}
	}	
	class SendBtnListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			String nom="";
			String dpr="";
			String mot="";
			Patient pat;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
			int countOk=0;
			int countMax=0;
			int idx;
			Ordonnance ordo;
			if (FO.tableOrdos.getSelectedRows().length>=1){
				countMax=FO.tableOrdos.getSelectedRows().length;
				for (int i=0;i<FO.tableOrdos.getSelectedRows().length;i++){
					idx=FO.tableOrdos.getSelectedRows()[i];
					nom=FO.tableOrdos.getValueAt(idx, 0).toString();
					dpr=FO.tableOrdos.getValueAt(idx, 1).toString();
					mot=FO.tableOrdos.getValueAt(idx, 3).toString();
					pat=cab.patients.get(cab.getPatientIdx(nom));
					ordo=pat.getOrdo(dpr, mot);
					String fileName=ordo.getFileName();
					File newDir=new File("Ordos a envoyer");
					if (!newDir.exists()){
						newDir.mkdir();
					}
					newDir=new File("Ordos a envoyer\\Ordos_"+sdf.format(crtDate));
					if (!newDir.exists()){
						newDir.mkdir();
					}
					
					File viewFile=new File(fileName);
					System.out.println("Copying "+fileName+" to Ordos a envoyer\\Ordos_"+sdf.format(crtDate)+"\\"+viewFile.getName());
					File archFile=new File("Ordos a envoyer\\Ordos_"+sdf.format(crtDate)+"\\"+viewFile.getName());
					ordo.send(true);
					if (AddOrdoDialog.copyFile(viewFile, archFile)) {countOk++;}
					
				}
			}
			FO.fillData(cab);
			cab.setModified(true);
			JOptionPane.showMessageDialog(null, countOk+"/"+countMax+" des ordonnances selectionnées ont été copiées dans : \nOrdos a envoyer\\Ordos_"+sdf.format(crtDate),"Information",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	class UndoSendBtnListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			String nom="";
			String dpr="";
			String mot="";
			Patient pat;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
			int countOk=0;
			int countMax=0;
			int idx;
			Ordonnance ordo;
			if (FO.tableOrdos.getSelectedRows().length>=1){
				countMax=FO.tableOrdos.getSelectedRows().length;
				for (int i=0;i<FO.tableOrdos.getSelectedRows().length;i++){
					idx=FO.tableOrdos.getSelectedRows()[i];
					nom=FO.tableOrdos.getValueAt(idx, 0).toString();
					dpr=FO.tableOrdos.getValueAt(idx, 1).toString();
					mot=FO.tableOrdos.getValueAt(idx, 3).toString();
					pat=cab.patients.get(cab.getPatientIdx(nom));
					ordo=pat.getOrdo(dpr, mot);
					ordo.send(false);
					
				}
			}
			FO.fillData(cab);
			cab.setModified(true);
		}
	}

	class ViewBtnListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			String nom="";
			String dpr="";
			String mot="";
			Patient pat;
			int idx;
			Ordonnance ordo;
			if (FO.tableOrdos.getSelectedRows().length>1){
				JOptionPane.showMessageDialog(null, "Erreur : Veuillez ne selectionner qu'une seule ordonnance\npour pouvoir la visualiser","Erreur",JOptionPane.ERROR_MESSAGE);
			} else {
				if (FO.tableOrdos.getSelectedRows().length==1){
					nom=FO.tableOrdos.getValueAt(FO.tableOrdos.getSelectedRow(), 0).toString();
					dpr=FO.tableOrdos.getValueAt(FO.tableOrdos.getSelectedRow(), 1).toString();
					mot=FO.tableOrdos.getValueAt(FO.tableOrdos.getSelectedRow(), 3).toString();
					idx=cab.getPatientIdx(nom);
					pat=cab.patients.get(idx);
					ordo=pat.getOrdo(dpr, mot);
					String fileName=ordo.getFileName();
					File viewFile=new File(fileName);
					try {
						Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " +viewFile.getAbsolutePath());
					} catch (IOException e){
						e.printStackTrace();
					}

					
				}
			}
		}
	}	

	class CalBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			//System.out.println("taille liste :"+tableau.getRowCount());
			CalendarSel cal=new CalendarSel(null,"Choisir la date",true);
			SimpleDateFormat sdfm = new SimpleDateFormat("MM");
			SimpleDateFormat sdfa = new SimpleDateFormat("yyy");
			String mois=sdfm.format(crtDate);
			String annee=sdfa.format(crtDate);
			
			
			cal.setCrtMY(mois,annee);
			Date selDate=cal.showCalendarSel();
			System.out.println("date choisie : "+selDate);
			crtDate=selDate;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
			dateEntry.setText(sdf.format(crtDate));
			getCrtDayOrder();
			refreshListActes();
			updateListSelection();
			

			
		}
	}	


	
	public void initTable(){
		// mod pour SF v1.2.3
//		File dir=new File("Archives");
//		if (!dir.exists()){
//			dir.mkdir();
//			
//			File f = new File("."); 
//			System.out.println("Fichiers : " ); 
//			String[] noms = f.list(); 
//			List<String> oldSortedFiles =new ArrayList<String>();
//			for (int i = 0; noms != null && i < noms.length; i++) {
//				if(noms[i].contains("archive_")) {
//					oldSortedFiles.add(noms[i]);
//				}
//			}
//			File crtFile;
//			for(int i=0;i<oldSortedFiles.size();i++) {
//				System.out.println(i+":moving "+oldSortedFiles.get(i)+" to :Archives\\"+oldSortedFiles.get(i));
//				crtFile=new File(oldSortedFiles.get(i));
//				crtFile.renameTo(new File("Archives\\"+oldSortedFiles.get(i)));
//				
//			}
//		}
		//
		File f = new File("."); 
		System.out.println("Fichiers : " ); 
		String[] noms = f.list(); 
		List<String> sortedFiles =new ArrayList<String>();
		for (int i = 0; noms != null && i < noms.length; i++) {
			if(noms[i].contains("archive")) {
				sortedFiles.add(noms[i]);
			}
		}
		int last=-1;
		for(int i=0;i<sortedFiles.size();i++) {
			System.out.println(i+":"+sortedFiles.get(i)); 
			last=i;
		}
		if (last>=0) {
			String fileName=sortedFiles.get(last); 

			cab=readArchive(fileName);
			
			synchronize(fileName);
			nbPatSelected=0;
			Date date=new Date();

			
			File dir=new File("Archives");
			if (!dir.exists()){
				dir.mkdir();
			}
			
			fileName="Archives\\archive_";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			fileName=fileName+sdf.format(date)+".avantModif.fdb";
			f = new File(fileName); 
			if (!f.exists()){
				writeArchive(cab,fileName,false);

			}
			//System.out.println(cab);
			refreshList();
			System.out.println("Soignant selectionné :"+cab.getCrtSoignant());

			refreshSoignant();
			getCrtDayOrder();
			//updateListSelection();
		}

	}
	public void synchronize(String fileName){
//		mise a jour db pour version 1.2
		File dir=new File("Archives");
		if (!dir.exists()){
			dir.mkdir();
		}
		System.out.println("Database version :"+cab.version);
		if (cab.getVersion()==1.1){
			cab.setVersion(1.2);
			try {
				cab.addSyncPath("");
			} catch (NullPointerException e) {
				Cabinet cabTmp=new Cabinet();
				Patient patTmp;
				for (int i=0;i<cab.patients.size();i++){
					patTmp=cab.patients.get(i);
					patTmp.setPrenom(cab.patients.get(i).getPrenom());
					cabTmp.addPatient(patTmp);
				}
				cabTmp.setVersion(1.2);
				cabTmp.setModified(true);
				
				cab=cabTmp;
				cab.addSyncPath("");
				cabTmp=null;
			}
			
			
			
			Date date=new Date();
			String fileNameOut="Archives\\archive_";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			fileNameOut=fileNameOut+sdf.format(date)+".patched.fdb";
			writeArchive(cab,fileNameOut,false);
			File f=new File("archive_crt.fdb");
			if (f.exists()){f.delete();}
			writeArchive(cab,"archive_crt.fdb",false);
			JOptionPane.showMessageDialog(null, "Base de donnée mise à jour correctement");
			
		}
		
		if (cab.getVersion()==1.2){
			cab.setVersion(1.3);

			
			File f = new File("."); 
			System.out.println("Fichiers : " ); 
			String[] noms = f.list(); 
			List<String> sortedFiles =new ArrayList<String>();
			for (int i = 0; noms != null && i < noms.length; i++) {
				if(noms[i].contains("archive_")) {
					sortedFiles.add(noms[i]);
				}
			}
			for(int i=0;i<sortedFiles.size();i++) {
				File fToMove=new File(sortedFiles.get(i));
				File fToMoveTo=new File("Archives\\"+sortedFiles.get(i));
				System.out.println("Moving :"+sortedFiles.get(i));
				fToMove.renameTo(fToMoveTo);
			}


			Cabinet cabTmp=new Cabinet();
			Patient patTmp;
			for (int i=0;i<cab.patients.size();i++){
				patTmp=cab.patients.get(i);
				patTmp.setPrenom(cab.patients.get(i).getPrenom());
				patTmp.setNom(cab.patients.get(i).getNom());
				cabTmp.addPatient(patTmp);
				//System.out.println("adding patient :"+patTmp);
			}
			cabTmp.setVersion(1.3);
			cabTmp.setModified(true);

			cab=cabTmp;
			cab.addSyncPath("");
			cab.addSoignant("Isabelle TREDILLE");
			cab.addSoignant("Mélissa THEVENIAU");
			cab.setCrtSoignant(0);
			cabTmp=null;


			Date date=new Date();
			String fileNameOut="Archives\\archive_";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			fileNameOut=fileNameOut+sdf.format(date)+".patched.fdb";
			writeArchive(cab,fileNameOut,false);
			
			
			
			File fPatch=new File("archive_crt.fdb");
			if (fPatch.exists()){fPatch.delete();}
			writeArchive(cab,"archive_crt.fdb",false);
			JOptionPane.showMessageDialog(null, "Base de donnée mise à jour correctement (-> v1.3)");
			
		}
		if (cab.getVersion()==1.3){
			Cabinet cabTmp=new Cabinet();
			Patient patTmp;
			for (int i=0;i<cab.patients.size();i++){
				patTmp=new Patient();
				patTmp=cab.patients.get(i);
				patTmp.setOrdos(new ArrayList<Ordonnance>());
				patTmp.setMedecins(new ArrayList<String>());
				cabTmp.addPatient(patTmp);
				//System.out.println("adding patient :"+patTmp);
			}
			for (int i=0;i<cab.soignants.size();i++){
				cabTmp.addSoignant(cab.soignants.get(i));
			}
			cabTmp.setCrtSoignant(cab.getCrtSoignant());
			cabTmp.setVersion(1.4);
			cabTmp.setModified(true);
			cabTmp.addSyncPath(cab.getSyncPath());

			cab=cabTmp;
			cabTmp=null;


			Date date=new Date();
			String fileNameOut="Archives\\archive_";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			fileNameOut=fileNameOut+sdf.format(date)+".patched.fdb";
			writeArchive(cab,fileNameOut,false);
			
			
			
			File fPatch=new File("archive_crt.fdb");
			if (fPatch.exists()){fPatch.delete();}
			writeArchive(cab,"archive_crt.fdb",false);
			JOptionPane.showMessageDialog(null, "Base de donnée mise à jour correctement (-> v1.4)");
		}
		String path=cab.getSyncPath();
		UidSyncPath UID=new UidSyncPath();
		System.out.println("Sync path of this machine ("+UID.getUID()+"): "+path);
		checkOrdoList();
		
	}
	public void checkOrdoList(){
		System.out.println("Checking Validité Ordonnances");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Patient pat;
		Ordonnance ordo;
		for (int i=0;i<cab.patients.size();i++){
			pat=cab.patients.get(i);
			for (int j=0;j<pat.getOrdos().size();j++){
				ordo=pat.getOrdos().get(j);
				
				if(!ordo.isArchived()){
					System.out.println(crtDate.toString()+" " +ordo.toString()+" "+ordo.getDateFin().compareTo(crtDate));
					if(ordo.getDateFin().compareTo(crtDate)<0){
						
						pat.archiveOrdo(ordo);

						cab.setModified(true);

					}
				}
			}
		}
	}
	public void refreshList(){
//		mise a jour db pour version 1.1
//		boolean allListNotVisible=true;
//		try {
//			double dbVersion=cab.getVersion();
//		} catch (NullPointerException e) {
//			Cabinet cabTmp=new Cabinet();
//			cabTmp=cab;
//			cabTmp=null;
//			cab.setVersion(version);
//			cab.setModified(false);
//			if (cab.getVersion()!=version) {
//				JOptionPane.showMessageDialog(null, "Erreur dans la mise a jour");
//			} else {
//				for(int i=0;i<cab.patients.size();i++){
//					cab.patients.get(i).setVisible(true);
//				}
//				Date date=new Date();
//				String fileName="archive_";
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//				fileName=fileName+sdf.format(date)+".patched.fdb";
//				writeArchive(cab,fileName,false);
//				JOptionPane.showMessageDialog(null, "Base de donnée mise Ã  jour correctement");
//
//			}
//		}
		
		clearList();
		for(int i=0;i<cab.patients.size();i++){
			if (cab.patients.get(i).getVisible()){
				Object[] donnee = new Object[]	{btnIcon1,cab.patients.get(i),new Boolean(false),btnIcon7};
				((ZModel)tableau.getModel()).addRow(donnee);
			}
		}
		System.out.println("import "+tableau.getRowCount()+" lignes");
		updateListSelection();
		
	}
	public void refreshListActes(){
		ActeJour act;
		Patient pat;
		String dupStr;
		boolean duplicate=false;
		List<Acte> listAct=new ArrayList<Acte>();
		List<String> listDup=new ArrayList<String>();
		this.listActesJour.clear();
		for(int i=0;i<this.cab.patients.size();i++){
			pat=this.cab.patients.get(i);
			listAct=pat.getActe(crtDate);
			for(int j=0;j<listAct.size();j++){
				dupStr=pat.toString()+";"+listAct.get(j).dateJour.getJour();
				if(listDup.contains(dupStr)){
					duplicate=true;
				} else {
					listDup.add(dupStr);
					duplicate=false;
				}
				if (pat.getCivilite()!=null) {
					addListActes(pat.getCivilite()+" "+pat.getNom(),pat.getPrenom(),listAct.get(j),duplicate);
				} else {
					addListActes(pat.getNom(),pat.getPrenom(),listAct.get(j),duplicate);
				}
				
			}
		}
		for(int i=0;i<this.listActesJour.size();i++){
			act=this.listActesJour.get(i);
			System.out.println(act.getSoignant()+" "+act.getDateJour().getJour()+" "+act.getNomPatient()+" "+act.getCotation()+" "+act.getDuplicate());
		}
		if (fl!=null) fl.setFicheListInfo(listActesJour,0);
		
	}
	public void addListActes(String nomPatient,String prenom,Acte act,boolean duplicate){
		String soignant =act.getSoignant();
		DateJour jour=act.getDateJour();
		String cot=act.getCotation().toString();
		int order=act.getOrder();
		ActeJour aj=new ActeJour(jour,soignant,nomPatient,prenom,cot,order);
		if (duplicate){aj.setDuplicate("  !");}
		int idx=0;
		for(int i=0;(i<this.listActesJour.size())&&(this.listActesJour.get(i).getSoignant().compareTo(soignant)<0);i++){idx++;}
		for(int i=idx;(
				i<this.listActesJour.size())&&
				(this.listActesJour.get(i).getSoignant().equals(soignant))&&
				(this.listActesJour.get(i).getDateJour().toString().compareTo(jour.toString())<0);i++){idx++;}
		for(int i=idx;(i<this.listActesJour.size())&&
				(this.listActesJour.get(i).getSoignant().equals(soignant))&&
				(this.listActesJour.get(i).getDateJour().toString().equals(jour.toString()))&&
				(this.listActesJour.get(i).getOrder()<order);i++){idx++;}
		
		this.listActesJour.add(idx,aj);
		
	}
	
	public void refreshSoignant(){
		int prevSelSgt=cab.getCrtSoignant();

		soignantCB.removeAllItems();
		for(int i=0;i<cab.soignants.size();i++){
			soignantCB.addItem(cab.soignants.get(i).toString());
		}
		
		cab.setCrtSoignant(prevSelSgt);
		if (cab.getCrtSoignant()>=0){
			soignantCB.setSelectedIndex(cab.getCrtSoignant());
		}
		if (cab.getCrtSoignant()>=0) {
			this.crtSoignant=cab.soignants.get(cab.getCrtSoignant()).toString();
			
		} else {
			this.crtSoignant="";
			
		}
		disableControlsOnSoignant();
	}
	public void disableControlsOnSoignant(){
		if ((this.crtSoignant.equals("Tous"))||(this.crtSoignant.equals(""))){
			tableau.setEnabled(false);
			if (this.crtSoignant.equals("")){statsBtn.setEnabled(false);}
			expPdfBtn.setEnabled(false);
			clearBtn.setEnabled(false);
			expXlsBtn.setEnabled(false);
			item1_4.setEnabled(false);
			item1_5.setEnabled(false);
			item2_1.setEnabled(false);
			item2_2.setEnabled(false);
			item2_3.setEnabled(false);
			item2_4.setEnabled(false);
			item2_5.setEnabled(false);
			item2_6.setEnabled(false);
			
		} else {
			tableau.setEnabled(true);
			statsBtn.setEnabled(true);
			expPdfBtn.setEnabled(true);
			clearBtn.setEnabled(true);
			expXlsBtn.setEnabled(true);
			item1_4.setEnabled(true);
			item1_5.setEnabled(true);
			item2_1.setEnabled(true);
			item2_2.setEnabled(true);
			item2_3.setEnabled(true);
			item2_4.setEnabled(true);
			item2_5.setEnabled(true);
			item2_6.setEnabled(true);
			
		}
		
	}
	public void clearList(){
		//System.out.println("taille liste :"+tableau.getRowCount());
		((ZModel)tableau.getModel()).clear();	
		//cab.patients.clear();
		nbPatSelected=0;
		//System.out.println("taille liste :"+tableau.getRowCount());



	}
	public void clearAllActes(){
		int nb=0;
		for(int i=0;i<cab.patients.size();i++){
			Patient pat=cab.patients.get(i);
			pat.clearAllActes();
		}
		refreshData(null);


	}
	public void clearUnusedCot(){
		int nb=0;
		for(int i=0;i<cab.patients.size();i++){
			Patient pat=cab.patients.get(i);
			pat.clearUnusedCot();
		}
		refreshData(null);


	}
	public void clearSelectedList(){
		Patient pat =new Patient();
		nbPatSelected=0;

		for (int k=1;k<tableau.getRowCount();k++) {
			tableau.setValueAt(false, k, 2);
			pat=cab.patients.get(cab.getPatientIdx(tableau.getValueAt(k, 1).toString()));
			if (pat.isSelected()){
				if (cab.getCrtSoignant()>=0){
					pat.removeActe(crtDate,cab.soignants.get(cab.getCrtSoignant()));
				} else {
					pat.removeActe(crtDate);
				}
				pat.deselect();
				pat.setVisible(false);
			}


		}
		cab.setModified(true);
		refreshData(null);

	}
	public void clearInactifList(){
		Patient pat =new Patient();
		nbPatSelected=0;

		for (int k=1;k<tableau.getRowCount();k++) {
			tableau.setValueAt(false, k, 2);
			pat=cab.patients.get(cab.getPatientIdx(tableau.getValueAt(k, 1).toString()));
			if (!pat.hasActe()){
				if (cab.getCrtSoignant()>=0){
					pat.removeActe(crtDate,cab.soignants.get(cab.getCrtSoignant()));
				} else {
					pat.removeActe(crtDate);
				}
				pat.deselect();
				pat.setVisible(false);
			}


		}
		cab.setModified(true);
		refreshData(null);

	}
	public void updateListSelection(){
		//System.out.println("import "+tableau.getRowCount()+" lignes");
		//System.out.println("1er element : "+tableau.getValueAt(1, 1));
		Patient pat;
		nbPatSelected=0;
		int idx;
		String patSearch="";
		for(int i=1;i<tableau.getRowCount();i++){
			patSearch=tableau.getValueAt(i, 1).toString();
			idx=cab.getPatientIdx(patSearch);
			if (idx<0){
				System.out.println("can't find :\""+tableau.getValueAt(i, 1).toString()+"\"");
			}
			idx=cab.getPatientIdx(patSearch);
			pat=cab.patients.get(cab.getPatientIdx(tableau.getValueAt(i, 1).toString()));
			if (cab.crtSoignant>=0){
				String soignant=cab.soignants.get(cab.crtSoignant);
				if (pat.hasActe(crtDate,soignant)){
					nbPatSelected++;
					tableau.setValueAt(true, i, 2);
					pat.select();

				} else {
					tableau.setValueAt(false, i, 2);
					pat.deselect();
				}
			} else {
				if (pat.hasActe(crtDate)){
					nbPatSelected++;
					tableau.setValueAt(true, i, 2);
					pat.select();

				} else {
					tableau.setValueAt(false, i, 2);
					pat.deselect();
				}
			}
			
		}
		tableau.setValueAt("Nb Patients Sélectionnés : "+nbPatSelected+"/"+cab.getSize(), 0, 1);
	}

	public void refreshData(Patient pat){
		SimpleDateFormat sdfM = new SimpleDateFormat("MM");
		SimpleDateFormat sdfA = new SimpleDateFormat("yyyy");
		String mois=sdfM.format(crtDate);
		String annee=sdfA.format(crtDate);
		
		tableau.setValueAt("Nb Patients Sélectionnés : "+nbPatSelected+"/"+cab.getSize(), 0, 1);
		if (fp!=null) {
			crtPat=pat;
			fp.setFichePatInfo(pat, mois, annee, crtSoignant);
		}
	}


	public static void main(String[] args){
		SimplyFact fen = new SimplyFact();
		fen.setVisible(true);
		//test();
	}	
	public static void writeArchive(Cabinet cab,String fileName,Boolean verbose){

		cab.setModified(false);

		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(
					new BufferedOutputStream(
							new FileOutputStream(
									new File(fileName))));
			oos.writeObject(cab);

			oos.close();
			if (verbose)
				JOptionPane.showMessageDialog(null, "Archive : "+fileName+" exportée avec succès");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString(),"Erreur",JOptionPane.ERROR_MESSAGE);

		} 

	}
	public static Cabinet readArchive(String fileName){
		//SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		//fileName=fileName+sdf.format(date)+".fdb";

		Cabinet cab=new Cabinet();
		cab.setModified(false);
		System.out.println("Reading : "+fileName);
		ObjectInputStream ois;

		try {
			ois = new ObjectInputStream(
					new BufferedInputStream(
							new FileInputStream(
									new File(fileName))));
			boolean eof=true;
			while(eof){
				try {
					cab=(Cabinet)ois.readObject();
				} catch (EOFException eofe) {eof=false;}
			}
			ois.close();
		} catch (FileNotFoundException e) {
			System.out.println("Aucune archive !");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e ){
			e.printStackTrace();
		} 
		return cab;



	}
	public static void printPat(Patient pat){
		System.out.println(pat);


		List<Acte> f=pat.getActe("01","2015");
		System.out.println("actes du 01/2015 :");
		for (int i=0;i<f.size();i++){
			System.out.println(f.get(i));
		}
		String mois = "01";
		String annee = "2015";
		System.out.println("Cotations du mois "+mois);
		CotationsMois cots=pat.getCotListMois(mois, annee);

		for (int i=0;i<cots.getCotStr().size();i++){
			System.out.println(i+":"+cots.getCotStr().get(i));
		}
		System.out.println("Cotation par defaut");
		pat.sortDefCot();
		for(int i=1;i<32;i++) {
			System.out.println(i+" "+cots.getCotDuJourMatin(i)+" "+cots.getCotDuJourMidi(i)+" "+cots.getCotDuJourSoir(i));
		}

	}
	
	public void assignActesToCrtSoignant(){
		if (cab.getCrtSoignant()<0) {
			JOptionPane.showMessageDialog(null, "Pas de soignant défini","Erreur",JOptionPane.ERROR_MESSAGE);
		} else {
			int confirm = JOptionPane.showOptionDialog(null, "Voulez-vous assigner tous les actes de la base de données a "+soignantCB.getSelectedItem().toString()+" ?", "Assignement Actes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (confirm == 0) {
				for (int i=0;i<cab.patients.size();i++){
					Patient pat=cab.patients.get(i);
					for(int j=0;j<pat.getActe().size();j++){
						Acte act=pat.getActe().get(j);
						act.setSoignant(soignantCB.getSelectedItem().toString());
						System.out.println(act);
					}
				}
				cab.setModified(true);
			}
		}
	}
	
	public void getCrtDayOrder(){
		int order = 0;
		List<Acte> dayListAct=cab.getActesJour(crtDate);
		Acte act;
		for(int i=0;i<dayListAct.size();i++){
			act=dayListAct.get(i);
			if(act.getOrder()>order){
				order=act.getOrder();
			}
		}
		this.crtDayOrder=order;
		
		System.out.println("Top Order du jour :"+crtDayOrder);
	}
	
	public void writeCsv(Date crtDate){
		//Date crtDate=new Date();
		System.out.println("exporting liste : ");
		
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
			line="Soignant;mMS;Nom;Prenom;Cotation"+System.getProperty("line.separator");
			bw.write(line);
			bwTxt.write("Listes du :"+sdf2.format(crtDate)+System.getProperty("line.separator"));
			
			String soignant="";
			ActeJour aj;
			for (int i=0;i<listActesJour.size();i++){
				aj=listActesJour.get(i);
				if(!aj.getSoignant().equals(soignant)){
					soignant=aj.getSoignant();
					line=System.getProperty("line.separator")+"Liste pour : "+soignant+System.getProperty("line.separator");
					bwTxt.write(line);
				}
				line=aj.getDateJour().getJour()+"\t"+aj.getNomPatient()+" "+aj.getPrenom()+"\t\t"+aj.getCotation()+System.getProperty("line.separator");
				bwTxt.write(line);
				line=aj.getSoignant()+";"+aj.getDateJour().getJour()+";"+aj.getNomPatient()+";"+aj.getPrenom()+";"+aj.getCotation()+System.getProperty("line.separator");
				bw.write(line);
			}
			JOptionPane.showMessageDialog(null, "Listes : "+fileNameTxt+","+fileName+" exportées avec succès");


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
			"fichier .csv", "csv");
	chooser.setFileFilter(filter);
	chooser.setCurrentDirectory(new File(".\\Listes"));
	int returnVal = chooser.showOpenDialog(null);
	if(returnVal == JFileChooser.APPROVE_OPTION) {
		String fileName=chooser.getSelectedFile().getAbsolutePath();
		//System.out.println("You chose to open this file: " +fileName );
		try {
			//patients.clear();
			br = new BufferedReader(new FileReader(fileName));

			String soignant="";
			String jourStr;
			String patStr;
			String prenom;
			Cotation cot;
			int initOrder=crtDayOrder;
			while ((line = br.readLine()) != null) {
				if(line.contains("Date")){
					String[] lineSpl=line.split(";");
					String dateStr=lineSpl[lineSpl.length-1].trim();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					try {
						crtDate=sdf.parse(dateStr);
						dateEntry.setText(sdf.format(crtDate));
						getCrtDayOrder();
						refreshListActes();

					} catch (ParseException e) {
						e.printStackTrace();
					}

				} else if(!line.contains("Soignant")){
					String[] lineSpl=line.split(";");
					soignant=lineSpl[0];
					if(!cab.soignants.contains(soignant)){
						cab.addSoignant(soignant);
						refreshSoignant();
					}
					jourStr=lineSpl[1];
					patStr=lineSpl[2];
					prenom=lineSpl[3];
					Jour jour=Jour.matin;
					cot=new Cotation(lineSpl[4]);
					if (jourStr.equals("midi")){
						jour=Jour.midi;
					} else if (jourStr.equals("soir")){
						jour=Jour.soir;
					} 
					DateJour dj=new DateJour(crtDate,jour);
					Acte act=new Acte(cot,dj,soignant);
					initOrder++;
					act.setOrder(initOrder);
					int idx=-1;
					if (!prenom.equals("")){
						idx=cab.getPatientIdx(patStr+" "+prenom);
					} else {
						idx=cab.getPatientIdx(patStr);
					}
					
					if(idx<0) {
						Patient pat=new Patient(patStr,prenom);
						pat.addCotation(cot);
						pat.addActe(act);
						cab.addPatient(pat);
						// ajout cot par defaut selon jour
					} else {
						Patient pat=cab.patients.get(idx);
						pat.addCotation(cot);
						if (!pat.hasActe(act)){
							pat.addActe(act);
						}
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
					refreshListActes();
					updateListSelection();
					JOptionPane.showMessageDialog(null,"Liste Importee avec succes");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

}
