package com.rt.simplyFact;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfWrite {
  	private  Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
  	private  Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.NORMAL, BaseColor.RED);
  	private  Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
  	private  Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, BaseColor.GRAY);
  	private  Font titleFont2 = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.GRAY);
  	private  Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD, BaseColor.GRAY);
  	private  Font dingbats = new Font(Font.FontFamily.ZAPFDINGBATS, 12,Font.NORMAL);
  	private  Font cellFont = new Font(Font.FontFamily.HELVETICA, 18, Font.NORMAL);
  	private  Font cotFont = new Font(Font.FontFamily.HELVETICA, 12,Font.NORMAL);
  	public PdfWrite(){
  		
  	}
		
  	public  void exportPdf(Patient pat,String mois,String annee,String soignant) {
		String fileName="Facturation\\facturation_";
		fileName=fileName+"_"+soignant.replace(" ", "_")+pat.getNom();
		
		File f =new File("Facturation");
		if (!f.exists()){
			f.mkdir();
		}
		if (!pat.getPrenom().equals("")) {fileName=fileName+"_"+pat.getPrenom();}
		fileName=fileName+"_"+annee+mois+".pdf";
  	
	     	try {
	       		Document document = new Document();
	       		PdfWriter.getInstance(document, new FileOutputStream(fileName));
			String subject="Pour le mois de "+mois+"/"+annee;
			String title="Facturation pour "+pat.getNom();
			if (!pat.getPrenom().equals("")) {title=title+" "+pat.getPrenom();}
			title=title+" par "+soignant.replace(" ", "_");
	       		document.open();
	       		document.addTitle(title);
    			document.addSubject(subject);
    			document.addKeywords("Facturation,Vital'Fact");
    			document.addAuthor("SimplyFact");
    			document.addCreator("SimplyFact");
    			addPage(document,pat,mois,annee,soignant);
	       		document.close();
	       		JOptionPane.showMessageDialog(null, fileName+" exporté avec succès");
	     	} catch (Exception e) {
	       			e.printStackTrace();
	       			JOptionPane.showMessageDialog(null, e.toString(),"Erreur",JOptionPane.ERROR_MESSAGE);
	     	}
	   }
  	public  void exportPdf(Patient pat,String mois,String annee) {
		String fileName="Facturation\\facturation_"+pat.getNom();
		File f =new File("Facturation");
		if (!f.exists()){
			f.mkdir();
		}
		if (!pat.getPrenom().equals("")) {fileName=fileName+"_"+pat.getPrenom();}

		fileName=fileName+"_"+annee+mois+".pdf";
		
		
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(fileName));
			String subject="Pour le mois de "+mois+"/"+annee;

			String title="Facturation pour ";
			if (pat.getCivilite()!=null){
				title=title+pat.getCivilite()+" ";
			}
			title=title+pat.getNom();
			if (!pat.getPrenom().equals("")) {title=title+" "+pat.getPrenom();}
			document.open();
			document.addTitle(title);
			document.addSubject(subject);
			document.addKeywords("Facturation,Vital'Fact");
			document.addAuthor("SimplyFact");
			document.addCreator("SimplyFact");
			addPage(document,pat,mois,annee);
			document.close();
			JOptionPane.showMessageDialog(null, fileName+" exporté avec succès");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString(),"Erreur",JOptionPane.ERROR_MESSAGE);
		}
  	}
  	public  void exportPatListPdf(ArrayList<Patient> patList,String mois,String annee,String soignant) {
		File f =new File("Facturation");
		if (!f.exists()){
			f.mkdir();
		}
  		String fileName="Facturation\\facturation_"+soignant.replace(" ", "_")+"_"+annee+"_"+mois+".pdf";
  		try {
  			Document document = new Document();
  			PdfWriter.getInstance(document, new FileOutputStream(fileName));
  			String subject="Pour le mois de "+mois+"/"+annee;
  			String title="Facturation";
  			document.open();
  			document.addTitle(title);
  			document.addSubject(subject);
  			document.addKeywords("Facturation,Vital'Fact");
  			document.addAuthor("SimplyFact");
  			document.addCreator("SimplyFact");
  			for (int i=0;i<patList.size();i++) {
  				document.newPage();
  				addPage(document,patList.get(i),mois,annee,soignant);
  			}
  			document.close();
  			JOptionPane.showMessageDialog(null, "Facturation du mois de "+mois+"/"+annee+" ("+patList.size()+" fiche(s) ) "+fileName+" exportée avec succès");
  		} catch (Exception e) {
  			e.printStackTrace();
  			JOptionPane.showMessageDialog(null, e.toString(),"Erreur",JOptionPane.ERROR_MESSAGE);
  		}

  	}
  	private  void addPage(Document document,Patient patient, String mois,String annee,String soignant) throws DocumentException {
  		try {
 		Paragraph page = new Paragraph();
 		Image img=Image.getInstance(getClass().getResource("/logoVF.gif"));
 		img.scalePercent(50f);
			document.add(img);
			addEmptyLine(page, 3);
 	 	document.add(page);
		
 		Paragraph page2 = new Paragraph();
		page2.setAlignment(Paragraph.ALIGN_CENTER);
		Phrase ph1=new Phrase("FEUILLE DE LIAISON",titleFont);
		page2.add(ph1);
		ph1=new Phrase(soignant,titleFont2);
 	 	addEmptyLine(page2, 2);
 	 	document.add(page2);
		
 		Paragraph page3 = new Paragraph();
 		PdfPTable table1=createTable1(patient,mois,annee);
 		page3.add(table1);
 	 	addEmptyLine(page3, 1);
 	 	page3.add(createTable2(mois,annee));
 	 	document.add(page3);
		
 	 	Paragraph page4 = new Paragraph();
 	 	page4.setAlignment(Paragraph.ALIGN_LEFT);
		Phrase ph2=new Phrase("COTATION",titleFont2);
		addEmptyLine(page4, 2);
 	 	page4.add(ph2);
			addEmptyLine(page4, 1);
			CotationsMois cotPat=patient.getCotListMois(mois, annee);
			page4.add(createTable3(cotPat));
 	 	document.add(page4);
 	 	
 	 	Paragraph page5 = new Paragraph();
 	 	page5.setAlignment(Paragraph.ALIGN_LEFT);
		Phrase ph3=new Phrase("PLANNING",titleFont2);
		addEmptyLine(page5, 2);
 	 	page5.add(ph3);
 	 	addEmptyLine(page5, 1);
			page5.add(createTable4(cotPat));
			addEmptyLine(page5, 1);
			page5.add(createTable5(cotPat));
 	 	document.add(page5);
		
	       	} catch (DocumentException | IOException e) {
        		e.printStackTrace();
        		JOptionPane.showMessageDialog(null, e.toString(),"Erreur",JOptionPane.ERROR_MESSAGE);
   		} 
}

  	private  void addPage(Document document,Patient patient, String mois,String annee) throws DocumentException {
    	try {
 		Paragraph page = new Paragraph();
 		Image img=Image.getInstance(getClass().getResource("/logoVF.gif"));
 		img.scalePercent(50f);
			document.add(img);
			addEmptyLine(page, 3);
 	 	document.add(page);
		
 		Paragraph page2 = new Paragraph();
		page2.setAlignment(Paragraph.ALIGN_CENTER);
		Phrase ph1=new Phrase("FEUILLE DE LIAISON",titleFont);
		page2.add(ph1);
		//ph1=new Phrase(soignant,titleFont2);
 	 	addEmptyLine(page2, 2);
 	 	document.add(page2);
		
 		Paragraph page3 = new Paragraph();
 		PdfPTable table1=createTable1(patient,mois,annee);
 		page3.add(table1);
 	 	addEmptyLine(page3, 1);
 	 	page3.add(createTable2(mois,annee));
 	 	document.add(page3);
		
 	 	Paragraph page4 = new Paragraph();
 	 	page4.setAlignment(Paragraph.ALIGN_LEFT);
		Phrase ph2=new Phrase("COTATION",titleFont2);
		addEmptyLine(page4, 2);
 	 	page4.add(ph2);
			addEmptyLine(page4, 1);
			CotationsMois cotPat=patient.getCotListMois(mois, annee);
			page4.add(createTable3(cotPat));
 	 	document.add(page4);
 	 	
 	 	Paragraph page5 = new Paragraph();
 	 	page5.setAlignment(Paragraph.ALIGN_LEFT);
		Phrase ph3=new Phrase("PLANNING",titleFont2);
		addEmptyLine(page5, 2);
 	 	page5.add(ph3);
 	 	addEmptyLine(page5, 1);
			page5.add(createTable4(cotPat));
			addEmptyLine(page5, 1);
			page5.add(createTable5(cotPat));
 	 	document.add(page5);
		
	       	} catch (DocumentException | IOException e) {
        		e.printStackTrace();
        		JOptionPane.showMessageDialog(null, e.toString(),"Erreur",JOptionPane.ERROR_MESSAGE);
   		} 
}

	private  PdfPTable createTable1(Patient patient,String mois, String annee) throws BadElementException {
		PdfPTable table = new PdfPTable(4);
		GrayColor cellColor = new GrayColor(0.9f);


		PdfPCell c1 = new PdfPCell(new Phrase("Nom du patient :",smallBold));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(Rectangle.NO_BORDER);
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(c1);
		String str;
		if (patient.getCivilite()!=null){
			str=patient.getCivilite()+" "+patient.getNom();

		} else {
			str=patient.getNom();
		}
							
		
		c1 = new PdfPCell(new Phrase(str,cellFont));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(Rectangle.BOTTOM);
		c1.setColspan(3);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setBackgroundColor(cellColor);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Prénom",smallBold));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(Rectangle.NO_BORDER);
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(patient.getPrenom(),cellFont));
		c1.setBackgroundColor(cellColor);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(Rectangle.BOTTOM);
		c1.setColspan(3);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Déplacement",smallBold));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(Rectangle.NO_BORDER);
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(patient.getDefIK(),cellFont));
		c1.setBackgroundColor(cellColor);
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(Rectangle.BOTTOM);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("kms",smallBold));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(Rectangle.NO_BORDER);
		c1.setColspan(2);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(c1);

		return(table);

	}
	private  PdfPTable createTable2(String mois, String annee) throws BadElementException {
		PdfPTable table = new PdfPTable(4);
		GrayColor cellColor = new GrayColor(0.9f);


		PdfPCell c1 = new PdfPCell(new Phrase("Mois :",smallBold));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(Rectangle.NO_BORDER);
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(mois+"/"+annee,cellFont));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(Rectangle.BOTTOM);
		c1.setColspan(2);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setBackgroundColor(cellColor);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(" ",cellFont));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(Rectangle.NO_BORDER);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(c1);

		
		return(table);

	}
	private  PdfPTable createTable3(CotationsMois cotPat) throws BadElementException {
		PdfPTable table = new PdfPTable(1);
		GrayColor cellColor = new GrayColor(0.9f);

		String cot=cotPat.getCotStr().get(0);
		PdfPCell c1=new PdfPCell(new Phrase(cot,cotFont));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(Rectangle.BOTTOM);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setBackgroundColor(cellColor);
		table.addCell(c1);
		if (cotPat.getCotStr().size()>1) {
			c1= new PdfPCell(new Phrase("Sauf :",cotFont));
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			c1.setBorder(Rectangle.NO_BORDER);
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			//c1.setBackgroundColor(cellColor);
			table.addCell(c1);
			for(int i=1;i<cotPat.getCotStr().size();i++) {
				cot=cotPat.getCotStr().get(i);
				c1= new PdfPCell(new Phrase(cot,cotFont));
				c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				c1.setBorder(Rectangle.BOTTOM);
				c1.setHorizontalAlignment(Element.ALIGN_LEFT);
				c1.setBackgroundColor(cellColor);
				table.addCell(c1);
			}
		}
		
		return(table);

	}
	private  PdfPTable createTable4(CotationsMois cotPat) throws BadElementException {
		PdfPTable table = new PdfPTable(18);
		GrayColor cellColor = new GrayColor(0.9f);
		
		PdfPCell c1 = new PdfPCell(new Phrase(" ",cotFont));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(Rectangle.NO_BORDER);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setColspan(2);
		table.addCell(c1);
		
		
		for(int i=1;i<17;i++) {
			c1 = new PdfPCell(new Phrase(String.valueOf(i),cotFont));
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(c1);
		}

		c1 = new PdfPCell(new Phrase("Matin",cotFont));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setColspan(2);
		table.addCell(c1);
		for(int i=1;i<17;i++) {
			String cdj=" ";
			if (cotPat.getCotDuJourMatin(i)>=0) {cdj="4";}
			c1 = new PdfPCell(new Phrase(cdj,dingbats));
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setBackgroundColor(cellColor);
			table.addCell(c1);
		}
		c1 = new PdfPCell(new Phrase("Midi",cotFont));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setColspan(2);
		table.addCell(c1);
		for(int i=1;i<17;i++) {
			String cdj=" ";
			if (cotPat.getCotDuJourMidi(i)>=0) {cdj="4";}
			c1 = new PdfPCell(new Phrase(cdj,dingbats));
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setBackgroundColor(cellColor);
			table.addCell(c1);
		}
		c1 = new PdfPCell(new Phrase("Soir",cotFont));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setColspan(2);
		table.addCell(c1);
		for(int i=1;i<17;i++) {
			String cdj=" ";
			if (cotPat.getCotDuJourSoir(i)>=0) {cdj="4";}
			c1 = new PdfPCell(new Phrase(cdj,dingbats));
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setBackgroundColor(cellColor);
			table.addCell(c1);
		}
		table.setHeaderRows(1);

		
		return(table);

	}

	private  PdfPTable createTable5(CotationsMois cotPat) throws BadElementException {
		PdfPTable table = new PdfPTable(18);
		GrayColor cellColor = new GrayColor(0.9f);
		
		PdfPCell c1 = new PdfPCell(new Phrase(" ",cotFont));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(Rectangle.NO_BORDER);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setColspan(2);
		table.addCell(c1);
		
		for(int i=17;i<32;i++) {
			c1 = new PdfPCell(new Phrase(String.valueOf(i),cotFont));
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(c1);
		}
		c1 = new PdfPCell(new Phrase(" ",cotFont));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(Rectangle.NO_BORDER);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Matin",cotFont));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setColspan(2);
		table.addCell(c1);
		for(int i=17;i<32;i++) {
			String cdj=" ";
			if (cotPat.getCotDuJourMatin(i)>=0) {cdj="4";}
			c1 = new PdfPCell(new Phrase(cdj,dingbats));
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setBackgroundColor(cellColor);
			table.addCell(c1);
		}
		c1 = new PdfPCell(new Phrase(" ",cotFont));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(Rectangle.NO_BORDER);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Midi",cotFont));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setColspan(2);
		table.addCell(c1);
		for(int i=17;i<32;i++) {
			String cdj=" ";
			if (cotPat.getCotDuJourMidi(i)>=0) {cdj="4";}
			c1 = new PdfPCell(new Phrase(cdj,dingbats));
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setBackgroundColor(cellColor);
			table.addCell(c1);
		}
		c1 = new PdfPCell(new Phrase(" ",cotFont));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(Rectangle.NO_BORDER);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Soir",cotFont));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setColspan(2);
		table.addCell(c1);
		for(int i=17;i<32;i++) {
			String cdj=" ";
			if (cotPat.getCotDuJourSoir(i)>=0) {cdj="4";}
			c1 = new PdfPCell(new Phrase(cdj,dingbats));
			c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setBackgroundColor(cellColor);
			table.addCell(c1);
		}
		c1 = new PdfPCell(new Phrase(" ",cotFont));
		c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		c1.setBorder(Rectangle.NO_BORDER);
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(c1);

		table.setHeaderRows(1);

		
		return(table);

	}


  
  private static void addEmptyLine(Paragraph paragraph, int number) {
    for (int i = 0; i < number; i++) {
      paragraph.add(new Paragraph(" "));
    }
  }
} 
