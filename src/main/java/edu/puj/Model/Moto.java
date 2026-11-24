package edu.puj.Model;

import edu.puj.Excepciones.PlacaInvalidaException;

public class Moto extends vehiculos{
    private static String tipodev= "Moto";
    private static long precioporminuto=80;
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
        if (getPlaca().length() != 6) {
            throw new PlacaInvalidaException("La placa es invalida ya que no tiene 6 digitos");
        }

        for (int i = 0; i < 3; i++) {

            if (!Character.isLetter(getPlaca().charAt(i))) {
                throw new PlacaInvalidaException(
                        "Los primeros 3 caracteres de la placa  deben ser letras"
                );
            }
            if (Character.isLowerCase(getPlaca().charAt(i))) {
                throw new PlacaInvalidaException(
                        "Los primeros 3 caracteres de la placa  deben ser letras mayusculas"
                );
            }
        }

        for (int i = 3; i < 6; i++) {

            if (!Character.isDigit(getPlaca().charAt(i))&&i!=5) {
                throw new PlacaInvalidaException("Los dos caracteres antes del ultimo digito de la placa deben ser números");
            } else if (i==5&&!Character.isLetter(getPlaca().charAt(i))) {
                throw new PlacaInvalidaException("El ultimo caracter de la placa debe ser una letra");
            } else if (i==5&&Character.isLowerCase(getPlaca().charAt(i))) {
                throw new PlacaInvalidaException(
                            "La ultima letra de la placa debe estar en mayusculas"
                );
            }
        }


        return true;
    }
    public long Calculartarifa(long minuto){
        return precioporminuto*minuto;
    }
}
