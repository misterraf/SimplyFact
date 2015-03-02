package com.rt.simplyFact;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class CotationsMois implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6764349392912680360L;
	protected JourIdx[] cotDuJour=new JourIdx[32];
	protected List<String> cotStr=new ArrayList<String>();
	protected List<Cotation> cotList=new ArrayList<Cotation>();
	protected List<Integer> cotListNb=new ArrayList<Integer>();
	
	public CotationsMois(){
		for(int i=1;i<32;i++){
			cotDuJour[i]=new JourIdx();
		}
		
	}
	public void setCotDuJour(JourIdx[] cotIdx){
		for(int i=1;i<32;i++){cotDuJour[i]=cotIdx[i];}
	}
	public void setCotDuJour(JourIdx cotIdxJ,int jourNb){
		cotDuJour[jourNb]=cotIdxJ;
	}
	public void setCotDuJourMatin(int cotIdx,int jourNb){
		cotDuJour[jourNb].setMatin(cotIdx);
	}
	public void setCotDuJourSoir(int cotIdx,int jourNb){
		cotDuJour[jourNb].setSoir(cotIdx);
	}
	public void setCotDuJourMidi(int cotIdx,int jourNb){
		cotDuJour[jourNb].setMidi(cotIdx);
	}
	public void addCotStr(String str){
		cotStr.add(str);
	}
	public void addCotList(Cotation cot){
		cotList.add(cot);
	}
	public void addCotListNb(int cotNb){
		cotListNb.add(cotNb);
	}
	public List<String> getCotStr(){
		return cotStr;
	}
	public List<Cotation> getCotList(){
		return cotList;
	}
	public List<Integer> getCotListNb(){
		return cotListNb;
	}
	public JourIdx[] getCotDuJour(){
		return cotDuJour;
	}
	public JourIdx getCotDuJour(int jourNb){
		return cotDuJour[jourNb];
	}
	public int getCotDuJourMatin(int jourNb){
		return cotDuJour[jourNb].getMatin();
	}
	public int getCotDuJourMidi(int jourNb){
		return cotDuJour[jourNb].getMidi();
	}
	public int getCotDuJourSoir(int jourNb){
		return cotDuJour[jourNb].getSoir();
	}
	
	
}
