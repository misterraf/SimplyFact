package com.rt.simplyFact;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.table.TableRowSorter;


public class FichePat extends JFrame {
	private JLabel lbNom =new JLabel();
	private JLabel lbTel =new JLabel();
	private JLabel lbAddr =new JLabel();
	private JLabel label4 =new JLabel();
	private JLabel label5 =new JLabel();
	private JLabel label6 =new JLabel();
	private JLabel label7 =new JLabel();
	private JLabel label8 =new JLabel();
	private String mois;
	private String annee;
	private String soignant;
	protected Patient pat;
	protected JTable listOrdos;
	private JTable acteTable1;
	private JTable acteTable2;
	private DefaultListModel dataList2=new DefaultListModel();
	private ImageIcon btnIcon1 = new ImageIcon(getClass().getResource("/close.png"));
	private ImageIcon btnIcon2 = new ImageIcon(getClass().getResource("/exportPDF.png"));
	private ImageIcon btnIcon3 = new ImageIcon(getClass().getResource("/File-New-icon.png"));
	private ImageIcon btnIcon4 = new ImageIcon(getClass().getResource("/edit-icon.png"));
	private ImageIcon btnIcon8 = new ImageIcon(getClass().getResource("/File-Delete-icon.png"));
	private ImageIcon btnIcon7 = new ImageIcon(getClass().getResource("/Document-Preview-icon.png"));
	private JButton closeBtn = new JButton("Fermer",btnIcon1);
	private JButton expPdfBtn = new JButton("Export PDF",btnIcon2);
	protected JButton addOrdoBtn = new JButton(btnIcon3);
	protected JButton delOrdoBtn = new JButton(btnIcon8);
	private JButton viewOrdoBtn = new JButton(btnIcon7);
	protected JButton changeBtn = new JButton("Modifier",btnIcon4);
	private DefaultListModel dataList=new DefaultListModel();
	private JComboBox moisList = new JComboBox();
	private JComboBox anneeList = new JComboBox();
	
	public FichePat() {
		
		this.setTitle("Fiche Patient");
		this.setSize(500,750);
		this.setLocation(550,100);

		//this.setDefaultCloseOperation();
		JPanel pan=new JPanel(new BorderLayout());
		Box box=new Box(BoxLayout.Y_AXIS);
		JPanel panMid=new JPanel(new BorderLayout());
		GridLayout civGrid=new GridLayout(1,2);
		civGrid.setVgap(5);

		JPanel panCivil=new JPanel(civGrid);

		JPanel panCot=new JPanel(new BorderLayout());
		JPanel panOrdos=new JPanel(new BorderLayout());
		Box boxCot=new Box(BoxLayout.Y_AXIS);
		panCot.add(boxCot,BorderLayout.CENTER);
		JPanel panActes=new JPanel(new BorderLayout());
		pan.add(panMid,BorderLayout.CENTER);
		panMid.setBorder(BorderFactory.createTitledBorder("Fiche Patient"));
		panCivil.setBorder(BorderFactory.createTitledBorder("Etat Civil"));
		panOrdos.setBorder(BorderFactory.createTitledBorder("Ordonnances en cours"));
		panCot.setBorder(BorderFactory.createTitledBorder("Cotations"));
		panActes.setBorder(BorderFactory.createTitledBorder("Actes"));
		Box boxAct=new Box(BoxLayout.Y_AXIS);
		panActes.add(boxAct,BorderLayout.CENTER);
		panMid.add(box);
		box.add(panCivil);
		box.add(panOrdos);
		box.add(panCot);
		box.add(panActes);
		//pan.setBackground(Color.MAGENTA);
		lbNom.setText("Nom : ");
		lbAddr.setText("Adresse : ");
		lbTel.setText("Tél : ");
		Box civBoxLeft=new Box(BoxLayout.Y_AXIS);
		Box civBoxRight=new Box(BoxLayout.Y_AXIS);
		panCivil.add(civBoxLeft);
		panCivil.add(civBoxRight);
		civBoxLeft.add(lbNom);
		civBoxLeft.add(lbTel);
		civBoxLeft.add(lbAddr);
		civBoxRight.add(changeBtn);

		label4.setText("Cotation Matin par defaut :");
		boxCot.add(label4);
		label5.setText("Cotation Midi par defaut :");
		boxCot.add(label5);
		label6.setText("Cotation Soir par defaut :");
		boxCot.add(label6);
//		dataList.clear();
//		for(int i=0;i<pat.getCotList().size();i++){
//			dataList.addElement(pat.getCotList().get(i).toString());
//		}
		JList<String> myList = new JList<String>(dataList);
		JScrollPane js=new JScrollPane(myList);
		js.setBorder(BorderFactory.createTitledBorder("Liste Cotations"));
		js.setPreferredSize(new Dimension(400,400));
		boxCot.add(js);
		Date date=new Date();
		SimpleDateFormat sdfA = new SimpleDateFormat("yyyy");
		int crtY=Integer.parseInt(sdfA.format(date));
		for(int i=0;i<crtY-2000;i++){
			anneeList.addItem(String.valueOf(crtY-i));
			
		}

		//Create the combo box, select item at index 4.
		//Indices start at 0, so 4 specifies the pig.
		ComboMoisListener clm=new ComboMoisListener();
		ComboAnneeListener cla=new ComboAnneeListener();
		for(int i=1;i<13;i++){
			if (i<10){
				moisList.addItem("0"+String.valueOf(i));
			} else {
				moisList.addItem(String.valueOf(i));
			}
			
		}
		
		moisList.addActionListener(clm);
		anneeList.addActionListener(cla);
		label7.setText("Mois : ");
		label8.setText("Année : ");
		JPanel acteDatePanel=new JPanel();
		panActes.add(acteDatePanel,BorderLayout.NORTH);

		acteDatePanel.add(label7);
		acteDatePanel.add(moisList);
		acteDatePanel.add(label8);
		acteDatePanel.add(anneeList);

		GridLayout acteGrid=new GridLayout(3,1);
		JPanel acteCotPanel=new JPanel(acteGrid);




		JList myList2 = new JList<String>(dataList2);
		JScrollPane js2=new JScrollPane(myList2);
		js2.setBorder(BorderFactory.createTitledBorder("Cotations du Mois"));
		js2.setPreferredSize(new Dimension(400,400));
		acteCotPanel.add(js2);


		//		JLabel[] jl=new JLabel[cots.getCotStr().size()];
		//		for (int i=0;i<cots.getCotStr().size();i++){
		//			jl[i]=new JLabel();
		//			jl[i].setText(cots.getCotStr().get(i));
		//			acteCotBox.add(jl[i]);
		//		}
		//		
		Object[][] data1=new Object[3][17];
		data1[0][0]="Matin";
		data1[1][0]="Midi";
		data1[2][0]="Soir";
		for(int i=0;i<3;i++){
			for (int j=1;j<16;j++){
				data1[i][j]=false;
			}
			data1[i][16]="";
		}
		Object[][] data2=new Object[3][17];
		data2[0][0]="Matin";
		data2[1][0]="Midi";
		data2[2][0]="Soir";
		for(int i=0;i<3;i++){
			for (int j=1;j<17;j++){
				data2[i][j]=false;
			}
		}
		String title1[] = new String[17];
		title1[0]="Jour";
		for(int i=1;i<16;i++){
			if(i<10){
				title1[i]="0"+String.valueOf(i);
			} else {
				title1[i]=String.valueOf(i);
			}
		}
		String title2[] = new String[17];
		title2[0]="Jour";
		for(int i=1;i<17;i++){
			title2[i]=String.valueOf(i+15);
		}
		ActTableModel model1=new ActTableModel(data1,title1);
		acteTable1 = new JTable(model1);
		acteTable1.getColumn("Jour").setPreferredWidth(100);
		acteCotPanel.add(new JScrollPane(acteTable1));

		ActTableModel model2=new ActTableModel(data2,title2);
		acteTable2 = new JTable(model2);
		acteTable2.getColumn("Jour").setPreferredWidth(100);
		acteCotPanel.add(new JScrollPane(acteTable2));
		acteTable1.setCellSelectionEnabled(false);
		acteTable2.setCellSelectionEnabled(false);
		panActes.add(acteDatePanel,BorderLayout.NORTH);
		panActes.add(acteCotPanel,BorderLayout.CENTER);

		String ordoTitle[]={"Date Presc.","Motif","Médecin"};
		Object[][] ordoData=new Object[1][3];
		for (int i=0;i<3;i++){
			ordoData[0][i]="";
		}
		
		OrdoTableModel ordoModel=new OrdoTableModel(ordoData,ordoTitle);
		listOrdos=new JTable(ordoModel);
		JScrollPane os=new JScrollPane(listOrdos);
		RowSorter<OrdoTableModel> sorter = new TableRowSorter<OrdoTableModel>(ordoModel);
		listOrdos.setRowSorter(sorter);
		listOrdos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		os.setPreferredSize(new Dimension(400,350));
		panOrdos.add(os);
		
		//fillData(this.mois,this.annee);
		JPanel btnPanel=new JPanel();
		addOrdoBtn.setToolTipText("Ajouter une ordonnance");
		delOrdoBtn.setToolTipText("Effacer l'ordonnance selectionnée");
		viewOrdoBtn.setToolTipText("Voir l'ordonnance selectionnée");
		expPdfBtn.setToolTipText("Exporter la facturation (pdf) de ce patient");
		btnPanel.add(addOrdoBtn);
		btnPanel.add(delOrdoBtn);
		btnPanel.add(viewOrdoBtn);
		btnPanel.add(expPdfBtn);
		btnPanel.add(closeBtn);
	
		//delOrdoBtn.setEnabled(false);
		//viewOrdoBtn.setEnabled(false);
		
		pan.add(btnPanel,BorderLayout.SOUTH);
		closeBtn.addActionListener(new CloseBtnListener());
		viewOrdoBtn.addActionListener(new ViewOrdoBtnListener());
		expPdfBtn.addActionListener(new ExpPdfBtnListener());
		//changeBtn.addActionListener(new changeBtnListener());
		setContentPane(pan);

		//this.setVisible(true);                             
	}
	public void refreshOrdoList(){
		((OrdoTableModel)listOrdos.getModel()).clear();
		Ordonnance ordo;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
		for(int i=0;i<pat.getOrdos().size();i++){
			ordo=pat.getOrdos().get(i);
			if (!ordo.isArchived()){
			Object[] ordoData=new Object[3];
			ordoData[0]=sdf.format(ordo.getDatePresc());
			ordoData[1]=ordo.getMotif();
			ordoData[2]=ordo.getMedecin();
			((OrdoTableModel)listOrdos.getModel()).addRow(ordoData);
			}
		}
	}
	public void updateDataMois(String mois){
		fillData(mois,this.annee,this.soignant);
	}
	public void updateDataAnnee(String annee){
		fillData(this.mois,annee,this.soignant);
	}
	public void setFichePatInfo(Patient pat,String mois,String annee, String soignant){
		if (pat!=null){
			this.pat=pat;
		}
		this.mois=mois;
		this.annee=annee;
		this.soignant=soignant;
		fillData(mois,annee,soignant);
		refreshOrdoList();
	}
	public void fillData(String mois,String annee,String soignant){
		lbNom.setText("Nom : "+pat.toString());
		lbAddr.setText("Adresse : "+pat.getAdresse());
		lbTel.setText("Tél : "+pat.getTel());
		label4.setText("Cotation Matin par defaut :"+pat.getDefCotMatin());
		label5.setText("Cotation Midi par defaut :"+pat.getDefCotMidi());
		label6.setText("Cotation Soir par defaut :"+pat.getDefCotSoir());

		dataList.clear();
		for(int i=0;i<pat.getCotList().size();i++){
			dataList.addElement(pat.getCotList().get(i).toStringComment());
		}

		CotationsMois cots=pat.getCotListMois(mois, annee, soignant);
		dataList2.clear();
		for(int i=0;i<cots.getCotStr().size();i++){
			dataList2.addElement(cots.getCotStr().get(i).toString());
		}

		for(int i=1;i<32;i++){
			if (i<16){
				Boolean hasAct=false;
				if (cots.getCotDuJourMatin(i)>=0){
					hasAct=true;
				} else {
					hasAct=false;
				}
				acteTable1.setValueAt(hasAct, 0, i);
				if (cots.getCotDuJourMidi(i)>=0){
					hasAct=true;
				} else {
					hasAct=false;
				}
				acteTable1.setValueAt(hasAct, 1, i);
				if (cots.getCotDuJourSoir(i)>=0){
					hasAct=true;
				} else {
					hasAct=false;
				}
				acteTable1.setValueAt(hasAct, 2, i);

			} else {
				Boolean hasAct=false;
				if (cots.getCotDuJourMatin(i)>=0){

					hasAct=true;
				} else {
					hasAct=false;
				}
				acteTable2.setValueAt(hasAct, 0, i-15);
				if (cots.getCotDuJourMidi(i)>=0){
					hasAct=true;
				} else {
					hasAct=false;
				}
				acteTable2.setValueAt(hasAct, 1, i-15);
				if (cots.getCotDuJourSoir(i)>=0){
					hasAct=true;
				} else {
					hasAct=false;
				}
				acteTable2.setValueAt(hasAct, 2, i-15);
			}
		}
		moisList.setSelectedIndex(Integer.parseInt(mois)-1);
		Date date=new Date();
		SimpleDateFormat sdfA = new SimpleDateFormat("yyyy");
		int crtY=Integer.parseInt(sdfA.format(date));
		anneeList.setSelectedIndex(crtY-Integer.parseInt(annee));

	}
	class ComboMoisListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			JComboBox cb = (JComboBox)event.getSource();
			mois=(String)cb.getSelectedItem();
			updateDataMois(mois);
		}
	}

	class ComboAnneeListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			JComboBox cb = (JComboBox)event.getSource();
			annee=(String)cb.getSelectedItem();
			updateDataAnnee(annee);
		}
	}

	class CloseBtnListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}	
	class ExpPdfBtnListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			if (pat.hasActe(mois, annee)) {
				PdfWrite doc=new PdfWrite();
				doc.exportPdf(pat, mois, annee,soignant);
			} else {
				JOptionPane.showMessageDialog(null, "Pas d'actes pour ce patient en "+mois+"/"+annee,"Erreur",JOptionPane.ERROR_MESSAGE);

			}
		}
	}	
	class ViewOrdoBtnListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			if (listOrdos.getSelectedRow()>=0){
				String date= listOrdos.getValueAt(listOrdos.getSelectedRow(),0).toString();
				String motif= listOrdos.getValueAt(listOrdos.getSelectedRow(),1).toString();
				String medecin= listOrdos.getValueAt(listOrdos.getSelectedRow(),2).toString();
				if(!date.equals("")){
					Ordonnance ordo=pat.getOrdo(date,motif,medecin);
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
}	
