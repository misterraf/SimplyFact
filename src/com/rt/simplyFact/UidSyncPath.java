package com.rt.simplyFact;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UidSyncPath  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5561650697073047788L;
	private String syncPath;
	private String serial;

	public UidSyncPath(){
		InetAddress ip;
		this.serial ="";
		this.syncPath="";
		try {

			ip = InetAddress.getLocalHost();
			//System.out.println("Current IP address : " + ip.getHostAddress());

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			//System.out.print("Current MAC address : ");

			
			for (int i=0;i<mac.length;i++)		{
				this.serial=this.serial+String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "");        
			}
			//System.out.println(this.serial);

		} catch (UnknownHostException e) {

			e.printStackTrace();

		} catch (SocketException e){

			e.printStackTrace();

		}
	}
	public void setSyncPath(String syncPath){
		this.syncPath=syncPath;
	}
	public String getSyncPath(){
		return this.syncPath;
	}
	public String getUID(){
		return this.serial;
	}
}
