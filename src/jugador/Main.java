package jugador;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import seguridad.*;

public class Main extends JFrame {
	
	private Cliente cliente;
	
	private javax.swing.JPanel Panel;
	private javax.swing.JLabel A[][];
	private javax.swing.JLabel B[][];
	private javax.swing.JLabel letras[];
	private javax.swing.JLabel JL1;
	private javax.swing.JLabel JL2;
	private javax.swing.JButton JB1;
	private javax.swing.JButton JB2;
	private javax.swing.JButton JB3;
	/*private javax.swing.JButton JB4;*/
	private javax.swing.JTextField disparo;
	
	public Main() {
		dibujarGUI();
		init();
		
		//Solicitar Datos de la Conexion
		String puert = JOptionPane.showInputDialog("Puerto", "");
		String h = JOptionPane.showInputDialog("Host", "localhost");
		int p = Integer.parseInt(puert);	
	
		cliente= new Cliente(this, h, p);	
		
		JB2.setEnabled(false);
	
        Thread hilo = new Thread(cliente);
        hilo.start();
	}
	
	//GETs Para Modificar la Interfaz Desde el Cliente
	public javax.swing.JButton getJB1() {
		return JB1;}
	public javax.swing.JButton getJB2() {
		return JB2;}
	public javax.swing.JLabel[][] getA() {
		return A; }
	public javax.swing.JLabel[][] getB() {
		return B; }
	
	public void init() {
		setSize(850, 500);
		setTitle("Batalla Naval");
		setResizable(false);
		setDefaultCloseOperation(0);	}
	
	//DIBUJAR INTERFAZ COMPLETA
	public void dibujarGUI(){
		Panel = new JPanel();	
		Panel.setLayout(null);
		Panel.setSize(350, 350);
		
		A = new JLabel[12][12];
		B = new JLabel[12][12];
		letras = new JLabel[12];
		JB1 = new JButton();
		JB2 = new JButton();
		JB3 = new JButton();
		/*JB4 = new JButton();*/
		disparo = new JTextField();
		
		String dicc = "ABCDEFGHIJKL";
		
		//COORDENADAS
		int x1 = 130;
		int y1 = 100;
		int x2 = 500;
		int y2 = 100;
		int num1 = 115;
		int num2 = 485;
		
		for (int i = 0; i < 12; i++){
			//LETRAS
			String L = dicc.substring(i, i+1);
			
			letras[i]=new JLabel(L);
	        letras[i].setBounds(118,y2,20,16);
	        Panel.add(letras[i]);
	        
	        letras[i]=new JLabel(L);
	        letras[i].setBounds(488,y2,20,16);
	        Panel.add(letras[i]);
	        
	        //NUMEROS
	        String n = Integer.toString(i+1);
	        
	        letras[i]=new JLabel(n);
	        letras[i].setBounds(num1+=21,86,20,16);
	        Panel.add(letras[i]);
	        
	        letras[i]=new JLabel(n);
	        letras[i].setBounds(num2+=21,86,20,16);
	        Panel.add(letras[i]);
	       
	        //TABLEROS
            for (int j = 0; j < 12; j++){
            	A[i][j]=new JLabel("");
                A[i][j].setBounds(x1,y1,20,16);
                A[i][j].setBackground(Color.GRAY);
                A[i][j].setOpaque(true);
                Panel.add(A[i][j]);
                x1+=21;
                
                B[i][j]=new JLabel("");
                B[i][j].setBounds(x2,y2,20,16);
                B[i][j].setBackground(Color.GRAY);
                B[i][j].setOpaque(true);
                Panel.add(B[i][j]);
                x2+=21;
            }
            y1+=17;
            x1=130;
            y2+=17;
            x2=500;                 
        }
		
		//TITULO
		JL1 = new JLabel("BATALLA NAVAL");
		JL1.setBounds(330,0,400,25);
		JL1.setFont(new java.awt.Font("Tahoma", 0, 30));
		Panel.add(JL1);
		
		//BORDES TITULOS
		int f1 = 324;
		for(int i = 0; i < 33; i++) {
			JL1 = new JLabel("_");
			JL1.setBounds(f1,10,100,28);
			Panel.add(JL1);
			f1+=7;	}
		
		int f2 = -12;
		for(int i = 0; i < 3; i++) {
			JL1 = new JLabel("|");
			JL1.setBounds(322,f2,100,28);
			Panel.add(JL1);
			
			JL1 = new JLabel("|");
			JL1.setBounds(553,f2,100,28);
			Panel.add(JL1);
			f2+=11;	}
		
		int f3 = 22;
		for(int i = 0; i < 61; i++) {
			JL1 = new JLabel("_");
			JL1.setBounds(f3,314,200,100);
			Panel.add(JL1);
			f3+=7; }
		
		//BORDES HORIZONTAL
		JL1 = new JLabel("________________MI TABLERO________________");
		JL1.setBounds(105,20,400,100);
		Panel.add(JL1);
		JL1 = new JLabel("_______________TABLERO RIVAL______________");
		JL1.setBounds(475,20,400,100);
		Panel.add(JL1);
		
		//BORDES VERTICAL
		int v = 30;
		for(int i = 0; i < 22; i++) {
			JL1 = new JLabel("|");
			JL1.setBounds(103,v,200,100);
			Panel.add(JL1);
			
			JL1 = new JLabel("|");
			JL1.setBounds(473,v,200,100);
			Panel.add(JL1);
			v+=11;	}
		
		//Boton de Comenzar Para Posicionar los Barcos
		JB1.setText("Comenzar");
		JB1.setBounds(75,400,93,30);
		JB1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB1ActionPerformed(evt);
            }
        });
		Panel.add(JB1);
		
		//Botones y Etiquetas Para Disparar
		JL2 = new JLabel("Coordenadas Disparo (EJ: k3)");
		JL2.setBounds(500,380,170,25);
		Panel.add(JL2);
		
		disparo.setBounds(537,405,105,23);
		Panel.add(disparo);
		
		JB2.setText("Fuego !!");
		JB2.setBounds(677,400,93,30);
		JB2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB2ActionPerformed(evt);
            }
        });
		Panel.add(JB2);
		
		//BORDES DE LOS BOTONES
		int b1 = 325;
		for(int i = 0; i < 8; i++) {
			JL1 = new JLabel("|");
			JL1.setBounds(473,b1,200,100);
			Panel.add(JL1);	
			b1+=11;	}
		
		int b2 = 474;
		for(int i = 0; i < 50; i++) {
			JL1 = new JLabel("_");
			JL1.setBounds(b2,314,200,100);
			Panel.add(JL1);
			b2+=7;	}
		
		//ID CLIENTE
		JL1.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
	    JL1.setText("No Conectado");
	    JL1.setBounds(370,400,200,100);
		Panel.add(JL1);
	    
		//BOTON SALIR
		JB3.setText("Salir");
		JB3.setBounds(220,400,93,30);
		JB3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB3ActionPerformed(evt);
            }
        });
		Panel.add(JB3);
		
		/*JB4.setText("Info. jugador");
		JB4.setBounds(75,435,105,30);
		JB4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JB4ActionPerformed(evt);
            }
        });
		Panel.add(JB4);*/
		
		this.add(Panel);	
	}
	
	//POSICIONAR BARCOS
	private void JB1ActionPerformed(java.awt.event.ActionEvent evt) {
		boolean flag = false;
		while(flag == false) {		//portaaviones
			flag = Barco("5");  }
		
		boolean flag2 = false;
		while(flag2 == false) {		//acorazado
			flag2 = Barco("4"); }
		
		boolean flag3 = false;
		while(flag3 == false) {		//submarino
			flag3 = Barco("3"); }
		
		boolean flag4 = false;
		while(flag4 == false) {		//submarino
			flag4 = Barco("3"); }
		
		boolean flag5 = false;
		while(flag5 == false) {		//destructor
			flag5 = Barco("2"); }
		
		boolean flag6 = false;
		while(flag6 == false) {		//destructor
			flag6 = Barco("2"); }
		
		JB1.setEnabled(false);
		JB2.setEnabled(true);	}
	
	//Metodo para Posicionar cada Barco
	public boolean Barco(String casilleros) {
		boolean flag = false;
		String cant = casilleros;
		
		int cas = Integer.parseInt(casilleros);
		int hv = 0;
		
		String coordenada = "";
		String verhor = "";
		
		switch(cas) {
			case 5 : {
				String b = JOptionPane.showInputDialog("portaaviones (5 espacios)", "");
				int direcc = JOptionPane.showOptionDialog(null, "Seleccione Direccion",
						"풴ertical u Horizontal?",
						   JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
						   null, new Object[] {"H", "V"}, "H");
				hv = direcc;
				coordenada = b;	}break;
			case 4 : {
				String b = JOptionPane.showInputDialog("acorazado (4 espacios)", "");
				int direcc = JOptionPane.showOptionDialog(null, "Seleccione Direccion",
						"풴ertical u Horizontal?",
						   JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
						   null, new Object[] {"H", "V"}, "H");
				hv = direcc;
				coordenada = b;	}break;
			case 3 : {
				String b = JOptionPane.showInputDialog("submarino (3 espacios)", "");
				int direcc = JOptionPane.showOptionDialog(null, "Seleccione Direccion",
						"풴ertical u Horizontal?",
						   JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
						   null, new Object[] {"H", "V"}, "H");
				hv = direcc;
				coordenada = b; }break;
			case 2 : {
				String b = JOptionPane.showInputDialog("destructor (2 espacios)", "");
				int direcc = JOptionPane.showOptionDialog(null, "Seleccione Direccion",
						"풴ertical u Horizontal?",
						   JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
						   null, new Object[] {"H", "V"}, "H");
				hv = direcc;
				coordenada = b; }break; }
		
	if(hv == 0) {
		verhor = "H"; }
	if(hv == 1) {
		verhor = "V"; }
	
	try{	
		if((coordenada != null) && (verhor != null)) {			
			int aux1 = letraXNumero(coordenada.substring(0, 1)) - 1;
			String f = String.format("%02d",aux1);
		
			int max = coordenada.length();
			int aux2 = Integer.parseInt(coordenada.substring(1, max)) - 1;
			String c = String.format("%02d",aux2);
		
		//Si las Posiciones no son Validas el Mensaje no se Envia y Solicita las Coordenadas
			//Del Barco otra vez.
			if(cliente.revisarTablero(f, c, verhor, cant) == true) {
				flag = true;
				cliente.enviarMsjPos(f, c, verhor, cant);
			}
			else {
				flag = false;
				JOptionPane.showMessageDialog(null, "ERROR Tablero", "ERROR",
						JOptionPane.ERROR_MESSAGE); }
		}
	}
	catch(Exception e) {
		JOptionPane.showMessageDialog(null, "ERROR", "ERROR",
				JOptionPane.ERROR_MESSAGE);	}	
	
		return flag;
	}
	
	//BOTON DISPARAR
	private void JB2ActionPerformed(java.awt.event.ActionEvent evt) {
	try{	
		String coordenada = disparo.getText();
		
		if(coordenada != null) {
			
			int aux1 = letraXNumero(coordenada.substring(0, 1)) - 1;			//Letra
			String f = String.format("%02d",aux1);
			
			int max = coordenada.length();
			int aux2 = Integer.parseInt(coordenada.substring(1, max)) - 1;		//Numero
			String c = String.format("%02d",aux2);
		
			cliente.enviarMsjDis(f, c);
		}
		else {
			JOptionPane.showMessageDialog(null, "Error Disparo", "ERROR",
					JOptionPane.ERROR_MESSAGE); }
	}
	catch(Exception e) {
		JOptionPane.showMessageDialog(null, "Error Disparo", "ERROR",
				JOptionPane.ERROR_MESSAGE); }	
	}
	
	private void JB3ActionPerformed(java.awt.event.ActionEvent evt) {
		System.exit(0);
	}
	
	/*private void JB4ActionPerformed(ActionEvent evt) 
	   { 
		DireccionesIP irinfo=new DireccionesIP(); 
		irinfo.setVisible(true); 
	   }*/
	
	public int letraXNumero(String letra) {
		String ABC = "ABCDEFGHIJKL";
    	String abc = "abcdefghijkl";
    	boolean flag = false;
    	char l = letra.charAt(0);
    	int i = 0; 
    	
    	while((flag == false) && (i < 25)) {
    		if((abc.charAt(i) == l) || (ABC.charAt(i) == l)) {
    			flag = true; }
    		i++; 
    	}
    	return i; }
	
	public void cambioTexto(String cad){
        JL1.setText(cad);       
    }
	
	public static void main(String args[]) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.
				swing.UIManager.getInstalledLookAndFeels()) {
	        if ("Nimbus".equals(info.getName())) {
	        	javax.swing.UIManager.setLookAndFeel(info.getClassName());break; }
	        }
		} catch (ClassNotFoundException ex) {
			 java.util.logging.Logger.getLogger(Main.class.getName()).
			 log(java.util.logging.Level.SEVERE, null, ex);
	    } catch (InstantiationException ex) {
	    	 java.util.logging.Logger.getLogger(Main.class.getName()).
	    	 log(java.util.logging.Level.SEVERE, null, ex);
	    } catch (IllegalAccessException ex) {
	         java.util.logging.Logger.getLogger(Main.class.getName()).
	         log(java.util.logging.Level.SEVERE, null, ex);
	    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	         java.util.logging.Logger.getLogger(Main.class.getName()).
	         log(java.util.logging.Level.SEVERE, null, ex);
	    }
	    java.awt.EventQueue.invokeLater(new Runnable() {
	    	public void run() {
	               new Main().setVisible(true); }
	        });
	    }
}