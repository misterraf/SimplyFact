package com.rt.simplyFact;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class NewPatDialog extends JDialog{
	private Patient pat=new Patient();
	private boolean sendData;
	private ImageIcon btnIcon2 = new ImageIcon(getClass().getResource("/ok.png"));
	private ImageIcon btnIcon4 = new ImageIcon(getClass().getResource("/close.png"));
	private ImageIcon btnIcon7 = new ImageIcon(getClass().getResource("/Gear-icon.png"));
	private JButton btnOK=new JButton("OK",btnIcon2);
	private JButton btnCancel=new JButton("Cancel",btnIcon4);
	private JButton btnNewCot=new JButton("Cotations",btnIcon7);
	private JComboBox civCB=new JComboBox();
	private JLabel lbNom =new JLabel("Nom : ");
	private JLabel lbPrenom =new JLabel("Prénom : ");
	private JLabel lbAddr =new JLabel("Adresse : ");
	private JLabel lbTel =new JLabel("Téléphone : ");
	private JRadioButton rbnMr = new JRadioButton("Mr");
	private JRadioButton rbnMme = new JRadioButton("Mme");
	protected JTextField nomTF=new JTextField("");
	protected JTextField prenomTF=new JTextField("");
	protected JTextField telTF=new JTextField("");
	protected JTextField addrTF=new JTextField("");

	public NewPatDialog(JFrame parent, String title, boolean modal){
		super(parent, title, modal);
		this.setSize(450, 200);
		PointerInfo a=MouseInfo.getPointerInfo();
		Point b=a.getLocation();
		int x=(int)b.getX();
		int y=(int)b.getY(); 
		this.setLocation(x,y);
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
		btnPanel.add(btnNewCot);
		btnPanel.add(btnCancel);
		Box box=new Box(BoxLayout.Y_AXIS);
		Box ligne1=new Box(BoxLayout.X_AXIS);
		Box ligne2=new Box(BoxLayout.X_AXIS);
		Box ligne3=new Box(BoxLayout.X_AXIS);
		this.getContentPane().add(box,BorderLayout.CENTER);
		box.add(ligne1);
		box.add(ligne2);
		box.add(ligne3);
		ButtonGroup btnGrp=new ButtonGroup();
		btnGrp.add(rbnMr);
		btnGrp.add(rbnMme);
		rbnMr.setSelected(true);
		btnOK.addActionListener(new btnListener());
		btnNewCot.addActionListener(new btnListener());
		btnCancel.addActionListener(new btnListener());
		btnOK.addKeyListener(new btnListener());
		btnNewCot.addKeyListener(new btnListener());
		btnCancel.addKeyListener(new btnListener());
		ligne1.add(rbnMr);
		ligne1.add(rbnMme);
		ligne1.add(lbNom);
		ligne1.add(nomTF);
		ligne2.add(lbPrenom);
		ligne2.add(prenomTF);
		ligne2.add(lbTel);
		ligne2.add(telTF);
		ligne3.add(lbAddr);
		ligne3.add(addrTF);
		nomTF.setFont(new Font("Arial", Font.PLAIN, 24));
		prenomTF.setFont(new Font("Arial", Font.PLAIN, 24));
		telTF.setFont(new Font("Arial", Font.PLAIN, 24));
		nomTF.grabFocus();
		Vector<Component> order=new Vector<Component>();
		order.add(nomTF);
		order.add(prenomTF);
		order.add(telTF);
		order.add(addrTF);
		order.add(btnOK);
		order.add(btnNewCot);
		order.add(btnCancel);
		order.add(rbnMr);
		order.add(rbnMme);
		MyOwnFocusTraversalPolicy newPolicy = new MyOwnFocusTraversalPolicy(order);

		this.setFocusTraversalPolicy(newPolicy);

	}
	public void setFields(MrMme civilite, String nom, String prenom, String adresse, String tel){
		this.nomTF.setText(nom);
		this.prenomTF.setText(prenom);
		if (civilite.equals(MrMme.Mr)) { 
			this.rbnMr.setSelected(true);
		} else {
			this.rbnMme.setSelected(true);
		}
		this.telTF.setText(tel);
		this.addrTF.setText(adresse);
		
	}
	public static class MyOwnFocusTraversalPolicy extends FocusTraversalPolicy {
		Vector<Component> order;

		public MyOwnFocusTraversalPolicy(Vector<Component> order) {
			this.order = new Vector<Component>(order.size());
			this.order.addAll(order);
		}

		public Component getComponentAfter(Container focusCycleRoot,
				Component aComponent) {
			int idx = (order.indexOf(aComponent) + 1) % order.size();
			return order.get(idx);
		}

		public Component getComponentBefore(Container focusCycleRoot,
				Component aComponent) {
			int idx = order.indexOf(aComponent) - 1;
			if (idx < 0) {
				idx = order.size() - 1;
			}
			return order.get(idx);
		}

		public Component getDefaultComponent(Container focusCycleRoot) {
			return order.get(0);
		}

		public Component getLastComponent(Container focusCycleRoot) {
			return order.lastElement();
		}

		public Component getFirstComponent(Container focusCycleRoot) {
			return order.get(0);
		}
	}	public Patient showNewPatDialog(){
		this.sendData = false;
		this.setVisible(true);      
		return this.pat;      
	}


	class btnListener implements ActionListener,KeyListener{
		public void keyPressed(KeyEvent event){
			if (event.getKeyCode()==KeyEvent.VK_ENTER){
				if (event.getSource()==btnCancel){
					setVisible(false);      
				} 
				if (event.getSource()==btnOK){
					if (rbnMr.isSelected()){
						pat.setCivilite(MrMme.Mr);
					} else {
						pat.setCivilite(MrMme.Mme);
					}
					pat.setNom(nomTF.getText());
					pat.setPrenom(prenomTF.getText());
					pat.setTel(telTF.getText());
					pat.setAdresse(addrTF.getText());
					setVisible(false);      
				}
				if (event.getSource()==btnNewCot){
					DefCotDialog dcd = new DefCotDialog(null, "Nouvelles Cotations par défaut", true);
					DefCotModel dcm=new DefCotModel();
					dcd.setDialogInfo(dcm);
					DefCotModel newDefCot=new DefCotModel();
					newDefCot = dcd.showDefCotDialog(); 
					if(!dcm.getCancelled()){
						pat.setCotList(dcm.getListCot());

						pat.setDefCotMatin(dcm.getDefCotMatin());
						pat.setDefCotMidi(dcm.getDefCotMidi());
						pat.setDefCotSoir(dcm.getDefCotSoir());


					}

				}

			}

		}
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {

		}	
		public void actionPerformed(ActionEvent event) {
			if (event.getSource()==btnCancel){
				setVisible(false);      
			} 
			if (event.getSource()==btnOK){
				if (rbnMr.isSelected()){
					pat.setCivilite(MrMme.Mr);
				} else {
					pat.setCivilite(MrMme.Mme);
				}
				pat.setNom(nomTF.getText());
				pat.setPrenom(prenomTF.getText());
				pat.setTel(telTF.getText());
				pat.setAdresse(addrTF.getText());
				setVisible(false);      
			}
			if (event.getSource()==btnNewCot){
				DefCotDialog dcd = new DefCotDialog(null, "Nouvelles Cotations par défaut", true);
				DefCotModel dcm=new DefCotModel();
				dcd.setDialogInfo(dcm);
				DefCotModel newDefCot=new DefCotModel();
				newDefCot = dcd.showDefCotDialog(); 
				if(!dcm.getCancelled()){
					pat.setCotList(dcm.getListCot());

					pat.setDefCotMatin(dcm.getDefCotMatin());
					pat.setDefCotMidi(dcm.getDefCotMidi());
					pat.setDefCotSoir(dcm.getDefCotSoir());


				}

			}
		}


	}


}

