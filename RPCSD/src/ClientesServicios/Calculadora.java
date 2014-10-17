package ClientesServicios;

import ClientesServicios.Servicio;

 public interface Calculadora extends Servicio {
     // retorna el numero PI con decs decimales
     double getNumeroPI(int decs);
 }
