package edu.puj.Model;

public class Carro extends vehiculos {
    private static String tipodev= "Carro";
    private static long precioporminuto=120;

    public Carro (String placa){
        super(placa);
    }
    public String getTipo() {
        return tipodev;
    }

    public long getPrecioporminuto() {
        return precioporminuto;
    }

    public long Calculartarifa (int minutos) {
        long totalapagar=0;
        totalapagar=precioporminuto*minutos;
        return minutos;
    }
}
