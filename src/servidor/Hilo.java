package servidor;

import seguridad.CommonsCodec;
import java.net.*;
import java.util.LinkedList;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Hilo implements Runnable {
	
	private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
	
    private LinkedList<Socket> usuarios = new LinkedList<Socket>();
	private int A[][];
	private int B[][];
	private int ID;
	
	public Hilo(Socket cliente, LinkedList users, int id, int[][] mA, int[][] mB) {
		socket = cliente;
		usuarios = users;
		ID = id;
		A = mA;
		B = mB;	}
	
	@Override
    public void run() {
        try { 
        	in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            
            String msg = "";
            msg = Integer.toString(ID);
            
            //Enviar ID
            System.out.println("enviando ID: " + ID);
            out.writeUTF(CommonsCodec.Encriptar(msg));
        	
        	while(true){
        		String mensaje = CommonsCodec.Desencriptar(in.readUTF());
        		leerMensaje(mensaje); }
        	
        } catch (Exception e) {
        	int index = 0;
        	//Si un Cliente se Desconecta lo sacamos de la Lista
            for (int i = 0; i < usuarios.size(); i++) {
                if(usuarios.get(i) == socket){
           //       usuarios.remove(i);
                	index = i;
                    break;	} 
            }
            disconect(index);
        }
    }
	
	public void disconect(int index) {
		try{
			String msj = "OUT";
			
			usuarios.get(index).close();
            usuarios.remove(index);
			
			for (Socket usuario : usuarios) {
                out = new DataOutputStream(usuario.getOutputStream());
                out.writeUTF(CommonsCodec.Encriptar(msj));	}	
			
		}catch (Exception e) {
            e.printStackTrace();	}
	}
            
    public void leerMensaje(String msj) {
    	String comando = msj.substring(0, 3);	//Leer Comando
    	
    	if(comando.compareTo("POS") == 0) {
    		posicionarBarco(msj);	}
    	if(comando.compareTo("DIS") == 0) {
    		registrarDisparo(msj);	}
    	if(comando.compareTo("REI") == 0) {
    		reiniciar();	}
    }
    
    public void reiniciar() {
    	for(int i = 0; i < 12; i++){
			for(int j = 0; j < 12; j++) {
				A[i][j] = 0;
				B[i][j] = 0;
			}
		}
    }
    
    public void posicionarBarco(String msj) {
    	try{
    		String mensaje = "";
    		int letra = Integer.parseInt(msj.substring(3, 5));
    		int numero = Integer.parseInt(msj.substring(5, 7));
    		int ID = Integer.parseInt(msj.substring(7, 8));
    		String direcc = msj.substring(8, 9);
    		int cantPos = Integer.parseInt(msj.substring(9, 10));
    		
    		String l = msj.substring(3, 5);
    		String n = msj.substring(5, 7);
    		
    		int relleno = 1;
    	
    		int i = 0;
    		while(i < cantPos) {
    			if((direcc.compareTo("v") == 0) || (direcc.compareTo("V") == 0)) {
    				int aux = letra + i;
        			l = String.format("%02d",aux); 
        			
        			if(ID == 1) {
        				A[letra+i][numero] = 1;
        				mensaje = "POS" + l + n + ID + relleno;
        			}
        			if(ID == 2) {
        				B[letra+i][numero] = 1;
        				mensaje = "POS" + l + n + ID + relleno;
        			}
        			for (Socket usuario : usuarios) {
                        out = new DataOutputStream(usuario.getOutputStream());
                        out.writeUTF(CommonsCodec.Encriptar(mensaje));	}	
    			}
    			if((direcc.compareTo("h") == 0) || (direcc.compareTo("H") == 0)) {
    				int aux = numero + i;
        			n = String.format("%02d",aux); 
        			
        			if(ID == 1) {
        				A[letra][numero+i] = 1;
        				mensaje = "POS" + l + n + ID + relleno;
        			}
        			if(ID == 2) {
        				B[letra][numero+i] = 1;
        				mensaje = "POS" + l + n + ID + relleno;
        			}
        			for (Socket usuario : usuarios) {
                        out = new DataOutputStream(usuario.getOutputStream());
                        out.writeUTF(CommonsCodec.Encriptar(mensaje));	}
    			}
    			i++;
    		}
    			
    	} catch (Exception e) {
            e.printStackTrace();	}	
    }
    
    public void registrarDisparo(String msj) {
    	try{
    		String mensaje = "";
    		int turno = 0;
  
    		String l = msj.substring(3, 5);
    		String n = msj.substring(5, 7);
        	
    		int letra = Integer.parseInt(msj.substring(3, 5));
    	    int numero = Integer.parseInt(msj.substring(5, 7));
    	    int ID = Integer.parseInt(msj.substring(7, 8));	
    	    
    	    //Cambiamos el Turno despues del Disparo
    	    if(ID == 1) {
    	    	turno = 2; }
    	    if(ID == 2) {
    	    	turno = 1; }
    		
    	    if(ID == 1) {
    	    	if(B[letra][numero] == 0) {
        			mensaje = "AGU" + l + n + ID + turno;
        		}
        		if(B[letra][numero] == 1) {
        			mensaje = "TOC" + l + n + ID + turno;
        			B[letra][numero] = 2;
        		}
    	    }
    	    if(ID == 2) {
    	    	if(A[letra][numero] == 0) {
        			mensaje = "AGU" + l + n + ID + turno;
        		}
        		if(A[letra][numero] == 1) {
        			mensaje = "TOC" + l + n + ID + turno;
        			A[letra][numero] = 2;
        		}
    	    }
 
    		for (Socket usuario : usuarios) {
                out = new DataOutputStream(usuario.getOutputStream());
                out.writeUTF(CommonsCodec.Encriptar(mensaje)); }	
    		
    		buscarGanador();	
    	
    	} catch (Exception e) {
            e.printStackTrace();	}
    	
    }
    
    public void buscarGanador() {
    	String msj = "";
    	boolean mA = true;
    	boolean mB = true;
    	boolean aux = false;
    	boolean aux2 = false;
    	
    	int ganador = 0;
    	
    	try{
    		while((mA == true) && (aux == false)) {
    			for(int i = 0; i < 12; i++) {
    				for(int j = 0; j < 12; j++) {
    					if(A[i][j] == 1) {	
    						mA = false; }
    					if((i == 11) && (j == 11)) {
    						aux = true; }
    				}
    			}	
    		}
    		while((mB == true) && (aux2 == false)) {
    			for(int i = 0; i < 12; i++) {
    				for(int j = 0; j < 12; j++) {
    					if(B[i][j] == 1) {
    						mB = false; }
    					if((i == 11) && (j == 11)) {
    						aux2 = true; }
    				}
    			}
    		}
    	
    	if(mA == true) {
    		ganador = 2;	}
    	if(mB == true) {
    		ganador = 1;	}
    	
    	if(ganador != 0) {
    		msj = "FIN" + ganador;
    		System.out.println("Fin Juego. Ganador: " + ganador);
    		
    		for (Socket usuario : usuarios) {
                out = new DataOutputStream(usuario.getOutputStream());
                out.writeUTF(CommonsCodec.Encriptar(msj)); }	
    	}
    	
    	} catch (Exception e) {
            e.printStackTrace(); }	
    }     
}