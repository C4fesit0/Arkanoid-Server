package com.mygdx.arkanoid.elementos;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.arkanoid.utiles.Config;
import com.mygdx.arkanoid.utiles.Render;
import com.mygdx.arkanoid.utiles.Utiles;

public class Pelota { 
 
	private float posX = 0, posY = 0;
	private float ancho = 20, alto = 20;
	private  float dirX = 1;
	private float dirY = 1;
	private int vel = 5;
	int nroJugador;
	
	public Pelota(int nroJugador, Paleta p1, Paleta p2) {
		posY = (Config.ALTO/2) - (alto/2);
		this.nroJugador = nroJugador;
		if(nroJugador==1) {
			posX = (Config.ANCHO/2) - (Config.ANCHO/4) - (ancho/2);
		}else {
			posX = (Config.ANCHO/2) + (Config.ANCHO/4) - (ancho/2);
		}
		dirY = (Utiles.r.nextInt(2)-1 == 0)?-1:1;
		dirX = (Utiles.r.nextInt(2)-1 == 0)?-1:1;
		System.out.println(nroJugador);
	}
	
	public void dibujar() {
		Render.sr.begin(ShapeType.Filled);
		Render.sr.setColor(Color.WHITE);
		Render.sr.rect(posX, posY, ancho, alto);
		Render.sr.end();
		update();
	}

	private void update() {
		posY += vel* dirY;
		posX += vel* dirX;
	}
	
	public float getPosY() {
		return posY;
	}

	public float getPosX() {
		return posX;
	}
	
	public void setPosX(float posX) {
		this.posX = posX;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public void setVel(int vel) {
		this.vel = vel;
	}

	public float getDirY() {
		return dirY;
	}

	public float getDirX() {
		return dirX;
	}
	
	public void setDirY(float dirY) {
		this.dirY = dirY;
	}

	public void setDirX(float dirX) {
		this.dirX = dirX;
	}
	
	public float getAncho() {
		return ancho;
	}

	public float getAlto() {
		return alto;
	}
	
	public int getNroJugador() {
		return nroJugador;
	}
}
