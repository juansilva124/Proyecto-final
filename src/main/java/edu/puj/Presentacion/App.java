package edu.puj.Presentacion;

import edu.puj.Model.*;

import java.util.ArrayList;
public class App 
{

    public static void main(String[] args) {
        ArrayList<Tiquete>t=new ArrayList<Tiquete>();
        t=Archivos.Leermovimientostxt(t);
        System.out.println(t.get(0).getVehiculo().getTipo());
    }
}
