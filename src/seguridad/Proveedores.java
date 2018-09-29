package seguridad;
// InfoProveedores.java
import java.security.*;
import java.util.*;

class Proveedores {
  public static void main(String[] args) {
    boolean listarProps = false;
    if ( args.length > 0 && args[0].equals("-l") )
      listarProps=true;
    System.out.println("------------------------------------");
    System.out.println("Proveedores instalados en su sistema");
    System.out.println("------------------------------------");
    Provider[] listaProv = Security.getProviders();
    for (int i = 0; i < listaProv.length; i++) {
      System.out.println("N�m. proveedor : "    + (i + 1));
      System.out.println("Nombre         : "    + listaProv[i].getName());
      System.out.println("Versi�n        : "    + listaProv[i].getVersion());
      System.out.println("Informaci�n    :\n  " + listaProv[i].getInfo());
      System.out.println("Propiedades    :");
      if (listarProps) {
        Enumeration propiedades = listaProv[i].propertyNames();
        while (propiedades.hasMoreElements()) {
          String clave = (String) propiedades.nextElement();
          String valor = listaProv[i].getProperty(clave);
          System.out.println("  " + clave + " = " + valor);
        }
      }
      System.out.println("------------------------------------");
    }
  }
}