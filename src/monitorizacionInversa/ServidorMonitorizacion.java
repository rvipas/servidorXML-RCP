package monitorizacionInversa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class ServidorMonitorizacion {
	private static int port;
	
	private static void cargarConfiguracion() {
		
		// leer de fichero y escribir variable 
		String cadena_leida;
		FileReader fich = null;
		try {
			fich = new FileReader("port.cfg");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      BufferedReader b = new BufferedReader(fich);
	      try {
	    	  cadena_leida = b.readLine();
	    	  cadena_leida=cadena_leida.split(" ")[1];
	    	  if (cadena_leida !=null){ port=Integer.parseInt(cadena_leida);
	    	  System.out.println("Servidor configurado en puerto: " +port);
	    	  } else System.out.println("No se ha detectado ningún puerto sobre el que esperar conexiones.");
	    	  cadena_leida = b.readLine();
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

    public static void main(String[] args) throws Exception {
    	
    	System.out.println("Iniciando servidor XML-RCP...");
    	cargarConfiguracion();
      WebServer webServer = new WebServer(port);
      XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
      PropertyHandlerMapping phm = new PropertyHandlerMapping();
      phm.setVoidMethodEnabled(true);
      phm.addHandler(Services.class.getName(), ServicesImpl.class);
      xmlRpcServer.setHandlerMapping(phm);
      
      XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl)
      xmlRpcServer.getConfig();

      serverConfig.setEnabledForExtensions(true);
      serverConfig.setContentLengthOptional(false);
      Thread.sleep((long) (1000*Math.random())); //espera aleatoria, por que soy chulo
      webServer.start();
      System.out.println("Proceso iniciado:");
      System.out.println("   esperando eventos ....");
    }
    
    
  }