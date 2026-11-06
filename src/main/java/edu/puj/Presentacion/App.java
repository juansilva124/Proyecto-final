package edu.puj.Presentacion;

import edu.puj.Model.*;
import edu.puj.Negocio.Parqueadero;
import edu.puj.Persistencia.Archivos;

import java.util.ArrayList;
public class App 
{

    public static void main(String[] args) {
        Parqueadero Sistema = new Parqueadero();
        Sistema.setTiquetes(Archivos.Leermovimientostxt(Sistema.getTiquetes(),Sistema));
        System.out.println(Sistema.getTiquetes().get(0).getVehiculo().getTipo());
    }
}
