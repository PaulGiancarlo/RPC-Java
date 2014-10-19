package Servidor.ServidorServicios;

import Clientes.ClientesServicios.Calculadora;
import Servidor.ServidorServicios.Servicio;

 public class CalculadoraImpl extends Servicio implements Calculadora {
     
     public CalculadoraImpl() {
     }

     
     public double getNumeroPI(int decs) {
         return redondear(Math.PI, decs);
     }

     
     private double redondear(double num, int deci) {
         double p = Math.pow(10, deci);
         double d = num * p + 0.5d;
         int i = (int)d;
         d = ((double)i)/p;
         return d;
     }
 }