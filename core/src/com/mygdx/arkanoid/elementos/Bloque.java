package com.mygdx.arkanoid.elementos;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.arkanoid.utiles.Config;
import com.mygdx.arkanoid.utiles.Render;

public class Bloque {

	private float posX = 0, posY = 0;
	private float ancho = 200, alto = 50;
	private static int cont = 0, capa = 0;

	public Bloque(int j, int i, int termina) {
		posY = Config.ALTO - 50 - (alto * capa);
		if (j == 1)
			posX = 0 + (ancho * cont);
		else
			posX = Config.ANCHO / 2 + (ancho * cont);
		cont++;
		if (cont == 4) {
			cont = 0;
			capa++;
		}
		if (i == termina - 1) {
			capa = 0;
			cont = 0;
		}
	}

	public void dibujar() {
		Render.sr.begin(ShapeType.Filled);
		Render.sr.setColor(Color.WHITE);
		Render.sr.rect(posX, posY, ancho - 5, alto - 5);
		Render.sr.end();
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}
	
	public void setPoxY(float posY)
	{
		this.posY=posY;
	}
	
	public float getPosY()
	{
		return this.posY;
	}

	public static Boolean choque(Pelota pe, Bloque[] bloque) {
		Boolean j = false;
		for (int i = 0; i < bloque.length; i++) {
			if ((pe.getPosX() >= bloque[i].posX && pe.getPosX() <= bloque[i].posX + bloque[i].ancho)
					&& (pe.getPosY() + pe.getAlto() == bloque[i].posY
							|| pe.getPosY() == bloque[i].posY + bloque[i].alto)) {
				bloque[i].posY = 1000;
				pe.setDirY(pe.getDirY() * -1);
				j = true;
			} else if ((pe.getPosY() >= bloque[i].posY && pe.getPosY() <= bloque[i].posY + bloque[i].alto)
					&& (pe.getPosX() + pe.getAncho() == bloque[i].posX
							|| pe.getPosX() == bloque[i].posX + bloque[i].ancho)) {
				bloque[i].posY = 1000;
				pe.setDirX(pe.getDirX() * -1);
				j = true;
			}
		}
		return j;
	}

}
