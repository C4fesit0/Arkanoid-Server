package com.mygdx.arkanoid.redes;

import java.net.InetAddress;

public class Clientes {
	
	private InetAddress ip;
	private int puerto;

	
	public Clientes(int numCliente,int puerto,InetAddress ip){

		this.ip=ip;
		this.puerto=puerto;
		
	}
	
	public InetAddress getIp()
	{
		return this.ip;
	}
	
	public int getPuerto()
	{
		return this.puerto;
	}
	
}
