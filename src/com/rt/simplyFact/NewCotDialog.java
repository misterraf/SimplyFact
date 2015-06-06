package com.rt.simplyFact;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class NewCotDialog extends JDialog {
	private Cotation cot=new Cotation();
	private boolean sendData;
	private ImageIcon btnIcon2 = new ImageIcon(getClass().getResource("/ok.png"));
	private ImageIcon btnIcon4 = new ImageIcon(getClass().getResource("/close.png"));
	private JButton btnOK=new JButton("OK",btnIcon2);
	private JButton btnCancel=new JButton("Cancel",btnIcon4);
	private JComboBox typeActe1CB=new JComboBox();
	private JComboBox typeActe2CB=new JComboBox();
	private JComboBox typeActe3CB=new JComboBox();
	private JLabel lbPlus1 =new JLabel(" + ");
	private JLabel lbPlus2 =new JLabel(" + ");
	private JLabel lbPlus3 =new JLabel(" ");
	private JLabel lbIK =new JLabel("IK : ");
	private JLabel lbComment =new JLabel("Commentaire : ");
	private JCheckBox chkMau=new JCheckBox("MAU");
	private JCheckBox chkMajDim=new JCheckBox("MajDim");
	private JCheckBox chkMajNuit=new JCheckBox("MajNuit");
	private JCheckBox chkIFD=new JCheckBox("IFD");
	private JCheckBox chkMCI=new JCheckBox("MCI");
	private JTextField k11TF =new JTextField();
	private JTextField k12TF =new JTextField();
	private JTextField k21TF =new JTextField();
	private JTextField k22TF =new JTextField();
	private JTextField k31TF =new JTextField();
	private JTextField k32TF =new JTextField();
	private JTextField ikTF =new JTextField();
	private JTextField commentTF =new JTextField();
	  
	public NewCotDialog(JFrame parent, String title, boolean modal){
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
		y=y+150;
		x=x-500;
		if(y+170>minH)y=minH-170;
		this.setLocation(x-550,y);
		this.setLocation(x,y);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent();
	}
	public Cotation showNewCotDialog(){
		this.sendData = false;
		this.setVisible(true);      
		return this.cot;      
	}

	public void setNewCotInfo(Cotation cot){
		this.cot=cot;    
		fillData();      
	}

	private void initComponent(){
		BorderLayout b = new BorderLayout();
		
		this.setLayout(b);
		JPanel btnPanel=new JPanel();
		this.getContentPane().add(btnPanel,BorderLayout.SOUTH);
		btnPanel.add(btnOK);
		btnPanel.add(btnCancel);
		Box box=new Box(BoxLayout.Y_AXIS);
		JPanel box1=new JPanel();
		JPanel box2=new JPanel();
		JPanel box3=new JPanel();
		JPanel box4=new JPanel();
		JPanel box5=new JPanel();
		this.getContentPane().add(box,BorderLayout.CENTER);
		box.add(box1);
		box.add(box4);
		box.add(box5);
		box1.add(k11TF);
		box1.add(typeActe1CB);
		box1.add(k12TF);
		box1.add(lbPlus1);
		box1.add(k21TF);
		box1.add(typeActe2CB);
		box1.add(k22TF);
		box1.add(lbPlus2);
		box1.add(k31TF);
		box1.add(typeActe3CB);
		box1.add(k32TF);
		//box2.add(lbPlus3);
		box4.add(chkMau);
		box4.add(chkMCI);
		box4.add(chkMajDim);
		box4.add(chkMajNuit);
		box4.add(chkIFD);
		box4.add(lbIK);
		box4.add(ikTF);
		box5.add(lbComment);
		box5.add(commentTF);
		//lbPlus1.setPreferredSize(new Dimension (40,25));
		//lbPlus2.setPreferredSize(new Dimension (40,25));
		//lbPlus3.setPreferredSize(new Dimension (40,25));
		k11TF.setPreferredSize(new Dimension (40,25));
		k12TF.setPreferredSize(new Dimension (40,25));
		k21TF.setPreferredSize(new Dimension (40,25));
		k22TF.setPreferredSize(new Dimension (40,25));
		k31TF.setPreferredSize(new Dimension (40,25));
		k32TF.setPreferredSize(new Dimension (40,25));
		ikTF.setPreferredSize(new Dimension (70,25));
		commentTF.setPreferredSize(new Dimension (270,25));
		typeActe1CB.setPreferredSize(new Dimension (70,25));
		typeActe2CB.setPreferredSize(new Dimension (70,25));
		typeActe3CB.setPreferredSize(new Dimension (70,25));
		btnOK.addActionListener(new btnListener());
		btnCancel.addActionListener(new btnListener());
		typeActe1CB.addActionListener(new comboChange());
		typeActe2CB.addActionListener(new comboChange());
		typeActe3CB.addActionListener(new comboChange());
		for(TypeActe item : TypeActe.values()){
			typeActe1CB.addItem(item);	
			typeActe2CB.addItem(item);	
			typeActe3CB.addItem(item);	
			}
		
		typeActe2CB.setEnabled(false);
		typeActe3CB.setEnabled(false);
		k11TF.setEnabled(false);
		k12TF.setEnabled(false);
		k21TF.setEnabled(false);
		k22TF.setEnabled(false);
		k31TF.setEnabled(false);
		k32TF.setEnabled(false);
		k11TF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		k12TF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		k21TF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		k22TF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		k31TF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		k32TF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
	}
	class comboChange implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (event.getSource()==typeActe1CB){
				if(!typeActe1CB.getSelectedItem().equals(TypeActe.aucun)){
					typeActe2CB.setEnabled(true);
					
					k11TF.setEnabled(true);
					k12TF.setEnabled(true);
					k11TF.setText("1");
					k12TF.setText("1");

				} else {
					typeActe2CB.setEnabled(false);
					typeActe3CB.setEnabled(false);
					k11TF.setEnabled(false);
					k12TF.setEnabled(false);
					k21TF.setEnabled(false);
					k22TF.setEnabled(false);
					k31TF.setEnabled(false);
					k32TF.setEnabled(false);
					typeActe2CB.setSelectedItem(TypeActe.aucun);
					typeActe3CB.setSelectedItem(TypeActe.aucun);
				}
			}
			if (event.getSource()==typeActe2CB){
				if(!typeActe2CB.getSelectedItem().equals(TypeActe.aucun)){
					typeActe3CB.setEnabled(true);
					k21TF.setEnabled(true);
					k22TF.setEnabled(true);
					k21TF.setText("1");
					k22TF.setText("1");
				} else {
					typeActe3CB.setEnabled(false);
					k21TF.setEnabled(false);
					k22TF.setEnabled(false);
					k31TF.setEnabled(false);
					k32TF.setEnabled(false);
					typeActe3CB.setSelectedItem(TypeActe.aucun);
				
				}
			}
			if (event.getSource()==typeActe3CB){
				if(!typeActe3CB.getSelectedItem().equals(TypeActe.aucun)){
					k31TF.setEnabled(true);
					k32TF.setEnabled(true);
					k31TF.setText("0.5");
					k32TF.setText("1");
				} else {
					k31TF.setEnabled(false);
					k32TF.setEnabled(false);
					
				}
			}
	}

	}
	class btnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			k11TF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			k12TF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			k21TF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			k22TF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			k31TF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			k32TF.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
			boolean error=false;
			cot=new Cotation();
			if (event.getSource()==btnOK){
				
				cot.setComment(commentTF.getText());
				cot.setMau(chkMau.isSelected());
				cot.setMci(chkMCI.isSelected());
				cot.setMajDim(chkMajDim.isSelected());
				cot.setMajNuit(chkMajNuit.isSelected());
				cot.setIfd(chkIFD.isSelected());
				if(ikTF.getText().equals(""))ikTF.setText("0");
				if(k11TF.getText().equals(""))k11TF.setText("0");
				if(k12TF.getText().equals(""))k12TF.setText("0");
				if(k21TF.getText().equals(""))k21TF.setText("0");
				if(k22TF.getText().equals(""))k22TF.setText("0");
				if(k31TF.getText().equals(""))k31TF.setText("0");
				if(k32TF.getText().equals(""))k32TF.setText("0");
				
				try {
					Double k = Double.parseDouble(k11TF.getText());
					cot.setK11(k);
				} catch(NumberFormatException e) {
					k11TF.setBorder(BorderFactory.createLineBorder(Color.red, 2));
					JOptionPane.showMessageDialog(null, "Valeurs numériques uniquement dans les coefficients de cotation","Erreur",JOptionPane.ERROR_MESSAGE);
					error=true;
				}
				try {
					Double k = Double.parseDouble(k12TF.getText());
					cot.setK12(k);
				} catch(NumberFormatException e) {
					k12TF.setBorder(BorderFactory.createLineBorder(Color.red, 2));
					if(!error) JOptionPane.showMessageDialog(null, "Valeurs numériques uniquement dans les coefficients de cotation","Erreur",JOptionPane.ERROR_MESSAGE);
					error=true;
				}
				try {
					Double k = Double.parseDouble(k21TF.getText());
					cot.setK21(k);
				} catch(NumberFormatException e) {
					k21TF.setBorder(BorderFactory.createLineBorder(Color.red, 2));
					if(!error) JOptionPane.showMessageDialog(null, "Valeurs numériques uniquement dans les coefficients de cotation","Erreur",JOptionPane.ERROR_MESSAGE);
					error=true;
				}
				try {
					Double k = Double.parseDouble(k22TF.getText());
					cot.setK22(k);
				} catch(NumberFormatException e) {
					k22TF.setBorder(BorderFactory.createLineBorder(Color.red, 2));
					if(!error) JOptionPane.showMessageDialog(null, "Valeurs numériques uniquement dans les coefficients de cotation","Erreur",JOptionPane.ERROR_MESSAGE);
					error=true;
				}
				try {
					Double k = Double.parseDouble(k31TF.getText());
					cot.setK31(k);
				} catch(NumberFormatException e) {
					k31TF.setBorder(BorderFactory.createLineBorder(Color.red, 2));
					if(!error) JOptionPane.showMessageDialog(null, "Valeurs numériques uniquement dans les coefficients de cotation","Erreur",JOptionPane.ERROR_MESSAGE);
					error=true;
				}
				try {
					Double k = Double.parseDouble(k32TF.getText());
					cot.setK32(k);
				} catch(NumberFormatException e) {
					k32TF.setBorder(BorderFactory.createLineBorder(Color.red, 2));
					if(!error) JOptionPane.showMessageDialog(null, "Valeurs numériques uniquement dans les coefficients de cotation","Erreur",JOptionPane.ERROR_MESSAGE);
					error=true;
				}
				try {
					Integer k = Integer.parseInt(ikTF.getText());
					cot.setIk(k);
				} catch(NumberFormatException e) {
					ikTF.setBorder(BorderFactory.createLineBorder(Color.red, 2));
					if(!error) JOptionPane.showMessageDialog(null, "Valeurs numériques sans virgule uniquement pour les IK","Erreur",JOptionPane.ERROR_MESSAGE);
					error=true;
				}

				cot.settypeActe1((TypeActe)typeActe1CB.getSelectedItem());
				cot.settypeActe2((TypeActe)typeActe2CB.getSelectedItem());
				cot.settypeActe3((TypeActe)typeActe3CB.getSelectedItem());
			}
			if (!error) setVisible(false);
		}

	}	
	private void fillData( ){
		typeActe1CB.setSelectedItem(cot.gettypeActe1());
		typeActe2CB.setSelectedItem(cot.gettypeActe2());
		typeActe3CB.setSelectedItem(cot.gettypeActe3());
		k11TF.setText(removeDotZero(String.valueOf(cot.getK11())));
		k12TF.setText(removeDotZero(String.valueOf(cot.getK12())));
		k21TF.setText(removeDotZero(String.valueOf(cot.getK21())));
		k22TF.setText(removeDotZero(String.valueOf(cot.getK22())));
		k31TF.setText(removeDotZero(String.valueOf(cot.getK31())));
		k32TF.setText(removeDotZero(String.valueOf(cot.getK32())));
		ikTF.setText(removeDotZero(String.valueOf(cot.getIk())));
		commentTF.setText(cot.getComment());
		chkMau.setSelected(cot.getMau());
		chkMCI.setSelected(cot.getMci());
		chkMajDim.setSelected(cot.getMajDim());
		chkMajNuit.setSelected(cot.getMajNuit());
		chkIFD.setSelected(cot.getIfd());
		 
	}
	private String removeDotZero(String val){
		if(val.contains(".0")){
			if(val.substring(val.length()-2,val.length()).equals(".0")) {
				val=val.substring(0, val.length()-2);
			}
		}
		return val;
	}
}
