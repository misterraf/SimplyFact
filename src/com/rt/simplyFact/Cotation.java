package com.rt.simplyFact;
import java.io.Serializable;
public class Cotation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3286781367530893254L;
	private String cotation,comment;
	private double totalCotation,k1_1,k1_2,k2_1,k2_2,k3_1,k3_2;
	private TypeActe typeActe1,typeActe2,typeActe3;
	private boolean mau,ifd,majDim,majNuit,mci;
	private int deplacement;


/*
	private  double Tarif.cotMau = 1.35f;
	private  double Tarif.cotAmi = 3.15f;
	private  double Tarif.cotAis = 2.65f;
	private  double Tarif.cotIk = 0.35f;
	private  double Tarif.cotIfd = 2.5f;
	private  double Tarif.cotMajDim = 8.0f;
	private  double Tarif.cotMajNuit = 9.15f; 
	private  double Tarif.cotMci = 5.00f; 
	private  final String euroChar ="\u20AC";  
*/
	
	
	public Cotation(){
		k1_1=0;
		k1_2=0;
		k2_1=0;
		k2_2=0;
		k3_1=0;
		k3_2=0;
		mau=false;
		mci=false;
		ifd=false;
		majDim=false;
		majNuit=false;
		typeActe1=TypeActe.aucun;
		typeActe2=TypeActe.aucun;
		typeActe3=TypeActe.aucun;
		deplacement=0;
		cotation=this.setCotStr();
		totalCotation=this.setCotation();
		comment="";
		
	}
	public Cotation(String cotStr){
		k1_1=1;
		k1_2=1;
		k2_1=0;
		k2_2=0;
		k3_1=0;
		k3_2=0;
		mau=false;
		mci=false;
		ifd=false;
		majDim=false;
		majNuit=false;
		typeActe1=TypeActe.AMI;
		typeActe2=TypeActe.aucun;
		typeActe3=TypeActe.aucun;
		deplacement=0;
		comment="";
		cotStr=cotStr.replace("+",	"_");
		String[] cotSpl=cotStr.split("_");
		int nbAMI=1;
		for (int i=0;i<cotSpl.length;i++){
			
			if((cotSpl[i].contains("AMI"))||(cotSpl[i].contains("AIS"))){
				TypeActe ta=TypeActe.aucun;
				double k_1=0;
				double k_2=0;
				String item=cotSpl[i];
				if (cotSpl[i].contains("AMI")){
					ta=TypeActe.AMI;
					int idx=item.indexOf("AMI");
					k_1=Double.parseDouble(cotSpl[i].substring(0, idx));
					k_2=Double.parseDouble(cotSpl[i].substring(cotSpl[i].indexOf("AMI")+3,cotSpl[i].length()));
				} else {
					ta=TypeActe.AIS;
					int idx=item.indexOf("AIS");
					k_1=Double.parseDouble(cotSpl[i].substring(0, idx));
					k_2=Double.parseDouble(cotSpl[i].substring(cotSpl[i].indexOf("AIS")+3,cotSpl[i].length()));
				}
				if (nbAMI==1){
					k1_1=k_1;
					typeActe1=ta;
					k1_2=k_2;
				} else if(nbAMI==2){
					k2_1=k_1;
					typeActe2=ta;
					k2_2=k_2;
				} else if(nbAMI==3){
					k3_1=k_1;
					typeActe3=ta;
					k3_2=k_2;
				}
				nbAMI++;
			}
			if (cotSpl[i].contains("MAU")){
				mau=true;
			}
			if (cotSpl[i].contains("MCI")){
				mci=true;
			}
			if (cotSpl[i].contains("MajDim")){
				majDim=true;
			}
			if (cotSpl[i].contains("MajNuit")){
				majNuit=true;
			}
			if (cotSpl[i].contains("IFD")){
				ifd=true;
			}
			if (cotSpl[i].contains("IK")){
				ifd=true;
				int k=Integer.parseInt(cotSpl[i].substring(0, cotSpl[i].indexOf("IK")));
				deplacement=k;
			}
			
			
		}
		cotation=this.setCotStr();
		totalCotation=this.setCotation();

	}
	private double setCotation(){
		double total=0d;
		switch (typeActe1){
			case aucun:
			return 0;
			case AMI:
			total=k1_1*Tarif.cotAmi*k1_2;
			break;
			case AIS:
			total=k1_1*Tarif.cotAis*k1_2;
		}
		switch (typeActe2){
			case aucun:
			break;
			case AMI:
			total=total+k2_1*Tarif.cotAmi*k2_2;
			break;
			case AIS:
			total=total+k2_1*Tarif.cotAis*k2_2;
		}
		switch (typeActe3){
			case aucun:
			break;
			case AMI:
			total=total+k3_1*Tarif.cotAmi*k3_2;
			break;
			case AIS:
			total=total+k3_1*Tarif.cotAis*k3_2;
		}
		if (mau){total=total+Tarif.cotMau;}
		if (mci){total=total+Tarif.cotMci;}
		if (ifd){total=total+Tarif.cotIfd;}
		if (majDim){total=total+Tarif.cotMajDim;}
		if (majNuit){total=total+Tarif.cotMajNuit;}
		if (deplacement>4){
		total=total+(deplacement-4)*Tarif.cotIk-4;}
		return round2d(total);
		
	}
	public double round2d(double x){
		return ((int)(x*100+0.5))/100.00;
	}
	private String setCotStr(){
		String cot="";
		if (typeActe1==TypeActe.aucun){
			return "";
		}
		if (typeActe1==TypeActe.AMI){
			if (k1_1==(int)k1_1) {
				cot=(int)k1_1+"AMI";
			} else {
				cot=k1_1+"AMI";
			}
			if (k1_2==(int)k1_2) {
				cot=cot+(int)k1_2;
			} else {
				cot=cot+k1_2;
			}
		} else {
			if (k1_1==(int)k1_1) {
				cot=(int)k1_1+"AIS";
			} else {
				cot=k1_1+"AIS";
			}
			if (k1_2==(int)k1_2) {
				cot=cot+(int)k1_2;
			} else {
				cot=cot+k1_2;
			}
		}
		
		if (typeActe2!=TypeActe.aucun){
			if (typeActe2==TypeActe.AMI){
				if (k2_1==(int)k2_1) {
					cot=cot+"+"+(int)k2_1+"AMI";
				} else {
					cot=cot+"+"+k2_1+"AMI";
				}
				if (k2_2==(int)k2_2) {
					cot=cot+(int)k2_2;
				} else {
					cot=cot+k2_2;
				}
			} else {
				if (k2_1==(int)k2_1) {
					cot="+"+(int)k2_1+"AIS";
				} else {
					cot=cot+"+"+k2_1+"AIS";
				}
				if (k2_2==(int)k2_2) {
					cot=cot+(int)k2_2;
				} else {
					cot=cot+k2_2;
				}
			}
			if (typeActe3!=TypeActe.aucun){
			if (typeActe3==TypeActe.AMI){
				if (k3_1==(int)k3_1) {
					cot=cot+"+"+(int)k3_1+"AMI";
				} else {
					cot=cot+"+"+k3_1+"AMI";
				}
				if (k3_2==(int)k3_2) {
					cot=cot+(int)k3_2;
				} else {
					cot=cot+k3_2;
				}
			} else {
				if (k3_1==(int)k3_1) {
					cot=cot+(int)k3_1+"AIS";
				} else {
					cot=cot+k3_1+"AIS";
				}
				if (k3_2==(int)k3_2) {
					cot=cot+(int)k3_2;
				} else {
					cot=cot+k3_2;
				}
			}
						
			}
		}
		if (mau){cot=cot+"+MAU";}
		if (mci){cot=cot+"+MCI";}
		if (ifd){cot=cot+"+IFD";}
		if (majDim){cot=cot+"+MajDim";}
		if (majNuit){cot=cot+"+MajNuit";}
		if (deplacement>0) {cot=cot+"+"+deplacement+"IK";}
		//		if (!comment.equals("")) 
		//			cot=cot+" "+comment;
		//		ne pas rajouter commentaire...

		//System.out.println("cotStr :"+cot);
		return cot;

	}
	public Cotation(double ik11, TypeActe iact1,double ik12){
		k1_1=ik11;
		k1_2=ik12;
		k2_1=0;
		k2_2=0;
		k3_1=0;
		k3_2=0;
		mau=false;
		mci=false;
		ifd=false;
		majDim=false;
		majNuit=false;
		typeActe1=iact1;
		typeActe2=TypeActe.aucun;
		typeActe3=TypeActe.aucun;
		deplacement=0;
		totalCotation=this.setCotation();
		cotation=this.setCotStr();
		comment="";
	}
	public Cotation(double ik11, TypeActe iact1,double ik12,double ik21, TypeActe iact2,double ik22){
		k1_1=ik11;
		k1_2=ik12;
		k2_1=ik21;
		k2_2=ik22;
		k3_1=0;
		k3_2=0;
		mau=false;
		mci=false;
		ifd=false;
		majDim=false;
		majNuit=false;
		typeActe1=iact1;
		typeActe2=iact2;
		typeActe3=TypeActe.aucun;
		deplacement=0;
		totalCotation=this.setCotation();
		cotation=this.setCotStr();
		comment="";
	}
	public Cotation(double ik11, TypeActe iact1,double ik12,double ik21, TypeActe iact2,double ik22,double ik31, TypeActe iact3,double ik32){
		k1_1=ik11;
		k1_2=ik12;
		k2_1=ik21;
		k2_2=ik22;
		k3_1=ik31;
		k3_2=ik32;
		mau=false;
		mci=false;
		ifd=false;
		majDim=false;
		majNuit=false;
		typeActe1=iact1;
		typeActe2=iact2;
		typeActe3=iact3;
		deplacement=0;
		totalCotation=this.setCotation();
		cotation=this.setCotStr();
		comment="";
	}
	public Cotation(double ik11, TypeActe iact1,double ik12, boolean imau,boolean imci,boolean imajDim,boolean iifd,int iik){
		k1_1=ik11;
		k1_2=ik12;
		k2_1=0;
		k2_2=0;
		k3_1=0;
		k3_2=0;
		mau=imau;
		mci=imci;
		ifd=iifd;
		majDim=imajDim;
		majNuit=false;
		typeActe1=iact1;
		typeActe2=TypeActe.aucun;
		typeActe3=TypeActe.aucun;
		deplacement=iik;
		totalCotation=this.setCotation();
		cotation=this.setCotStr();
		comment="";
	}
	public Cotation(double ik11, TypeActe iact1,double ik12, double ik21, TypeActe iact2,double ik22,boolean imau,boolean imci,boolean imajDim,boolean iifd,int iik){
		k1_1=ik11;
		k1_2=ik12;
		k2_1=ik21;
		k2_2=ik22;
		k3_1=0;
		k3_2=0;
		mau=imau;
		mci=imci;
		ifd=iifd;
		majDim=imajDim;
		majNuit=false;
		typeActe1=iact1;
		typeActe2=iact2;
		typeActe3=TypeActe.aucun;
		deplacement=iik;
		totalCotation=this.setCotation();
		cotation=this.setCotStr();
		comment="";
	}
	public Cotation(double ik11, TypeActe iact1,double ik12, double ik21, TypeActe iact2,double ik22,double ik31, TypeActe iact3,double ik32,boolean imau,boolean imci,boolean imajDim,boolean iifd,int iik){
		k1_1=ik11;
		k1_2=ik12;
		k2_1=ik21;
		k2_2=ik22;
		k3_1=ik31;
		k3_2=ik32;
		mau=imau;
		mci=imci;
		ifd=iifd;
		majDim=imajDim;
		majNuit=false;
		typeActe1=iact1;
		typeActe2=iact2;
		typeActe3=iact3;
		deplacement=iik;
		totalCotation=this.setCotation();
		cotation=this.setCotStr();
		comment="";
	}
	public void setK11(double ik11){
		k1_1=ik11;
		cotation=this.setCotStr();
		totalCotation=this.setCotation();
	}
	public void setK12(double ik12){
		k1_2=ik12;
		cotation=this.setCotStr();
		totalCotation=this.setCotation();
	}
	public void setK21(double ik21){
		k2_1=ik21;
		cotation=this.setCotStr();
		totalCotation=this.setCotation();
	}
	public void setK22(double ik22){
		k2_2=ik22;
		cotation=this.setCotStr();
		totalCotation=this.setCotation();
	}
	public void setK31(double ik31){
		k3_1=ik31;
		cotation=this.setCotStr();
		totalCotation=this.setCotation();
	}
	public void setK32(double ik32){
		k3_2=ik32;
		cotation=this.setCotStr();
		totalCotation=this.setCotation();
	}
	public void settypeActe1(TypeActe iact){
		typeActe1=iact;
	}
	public void settypeActe2(TypeActe iact){
		typeActe2=iact;
		cotation=this.setCotStr();
		totalCotation=this.setCotation();
	}
	public void settypeActe3(TypeActe iact){
		typeActe3=iact;
		cotation=this.setCotStr();
		totalCotation=this.setCotation();
	}
	public void setMau(boolean imau){
		mau=imau;
		cotation=this.setCotStr();
		totalCotation=this.setCotation();
	}
	public void setMci(boolean imci){
		mci=imci;
		cotation=this.setCotStr();
		totalCotation=this.setCotation();
	}
	public void setIfd(boolean iifd){
		ifd=iifd;
		cotation=this.setCotStr();
		totalCotation=this.setCotation();
	}
	public void setMajDim(boolean iMajDim){
		majDim=iMajDim;
		cotation=this.setCotStr();
		totalCotation=this.setCotation();
	}
	public void setMajNuit(boolean iMajNuit){
		majNuit=iMajNuit;
		cotation=this.setCotStr();
		totalCotation=this.setCotation();
	}
	public void setIk(int iik){
		deplacement=iik;
		cotation=this.setCotStr();
		totalCotation=this.setCotation();
	}
	public void setComment(String comment){
		this.comment=comment;
	}
	public double getK11(){
		return k1_1;
	}
	public double getK12(){
		return k1_2;
	}
	public double getK21(){
		return k2_1;
	}
	public double getK22(){
		return k2_2;
	}
	public double getK31(){
		return k3_1;
	}
	public double getK32(){
		return k3_2;
	}
	public TypeActe gettypeActe1(){
		return typeActe1;
	}
	public TypeActe gettypeActe2(){
		return typeActe2;
	}
	public TypeActe gettypeActe3(){
		return typeActe3;
	}
	public boolean getMau(){
		return mau;
	}
	public boolean getMci(){
		return mci;
	}
	public boolean getIfd(){
		return ifd;
	}
	public boolean getMajDim(){
		return majDim;
	}
	public boolean getMajNuit(){
		return majNuit;
	}
	public int getIk(){
		return deplacement;
	}
	public double getCotation(){
		return this.setCotation();
	}
	public String getCotStr(){
		return this.setCotStr();
	}
	public String getComment(){
		return this.comment;
	}
		
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result
				+ ((cotation == null) ? 0 : cotation.hashCode());
		result = prime * result + deplacement;
		result = prime * result + (ifd ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(k1_1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(k1_2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(k2_1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(k2_2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(k3_1);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(k3_2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (majDim ? 1231 : 1237);
		result = prime * result + (majNuit ? 1231 : 1237);
		result = prime * result + (mau ? 1231 : 1237);
		result = prime * result + (mci ? 1231 : 1237);
		temp = Double.doubleToLongBits(totalCotation);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((typeActe1 == null) ? 0 : typeActe1.hashCode());
		result = prime * result
				+ ((typeActe2 == null) ? 0 : typeActe2.hashCode());
		result = prime * result
				+ ((typeActe3 == null) ? 0 : typeActe3.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cotation other = (Cotation) obj;
		if (cotation == null) {
			if (other.cotation != null)
				return false;
		} else if (!cotation.equals(other.cotation))
			return false;
		if (deplacement != other.deplacement)
			return false;
		if (ifd != other.ifd)
			return false;
		if (Double.doubleToLongBits(k1_1) != Double
				.doubleToLongBits(other.k1_1))
			return false;
		if (Double.doubleToLongBits(k1_2) != Double
				.doubleToLongBits(other.k1_2))
			return false;
		if (Double.doubleToLongBits(k2_1) != Double
				.doubleToLongBits(other.k2_1))
			return false;
		if (Double.doubleToLongBits(k2_2) != Double
				.doubleToLongBits(other.k2_2))
			return false;
		if (Double.doubleToLongBits(k3_1) != Double
				.doubleToLongBits(other.k3_1))
			return false;
		if (Double.doubleToLongBits(k3_2) != Double
				.doubleToLongBits(other.k3_2))
			return false;
		if (majDim != other.majDim)
			return false;
		if (majNuit != other.majNuit)
			return false;
		if (mau != other.mau)
			return false;
		if (mci != other.mci)
			return false;
		if (Double.doubleToLongBits(totalCotation) != Double
				.doubleToLongBits(other.totalCotation))
			return false;
		if (typeActe1 != other.typeActe1)
			return false;
		if (typeActe2 != other.typeActe2)
			return false;
		if (typeActe3 != other.typeActe3)
			return false;
		return true;
	}


	
	
  	public String toString(){
		return this.getCotStr();
	}
  	public String toStringComment(){
  		String str=this.getCotStr();
  		str=str+" "+this.getComment();
		return str ; 	
	}
}

