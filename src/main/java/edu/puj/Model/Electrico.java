package edu.puj.Model;

public class Electrico extends Carro implements Recargable{
    private static String tipodev= "Electrico";
    private static Double descuento=0.1;

    public Electrico(String placa){
        super(placa);
    }
    public String getTipo() {
        return tipodev;
    }
    public static Double getDescuento() {
        return descuento;
    }
    public long costorecarga(int minutos){
        long costo =0;
        costo=this.costo_minuto*minutos;
        return costo;
    }

}
