package jugador;

import seguridad.CommonsCodec;
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class Cliente implements Runnable {
	
	private Socket socketCliente;
	private DataOutputStream out;
    private DataInputStream in;
    private Main frame;
    private String mensaje;
    private int ID;			
    private int turno = 2;
    //Matriz para revisar Mi Tablero y Confirmar la Posicion de mis Barcos
    private int MiTablero[][] = new int[12][12];
	public Cliente(Main frame, String host, int puerto) {
		try{
			init();
			this.frame = frame;
			socketCliente = new Socket(host, puerto);
			in = new DataInputStream(socketCliente.getInputStream());
            out = new DataOutputStream(socketCliente.getOutputStream());
            
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Revise Puerto y/o Host", "Error Conexion",
					JOptionPane.ERROR_MESSAGE); }
	}
	//Inicializo la Matriz
	public void init() {
	    for(int i = 0; i < 12; i++) {
	    	for(int j = 0; j < 12; j++) {
	    		MiTablero[i][j] = 0; }
	    }
	}
	@Override
	public void run() {
		try{
			//Leer ID del Jugador
			mensaje = CommonsCodec.Desencriptar(in.readUTF());	
			ID = Integer.parseInt(mensaje);
			String c = Integer.toString(ID);
			frame.cambioTexto("ID:" + c);
			while(true){
				//Leer Movimientos del Juego
				mensaje = CommonsCodec.Desencriptar(in.readUTF());	
		
				String comando = mensaje.substring(0, 3);
				
				if(comando.compareTo("FIN") == 0) {
					finJuego(mensaje);
				}
				else if(comando.compareTo("OUT") == 0) {
					JOptionPane.showMessageDialog(null, "Tu Rival se Retiro", "Fin Juego",
							JOptionPane.INFORMATION_MESSAGE);
					reiniciar();
				}
				else {
					leerMensaje(mensaje);	}
			}
		} catch (Exception e) {
			e.printStackTrace(); }
	}
	public void leerMensaje(String msj) {
		String comando = msj.substring(0,3);
    	int letra = Integer.parseInt(msj.substring(3, 5));
	    int numero = Integer.parseInt(msj.substring(5, 7));
	    int id = Integer.parseInt(msj.substring(7, 8));
	    int t = Integer.parseInt(msj.substring(8, 9));		
	    turno = t;										
    	if(comando.compareTo("TOC") == 0) {
    		if(id == ID) {
    			frame.getB()[letra][numero].setBackground(Color.RED);
    		}
    		else {
    			frame.getA()[letra][numero].setBackground(Color.RED); }
    	}
    	if(comando.compareTo("AGU") == 0) {
    		if(id == ID) {
    			frame.getB()[letra][numero].setBackground(Color.CYAN);
    		}
    		else {
    			frame.getA()[letra][numero].setBackground(Color.CYAN); }
    	}
    	if(comando.compareTo("POS") == 0) {
    		if(id == ID) {
    			MiTablero[letra][numero] = 1;								
    			frame.getA()[letra][numero].setBackground(Color.BLACK); }
    	}
	}
	public void enviarMsjDis(String f, String c) {
		try {
			String msj = "";
			msj = "DIS" + f + c + ID;
			if(turno == ID) {
				out.writeUTF(CommonsCodec.Encriptar(msj)); } 
			else {
				JOptionPane.showMessageDialog(null, "Espera Tu Turno", "ERROR",
						JOptionPane.ERROR_MESSAGE); }
		} catch (Exception e) {
            e.printStackTrace(); }
	}
	//Parametros: Fila, Columna, Direcc, Cant Posiciones del Barco
	public void enviarMsjPos(String f, String c, String vh, String cant) {
		try {
			String msj = "";
			msj = "POS" + f + c + ID + vh + cant;
			out.writeUTF(CommonsCodec.Encriptar(msj));
		} catch (Exception e) {
            e.printStackTrace(); }
	}
	//Revisa las posiciones que OCUPARÁ el barco para validar el envio del mensaje
    public boolean revisarTablero(String fila, String columna, String direcc, String cant) {
    	boolean valido = true;
    	boolean aux = false;
    	int f = Integer.parseInt(fila);
    	int c = Integer.parseInt(columna);
    	int p = Integer.parseInt(cant);
    	if((direcc.compareTo("h") == 0) || (direcc.compareTo("H") == 0)) {
    		//Comprobar que no se Exceda del Tablero
    		if(c+p > 12) {
    			aux = true; }
    		//Si no se pasa Revisamos cada Casilla para ver si Estan Vacias
    		int i = 0;
    		while((aux == false) && (i < p)) {
    			if(MiTablero[f][c+i] == 0) {
    				aux = false; }
    			else {
    				aux = true; }
    			i++;
    		}
    	}
    	if((direcc.compareTo("v") == 0) || (direcc.compareTo("V") == 0)) {
    		//Idem..
    		if(f+p > 12) {
    			aux = true; }
    		//Idem..
    		int i = 0;
    		while((aux == false) && (i < p)) {
    			if(MiTablero[f+i][c] == 0) {
    				aux = false; }
    			else {
    				aux = true;}
    			i++;
    		}	
    	}
    	if(aux == true) {
    		valido = false; }
    	
    	return valido;
    }
	public void finJuego(String msj) {
		int ganador = Integer.parseInt(msj.substring(3, 4));
		
		if(ganador == ID) {
			JOptionPane.showMessageDialog(null, "GANASTE", "Fin del Juego",
					JOptionPane.INFORMATION_MESSAGE);
			reiniciar();
		}
		else{
			JOptionPane.showMessageDialog(null, "PERDISTE", "Fin del Juego",
					JOptionPane.INFORMATION_MESSAGE);
			reiniciar(); }
	}
	public void reiniciar() {
	try{	
		String msj = "REI";
		for(int i = 0; i < 12; i++){
			for(int j = 0; j < 12; j++) {
				MiTablero[i][j] = 0;
				frame.getA()[i][j].setBackground(Color.GRAY);
				frame.getB()[i][j].setBackground(Color.GRAY);
			}
		}
		frame.getJB1().setEnabled(true);
		frame.getJB2().setEnabled(false);
		out.writeUTF(CommonsCodec.Encriptar(msj));	
	}catch (Exception e) {
		e.printStackTrace(); } }   
}