package com.mygdx.arkanoid.elementos;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.arkanoid.utiles.Config;
import com.mygdx.arkanoid.utiles.Render;

public class Paleta {

	private float posX = 0, posY = 0;
	private float ancho = 200, alto = 20;
	
	public Paleta(int nroJugador) {
		
		if(nroJugador==1) {
			posX = (Config.ANCHO/2) - (Config.ANCHO/4) - (ancho/2);
		}else {
			posX = (Config.ANCHO/2) + (Config.ANCHO/4) - (ancho/2);
		}
	}
	
	public void dibujar() {
		Render.sr.begin(ShapeType.Filled);
		Render.sr.setColor(Color.WHITE);
		Render.sr.rect(posX, posY, ancho, alto);
		Render.sr.end();
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
	
	public float getAlto() {
		return alto;
	}
	
	public float getAncho() {
		return ancho;
	}
	
	public void setAncho(float ancho) {
		this.ancho = ancho;
	}
	
	
	
}
