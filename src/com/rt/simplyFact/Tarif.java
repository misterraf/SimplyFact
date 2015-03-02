package com.rt.simplyFact;
import java.io.Serializable;
public class Tarif implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2817933126539941618L;
	public static double cotMau = 1.35f;
	public static  double cotAmi = 3.15f;
	public static  double cotAis = 2.65f;
	public static  double cotIk = 0.35f;
	public static  double cotIfd = 2.5f;
	public static  double cotMajDim = 8.0f;
	public static  double cotMajNuit = 9.15f; 
	public  final String euroChar ="\u20AC";  
	public Tarif() {
		cotMau = 1.35f;
		cotAmi = 3.15f;
		cotAis = 2.65f;
		cotIk = 0.35f;
		cotIfd = 2.5f;
		cotMajDim = 8.0f;
		cotMajNuit = 9.15f;
	}
	public static void setCotMau(float v){
		cotMau=v;
	}
	public static void setCotAmi(float v){
		cotAmi=v;
	}
}
