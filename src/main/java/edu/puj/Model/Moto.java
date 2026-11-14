package edu.puj.Model;

import edu.puj.Excepciones.PlacaInvalidaException;

public class Moto extends vehiculos{
    private static String tipodev= "Moto";
    private static long precioporminuto=50;
    public Moto (String placa){
        super(placa);
    }

    public String getTipo(){
        return tipodev;
    }
    public static long getPrecioporminuto() {
        return precioporminuto;
    }
    public boolean laplacaesvalida () throws PlacaInvalidaException {
        return true;
    }
    public long Calculartarifa(long minuto){
        return precioporminuto*minuto;
    }
}
