package com.rt.simplyFact;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.TableRowSorter;

import com.rt.simplyFact.FichePat.ExpPdfBtnListener;

public class FicheOrdos extends JFrame {
	private ImageIcon btnIcon1 = new ImageIcon(getClass().getResource("/find.png"));
	private ImageIcon btnIcon2 = new ImageIcon(getClass().getResource("/zip-icon.png"));
	private ImageIcon btnIcon3 = new ImageIcon(getClass().getResource("/undo-icon.png"));
	private ImageIcon btnIcon4 = new ImageIcon(getClass().getResource("/close.png"));
	protected JButton viewBtn = new JButton("Voir Ordo",btnIcon1);
	protected JButton sendOrdosBtn = new JButton("Zip",btnIcon2);
	protected JButton undoSendBtn = new JButton("Annuler Zip",btnIcon3);
	private JRadioButton allRbn = new JRadioButton("Toutes");
	private JRadioButton unarchRbn = new JRadioButton("Ordos en cours");
	private JRadioButton unsentRbn = new JRadioButton("Ordos non envoyeés");
	
	private JButton closeBtn = new JButton("Fermer",btnIcon4);
	private Cabinet cab;
	protected JTable tableOrdos;

	public FicheOrdos() {

		this.setTitle("Liste du jour");
		this.setSize(600,600);
		this.setLocation(600,150);

		//this.setDefaultCloseOperation();
		JPanel pan=new JPanel(new BorderLayout());
		JPanel panmid=new JPanel(new BorderLayout());
		JPanel btnPanel=new JPanel();
		JPanel selPanel=new JPanel();
		btnPanel.add(viewBtn);
		btnPanel.add(sendOrdosBtn);
		btnPanel.add(undoSendBtn);
		btnPanel.add(closeBtn);
		selPanel.add(allRbn);
		selPanel.add(unarchRbn);
		selPanel.add(unsentRbn);
		ButtonGroup bg=new ButtonGroup();
		bg.add(allRbn);
		bg.add(unarchRbn);
		bg.add(unsentRbn);
		allRbn.addActionListener(new ChangeRbnListener());
		unarchRbn.addActionListener(new ChangeRbnListener());
		unsentRbn.addActionListener(new ChangeRbnListener());
		Object[][] data=new Object[1][5];
		for(int i=0;i<4;i++){
			data[0][i]="";
		}
		data[0][4]=false;
		String title[] = new String[5];
		title[0]="Patient";
		title[1]="Date Prescr°";
		title[2]="Date Fin";
		title[3]="Motif";
		title[4]="Zip";
		
		OrdoListTableModel model=new OrdoListTableModel(data,title);
		tableOrdos = new JTable(model);
		tableOrdos.getColumn("Patient").setPreferredWidth(100);
		tableOrdos.getColumn("Date Prescr°").setPreferredWidth(50);
		tableOrdos.getColumn("Date Fin").setPreferredWidth(50);
		tableOrdos.getColumn("Motif").setPreferredWidth(105);
		tableOrdos.getColumn("Zip").setPreferredWidth(10);
		RowSorter<OrdoListTableModel> sorter = new TableRowSorter<OrdoListTableModel>(model);
		tableOrdos.setRowSorter(sorter);
		
		//tableOrdos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		//tableActes.setRowSorter(sorter);
		panmid.add(new JScrollPane(tableOrdos),BorderLayout.CENTER);
		panmid.add(selPanel,BorderLayout.SOUTH);
		pan.add(panmid,BorderLayout.CENTER);
		pan.add(btnPanel,BorderLayout.SOUTH);
		
		closeBtn.addActionListener(new CloseBtnListener());
		
		setContentPane(pan);

		//this.setVisible(true);                             
	}
	public int getSelectedLine(){
		int selLine;
		selLine=this.tableOrdos.getSelectedRow();
		if (selLine==this.tableOrdos.getRowCount()-1) {return -1;}
		return selLine;
	}
	public void setSelectedLine(int selLine){
		this.tableOrdos.setRowSelectionInterval(selLine, selLine);
	}
	public void fillData(Cabinet cab){
		this.cab=cab;
		allRbn.setSelected(true);
		refresh();
		
	}
	public void refresh(){
		((OrdoListTableModel)tableOrdos.getModel()).clear();
		Patient pat;
		String nom="";
		String ddf="";
		String dpr="";
		String mot="";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Boolean sent=false;
		List<Ordonnance> listOrdo=new ArrayList<Ordonnance>();
		Ordonnance ordo=new Ordonnance();
		Object[] newdonnee = new Object[]	{"","","","",false};
		for (int i=0;i<cab.patients.size();i++){
			pat=cab.patients.get(i);
			if (pat.getVisible()){
				listOrdo=pat.getOrdos();
				nom=pat.toString();
				for (int j=0;j<listOrdo.size();j++){
					ordo=listOrdo.get(j);
					if (
					(unarchRbn.isSelected()&&(!ordo.isArchived()))||
					(unsentRbn.isSelected()&&(!ordo.isSent()))||
					(allRbn.isSelected())
					){

						dpr=sdf.format(ordo.getDatePresc());
						ddf=sdf.format(ordo.getDateFin());
						mot=ordo.getMotif();
						sent=ordo.isSent();
						newdonnee = new Object[]	{nom,dpr,ddf,mot,sent};

						((OrdoListTableModel)tableOrdos.getModel()).addRow(newdonnee);
					}
				}
			}
		}
	}
	
	class CloseBtnListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}	
	class ChangeRbnListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			refresh();
		}
	}	

}
