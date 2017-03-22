package monitorizacionInversa;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.util.ClientFactory;

public class ClienteMonitorizacion {
	static String serverIP;
	static String identificador;
	
	private static void cargarConfiguracion() {
		// leer de fichero y escribir variable 
		String cadena_leida;
		FileReader fich = null;
		try {
			fich = new FileReader("config.cfg");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      BufferedReader b = new BufferedReader(fich);
	      try {
	    	  cadena_leida = b.readLine();
	    	  cadena_leida=cadena_leida.split(" ")[1];
	    	  if (cadena_leida !=null){ identificador=cadena_leida;
	    	  System.out.println("Identificador de dispositivo cliente establecido: " +identificador);
	    	  } else System.out.println("No se ha detectado identificador de dispositivo.");
	    	  cadena_leida = b.readLine();
	    	
	    	  cadena_leida=cadena_leida.split(" ")[1];
	    	  if (cadena_leida !=null){ serverIP=cadena_leida;
	    	  System.out.println("Servidor asociado a dispositivo cliente establecido: " +serverIP);
	    	  }else System.out.println("No se ha detectado una direccion IP de servidor");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      try {
			b.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public static void main(String[] args) throws InterruptedException {
    	cargarConfiguracion();
    	String cadena_conexion="http://"+serverIP;
		System.out.println("Iniciando la conexion con el servidor ip: "+serverIP);
		System.out.println("..............................................................");
      XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
      try {
		config.setServerURL(new URL(cadena_conexion));
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		System.out.println("URL mal formada");
	}
      config.setEnabledForExtensions(true);
      config.setConnectionTimeout(60 * 1000);
      config.setReplyTimeout(60 * 1000);
      XmlRpcClient client = new XmlRpcClient();
      client.setConfig(config);
      ClientFactory factory = new ClientFactory(client);
      Services servicio = (Services) factory.newInstance(Services.class);
     
      while(true){
    	  boolean send=false;
    	  while (!send){
    		  send=false;
    	  try {
    		  servicio.notifica("I'm alive!... " +identificador+" y son: "+new Date());
    		  servicio.inserta(identificador, new Date());
    		  send=true;
    		  Thread.sleep(10000);
    		} catch (Exception e) {
    			send=false;
    		}
    	  	send=true;
    	  }
      }
    }
  }