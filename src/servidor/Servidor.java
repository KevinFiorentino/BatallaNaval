package servidor;

import java.net.*;
import java.util.LinkedList;

public class Servidor {
	
    private final int puerto = 2020;
    private ServerSocket socketServidor;
    private LinkedList<Socket> usuarios = new LinkedList<Socket>();

    private int A[][] = new int[12][12];
    private int B[][] = new int[12][12];	
    private static int ID = 1;			
    
    public void escuchar(){ 
    try {
    	//inicializamos ambas matrices en 0
    	for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {      	
                	A[i][j] = 0;
                	B[i][j] = 0;
            }
        }
    	
    	socketServidor = new ServerSocket(puerto);
        
        System.out.println("Esperando jugadores...");
        
        while(true){
        	
        	Socket cliente = socketServidor.accept();	//Aceptamos Cliente
           
            System.out.println("Cliente Aceptado");
            
            usuarios.add(cliente);	//Agregamos Cliente a la Lista
            
            int id = ID % 2;		//Maximo dos Jugadores en el Servidor
            ID++;
            id++;

        //El Hilo recibe el Socket del cliente, la lista, el ID y las dos matrices
            Runnable  run = new Hilo(cliente, usuarios, id, A, B);
            Thread hilo = new Thread(run);
            hilo.start();
            
        }
    }
    catch (Exception e) {
        e.printStackTrace();	}    	
    }
    
    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.escuchar();
    }
}