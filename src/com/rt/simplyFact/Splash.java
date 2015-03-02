package com.rt.simplyFact;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Splash extends JDialog {
	private JLabel verLb=new JLabel();
	private JLabel verDbLb=new JLabel();

	public Splash(JFrame parent, String title, boolean modal){
		super(parent, title, modal);
		this.setSize(394, 235);
		this.setLocation(110,300);
		JPanel content = (JPanel)getContentPane();


		JPanel versionPanel=new JPanel();
		JLabel label = new JLabel(new ImageIcon(getClass().getResource("/logoSF.gif")));
		JLabel copyrt = new JLabel
				("(C)opyright 2015, R.Theveniau", JLabel.CENTER);
		copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
		JButton okBtn=new JButton("OK");
		versionPanel.add(label);
		versionPanel.add(verLb);
		versionPanel.add(verDbLb);
		versionPanel.add(copyrt);
		content.add(versionPanel,BorderLayout.CENTER);
		content.add(okBtn,BorderLayout.SOUTH);
		content.setBorder(BorderFactory.createLineBorder(Color.blue, 2));

		
		okBtn.addActionListener((ActionListener) new OkBtnListener());

	}
	public void showSplash() {
		setVisible(true);
	}
	public void setVersion(String ver,double verDb){
		verLb.setText("Version Du Logiciel : "+ver);
		verDbLb.setText("Version Base De Donnees : "+verDb);
	}
	class OkBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			setVisible(false);
		}
	}
}
