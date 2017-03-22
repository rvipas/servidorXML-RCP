package monitorizacionInversa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import com.mysql.jdbc.ResultSet;

public class ServicesImpl implements Services {

    public void notifica(String msg) {
      System.out.println(msg);
    }

	@Override
	public boolean inserta(String ident, Date fecha) {
		// este metodo realizara la insercion en la base de datos
	    	try
			{
	    		System.out.println("Insertando en BBDD");
			   Class.forName("com.mysql.jdbc.Driver");
			   Connection conexion = DriverManager.getConnection ("jdbc:mysql://localhost/app","root","");
			   java.sql.Statement s = conexion.createStatement(); 
		 
			   java.sql.ResultSet result = s.executeQuery ("select * from mensajes WHERE identificador='"+ident+"';");
			   boolean existe=false;
			   if (result.next()) existe=true;
			   java.util.Date dt = new java.util.Date();
			   java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			   String currentTime = sdf.format(dt);
			   
			   System.out.println("Fecha: "+currentTime);
			   //boolean rs = s.execute ("UPDATE mensajes SET identificador='ratoncillo1' WHERE identificador='ratoncillo1';");
			   //comprobar si existe
			   
			   if(existe){
			   boolean rs = s.execute ("UPDATE mensajes SET ultimo_mensaje='"+currentTime +"' WHERE identificador='"+ident+"';");
			    //si no actualiza  no existe crear primero
			   }
			    else{
			    	boolean rs = s.execute ("INSERT INTO mensajes (identificador, ultimo_mensaje) VALUES ('"+ident+"', '"+currentTime+"');");
			    }//boolean rs = s.execute ("INSERT INTO mensajes (identificador, ultimo_mensaje) VALUES ('"+ident+"', '"+currentTime+"');");
			/*//Recorremos el resultado, mientras haya registros para leer, y escribimos el resultado en pantalla. +
			   while (rs.next()) 
			   { 
			       System.out.println (rs.getString (1)+ " " + rs.getDate(2)); 
			   }*/
			} catch (Exception e)
			{
			   e.printStackTrace();
			}
		return false;
	}
    
    
  }