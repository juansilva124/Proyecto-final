package edu.puj.Model;

public class Electrico extends Carro implements Recargable{
    private static String tipodev= "Electrico";
    private static long precioporminuto= 108;

    public Electrico(String placa){
        super(placa);
    }
    public String getTipo() {
        return tipodev;
    }
    public static long getPrecioporminuto() {
        return precioporminuto;
    }
    public long costorecarga(long minutos){
        return costo_minuto*minutos;
    }
    public long Calculartarifa (long minutos) {
        long totalapagar=0;
        totalapagar=precioporminuto*minutos;
        return totalapagar;

    }

}
