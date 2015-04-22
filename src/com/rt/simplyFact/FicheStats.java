package com.rt.simplyFact;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class FicheStats extends JFrame{
	private ImageIcon btnIcon1 = new ImageIcon(getClass().getResource("/close.png"));
	private Cabinet cab=new Cabinet();
	private JButton fermer=new JButton("Fermer");
	private JButton btnTotal=new JButton("Total");
	private JLabel lbTot=new JLabel("Actes");
	private JRadioButton rbnAct = new JRadioButton("Nb Actes");
	private JRadioButton rbnFact = new JRadioButton("Facturation");
	private JComboBox moisList = new JComboBox();
	private JComboBox anneeList = new JComboBox();
	private JComboBox anneeList2 = new JComboBox();
	private ArrayList<JButton> moisBtns=new ArrayList<JButton>();
	private ArrayList<JButton> jourBtns=new ArrayList<JButton>();
	private int[] nbActesMois=new int[31];
	private double[] factMois=new double[31];
	private int[] nbActesAnnee=new int[12];
	private double[] factAnnee=new double[12];
	private barGraphActAnnee bgaa=new barGraphActAnnee();
	private barGraphActMois bgam=new barGraphActMois();
	private barGraphFactAnnee bgfa=new barGraphFactAnnee();
	private barGraphFactMois bgfm=new barGraphFactMois();
	private SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
	private String mois;
	private String annee;
	private String annee2;

	public FicheStats(){
		this.setTitle("Statistiques");
		this.setSize(600,600);
		//this.setDefaultCloseOperation();
		this.setLocationRelativeTo(null);
		JPanel pan=new JPanel(new BorderLayout());
		this.setContentPane(pan);
		JPanel btnPan=new JPanel();
		btnPan.add(fermer);
		pan.add(btnPan,BorderLayout.SOUTH);
		JPanel panMid=new JPanel(new BorderLayout());
		pan.add(panMid,BorderLayout.CENTER);

		ButtonGroup btnGrp=new ButtonGroup();
		btnGrp.add(rbnAct);
		btnGrp.add(rbnFact);
		rbnAct.setSelected(true);



		JTabbedPane tp=new JTabbedPane();

		JPanel tabAnnee=new JPanel(new BorderLayout());
		JPanel tabMois=new JPanel(new BorderLayout());
		//JPanel tabWeek=new JPanel(new BorderLayout());
		tp.addTab("Année", tabAnnee);
		tp.addTab("Mois", tabMois);
		//tp.addTab("Semaine", tabWeek);


		panMid.add(tp,BorderLayout.CENTER);
		fermer.addActionListener(new btnListener());

		Date date=new Date();
		SimpleDateFormat sdfA = new SimpleDateFormat("yyyy");
		int crtY=Integer.parseInt(sdfA.format(date));
		annee=String.valueOf(crtY);
		annee2=annee;
		mois="01";
		for(int i=0;i<crtY-2000;i++){
			anneeList.addItem(String.valueOf(crtY-i));
			anneeList2.addItem(String.valueOf(crtY-i));

		}

		for(int i=1;i<13;i++){
			if (i<10){
				moisList.addItem("0"+String.valueOf(i));
			} else {
				moisList.addItem(String.valueOf(i));
			}

		}
		JLabel label7=new JLabel("Mois : ");
		JLabel label8=new JLabel("Année : ");
		JLabel label9=new JLabel("Année : ");
		JPanel statsDateAnneePanel=new JPanel();
		JPanel statsDateMoisPanel=new JPanel();
		tabMois.add(statsDateMoisPanel,BorderLayout.NORTH);
		tabAnnee.add(statsDateAnneePanel,BorderLayout.NORTH);

		statsDateMoisPanel.add(label7);
		statsDateMoisPanel.add(moisList);
		statsDateMoisPanel.add(label8);
		statsDateMoisPanel.add(anneeList2);
		statsDateAnneePanel.add(label9);
		statsDateAnneePanel.add(anneeList);


		//panCenter.add(Box.createVerticalGlue());
		//panCenter.add(anneeBox);
		//panCenter.add(Box.createVerticalGlue());

		//fillData();

		GridLayout anneeGrid=new GridLayout(2,1);
		GridLayout moisGrid=new GridLayout(2,1);
		JPanel anneeCenterPan=new JPanel(anneeGrid);
		JPanel moisCenterPan=new JPanel(moisGrid);
		tabAnnee.add(anneeCenterPan,BorderLayout.CENTER);
		tabMois.add(moisCenterPan,BorderLayout.CENTER);
		anneeCenterPan.add(bgaa);
		moisCenterPan.add(bgam);
		anneeCenterPan.add(bgfa);
		moisCenterPan.add(bgfm);



		ComboMoisListener clm=new ComboMoisListener();
		ComboAnneeListener cla=new ComboAnneeListener();
		ComboAnnee2Listener cla2=new ComboAnnee2Listener();


		moisList.addActionListener(clm);
		anneeList.addActionListener(cla);
		anneeList2.addActionListener(cla2);

	}
	class ComboMoisListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			JComboBox cb = (JComboBox)event.getSource();
			mois=(String)cb.getSelectedItem();
			fillData();
		}
	}
	class ComboAnneeListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			JComboBox cb = (JComboBox)event.getSource();
			annee=(String)cb.getSelectedItem();
			fillData();
		}
	}
	class ComboAnnee2Listener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			JComboBox cb = (JComboBox)event.getSource();
			annee2=(String)cb.getSelectedItem();
			fillData();
		}
	}
	public void  setFicheStatsInfo(Cabinet cab){
		this.cab=cab;
		fillData();
	
	}
	public double round2d(double x){
		return ((int)(x*100+0.5))/100.00;
	}
	public void fillData(){
		Date datei=new Date();
		String crtSoignant=cab.soignants.get(cab.getCrtSoignant());
		this.setTitle("Statistiques pour "+crtSoignant);

		int randomNum;
		double randomNumD;
		for (int i=0;i<12;i++){
			randomNum = 0 + (int)(Math.random()*1500); 
			randomNumD = ((int)(Math.random()*500000))/100d; 
			nbActesAnnee[i]=randomNum;
			factAnnee[i]=randomNumD;
			try {
				datei=sdf.parse("01/"+String.valueOf(i+1)+"/"+annee);
				//System.out.println("FS date :"+"01/"+String.valueOf(i+1)+"/"+annee);
				nbActesAnnee[i]=cab.getNbActesMois(datei,crtSoignant);
				//System.out.println("nbactes :"+nbActesAnnee[i]);
				factAnnee[i]=round2d(cab.getFactMois(datei,crtSoignant));
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("FS can't convert date :"+String.valueOf(i)+"/"+mois+"/"+annee);
			}
		}
		for (int i=0;i<31;i++){
			randomNum = 0 + (int)(Math.random()*150); 
			randomNumD = ((int)(Math.random()*50000))/100d; 
			nbActesMois[i]=randomNum;
			factMois[i]=randomNumD;
			try {
				datei=sdf.parse(String.valueOf(i+1)+"/"+mois+"/"+annee2);
				nbActesMois[i]=cab.getNbActesJour(datei,crtSoignant);
				factMois[i]=round2d(cab.getFactJour(datei,crtSoignant));
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("FS can't convert date :"+String.valueOf(i)+"/"+mois+"/"+annee2);
			}


		}
		bgaa.repaint();
		bgam.repaint();
		bgfa.repaint();
		bgfm.repaint();

	}
	class btnListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			dispose();
		}


	}

	public static void drawRotate(Graphics2D g2d, String text, double x, double y, int angle ) {    
		g2d.translate((float)x,(float)y);
		g2d.rotate(Math.toRadians(angle));
		g2d.drawString(text,0,0);
		g2d.rotate(-Math.toRadians(angle));
		g2d.translate(-(float)x,-(float)y);
	}    
	public class barGraphActAnnee extends JPanel{

		public void paintComponent(Graphics g){
			int pw=this.getWidth();
			int ph=this.getHeight()-60;
			int barW=(int)(pw/12.0);
			int max=0;
			Graphics2D g2d = (Graphics2D)g;
			GradientPaint gp; 
			gp = new GradientPaint(0, 0, Color.WHITE, (int)barW/2, 0, Color.lightGray, true);
			g2d.setPaint(gp);
			for(int i=0;i<12;i++){
				if (nbActesAnnee[i]>max) max=nbActesAnnee[i];
			}
			double ratio=(double)ph/(double)max;
			AffineTransform at = g2d.getTransform();
			int total=0;
			for(int i=0;i<12;i++){
				int val=nbActesAnnee[i];
				total=total+val;
				g2d.setPaint(gp);
				g2d.fillRect(i*barW,(ph-(int)(val*ratio)),barW,(int)(val*ratio));
				g2d.setPaint(Color.lightGray);
				g2d.drawRect(i*barW,(ph-(int)(val*ratio)),barW,(int)(val*ratio));
				g2d.setPaint(Color.BLACK);
				drawRotate(g2d,String.valueOf(val),i*barW+(int)(barW/2.0),ph,270);

				g2d.drawString(String.valueOf(i+1),i*barW+(int)(barW/2.0),ph+20);
				g2d.setTransform(at);

			}
			g2d.drawString("Nombre d'actes mois par mois",pw/2-80,ph+40);
			g2d.drawString("Total :"+total,20,ph+40);
		}
	}
	public class barGraphActMois extends JPanel{

		public void paintComponent(Graphics g){
			int pw=this.getWidth();
			int ph=this.getHeight()-60;
			int barW=(int)(pw/31.0);
			int max=0;
			Graphics2D g2d = (Graphics2D)g;
			GradientPaint gp; 
			gp = new GradientPaint(0, 0, Color.WHITE, (int)barW/2, 0, Color.lightGray, true);
			g2d.setPaint(gp);
			for(int i=0;i<31;i++){
				if (nbActesMois[i]>max) max=nbActesMois[i];
			}
			double ratio=(double)ph/(double)max;
			AffineTransform at = g2d.getTransform();
			int total=0;
			for(int i=0;i<31;i++){
				int val=nbActesMois[i];
				total=total+val;
				g2d.setPaint(gp);
				g2d.fillRect(i*barW,(ph-(int)(val*ratio)),barW,(int)(val*ratio));
				g2d.setPaint(Color.lightGray);
				g2d.drawRect(i*barW,(ph-(int)(val*ratio)),barW,(int)(val*ratio));
				g2d.setPaint(Color.BLACK);
				drawRotate(g2d,String.valueOf(val),i*barW+(int)(barW/2.0),ph,270);
				if (i<10){
					g2d.drawString(String.valueOf(i+1),i*barW+(int)(barW/4.0),ph+20);
				} else {
					g2d.drawString(String.valueOf(i+1),i*barW+(int)(barW/8.0),ph+20);
				}
				g2d.setTransform(at);
				
			}
			g2d.drawString("Nombre d'actes jour par jour",pw/2-80,ph+40);
			g2d.drawString("Total :"+total,20,ph+40);
		}
	}
	public class barGraphFactAnnee extends JPanel{

		public void paintComponent(Graphics g){
			int pw=this.getWidth();
			int ph=this.getHeight()-60;
			int barW=(int)(pw/12.0);
			double max=0;
			Graphics2D g2d = (Graphics2D)g;
			GradientPaint gp; 
			gp = new GradientPaint(0, 0, Color.WHITE, (int)barW/2, 0, Color.lightGray, true);
			g2d.setPaint(gp);
			for(int i=0;i<12;i++){
				if (factAnnee[i]>max) max=factAnnee[i];
			}
			double ratio=(double)ph/(double)max;
			AffineTransform at = g2d.getTransform();
			double total=0;
			for(int i=0;i<12;i++){
				double val=factAnnee[i];
				total=total+val;
				g2d.setPaint(gp);
				g2d.fillRect(i*barW,(ph-(int)(val*ratio)),barW,(int)(val*ratio));
				g2d.setPaint(Color.lightGray);
				g2d.drawRect(i*barW,(ph-(int)(val*ratio)),barW,(int)(val*ratio));
				g2d.setPaint(Color.BLACK);
				drawRotate(g2d,String.valueOf(val),i*barW+(int)(barW/2.0),ph,270);

				g2d.drawString(String.valueOf(i+1),i*barW+(int)(barW/2.0),ph+20);
				g2d.setTransform(at);

			}
			g2d.drawString("Facturation mois par mois (\u20AC)",pw/2-80,ph+40);
			g2d.drawString("Total :"+round2d(total)+" (\u20AC)",20,ph+40);
		}
	}
	public class barGraphFactMois extends JPanel{

		public void paintComponent(Graphics g){
			int pw=this.getWidth();
			int ph=this.getHeight()-60;
			int barW=(int)(pw/31.0);
			double max=0;
			Graphics2D g2d = (Graphics2D)g;
			GradientPaint gp; 
			gp = new GradientPaint(0, 0, Color.WHITE, (int)barW/2, 0, Color.lightGray, true);
			g2d.setPaint(gp);
			for(int i=0;i<31;i++){
				if (factMois[i]>max) max=factMois[i];
			}
			double ratio=(double)ph/(double)max;
			AffineTransform at = g2d.getTransform();
			
			double total=0;
			for(int i=0;i<31;i++){
				double val=factMois[i];
				total=total+val;
				g2d.setPaint(gp);
				g2d.fillRect(i*barW,(ph-(int)(val*ratio)),barW,(int)(val*ratio));
				g2d.setPaint(Color.lightGray);
				g2d.drawRect(i*barW,(ph-(int)(val*ratio)),barW,(int)(val*ratio));
				g2d.setPaint(Color.BLACK);
				drawRotate(g2d,String.valueOf(val),i*barW+(int)(barW/2.0),ph,270);
				if (i<10){
					g2d.drawString(String.valueOf(i+1),i*barW+(int)(barW/4.0),ph+20);
				} else {
					g2d.drawString(String.valueOf(i+1),i*barW+(int)(barW/8.0),ph+20);
				}
				g2d.setTransform(at);

			}
			g2d.drawString("Facturation jour par jour (\u20AC)",pw/2-80,ph+40);
			g2d.drawString("Total :"+round2d(total)+" (\u20AC)",20,ph+40);
		}
	}

}
