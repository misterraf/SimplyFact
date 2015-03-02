package com.rt.simplyFact;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DefCotDialog extends JDialog {
	private DefCotModel dcm=new DefCotModel();
	private boolean sendData;
	private ImageIcon btnIcon1 = new ImageIcon(getClass().getResource("/save.png"));
	private ImageIcon btnIcon2 = new ImageIcon(getClass().getResource("/ok.png"));
	private ImageIcon btnIcon3 = new ImageIcon(getClass().getResource("/add.png"));
	private ImageIcon btnIcon4 = new ImageIcon(getClass().getResource("/close.png"));
	private JButton btnOK=new JButton("OK",btnIcon2);
	private JButton btnCancel=new JButton("Cancel",btnIcon4);
	private JButton btnNewMatin=new JButton(btnIcon3);
	private JButton btnNewMidi=new JButton(btnIcon3);
	private JButton btnNewSoir=new JButton(btnIcon3);
	private JButton btnDef=new JButton("Valeurs par Défaut",btnIcon1);
	private JComboBox comboMatin=new JComboBox();
	private JComboBox comboMidi=new JComboBox();
	private JComboBox comboSoir=new JComboBox();
	private JLabel lbMatin =new JLabel("Matin : ");
	private JLabel lbMidi =new JLabel("Midi : ");
	private JLabel lbSoir =new JLabel("Soir : ");
	  
	public DefCotDialog(JFrame parent, String title, boolean modal){
		super(parent, title, modal);
		this.setSize(550, 170);
		PointerInfo a=MouseInfo.getPointerInfo();
		Point b=a.getLocation();
		int x=(int)b.getX();
		int y=(int)b.getY(); 
		GraphicsDevice[] gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		GraphicsDevice gdMain = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		GraphicsDevice gdSecond = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		for(int i=0;i<gd.length;i++){
			if(!gd[i].equals(gdMain)) gdSecond=gd[i];
		}
		int wMain = gdMain.getDisplayMode().getWidth();
		int hMain = gdMain.getDisplayMode().getHeight();
		int wSecond = gdSecond.getDisplayMode().getWidth();
		int hSecond = gdSecond.getDisplayMode().getHeight();
		int minH;
		int minW;
		if ((x>wMain)||(x<0)){
			if (x<0){
				minW=0;
			} else {
				minW=wMain+wSecond;
			}
			minH=hSecond;
		} else {
			minW=wMain;
			minH=hMain;
		}
		if(y+170>minH)y=minH-170;
		if(x+550>minW)x=minW-550;
		this.setLocation(x,y);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent();
	}
	public DefCotModel showDefCotDialog(){
		this.sendData = false;
		this.setVisible(true);      
		return this.dcm;      
	}

	public void setDialogInfo(DefCotModel dcm){
		this.dcm=dcm;    
		fillData();      
	}

	private void initComponent(){
		BorderLayout b = new BorderLayout();
		
		this.setLayout(b);
		JPanel btnPanel=new JPanel();
		this.getContentPane().add(btnPanel,BorderLayout.SOUTH);
		btnPanel.add(btnOK);
		btnPanel.add(btnDef);
		btnPanel.add(btnCancel);
		Box box=new Box(BoxLayout.Y_AXIS);
		Box boxMatin=new Box(BoxLayout.X_AXIS);
		Box boxMidi=new Box(BoxLayout.X_AXIS);
		Box boxSoir=new Box(BoxLayout.X_AXIS);
		lbSoir.setPreferredSize(new Dimension(70,25));
		lbMidi.setPreferredSize(new Dimension(70,25));
		lbMatin.setPreferredSize(new Dimension(70,25));
		this.getContentPane().add(box,BorderLayout.CENTER);
		box.add(boxMatin);
		box.add(boxMidi);
		box.add(boxSoir);
		boxMatin.add(lbMatin);
		boxMatin.add(comboMatin);
		boxMatin.add(btnNewMatin);
		boxMidi.add(lbMidi);
		boxMidi.add(comboMidi);
		boxMidi.add(btnNewMidi);
		boxSoir.add(lbSoir);
		boxSoir.add(comboSoir);
		boxSoir.add(btnNewSoir);
		btnOK.addActionListener(new btnListener());
		btnCancel.addActionListener(new btnListener());
		btnDef.addActionListener(new btnListener());
		btnNewMatin.addActionListener(new btnListener());
		btnNewMidi.addActionListener(new btnListener());
		btnNewSoir.addActionListener(new btnListener());
		//comboSoir.addItem("");
		//comboMatin.addItem("");
		//comboMidi.addItem("");
		comboMatin.addActionListener(new comboChange());
		comboMidi.addActionListener(new comboChange());
		comboSoir.addActionListener(new comboChange());
		
		
		
	}
	class comboChange implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (event.getSource()==comboMatin) {
				int idx=comboMatin.getSelectedIndex();
				if (idx<=0){
					dcm.setDefCotMatin(new Cotation());
				} else {
					dcm.setDefCotMatin(dcm.getListCot().get(idx-1));	
				}
			}
			if (event.getSource()==comboMidi) {
				int idx=comboMidi.getSelectedIndex();
				if (idx<=0){
					dcm.setDefCotMidi(new Cotation());
				} else {
					dcm.setDefCotMidi(dcm.getListCot().get(idx-1));	
				}
			}
			if (event.getSource()==comboSoir) {
				int idx=comboSoir.getSelectedIndex();
				if (idx<=0){
					dcm.setDefCotSoir(new Cotation());
				} else {
					dcm.setDefCotSoir(dcm.getListCot().get(idx-1));	
				}
			}
			
	}

	}
	class btnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (event.getSource()==btnCancel){
				dcm.setCancelled();
				setVisible(false);
				
			}
			if (event.getSource()==btnDef){
				if ((dcm.getDefCotMatin().toString().equals(""))&&
						(dcm.getDefCotMidi().toString().equals(""))&&
						(dcm.getDefCotSoir().toString().equals(""))
						) {
					JOptionPane.showMessageDialog(null, "Erreur : Pas de cotation definie pour ce patient","Erreur",JOptionPane.ERROR_MESSAGE);
				} else {
					dcm.setDefault();
				}

			}
			if (event.getSource()==btnOK){
				if ((dcm.getDefCotMatin().toString().equals(""))&&
						(dcm.getDefCotMidi().toString().equals(""))&&
						(dcm.getDefCotSoir().toString().equals(""))
						) {
					JOptionPane.showMessageDialog(null, "Erreur : Pas de cotation definie pour ce patient","Erreur",JOptionPane.ERROR_MESSAGE);
				} else {
					setVisible(false);
				}

			}


			if (event.getSource()==btnNewMatin){
				NewCotDialog ncd=new NewCotDialog(null,"Nouvelle Cotation",true);
				Cotation oldCot=new Cotation();
				oldCot.settypeActe1(dcm.getDefCotMatin().gettypeActe1());
				oldCot.settypeActe2(dcm.getDefCotMatin().gettypeActe2());
				oldCot.settypeActe3(dcm.getDefCotMatin().gettypeActe3());
				oldCot.setK11(dcm.getDefCotMatin().getK11());
				oldCot.setK12(dcm.getDefCotMatin().getK12());
				oldCot.setK21(dcm.getDefCotMatin().getK21());
				oldCot.setK22(dcm.getDefCotMatin().getK22());
				oldCot.setK31(dcm.getDefCotMatin().getK31());
				oldCot.setK32(dcm.getDefCotMatin().getK32());
				oldCot.setIk(dcm.getDefCotMatin().getIk());
				oldCot.setMau(dcm.getDefCotMatin().getMau());
				oldCot.setIfd(dcm.getDefCotMatin().getIfd());
				oldCot.setMajNuit(dcm.getDefCotMatin().getMajNuit());
				oldCot.setMajDim(dcm.getDefCotMatin().getMajDim());
				
				ncd.setNewCotInfo(oldCot);
				Cotation newCot=new Cotation();
				newCot=ncd.showNewCotDialog();
				if(!newCot.toString().equals("")){
					dcm.addCotation(newCot);
					//dcm.setDefCotMatin(newCot);
					comboMatin.addItem(newCot);
					comboMatin.setSelectedItem(newCot);
				}
				
			}
			if (event.getSource()==btnNewMidi){
				NewCotDialog ncd=new NewCotDialog(null,"Nouvelle Cotation",true);
				Cotation oldCot=new Cotation();
				oldCot.settypeActe1(dcm.getDefCotMidi().gettypeActe1());
				oldCot.settypeActe2(dcm.getDefCotMidi().gettypeActe2());
				oldCot.settypeActe3(dcm.getDefCotMidi().gettypeActe3());
				oldCot.setK11(dcm.getDefCotMidi().getK11());
				oldCot.setK12(dcm.getDefCotMidi().getK12());
				oldCot.setK21(dcm.getDefCotMidi().getK21());
				oldCot.setK22(dcm.getDefCotMidi().getK22());
				oldCot.setK31(dcm.getDefCotMidi().getK31());
				oldCot.setK32(dcm.getDefCotMidi().getK32());
				oldCot.setIk(dcm.getDefCotMidi().getIk());
				oldCot.setMau(dcm.getDefCotMidi().getMau());
				oldCot.setIfd(dcm.getDefCotMidi().getIfd());
				oldCot.setMajNuit(dcm.getDefCotMidi().getMajNuit());
				oldCot.setMajDim(dcm.getDefCotMidi().getMajDim());
				
				
				ncd.setNewCotInfo(oldCot);
				Cotation newCot=new Cotation();
				System.out.println("nouvelle cotation :"+newCot);
				newCot=ncd.showNewCotDialog();
				if(!newCot.toString().equals("")){
					dcm.addCotation(newCot);
					//dcm.setDefCotMidi(newCot);
					comboMidi.addItem(newCot);
					comboMidi.setSelectedItem(newCot);
				}
				
			}
			if (event.getSource()==btnNewSoir){
				NewCotDialog ncd=new NewCotDialog(null,"Nouvelle Cotation",true);
				Cotation oldCot=new Cotation();
				oldCot.settypeActe1(dcm.getDefCotSoir().gettypeActe1());
				oldCot.settypeActe2(dcm.getDefCotSoir().gettypeActe2());
				oldCot.settypeActe3(dcm.getDefCotSoir().gettypeActe3());
				oldCot.setK11(dcm.getDefCotSoir().getK11());
				oldCot.setK12(dcm.getDefCotSoir().getK12());
				oldCot.setK21(dcm.getDefCotSoir().getK21());
				oldCot.setK22(dcm.getDefCotSoir().getK22());
				oldCot.setK31(dcm.getDefCotSoir().getK31());
				oldCot.setK32(dcm.getDefCotSoir().getK32());
				oldCot.setIk(dcm.getDefCotMatin().getIk());
				oldCot.setMau(dcm.getDefCotSoir().getMau());
				oldCot.setIfd(dcm.getDefCotSoir().getIfd());
				oldCot.setMajNuit(dcm.getDefCotSoir().getMajNuit());
				oldCot.setMajDim(dcm.getDefCotSoir().getMajDim());
				
				ncd.setNewCotInfo(oldCot);
				Cotation newCot=new Cotation();
				newCot=ncd.showNewCotDialog();
				if(!newCot.toString().equals("")){
					dcm.addCotation(newCot);
					//dcm.setDefCotSoir(newCot);
					comboSoir.addItem(newCot);
					comboSoir.setSelectedItem(newCot);
				}
			
			}
		}
	}	
	private void fillData(){
		int selMatin=0;
		int selMidi=0;
		int selSoir=0;

		for (int i=0;i<dcm.getListCot().size();i++){
			if (dcm.getListCot().get(i).equals(dcm.getDefCotMatin())){
				selMatin=i+1;
			}
			if (dcm.getListCot().get(i).equals(dcm.getDefCotMidi())){
				selMidi=i+1;
			}
			if (dcm.getListCot().get(i).equals(dcm.getDefCotSoir())){
				selSoir=i+1;
			}
		}
		comboMatin.removeAllItems();
		comboMidi.removeAllItems();
		comboSoir.removeAllItems();
		comboSoir.addItem("");
		comboMatin.addItem("");
		comboMidi.addItem("");
		for (int i=0;i<dcm.getListCot().size();i++){
			comboSoir.addItem(dcm.getListCot().get(i).toStringComment());
			comboMidi.addItem(dcm.getListCot().get(i).toStringComment());
			comboMatin.addItem(dcm.getListCot().get(i).toStringComment());
		}
		comboMidi.setSelectedIndex(selMidi);
		comboMatin.setSelectedIndex(selMatin);
		comboSoir.setSelectedIndex(selSoir);
		
		
	}
}
