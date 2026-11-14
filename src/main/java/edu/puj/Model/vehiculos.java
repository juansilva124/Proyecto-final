package edu.puj.Model;
import edu.puj.Excepciones.PlacaInvalidaException;

public abstract class vehiculos {
    private String placa;

    public vehiculos(String placa) {
        this.placa = placa;
    }

    public String getPlaca(){
        return this.placa;
    }
    public abstract String getTipo();

    public static long getPrecioporminuto() {
        return 0;
    }

    public boolean laplacaesvalida() throws PlacaInvalidaException {
        if (this.placa.length() != 6) {
            throw new PlacaInvalidaException("La placa es invalida ya que no tiene 6 digitos");
        }

        for (int i = 0; i < 3; i++) {

            if (!Character.isLetter(placa.charAt(i))) {
                throw new PlacaInvalidaException(
                        "Los primeros 3 caracteres de la placa  deben ser letras"
                );
            }
            if (Character.isLowerCase(placa.charAt(i))) {
                throw new PlacaInvalidaException(
                        "Los primeros 3 caracteres de la placa  deben ser letras mayusculas"
                );
            }
        }

        for (int i = 3; i < 6; i++) {

            if (!Character.isDigit(placa.charAt(i))) {
                throw new PlacaInvalidaException(
                        "Los últimos 3 caracteres de la placa deben ser números"
                );
            }
        }


        return true;
    }
    public abstract long Calculartarifa(long minutos);
}
