package com.mygdx.arkanoid.redes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.mygdx.arkanoid.ArkanoidServer;
import com.mygdx.arkanoid.utiles.Utiles;

public class HiloServidor extends Thread {

	private Clientes[] clientes = new Clientes[2];
	private final int puerto = 25565;
	private DatagramSocket sc;
	private ArkanoidServer arka;
	private int cantClientes = 0;
	private boolean fin = false;

	public void enviarMensajeATodos(String msg) {
		for (int i = 0; i < clientes.length; i++) {
			enviarMensaje(msg, clientes[i].getIp(), clientes[i].getPuerto());
		}
	}

	public HiloServidor(ArkanoidServer arka) {
		this.arka = arka;

		try {// Conexion
			System.out.println("SERVIDOR INICIADO");
			sc = new DatagramSocket(puerto);
		} catch (SocketException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		do {
			byte[] buffer = new byte[1024];
			DatagramPacket dataPacket = new DatagramPacket(buffer, buffer.length);
			try {
				System.out.println("servidor en espera");
				sc.receive(dataPacket);
				System.out.println("LLEGO PAQUETE");
			} catch (IOException e) {
				e.printStackTrace();
			}
			procesarMensaje(dataPacket);
		} while (!fin);
	}

	private void procesarMensaje(DatagramPacket dp) {
		System.out.println("SERVIDOR PROCESA PAQUETE");
		String msg = (new String(dp.getData())).trim();
		int numCliente = -1;

		if (cantClientes < 2) {

			// CONEXION
			if (msg.equals("Conexion") && cantClientes == 0) {

				clientes[cantClientes] = new Clientes(cantClientes, dp.getPort(), dp.getAddress());
				enviarMensaje("OK-" + (cantClientes + 1), clientes[cantClientes].getIp(),
						clientes[cantClientes++].getPuerto());
				System.out.println("Se envia numero de jugador 1");

			} else if (cantClientes == 1) {
				clientes[cantClientes] = new Clientes(cantClientes, dp.getPort(), dp.getAddress());
				enviarMensaje("OK-" + (cantClientes + 1), clientes[cantClientes].getIp(),
						clientes[cantClientes++].getPuerto());
				System.out.println("Se envia numero del jugador 2");
				// COMIENZO DEL JUEGO
				if (cantClientes == 2) {
					Utiles.jugar = true;
					for (int i = 0; i < clientes.length; i++) {
						enviarMensaje("Empieza", clientes[i].getIp(), clientes[i].getPuerto());
						System.out.println("Empieza el juego");
					}
				}
			}
		} else {
			// PALETAS
			for (int i = 0; i < clientes.length; i++) {
				if (dp.getPort() == clientes[i].getPuerto() && dp.getAddress().equals(clientes[i].getIp())) {
					numCliente = i;
				}
			}
			if (numCliente != -1) {
				System.out.println("Entro A -1");
				System.out.println("numero cliente " + numCliente);
				System.out.println("MENSAJE:" + msg);
				if (msg.equals("Derecha")) {
					System.out.println(numCliente + " SDASD");
					if (numCliente == 0) {
						System.out.println("PALETA 1 derecha");
						arka.derechaP1 = true;
					} else {
						System.out.println("PALETA 2 derecha");
						arka.derechaP2 = true;
					}

				} else if (msg.equals("Izquier")) {
					if (numCliente == 0) {
						System.out.println("PALETA 1 izquierda");
						arka.izquierdaP1 = true;
					} else {
						System.out.println("PALETA 2 izquierda");
						arka.izquierdaP2 = true;
					}
				} else if (msg.equals("SolteDe")) {
					if (numCliente == 0) {
						System.out.println("PALETA 1 SoltoDerecha");
						arka.derechaP1 = false;
					} else {
						System.out.println("PALETA 1 SoltoDerecha");
						arka.derechaP2 = false;
					}
				}
				if (msg.equals("SolteIz")) {
					if (numCliente == 0) {
						System.out.println("PALETA 1 SoltoIzquierda");
						arka.izquierdaP1 = false;
					} else {
						System.out.println("PALETA 2 SoltoIzquierda");
						arka.izquierdaP2 = false;
					}
				}
			}

		}
	}

	public void enviarMensaje(String msg, InetAddress ip, int puerto) {
		byte[] buffer = new byte[1024];
		buffer = msg.getBytes();
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length, ip, puerto);
		try {
			sc.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
