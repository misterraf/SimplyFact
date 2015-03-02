package com.rt.simplyFact;
import java.io.Serializable;
public class JourIdx implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4727137283999582922L;
	protected int[] idx=new int[3];
	
	public JourIdx(){
		idx[0]=-1;
		idx[1]=-1;
		idx[2]=-1;
	}
	public void setMatin(int i){
		idx[0]=i;
	}
	public void setMidi(int i){
		idx[1]=i;
	}
	public void setSoir(int i){
		idx[2]=i;
	}
	public int getMatin(){
		return idx[0];
	}
	public int getMidi(){
		return idx[1];
	}
	public int getSoir(){
		return idx[2];
	}
	
	
}
