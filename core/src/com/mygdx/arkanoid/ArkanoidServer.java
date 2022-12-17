package com.mygdx.arkanoid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.mygdx.arkanoid.elementos.Bloque;
import com.mygdx.arkanoid.elementos.Paleta;
import com.mygdx.arkanoid.elementos.Pelota;
import com.mygdx.arkanoid.elementos.Texto;
import com.mygdx.arkanoid.redes.HiloServidor;
import com.mygdx.arkanoid.utiles.Config;
import com.mygdx.arkanoid.utiles.Recursos;
import com.mygdx.arkanoid.utiles.Render;
import com.mygdx.arkanoid.utiles.Utiles;

public class ArkanoidServer extends Game {

	public Paleta p1;
	public Paleta p2;
	public Pelota pe1, pe2;
	private Bloque[] bloque1, bloque2;
	public int nroJugador = 1;
	private int vel = 5, cont1 = 0, cont2 = 0;
	private Texto resul1, resul2;
	public boolean derechaP1 = false, izquierdaP1 = false,derechaP2=false,izquierdaP2=false;
	private HiloServidor hiloServidor;
	
	@Override
	public void create() {
		
		
		Render.sb = new SpriteBatch();
		Render.sr = new ShapeRenderer();
		hiloServidor=new HiloServidor(this);
		p1 = new Paleta(nroJugador);
		p2 = new Paleta((nroJugador == 1) ? 2 : 1);
		pe1 = new Pelota(nroJugador, p1, p2);
		pe2 = new Pelota((nroJugador == 1) ? 2 : 1, p1, p2);
		resul1 = new Texto(Recursos.FUENTE, 100, Color.WHITE);
		resul2 = new Texto(Recursos.FUENTE, 100, Color.WHITE);
		
		resul1.setTexto("");
		resul2.setTexto("");
		resul1.setPosition(+50, Config.ALTO / 2 - (resul1.getAlto()));
		resul2.setPosition((Config.ANCHO / 2 + 50), Config.ALTO / 2 - (resul2.getAlto()));
		bloque1 = new Bloque[8];
		bloque2 = new Bloque[8];
		for (int i = 0; i < bloque1.length; i++) {
			bloque1[i] = new Bloque(1,i,bloque1.length);
		}
		for (int i = 0; i < bloque2.length; i++) {
			bloque2[i] = new Bloque(2,i,bloque1.length);
		}
		hiloServidor.start();
		
	}

	@Override
	public void render() {
		
		Render.limpiarPantalla();

		if(!Utiles.jugar) {
			
		}else if(Utiles.jugar) {
		Render.sr.begin(ShapeType.Line);
		Render.sr.setColor(Color.WHITE);
		Render.sr.line(Config.ANCHO / 2, 0, Config.ANCHO / 2, Config.ALTO);
		Render.sr.end();

		p1.dibujar();
		p2.dibujar();
		pe1.dibujar();
		pe2.dibujar();
		hiloServidor.enviarMensajeATodos("Actualizar-P1-"+p1.getPosX()+"-"+pe1.getPosX()+"-"+pe1.getPosY());
		hiloServidor.enviarMensajeATodos("Actualizar-P2-"+p2.getPosX()+"-"+pe2.getPosX()+"-"+pe2.getPosY());
	
		for (int i = 0; i < bloque1.length; i++) {
			bloque1[i].dibujar();
			hiloServidor.enviarMensajeATodos("Actualizar-B1"+"-"+i+"-"+bloque1[i].getPosY());
		}

		for (int i = 0; i < bloque2.length; i++) {
			bloque2[i].dibujar();
			hiloServidor.enviarMensajeATodos("Actualizar-B2"+"-"+i+"-"+bloque1[i].getPosY());
		}
		

		update();
		
		}
	
	}

	private void update() {
		
		movimiento();
		cont1 = rebota(pe1,p1,bloque1,cont1);
		cont2 = rebota(pe2,p2,bloque2,cont2);

		if (cont1 == bloque1.length || cont2 == bloque2.length) {
			if (cont1 == bloque1.length) {
				resul1.setTexto("Ganaste");
				resul2.setTexto("Perdiste");
			} else if (cont2 == bloque1.length) {
				resul1.setTexto("Perdiste");
				resul2.setTexto("Ganaste");
			}
			Render.begin();
			resul1.dibujar();
			resul2.dibujar();
			Render.end();
			pe1.setVel(0);
			pe2.setVel(0);	
			
		}


	}

	private void movimiento() {
		
		
		
		
		if (izquierdaP1) {
			float posX = p1.getPosX() - vel;
			if (posX < 0) {
				posX = 0;
			}
			p1.setPosX(posX);
			System.out.println("izquierdaP1");
		}
		
		if (izquierdaP2) {
			float posX = p2.getPosX() - vel;
			if (posX < Config.ANCHO / 2) {
				posX = Config.ANCHO / 2;
			}
			p2.setPosX(posX);
			System.out.println("izquierdaP2");
		}

		if (derechaP1) {
			float posX = p1.getPosX() + vel;
			if (posX + p1.getAncho() > Config.ANCHO / 2) {
				posX = Config.ANCHO / 2 - p1.getAncho();
			}
			p1.setPosX(posX);
			System.out.println("derechaP1");
		}
		
		if (derechaP2) {
			float posX = p2.getPosX() + vel;
			if (posX + p2.getAncho() > Config.ANCHO) {
				posX = Config.ANCHO - p2.getAncho();
			}
			p2.setPosX(posX);
			System.out.println("derechaP2");
		}
		
	}
	
	public int rebota(Pelota pe, Paleta p, Bloque[] b, int cont) {
		if(pe.getNroJugador() == 1) {
			if ((pe.getPosX() + pe.getAncho()) == Config.ANCHO / 2 || pe.getPosX() == 0) {
				pe.setDirX(pe.getDirX() * -1);
			}

			if (pe.getPosY() == -500) {
				pe.setPosX((Config.ANCHO / 2) - (Config.ANCHO / 4) - (pe.getAncho() / 2));
				pe.setPosY((Config.ALTO / 2) - (pe.getAlto() / 2));
			}
		} else {
			if (pe.getPosX() == Config.ANCHO / 2 || (pe.getPosX() + pe.getAncho()) == Config.ANCHO) {
				pe.setDirX(pe.getDirX() * -1);
			}

			if (pe.getPosY() == -500) {
				pe.setPosX((Config.ANCHO / 2) + (Config.ANCHO / 4) - (pe.getAncho() / 2));
				pe.setPosY((Config.ALTO / 2) - (pe.getAlto() / 2));
			}
		}
		
		if ((pe.getPosY() + pe.getAlto()) == Config.ALTO || pe.getPosY() == (p.getPosY() + p.getAlto())
				&& (pe.getPosX() <= p.getPosX() + p.getAncho() && pe.getPosX() >= p.getPosX())) {
			pe.setDirY(pe.getDirY() * -1);
		}
		
		if (Bloque.choque(pe, b)) {
			cont++;
		}
		return cont;
	}

	@Override
	public void dispose() {

	}

}
