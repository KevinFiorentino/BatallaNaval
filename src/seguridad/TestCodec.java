package seguridad;

public class TestCodec {
	
	public static void main(String args[]) {
		
	try{	
		
		//Ejemplo Encrip/Desencrip con un Mensaje Tipico del Juego
		
	//Msj: Comando + Fila + Columna + IDCliente + Dirección + Tamaño Barco 
		
		String msj = "POS07031H5";
		System.out.println("Mensaje Inicial: " + msj);
		
		String msjEncrip = CommonsCodec.Encriptar(msj);
		System.out.println("Mensaje Encriptado: " + msjEncrip);
		
		String msjDesencrip = CommonsCodec.Desencriptar(msjEncrip);
		System.out.println("Mensaje Desencriptado: " + msjDesencrip);
	
	} catch(Exception e) {}
		
	}

}