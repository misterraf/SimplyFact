package com.rt.simplyFact;

import java.awt.BorderLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CalendarSel  extends JDialog {
	private String mois,annee;
	private JComboBox moisList = new JComboBox();
	private JComboBox anneeList = new JComboBox();
	private JTable calTable;
	private Date selDate=new Date();
	
	public CalendarSel(JFrame parent, String title, boolean modal){
		super(parent, title, modal);
		this.setSize(250, 190);
		PointerInfo a=MouseInfo.getPointerInfo();
		Point b=a.getLocation();
		int x=(int)b.getX();
		int y=(int)b.getY(); 
		this.setLocation(x,y);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.initComponent();
	}
	public void initComponent(){
		Date date=new Date();
		SimpleDateFormat sdfA = new SimpleDateFormat("yyyy");
		int crtY=Integer.parseInt(sdfA.format(date));
		annee=String.valueOf(crtY);
		mois="01";
		for(int i=0;i<crtY-2000;i++){
			anneeList.addItem(String.valueOf(crtY-i));

		}

		for(int i=1;i<13;i++){
			if (i<10){
				moisList.addItem("0"+String.valueOf(i));
			} else {
				moisList.addItem(String.valueOf(i));
			}

		}
		JPanel pan=new JPanel(new BorderLayout());
		JLabel label1=new JLabel("Mois : ");
		JLabel label2=new JLabel("Année : ");

		this.setContentPane(pan);
		JPanel datePan=new JPanel();
		datePan.add(label1);
		datePan.add(moisList);
		datePan.add(label2);
		datePan.add(anneeList);
		pan.add(datePan, BorderLayout.NORTH);
		Object[][] data=new Object[6][7];
		String[] title={"Lun","Mar","Mer","Jeu","Ven","Sam","Dim"};
		for(int i=0;i<6;i++){
			for (int j=0;j<7;j++){
				data[i][j]=i*7+j;
			}
		}
		CalTableModel model=new CalTableModel(data,title);
		calTable=new JTable(model);
		
		calTable.setCellSelectionEnabled(true);
		calTable.setRowSelectionAllowed(false);
		calTable.setColumnSelectionAllowed(false);
		ListSelectionModel calSelectionModel = calTable.getSelectionModel();
		calSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		calSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				 ListSelectionModel lsm = (ListSelectionModel) e.getSource();
				int[] selectedRow = calTable.getSelectedRows();
				int[] selectedColumn = calTable.getSelectedColumns();
				if ((selectedRow.length!=0)&&(selectedColumn.length!=0)) {
					System.out.println("case selected :"+selectedRow[0]+"x"+selectedColumn[0]);

					SimpleDateFormat sdfo = new SimpleDateFormat("EEE");
					SimpleDateFormat sdfi = new SimpleDateFormat("dd/MM/yyyy");
					String setDateStr=calTable.getValueAt(selectedRow[0],selectedColumn[0]).toString();
					if (setDateStr!=""){
						setDateStr=setDateStr+"/"+mois+"/"+annee;
						try{
							selDate=sdfi.parse(setDateStr);
						} catch (ParseException excp) {
							selDate=new Date();
							excp.printStackTrace();
						}
						System.out.println("value:"+sdfo.format(selDate)+sdfi.format(selDate));

						dispose();
					}

				}
				calTable.clearSelection();
		}
		});
		moisList.addActionListener(new ComboMoisListener());
		anneeList.addActionListener(new ComboAnneeListener());
		pan.add(new JScrollPane(calTable),BorderLayout.CENTER);
		refreshTab();
	}
	class ComboMoisListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			JComboBox cb = (JComboBox)event.getSource();
			mois=(String)cb.getSelectedItem();
			refreshTab();
		}
	}
	class ComboAnneeListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			JComboBox cb = (JComboBox)event.getSource();
			annee=(String)cb.getSelectedItem();
			refreshTab();
		}
	}
public Date showCalendarSel(){
		this.setVisible(true);      
		
		return selDate;      
	}

	public void refreshTab(){
		String dateStr="01"+mois+annee;
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat sdfm = new SimpleDateFormat("MM");
		SimpleDateFormat sdfj = new SimpleDateFormat("EEE");
		Date tempDate=new Date();
		try{
			tempDate=sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String jStr=sdfj.format(tempDate).substring(0, 3);
		List<String> jourList=new ArrayList<String>();
		String[] jours={"lun","mar","mer","jeu","ven","sam","dim"};
		for (int i=0;i<jours.length;i++){
			jourList.add(jours[i]);
		}
		int beginIdx=jourList.indexOf(jStr);
		String dateNbStr;
		String moisStr;
		for(int i=0;i<6;i++){
			for (int j=0;j<7;j++){
				dateStr=i*7+j-beginIdx+1+mois+annee;
				if (dateStr.length()==7){dateStr="0"+dateStr;}
				try{
					tempDate=sdf.parse(dateStr);
					dateNbStr=i*7+j-beginIdx+1+"";
				} catch (ParseException e) {
					dateNbStr="";
				}
				moisStr=sdfm.format(tempDate);
				if(!moisStr.equals(mois)){dateNbStr="";}
				calTable.setValueAt(dateNbStr,i,j);
			}
		}
	
	}
	public void setCrtMY(String mois, String annee){
		this.mois=mois;
		this.annee=annee;
		anneeList.setSelectedItem(annee);
		moisList.setSelectedItem(mois);
		
	}
}
