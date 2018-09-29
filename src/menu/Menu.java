package menu;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import jugador.*;

public class Menu {
	
   public static void main(String[] args) { 
      SwingUtilities.invokeLater(new Runnable() { 

         @Override 
         public void run() { 
            JFrame aplicacion = new MiAplicacionFrame(); 
            aplicacion.setVisible(true); 
            aplicacion.setLocationRelativeTo(null); 
         } 
      }); 
   } 
} 

class MiAplicacionFrame extends JFrame {
	
   //Mismas medidas que el juego final	
   public static final int DEFAULT_WIDTH = 850; 
   public static final int DEFAULT_HEIGHT = 590; 
   
   private Container contentPane; 
   private JMenuItem itemNuevo; 
   private JMenuItem itemConfig; 
   private JMenuItem itemSalir; 
   private JMenuItem itemAbout;
   JButton b1;
   
   public MiAplicacionFrame() 
   { 
      super("Batalla naval - El juego"); 
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT); 
      setDefaultCloseOperation(3); 
      
      contentPane = getContentPane(); 
      initComponents(); 
      addListeners(); 
      setVisible(true);

      setLayout(new BorderLayout());

      JLabel fondo=new JLabel(new ImageIcon("C:\\portada.jpg"));

      add(fondo);

      fondo.setLayout(new FlowLayout());
      fondo.setLayout(null);
      
      b1=new JButton("Jugar");
      b1.setBounds(370,465,100,35);
      b1.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
              B1ActionPerformed(evt);
          }
      });
      fondo.add(b1);
   } 
    
   private void initComponents() { 
      JMenuBar barraMenu = new JMenuBar(); 
      setJMenuBar(barraMenu); 
      
      JMenu menuJuego = new JMenu("Juego"); 
      barraMenu.add(menuJuego); 
      
      itemNuevo = new JMenuItem("Nueva partida", 'n'); 
      menuJuego.add(itemNuevo); 
      
      itemConfig = new JMenuItem("Instrucciones", 'c'); 
      menuJuego.add(itemConfig); 
      
      itemSalir = new JMenuItem("Salir", 's'); 
      menuJuego.add(itemSalir); 
      
      JMenu menuAyuda = new JMenu("Acerca de"); 
      barraMenu.add(menuAyuda);
      
      itemAbout = new JMenuItem("Créditos", 'a'); 
      menuAyuda.add(itemAbout); 
   } 
    
   private void addListeners() { 
      ActionListener listener = new ActionListener() { 

         @Override 
         public void actionPerformed(ActionEvent evt) 
         { 
            Object obj = evt.getSource(); 
            if (obj == itemNuevo) 
               itemNuevoActionPerformed(evt); 
            else if (obj == itemConfig) 
               itemConfigActionPerformed(evt); 
            else if (obj == itemSalir) 
               itemSalirActionPerformed(evt); 
            else if (obj == itemAbout) 
               itemAboutActionPerformed(evt); 
         } 
      }; 
      itemNuevo.addActionListener(listener); 
      itemConfig.addActionListener(listener); 
      itemSalir.addActionListener(listener); 
      itemAbout.addActionListener(listener);
   } 
    
   private void itemNuevoActionPerformed(ActionEvent evt) { 
	   Main irjuego=new Main(); 
	   irjuego.setVisible(true); 
   } 
   
   private void B1ActionPerformed(ActionEvent evt) { 
	   Main irjuego=new Main(); 
	   irjuego.setVisible(true); 
   } 
    
   private void itemConfigActionPerformed(ActionEvent evt) { 
      JOptionPane.showMessageDialog(this, "Representación del clásico juego de la 'Batalla Naval'.\n"
      		+ "Luego de entrar al juego, cada jugador debe colocar sus barcos en distintas "
      		+ "posiciones (vertical u horizontalmente)\nhasta completar un portaavione, un "
      		+ "acorazado, 2 submarinos y 2 destructores.\nCuando ambos contendientes hayan colocado todos "
      		+ "sus barcos, dará inicio el juego. Alternando turnos, cada jugador\ndeberá disparar "
      		+ "al tablero del oponente hasta derribar todos sus barcos.\nEl ganador será aquel que "
      		+ "derribe los barcos del equipo rival primero.", "Instrucciones y reglas", 
      		JOptionPane.ERROR_MESSAGE); 
   } 
    
   private void itemSalirActionPerformed(ActionEvent evt) { 
      System.exit(0); 
   } 
    
   private void itemAboutActionPerformed(ActionEvent evt) { 
      JOptionPane.showMessageDialog(this, "Creado por:\nFiorentino, Kevin\nPalazzo, Gabriel\nPizarro"
      		+ ", Julián Agustín\n\nPara la asignatura Redes y Comunicaciones - Universidad Nacional de Lanús"); 
   } 
} 