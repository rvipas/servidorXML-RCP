package monitorizacionInversa;

import java.util.Date;

public interface Services {
    public void notifica(String msg);
    public boolean inserta(String ident, Date fecha);
  }
  
