package com.rt.simplyFact;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class FicheList extends JFrame {
	private ImageIcon btnIcon1 = new ImageIcon(getClass().getResource("/find.png"));
	private ImageIcon btnIcon2 = new ImageIcon(getClass().getResource("/save.png"));
	private ImageIcon btnIcon3 = new ImageIcon(getClass().getResource("/importCsv.png"));
	private ImageIcon btnIcon4 = new ImageIcon(getClass().getResource("/upicon.png"));
	private ImageIcon btnIcon5 = new ImageIcon(getClass().getResource("/downicon.png"));
	private JButton closeBtn = new JButton("Fermer",btnIcon1);
	protected JButton expListBtn = new JButton("Export Liste",btnIcon2);
	protected JButton impListBtn = new JButton("Import Liste",btnIcon3);
	protected JButton upListBtn = new JButton(btnIcon4);
	protected JButton downListBtn = new JButton(btnIcon5);
	private List<ActeJour> listAct=new ArrayList<ActeJour>();
	private JTable tableActes;

	public FicheList() {

		this.setTitle("Liste du jour");
		this.setSize(600,600);
		this.setLocation(600,150);

		//this.setDefaultCloseOperation();
		JPanel pan=new JPanel(new BorderLayout());
		JPanel btnPanel=new JPanel();
		btnPanel.add(upListBtn);
		btnPanel.add(downListBtn);
		btnPanel.add(impListBtn);
		btnPanel.add(expListBtn);
		btnPanel.add(closeBtn);
		
		Object[][] data=new Object[1][6];
		for(int i=0;i<6;i++){
			data[0][i]="";
		}
		String title[] = new String[6];
		title[0]="IDE";
		title[1]="mMS";
		title[2]="Nom";
		title[3]="Prenom";
		title[4]="Dup";
		title[5]="Cotation";

		ActListTableModel model=new ActListTableModel(data,title);
		tableActes = new JTable(model);
		tableActes.getColumn("mMS").setPreferredWidth(50);
		tableActes.getColumn("IDE").setPreferredWidth(100);
		tableActes.getColumn("Nom").setPreferredWidth(105);
		tableActes.getColumn("Prenom").setPreferredWidth(105);
		tableActes.getColumn("Dup").setPreferredWidth(30);
		tableActes.getColumn("Cotation").setPreferredWidth(205);
		tableActes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		//tableActes.setRowSorter(sorter);
		pan.add(new JScrollPane(tableActes),BorderLayout.CENTER);

		pan.add(btnPanel,BorderLayout.SOUTH);
		closeBtn.addActionListener(new closeBtnListener());
		setContentPane(pan);

		//this.setVisible(true);                             
	}
	public void setFicheListInfo(List<ActeJour> listAct, int pos){
		this.listAct=listAct;
		fillData(listAct);

	}
	public int getSelectedLine(){
		int selLine;
		selLine=this.tableActes.getSelectedRow();
		if (selLine==this.tableActes.getRowCount()-1) {return -1;}
		return selLine;
	}
	public void setSelectedLine(int selLine){
		this.tableActes.setRowSelectionInterval(selLine, selLine);
	}
	public void fillData(List<ActeJour> listAct){
		((ActListTableModel)tableActes.getModel()).clear();
		String cot;
		String nom="";
		String mms;
		String dup="";
		String prenom="";
		DateJour dj;
		if (listAct.size()>0){
			dj=listAct.get(0).getDateJour();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String crtDateStr=sdf.format(dj.getDate());

			this.setTitle("Liste du jour : "+crtDateStr);
			
		}
		String soignant="";
		ActeJour aj;
		for(int i=0;i<listAct.size();i++){
			aj=listAct.get(i);
			dj=aj.getDateJour();
			mms=dj.getJour().toString();
			cot=aj.getCotation();
			nom=aj.getNomPatient();
			dup=aj.getDuplicate();
			prenom=aj.getPrenom();
			soignant=aj.getSoignant();
			int rc=((ActListTableModel)tableActes.getModel()).getRowCount()-1;
			tableActes.setValueAt(soignant, rc, 0);
			tableActes.setValueAt(mms, rc, 1);
			tableActes.setValueAt(nom, rc, 2);
			tableActes.setValueAt(prenom, rc, 3);
			tableActes.setValueAt(dup, rc, 4);
			tableActes.setValueAt(cot, rc, 5);
			Object[] newdonnee = new Object[]	{"","","","","","",""};
			((ActListTableModel)tableActes.getModel()).addRow(newdonnee);
		}
	}
	
	class closeBtnListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}	

}
